import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ClienteXat {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Port: ");
        int port = sc.nextInt();
        sc.nextLine();

        System.out.println("Paraula clau: ");
        String paraulaClau = sc.nextLine();

        try (Socket socket = new Socket("127.0.0.1", port);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);


        ){

            pw.println(paraulaClau);
            System.out.println("Inicialitzant Client..");
            System.out.println("Inicialitzant Chat...");

            boolean continuar = true;

            while(continuar){
                System.out.println("Enviar al Servidor:");
                String msg = sc.nextLine();
                pw.println(msg);

                if (msg.equals(paraulaClau)) {
                    System.out.println("PARAULA CLAU! Tancant el Xat.. ");
                    continuar = false;

                } else {
                   String resposta = br.readLine();

                   if  (resposta == null) {
                       System.out.println("El Servidor ha tancat el Xat..");
                       continuar = false;

                   } else  {
                       System.out.println("Rebut del Servidor: " + resposta);

                       if (resposta.equals(paraulaClau)) {
                           System.out.println("PARAULA CLAU! Tancant el Xat.. ");
                           continuar = false;
                       }
                   }
                }
            }

        } catch (IOException e) {
            System.out.println("Error del Servidor..");

        } finally {
            System.out.println("Adeu Siau!");
            sc.close();
        }
    }
}