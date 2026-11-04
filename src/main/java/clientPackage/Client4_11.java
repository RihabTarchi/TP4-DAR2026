package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client4_11 {

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

            // Thread de reception
            Thread reception = new Thread(() -> {

                try {

                    while (!socket.isClosed()) {

                        byte[] buffer = new byte[1024];

                        DatagramPacket packet =
                                new DatagramPacket(buffer, buffer.length);

                        socket.receive(packet);

                        String message = new String(
                                packet.getData(),
                                packet.getOffset(),
                                packet.getLength(),
                                StandardCharsets.UTF_8
                        );

                        System.out.println("\n" + message);
                        System.out.print(nom + " : ");
                    }

                } catch (Exception e) {
                    if (!socket.isClosed()) {
                        e.printStackTrace();
                    }
                }

            });

            reception.start();

            System.out.println("Bienvenue dans le chat UDP !");
            System.out.println("Tapez exit pour quitter.");

            // Envoi des messages
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

            System.out.println("Vous avez quitte le chat.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
