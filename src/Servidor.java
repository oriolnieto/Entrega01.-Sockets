import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("A quin port et vols conectar? (Recomanació: 1234)"); // Demanem el port..
            int port = Integer.parseInt(sc.nextLine());

            System.out.println("Quina es la paraula clau per voler tancar la connexió? ");  // Demanem la paraula clau per tancar la connexió..
            String paraulaClau = sc.nextLine();

            ServerSocket servidor = new ServerSocket(port); // Fem un ServerSocket amb el port especificat per l'usuari
            System.out.println("Iniciando servidor... OK");
            Socket socket = servidor.accept(); // Fem un Socket on el servidor accepta
            System.out.println("Cliente conectado... OK");

            // Entrada de dades
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Sortida de dades
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            String mensaje;
            boolean estat = true;

            while (estat) { // Mentres no es digui la paraula clau o  hi hagi missatge, està actiu.
                mensaje = entrada.readLine();

                if (mensaje == null) {
                    System.out.println("El cliente ha cerrado la connexión... ");
                    estat = false;

                } else {
                    System.out.println("Cliente dice: " + mensaje);

                    if (mensaje.equalsIgnoreCase(paraulaClau)) {
                        System.out.println("Palabra Clave detectada, cerrando connexión... ");
                        salida.println(paraulaClau);
                        estat = false;

                    } else {
                        System.out.print("Tú (servidor): ");
                        String respuesta = sc.nextLine();
                        System.out.println("Enviando Mensaje... OK"); // Enviem MD amb la linia escanejada previament..
                        salida.println(respuesta);

                        if (respuesta.equalsIgnoreCase(paraulaClau)) {
                            System.out.println("Palabra Clave detectada, cerrando connexión... ");
                            estat = false;
                        }
                    }
                }
            }

            sc.close();
            socket.close(); // Tanquem sockets i server..
            servidor.close();
            System.out.println("Cerrando servidor... OK");

        } catch (IOException e) {
            e.printStackTrace(); // Caçem errors.
        }
    }
}