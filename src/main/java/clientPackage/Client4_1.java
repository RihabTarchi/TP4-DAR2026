
package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Client4_1 {

    public static void main(String[] args) {

        final String IP_SERVEUR = "127.0.0.1";
        final int PORT_SERVEUR = 1234;

        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress adresseServeur =
                    InetAddress.getByName(IP_SERVEUR);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Entrez votre nom : ");
            String nom = scanner.nextLine();

            System.out.println("Bienvenue " + nom);
            System.out.println("Tapez vos messages (exit pour quitter)");

            while (true) {

                System.out.print(nom + " : ");
                String texte = scanner.nextLine();

                if (texte.equalsIgnoreCase("exit")) {
                    break;
                }

                String message = "[" + nom + "] : " + texte;

                byte[] donnees =
                        message.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet = new DatagramPacket(
                        donnees,
                        donnees.length,
                        new InetSocketAddress(adresseServeur, PORT_SERVEUR)
                );

                socket.send(packet);
            }

            socket.close();
            scanner.close();

            System.out.println("Client deconnecte.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
