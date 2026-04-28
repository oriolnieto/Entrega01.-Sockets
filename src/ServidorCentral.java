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

        System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
        int port = Integer.parseInt(sc.nextLine());

        System.out.println("Quants clients vols que tingui la sala? ");
        int maxClients = sc.nextInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at port: " + port);
            System.out.println("Servidor criado com sucesso! (eu farei)");

            while (servidorActiu) {

                if (salaBuida && filConversa.isEmpty()) {
                    System.out.println("NO CLIENTS, CLOSING THE SERVER");
                    servidorActiu = false;
                }

                serverSocket.setSoTimeout(1000); // timeout per fer refresh del bucle i tornar a evaluar la condició


                if (servidorActiu) {
                    try {
                        if (filConversa.size() < maxClients) {
                            Socket socket = serverSocket.accept();
                            salaBuida = true;
                            System.out.println("CONNECTION FROM CLIENT: " + (filConversa.size() + 1));

                            FilConversa filConversa1 = new FilConversa();
                            filConversa.add(filConversa1);
                            filConversa1.start();
                        }

                    } catch (SocketException e) {
                        // el timeout gestiona l'exepció i en cas d'error es fa refresh del bucle per evaluar la condició novament

                    }

                }

            }

        } catch (IOException e) {
            System.out.println("ERROR: Could not listen on port: " + port);


        } finally {
            System.out.println("adeusiau! :D");
            sc.close();
        }

    }


    public static void tancarServidor() {
        servidorActiu = true;
        synchronized (filConversa) {
            for (FilConversa f : filConversa) {
                f.adeuServidor();
        }


        }
    }
}
