import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Correo> buffer;
    private final int capacidad;
    
    public Buzon (int capacidad) {
        this.capacidad = capacidad;
        buffer = new LinkedList<>();
    }

    public synchronized void ponerPasiva(Correo correo, Thread thread){
        while (capacidad == buffer.size()){
            try {
                System.out.println(thread.getName()+": Esperando que se libere espacio...");
                wait();
                System.out.println(thread.getName()+": Fin de espera.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(thread.getName()+": Puso el correo "+correo.getId()+" en el buzón.");
        buffer.add(correo);
        notify();
    }

    public synchronized Correo quitarPasiva(Thread thread){
        while (buffer.isEmpty()) {
            try {
                System.out.println(thread.getName()+": Esperando correos...");
                wait();
                System.out.println(thread.getName()+": Fin de espera.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Correo correo = buffer.poll();
        System.out.println(thread.getName()+": Recogió el correo "+correo.getId()+" del buzón.");
        notify();
        return correo;
    }

    public synchronized void ponerSemiactiva(Correo correo, Thread thread){
        System.out.println(thread.getName()+": Intentando poner el correo "+correo.getId()+" en el buzón.");
        while (capacidad == buffer.size()){
            Thread.yield();            
        }
        System.out.println(thread.getName()+": Puso el correo "+correo.getId()+" en el buzón.");
        buffer.add(correo);
        notify();
    }
}