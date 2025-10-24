public class Filtro extends Thread {
    private static int nEmisores = 0;
    private static int emisoresFinalizados = 0;
    private Buzon buzonDeEntrada;
    private Buzon buzonDeCuarentena;
    private Buzon buzonDeSalida;

    public Filtro(String name, Buzon entrada, Buzon cuarentena, Buzon salida) {
        super(name);
        this.buzonDeEntrada = entrada;
        this.buzonDeCuarentena = cuarentena;
        this.buzonDeSalida = salida;
    }

    @Override
    public void run() {
        while (emisoresFinalizados < nEmisores || nEmisores == 0) {
            Correo correo = buzonDeEntrada.quitar(this);
            String id = correo.getId();
            String tipo = id.substring(id.length()-3);
            if (tipo.equals("000")) {
                // Mensaje de inicio
                nEmisores++;
                System.out.println(getName()+": Nuevo cliente emisor.");
            } else if (tipo.equals("999")) {
                emisoresFinalizados++;
                System.out.println(getName()+": Emisor finalizado.");
            } else {
                if (correo.isSpam()) {
                    // TODO: PONER EN BUZÓN DE CUARENTENA
                } else {
                    // TODO: PONER EN BUZÓN DE ENTREGA
                }
            }
        }
    }
}