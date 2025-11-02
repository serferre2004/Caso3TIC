public class Main {
    public static void main(String[] args) {
        BuzonDeEntrada buzonDeEntrada = new BuzonDeEntrada("entrada", 2);
        BuzonDeCuarentena buzonDeCuarentena = new BuzonDeCuarentena("cuarentena");
        BuzonDeEntrega buzonDeEntrega = new BuzonDeEntrega("entrega", 10, 2);
        Emisor emisor1 = new Emisor("Emisor 1", "1001", 3, buzonDeEntrada);
        Emisor emisor2 = new Emisor("Emisor 2", "1002", 3, buzonDeEntrada);
        Filtro filtro = new Filtro("Filtro de spam", buzonDeEntrada, buzonDeCuarentena, buzonDeEntrega);
        ManejadorDeCuarentena manejadorCuarentena = new ManejadorDeCuarentena("Manejador de cuarentena", buzonDeCuarentena, buzonDeEntrega);
        Servidor servidor1 = new Servidor("Servidor 1", buzonDeEntrega);
        Servidor servidor2 = new Servidor("Servidor 2", buzonDeEntrega);
        emisor1.start();
        emisor2.start();
        filtro.start();
        manejadorCuarentena.start();
        servidor1.start();
        servidor2.start();
    }
}