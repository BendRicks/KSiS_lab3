import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

public class EchoServer {

    public static void main(String[] args) {
        try {
            final DatagramSocket echoServer = new DatagramSocket(7);
            new Thread(() -> {
                int length = 65536;
                byte[] buffer = new byte[length];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, length);
                    try {
                        echoServer.receive(packet);
                        DatagramPacket sendingPacket = new DatagramPacket(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()),
                                packet.getLength(), packet.getSocketAddress());
                        echoServer.send(sendingPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            String command = "";
            Scanner sis = new Scanner(System.in);
            while (!command.equals("stop")){
                command = sis.nextLine();
            }
            echoServer.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
