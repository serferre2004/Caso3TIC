public class BuzonDeEntrega extends Buzon{
    private final int capacidad;
    private final int nServidores;

    public BuzonDeEntrega(String name, int capacidad, int nServidores) {
        super(name);
        this.capacidad = capacidad;
        this.nServidores = nServidores;
    }

    @Override
    public synchronized void poner(Correo correo, Thread thread){
        while (capacidad == buffer.size()){
            log(thread.getName()+": Esperando que se libere espacio...");
            Thread.yield();
            log(thread.getName()+": Fin de espera.");
        }
        log(thread.getName()+": Puso el correo "+correo.getId()+" en el buzón.");
        if (correo.getId().equals("9999999")) {
            for (int i = 0; i < nServidores; i++) {
                buffer.add(correo);
            }
        } else {
            buffer.add(correo);
        }
    }
    
    @Override
    public Correo quitar(Thread thread) {
        while (buffer.isEmpty()) {
            // Servidor esperando
        }
        synchronized (this) {
            Correo correo = buffer.poll();
            log(thread.getName()+": Recogió el correo "+correo.getId()+" del buzón.");
            return correo;
        }
    }
}
