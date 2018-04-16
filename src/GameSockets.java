import java.net.*;

public class GameSockets {
    private static Socket tcpSocket; 
    private static DatagramSocket udpSocket;
    private static String serverIpAddress;
    private static int tcpPort;
    private static int udpPort;

    public static void initialize(String serverIpAddress, int tcpPort, int udpPort) throws SocketException {
        try {
            GameSockets.serverIpAddress = serverIpAddress;
            GameSockets.tcpPort = tcpPort;
            GameSockets.udpPort = udpPort;
            
            //tcpSocket = new Socket(serverIpAddress, tcpPort);
            udpSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw e;
        }
    };

    public static Socket getTcpSocket() {
        return tcpSocket;
    }

    public static DatagramSocket getUdpSocket() {
        return udpSocket;
    }

    public synchronized static void sendUdpPacket(String data) {
        byte[] buf;
        buf = data.getBytes();
        System.out.println(data);
        try {
            InetAddress inetAddress = InetAddress.getByName(GameSockets.serverIpAddress);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, GameSockets.udpPort);
            GameSockets.udpSocket.send(packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String receiveUdpPacket() throws Exception {
        byte[] buf = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        GameSockets.udpSocket.receive(packet);
        return new String(packet.getData()).trim();
    }
}