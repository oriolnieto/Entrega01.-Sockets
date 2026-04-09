import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("A quin port et vols conectar? (Recomanació: 1234)");
            int port = sc.nextInt();
            sc.nextLine();

            System.out.println("Quina es la paraula clau per voler tancar la connexió? ");
            int paraulaClau = sc.nextInt();

            Socket socket = new Socket("127.0.0.1", port);
            System.out.println("Iniciando servidor... OK");

            // Salida de datos
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Enviando Mensaje... OK");
            salida.println("Hola servidor");

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));

            String mensaje = entrada.readLine();
            boolean estat = true;

            while (estat) {
                mensaje = sc.nextLine();
                System.out.println("Enviando Mensaje... OK");
                salida.println(mensaje);

                if (mensaje.equalsIgnoreCase(paraulaClau + "")) {
                    System.out.println("Palabra Clave detectada, cerrando connexión... ");
                    salida.println(paraulaClau);
                    estat = false;


                } else {
                    String respuesta = sc.nextLine();

                    if (mensaje == null) {
                        System.out.println("El cliente ha cerrado la connexión... ");
                        estat = false;

                    } else {
                        System.out.println("El servidor respondió: " + respuesta);

                        if (respuesta.equalsIgnoreCase(paraulaClau + "")) {
                            System.out.println("Palabra Clave detectada, cerrando connexión... ");
                            salida.println(paraulaClau);
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