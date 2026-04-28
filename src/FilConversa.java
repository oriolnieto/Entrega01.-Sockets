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
            throw new RuntimeException(e);
        }

    }

    public void run() {

        try (BufferedWriter bw = new BufferedWriter(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in);
        ) {
            String msgClau = bw.readLine();
            System.out.println("INICIALIZING CHAT");

            while(ServidorCentral.servidorActiu) {
                String msgClient = bw.nextLine();
                if (msgClient == null) {
                    ServidorCentral.filConversa.remove(this);
                    return;
                }

                System.out.println("El client diu: " + msgClient);

                if (msgClau.equals(ServidorCentral.paraulaClau)) {
                    System.out.println("PARAULA CLAU! TANCANT SERVIDOR");
                    ServidorCentral.tancarServidor();
                }
            }

        } catch (IOException e) {

        } finally {
            ServidorCentral.filConversa.remove(this);
            adeuSocket();
        }

        }






}
