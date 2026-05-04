import java.io.*;
import java.net.*;
import java.util.Scanner;


public class FilConversa extends Thread {
    private Socket socket;
    private String msgClau;

    public FilConversa(Socket socket) {
        this.socket = socket;
    }

    public void adeuSocket() {
        try {
            socket.close();

        } catch (IOException e) {
            System.out.println("Error al carregar el socket! " + e.getMessage());
        }

    }

    @Override
    public void run() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in);
        ) {
            msgClau = br.readLine();
            System.out.println("Inicialitzant Chat!");

            while(ServidorCentral.servidorActiu) {
                String msgClient = br.readLine();
                if (msgClient == null) {

                    return;
                }

                System.out.println("El client diu: " + msgClient);

                if (msgClau.equals(msgClient)) {
                    System.out.println("PARAULA CLAU!");
                    return;
                }

                if (msgClau.equals(ServidorCentral.paraulaClau)) {
                    System.out.println("PARAULA CLAU! Tancant Servidor");
                    ServidorCentral.tancarServidor();
                    return;
                }

                System.out.println("Enviant missatge al Client..");
                String resposta = sc.nextLine();
                pw.println(resposta);

                if (resposta.equals(msgClau)) {
                    ServidorCentral.tancarServidor();
                    return;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al fer run del socket! " + e.getMessage());
        }
        finally {
            ServidorCentral.filConversa.remove(this);
            System.out.println("S'esta tancant el xat!");
            adeuSocket();
        }
        }
}