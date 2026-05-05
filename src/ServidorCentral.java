import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ServidorCentral {
    public static String paraulaClau = "adeuandreu";
    public static List<FilConversa> filConversa = Collections.synchronizedList(new ArrayList<>());
    public static boolean servidorActiu = true;
    public static boolean salaBuida = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (args.length < 1) {
            System.out.println("Error: Falta l'argument del número de clients.");
            return;
        }
        int maxClients = Integer.parseInt(args[0]);

        System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
        int port = Integer.parseInt(sc.nextLine());

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at port: " + port);
            System.out.println("Servidor creat de forma correcta!");

            while (servidorActiu) {

                if (salaBuida && filConversa.isEmpty()) {
                    System.out.println("No hi han clients, tancant el server..");
                    servidorActiu = false;
                }

                serverSocket.setSoTimeout(1000);

                if (servidorActiu) {
                    try {
                        if (filConversa.size() < maxClients) {
                            Socket socket = serverSocket.accept();
                            salaBuida = true;
                            System.out.println("Connexió del Client: " + (filConversa.size() + 1));

                            FilConversa filConversa1 = new FilConversa(socket);
                            filConversa.add(filConversa1);
                            filConversa1.start();
                        }

                    } catch (SocketTimeoutException e) {
                        //gestionar sense dir res per no tallar el bucle
                    } catch (SocketException e) {
                        System.out.println("Error al conectar el servidor! " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Problema al Port: " + port + " | "  + e.getMessage());
        } finally {
            System.out.println("Adeu Andreu! :D");
            sc.close();
        }
    }
    public static void tancarServidor() {
        servidorActiu = false;
        synchronized (filConversa) {
            for (FilConversa fc : filConversa) {
                fc.adeuSocket();
                System.out.println("Tancant server..");
            }
            System.out.println("Adeu!");
            System.exit(0);
        }
    }
}