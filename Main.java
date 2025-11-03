import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== SISTEMA DE CORREO ELECTRÓNICO =====");
        File myObj = new File("config.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            int nEmisores = myReader.nextInt();
            int nMensajesPorEmisor = myReader.nextInt();
            int nFiltros = myReader.nextInt();
            int nServidores = myReader.nextInt();
            int capacidadBuzonEntrada = myReader.nextInt();
            int capacidadBuzonEntrega = myReader.nextInt();

            BuzonDeEntrada buzonDeEntrada = new BuzonDeEntrada("entrada", capacidadBuzonEntrada);
            BuzonDeCuarentena buzonDeCuarentena = new BuzonDeCuarentena("cuarentena");
            BuzonDeEntrega buzonDeEntrega = new BuzonDeEntrega("entrega", capacidadBuzonEntrega, nServidores);

            Emisor emisores[] = new Emisor[nEmisores];
            for (int i = 0; i < nEmisores; i++) {
                String nombreEmisor = "Emisor " + (i + 1);
                String idEmisor = "100" + (i + 1);
                emisores[i] = new Emisor(nombreEmisor, idEmisor, nMensajesPorEmisor, buzonDeEntrada);
                emisores[i].start();
            }

            Filtro filtros[] = new Filtro[nFiltros];
            for (int i = 0; i < nFiltros; i++) {
                String nombreFiltro = "Filtro " + (i + 1);
                filtros[i] = new Filtro(nombreFiltro, buzonDeEntrada, buzonDeCuarentena, buzonDeEntrega);
                filtros[i].start();
            }

            ManejadorDeCuarentena manejadorCuarentena = new ManejadorDeCuarentena("Manejador de cuarentena", buzonDeCuarentena, buzonDeEntrega);
            manejadorCuarentena.start();

            Servidor servidores[] = new Servidor[nServidores];
            for (int i = 0; i < nServidores; i++) {
                String nombreServidor = "Servidor " + (i + 1);
                servidores[i] = new Servidor(nombreServidor, buzonDeEntrega);
                servidores[i].start();
            }

            for (int i = 0; i < nEmisores; i++) {
                emisores[i].join();
            }
            for (int i = 0; i < nFiltros; i++) {
                filtros[i].join();
            }
            manejadorCuarentena.join();
            for (int i = 0; i < nServidores; i++) {
                servidores[i].join();
            }

            System.out.println("===== SIMULACIÓN TERMINADA =====");
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}