import java.util.Random;
public class Emisor extends Thread {
    private final String id;
    private final int nCorreos;
    private Buzon buzonDeEntrada;
    private int contadorCorreos;
    private static Random random = new Random();

    public Emisor(String name, String id, int nCorreos, Buzon buzonDeEntrada) {
        super(name);
        this.id = id;
        this.nCorreos = nCorreos;
        this.buzonDeEntrada = buzonDeEntrada;
        this.contadorCorreos = 0;
        System.out.println("Nuevo emisor ("+name+") creado");
    }

    @Override
    public void run () {
        enviarMensajeInicio();
        System.out.println(getName()+": Envió un mensaje de inicio");
        while (contadorCorreos < nCorreos){
            contadorCorreos++;
            String idCorreo = id+String.format("%03d", contadorCorreos);
            Correo mensaje = new Correo(idCorreo, random.nextBoolean());
            System.out.println(getName()+": Envió el correo "+idCorreo);
            buzonDeEntrada.poner(mensaje, this);
        }
        enviarMensajeFin();
        System.out.println(getName()+": Envió un mensaje de fin");
    }

    private void enviarMensajeInicio() {
        // Por convención tendra el id del cliente emisor seguido por 000 (ej: 1001000)
        Correo mensajeInicio = new Correo(id+"000", false);
        buzonDeEntrada.poner(mensajeInicio, this);
    }
    
    private void enviarMensajeFin() {
        // Por convención tendra el id del cliente emisor seguido por 999 (ej: 1001999)
        Correo mensajeFin = new Correo(id+"999", false);
        buzonDeEntrada.poner(mensajeFin, this);
    }


}