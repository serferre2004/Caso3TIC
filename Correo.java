public class Correo {
    private final String id;
    private final boolean spam;

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
}