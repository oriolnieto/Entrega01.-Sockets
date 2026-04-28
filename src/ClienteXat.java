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
            System.out.println("INICIALIZING CLIENT...");
            System.out.println("INICIALIZING CHAT...");

            boolean continuar = true;

            while(continuar){
                System.out.println("SENDING TO SERVER...");
                String msg = sc.nextLine();
                pw.println(msg);

                if (msg.equals(paraulaClau)) {
                    System.out.println("PARAULA CLAU! CLOSING CHAT... ");
                    continuar = false;

                } else {
                   String resposta = br.readLine();

                   if  (resposta == null) {
                       System.out.println("SERVER HAS CLOSED THE CHAT...");
                       continuar = false;

                   } else  {
                       System.out.println("SENDING TO SERVER " + resposta);

                       if (resposta.equals(paraulaClau)) {
                           System.out.println("PARAULA CLAU! CLOSING CHAT... ");
                           continuar = false;
                       }
                   }
                }

            }

        } catch (IOException e) {
            System.out.println("ERRORE SERVER...");

        } finally {
            System.out.println("adeusiau!");
            sc.close();
        }

    }



}
