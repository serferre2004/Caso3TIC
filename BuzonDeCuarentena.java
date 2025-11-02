import java.util.LinkedList;

public class BuzonDeCuarentena extends Buzon {

    public BuzonDeCuarentena(String name) {
        super(name);
    }

    @Override
    public synchronized void poner(Correo correo, Thread thread){
        log(thread.getName()+": Asignó a "+correo.getId()+" "+correo.getTiempoCuarentena()+"s de cuarentena.");
        buffer.add(correo);
        log(thread.getName()+": Puso el correo "+correo.getId()+" en el buzón.");
    }

    @Override
    public Correo quitar(Thread thread){
        while (buffer.isEmpty()) {
            Thread.yield();
        }
        int i = 0;
        while (i < buffer.size()) {
            Correo correo = ((LinkedList<Correo>) buffer).get(i);
            correo.reducirTiempo();
            if (correo.getTiempoCuarentena() == 0) {
                buffer.remove(correo);
                log(thread.getName()+": Recogió el correo "+correo.getId()+" del buzón.");
                return correo;
            } else {
                i++;
            }
        }
        return null;
    }

    public synchronized boolean tieneCorreosEnCuarentena() {
        return !buffer.isEmpty();
    }
}