public class Main {
    public static void main(String[] args) {
        Buzon buzon = new Buzon(6);
        Emisor emisor1 = new Emisor("Emisor 1", "1001", 10, buzon);
        Emisor emisor2 = new Emisor("Emisor 2", "1002", 10, buzon);
        Filtro filtro = new Filtro("Filtro de spam", buzon, buzon, buzon);
        emisor1.start();
        emisor2.start();
        filtro.start();
    }
}