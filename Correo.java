public class Correo {
    private final String id;
    private final boolean spam;
    private int tiempoCuarentena;


    public Correo (String id, boolean spam) {
        this.id = id;
        this.spam = spam;
    }

    public boolean isSpam(){
        return spam;
    }

    public String getId() {
        return id;
    }

    public int getTiempoCuarentena() {
        return tiempoCuarentena;
    }

    public void setTiempoCuarentena(int tiempoCuarentena) {
        this.tiempoCuarentena = tiempoCuarentena;
    }

    public void reducirTiempo() {
        if (tiempoCuarentena > 0) this.tiempoCuarentena--;
    }
}