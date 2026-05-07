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
    public static Scanner sc = new Scanner(System.in);
    public static List<FilConversa> colaResposta = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Falta l'argument del número de clients.");
            return;
        }
        int maxClients = Integer.parseInt(args[0]);

        System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
        int port = Integer.parseInt(sc.nextLine());

        Thread respondedor = new Thread(() -> {
            while (servidorActiu) {
                FilConversa fc = null;

                synchronized (colaResposta) {
                    while (colaResposta.isEmpty() && servidorActiu) {
                        try { colaResposta.wait(); } catch (InterruptedException e) { return; }
                    }
                    if (!colaResposta.isEmpty()) {
                        fc = colaResposta.remove(0);
                    }
                }

                if (fc != null && !fc.socket.isClosed() && servidorActiu) {
                    System.out.println("Enviar missatge al Client:");
                    String resposta = sc.nextLine();

                    if (resposta.equals(paraulaClau)) {
                        System.out.println("PARAULA CLAU SERVIDOR! Tancant tot..");
                        tancarServidor();
                    } else if (resposta.equals(fc.getMsgClau())) {
                        System.out.println("PARAULA CLAU CLIENT! Tancant xat del client..");
                        fc.enviarResposta(resposta);
                        fc.adeuSocket();
                    } else {
                        fc.enviarResposta(resposta);
                    }

                    synchronized (fc) {
                        fc.notify();
                    }
                }
            }
        });
        respondedor.setDaemon(true);
        respondedor.start();

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
            System.out.println("ERROR: Problema al Port: " + port + " | " + e.getMessage());
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