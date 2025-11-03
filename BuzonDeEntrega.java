public class BuzonDeEntrega extends Buzon{
    private final int capacidad;
    private final int nServidores;

    public BuzonDeEntrega(String name, int capacidad, int nServidores) {
        super(name);
        this.capacidad = capacidad;
        this.nServidores = nServidores;
    }

    @Override
    public boolean poner(Correo correo, Thread thread){
        log(thread.getName()+": Intentando poner el correo "+correo.getId()+" en el buzón.");
        while (capacidad == buffer.size()){
            Thread.yield();
        }
        while (correo.getId().equals("9999999") && capacidad - buffer.size() < nServidores){
            // Espera a que haya espacio para poner los mensajes de fin para cada servidor
            Thread.yield();
        }
        synchronized (this) {
            if (buffer.size() == capacidad) return false;
            log(thread.getName()+": Puso el correo "+correo.getId()+" en el buzón.");
            if (correo.getId().equals("9999999")) {
                for (int i = 0; i < nServidores; i++) {
                    buffer.add(correo);
                }
            } else {
                buffer.add(correo);
            }
            return true;
        }
    }
    
    @Override
    public Correo quitar(Thread thread) {
        while (buffer.isEmpty()) {
            // Servidor esperando
        }
        synchronized (this) {
            if (buffer.isEmpty()) {
                return null; // Se pueden dar condiciones de carrera aquí
            }
            Correo correo = buffer.poll();
            log(thread.getName()+": Recogió el correo "+correo.getId()+" del buzón.");
            return correo;
        }
    }
}
