import java.util.Random;

public class Filtro extends Thread {
    private static int nEmisores = 0;
    private static int emisoresFinalizados = 0;
    private static boolean activos = true;
    private BuzonDeEntrada buzonDeEntrada;
    private static BuzonDeCuarentena buzonDeCuarentena;
    private static BuzonDeEntrega buzonDeEntrega;
    private Random random = new Random();

    public Filtro(String name, BuzonDeEntrada entrada, BuzonDeCuarentena cuarentena, BuzonDeEntrega entrega) {
        super(name);
        this.buzonDeEntrada = entrada;
        this.buzonDeCuarentena = cuarentena;
        buzonDeEntrega = entrega;
    }

    @Override
    public void run() {
        while (emisoresFinalizados < nEmisores || nEmisores == 0) {
            Correo correo = buzonDeEntrada.quitar(this);
            String id = correo.getId();
            String tipo = id.substring(id.length()-3);
            if (tipo.equals("000")) {
                // Mensaje de inicio
                nEmisores++;
                System.out.println(getName()+": Nuevo cliente emisor.");
            } else if (tipo.equals("999")) {
                // Mensaje de fin
                emisoresFinalizados++;
                System.out.println(getName()+": Emisor finalizado.");
            } else {
                if (correo.isSpam()) {
                    correo.setTiempoCuarentena(random.nextInt(10)+10);
                    buzonDeCuarentena.poner(correo, this);
                } else {
                    while (!buzonDeEntrega.poner(correo,this)){
                        // Reintenta poner el correo si se presenta condición de carrera
                        Thread.yield();
                    }
                }
            }
        }
        while (buzonDeCuarentena.tieneCorreosEnCuarentena()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (getActivos()){
            ponerMensajeDeFin();
            System.out.println("Filtros finalizado.");
        }
    }

    private synchronized static void ponerMensajeDeFin() {
        Correo correoFin = new Correo("9999999", false);
        while(!buzonDeEntrega.poner(correoFin, Thread.currentThread())){
            // Reintenta poner el correo si se presenta condición de carrera
            Thread.yield();
        }
        correoFin.setTiempoCuarentena(1);
        buzonDeCuarentena.poner(correoFin, Thread.currentThread());
        activos = false;
    }

    private static synchronized boolean getActivos() {
        return activos;
    }
}