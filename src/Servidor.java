import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
            int port = sc.nextInt();
            ServerSocket servidor = new ServerSocket(port);
            System.out.println("Iniciando servidor... OK");
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado... OK");

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            String mensaje = entrada.readLine();
            System.out.println("Cliente dice: " + mensaje);

            // Salida de datos
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Enviando Mensaje... OK");
            salida.println("Hola desde el servidor");

            socket.close();
            servidor.close();
            System.out.println("Cerrando servidor... OK");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}