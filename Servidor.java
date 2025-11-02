import java.util.Random;

public class Servidor extends Thread {
    private Random random = new Random();
    private BuzonDeEntrega buzonDeEntrega;
    private boolean activo = true;

    public Servidor(String name, BuzonDeEntrega buzonDeEntrega) {
        super(name);
        this.buzonDeEntrega = buzonDeEntrega;
    }

    @Override
    public void run() {
        while (activo) {
            Correo correo = buzonDeEntrega.quitar(this);
            if (correo.getId().equals("9999999")) {
                activo = false;
                System.out.println(getName()+": Servidor finalizado.");
                break;
            }
            leerCorreo(correo);
        }
    }

    private void leerCorreo(Correo correo) {
        try{
            sleep(random.nextInt(10000)+10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(getName()+": Leyó el correo "+correo.getId());
    }
}