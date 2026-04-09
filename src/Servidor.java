import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
            int port = sc.nextInt();
            sc.nextLine();

            System.out.println("Quina es la paraula clau per voler tancar la connexió? ");
            int paraulaClau = sc.nextInt();


            ServerSocket servidor = new ServerSocket(port);
            System.out.println("Iniciando servidor... OK");
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado... OK");

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));


            // Salida de datos
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            String mensaje = entrada.readLine();
            boolean estat = true;

            while (estat) {
                mensaje = entrada.readLine();

                if (mensaje == null) {
                    System.out.println("El cliente ha cerrado la connexión... ");
                    estat = false;

                } else {
                    System.out.println("Cliente dice: " + mensaje);

                    if (mensaje.equalsIgnoreCase(paraulaClau + "")) {
                        System.out.println("Palabra Clave detectada, cerrando connexión... ");
                        salida.println(paraulaClau);
                        estat = false;

                    } else {
                        String respuesta = sc.nextLine();
                        System.out.println("Enviando Mensaje... OK");
                        salida.println(respuesta);


                        if (respuesta.equalsIgnoreCase(paraulaClau + "")) {
                            System.out.println("Palabra Clave detectada, cerrando connexión... ");
                            salida.println(paraulaClau);
                            estat = false;
                        }
                    }

                }
            }

                socket.close();
                servidor.close();
                System.out.println("Cerrando servidor... OK");




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}