import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            // Salida de datos
            PrintWriter salida = new
                    PrintWriter(socket.getOutputStream(), true);
            salida.println("Hola servidor");

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            String respuesta = entrada.readLine();
            System.out.println("El servidor respondió: " + respuesta);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}