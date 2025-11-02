import java.util.Random;

public class ManejadorDeCuarentena extends Thread {
    private Random random = new Random();
    private BuzonDeCuarentena buzonDeCuarentena;
    private BuzonDeEntrega buzonDeEntrega;
    private boolean activo = true;
    
    public ManejadorDeCuarentena(String name, BuzonDeCuarentena buzonDeCuarentena, BuzonDeEntrega buzonDeEntrega) {
        super(name);
        this.buzonDeCuarentena = buzonDeCuarentena;
        this.buzonDeEntrega = buzonDeEntrega;
    }

    @Override
    public void run() {
        while (activo) {
            Correo correo = buzonDeCuarentena.quitar(this);
            if (correo != null) {
                if (correo.getId().equals("9999999")) {
                    activo = false;
                    System.out.println(getName()+": Manejador de cuarentena finalizado.");
                    break;
                }
                int numeroDescarte = random.nextInt(20)+1;
                if (numeroDescarte % 7 == 0) {
                    System.out.println(getName()+": Descartó el correo "+correo.getId());
                } else {
                    buzonDeEntrega.poner(correo, this);
                }
            }
            try{
                sleep(1000); // El manejador corre cada segundo.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}