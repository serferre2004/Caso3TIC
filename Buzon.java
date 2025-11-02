import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Buzon {
    protected final String name;
    protected volatile Queue<Correo> buffer;
    
    public Buzon (String name) {
        this.name = name;
        buffer = new LinkedList<>();
        String fileName = name + "_log.txt";
        File file = new File(fileName);
        file.delete();
    }
    public abstract void poner(Correo correo, Thread thread);
    public abstract Correo quitar(Thread thread);
    
    protected void log(String message) {
        try (FileWriter writer = new FileWriter(name + "_log.txt", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}