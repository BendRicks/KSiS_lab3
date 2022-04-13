import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        try {
            final DatagramSocket echoSocket = new DatagramSocket(39885);
            new Thread(() -> {
                int length = 65536;
                byte[] buffer = new byte[length];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, length);
                    try {
                        echoSocket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(packet.getSocketAddress() + " message: "
                             + new String(Arrays.copyOfRange(packet.getData(), 0, packet.getLength())));
                }
            }).start();
            Scanner sis = new Scanner(System.in);
            InetAddress ip = InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 168, 100, 49});
            String command = "";
            while (!command.equals("stop")){
                byte[] buffer = "EchoClient".getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, 7);
                echoSocket.send(packet);
                command = sis.nextLine();
            }
            echoSocket.close();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
