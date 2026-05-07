import java.io.*;
import java.net.*;

public class FilConversa extends Thread {
    public Socket socket;
    private String msgClau;
    private PrintWriter pw;

    public FilConversa(Socket socket) {
        this.socket = socket;
    }

    public String getMsgClau() {
        return msgClau;
    }

    public void enviarResposta(String resposta) {
        if (pw != null) pw.println(resposta);
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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            pw = new PrintWriter(socket.getOutputStream(), true);
            msgClau = br.readLine();
            System.out.println("Inicialitzant Chat!");

            while (ServidorCentral.servidorActiu) {
                String msgClient = br.readLine();
                if (msgClient == null) {
                    return;
                }

                System.out.println("El client diu: " + msgClient);

                if (msgClient.equals(msgClau)) {
                    System.out.println("PARAULA CLAU!");
                    return;
                }

                synchronized (ServidorCentral.colaResposta) {
                    ServidorCentral.colaResposta.add(this);
                    ServidorCentral.colaResposta.notify();
                }

                synchronized (this) {
                    wait();
                }

                if (socket.isClosed()) return;
            }

        } catch (IOException | InterruptedException e) { // caçem dos tipus d'excepcions en un, optimitzat
            System.out.println("Error al fer run del socket! " + e.getMessage());
        } finally {
            ServidorCentral.filConversa.remove(this);
            System.out.println("S'esta tancant el xat!");
            adeuSocket();
        }
    }
}