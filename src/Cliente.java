import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
            int port = Integer.parseInt(sc.nextLine());

            System.out.println("Quina es la paraula clau per voler tancar la connexió? ");
            String paraulaClau = sc.nextLine();

            Socket socket = new Socket("127.0.0.1", port); // Port amb el host correcte.
            System.out.println("Conectado al servidor... OK");

            // Salida de datos
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));

            String mensaje;
            boolean estat = true;

            while (estat) {
                System.out.print("Tú (cliente): ");
                mensaje = sc.nextLine();
                System.out.println("Enviando Mensaje... OK");
                salida.println(mensaje);

                if (mensaje.equalsIgnoreCase(paraulaClau)) {
                    System.out.println("Palabra Clave detectada, cerrando connexión... ");
                    estat = false;

                } else {
                    String respuesta = entrada.readLine();

                    if (respuesta == null) {
                        System.out.println("El servidor ha cerrado la connexión... ");
                        estat = false;

                    } else {
                        System.out.println("El servidor respondió: " + respuesta);

                        if (respuesta.equalsIgnoreCase(paraulaClau)) {
                            System.out.println("Palabra Clave detectada, cerrando connexión... ");
                            estat = false;
                        }
                    }
                }
            }

            sc.close();
            socket.close();
            System.out.println("Cerrando cliente... OK");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}