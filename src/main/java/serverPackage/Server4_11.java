package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server4_11 {

    private static final int PORT = 1234;

    // Liste des clients
    private static final Set<SocketAddress> clients =
            ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {

        try {
            DatagramSocket socket =
                    new DatagramSocket(new InetSocketAddress(PORT));

            System.out.println("Serveur Chat UDP demarre sur le port " + PORT);

            while (true) {

                byte[] buffer = new byte[1024];

                DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);

                SocketAddress adresseClient = packet.getSocketAddress();

                // Enregistrement du client
                boolean nouveauClient = clients.add(adresseClient);

                if (nouveauClient) {
                    System.out.println("Nouveau client : " + adresseClient);
                }

                String message = new String(
                        packet.getData(),
                        packet.getOffset(),
                        packet.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Message recu : " + message);

                // Diffusion aux autres clients
                byte[] donnees =
                        message.getBytes(StandardCharsets.UTF_8);

                for (SocketAddress client : clients) {

                    if (!client.equals(adresseClient)) {

                        DatagramPacket diffusion = new DatagramPacket(
                                donnees,
                                donnees.length,
                                client
                        );

                        socket.send(diffusion);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
