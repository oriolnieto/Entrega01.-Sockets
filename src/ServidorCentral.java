import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ServidorCentral {
    public static String paraulaClau = "adeuandreu";
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
                Socket socket = serverSocket.accept();
            }




        } catch (Exception e) {

        }

    }
}
