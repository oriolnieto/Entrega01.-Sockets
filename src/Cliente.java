import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
            int port = sc.nextInt();
            Socket socket = new Socket("127.0.0.1", port);
            // Salida de datos
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Enviando Mensaje... OK");
            salida.println("Hola servidor");

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            String respuesta = entrada.readLine();
            System.out.println("El servidor respondió: " + respuesta);
            socket.close();
            System.out.println("Cerrando cliente... OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}