
public class BuzonDeEntrada extends Buzon {
    private final int capacidad;

    public BuzonDeEntrada(String name, int capacidad) {
        super(name);
        this.capacidad = capacidad;
    }

    @Override
    public synchronized boolean poner(Correo correo, Thread thread){
        while (capacidad == buffer.size()){
            try {
                log(thread.getName()+": Esperando que se libere espacio...");
                wait();
                log(thread.getName()+": Fin de espera.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        log(thread.getName()+": Puso el correo "+correo.getId()+" en el buzón.");
        buffer.add(correo);
        notify();
        return true;
    }

    @Override
    public synchronized Correo quitar(Thread thread){
        while (buffer.isEmpty()) {
            try {
                log(thread.getName()+": Esperando correos...");
                wait();
                log(thread.getName()+": Fin de espera.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Correo correo = buffer.poll();
        log(thread.getName()+": Recogió el correo "+correo.getId()+" del buzón.");
        notify();
        return correo;
    }
}