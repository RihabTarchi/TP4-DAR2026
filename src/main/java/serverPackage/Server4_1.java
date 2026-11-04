
package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server4_1 {

    public static void main(String[] args) {

        final int PORT = 1234;

        try {
            DatagramSocket socket =
                    new DatagramSocket(new InetSocketAddress(PORT));

            System.out.println("Serveur UDP demarre...");
            System.out.println("En attente des messages sur le port " + PORT);

            while (true) {

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

                System.out.println(
                        "Message recu de "
                                + packet.getAddress().getHostAddress()
                                + ":" + packet.getPort()
                );

                System.out.println(message);
                System.out.println("--------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
