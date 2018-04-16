import java.io.*;
import java.net.*;
import java.util.*;

class UDPServer implements Runnable{
    private DatagramSocket serverSocket;
    private int numClients;
    private HashMap<String, ClientData> clientData;

    private class ClientData {
        public int port;
        public InetAddress inetAddress;

        public ClientData(int port, InetAddress inetAddress) {
            this.port = port;
            this.inetAddress = inetAddress;
        }
    }

    public UDPServer(int port, int numClients) {
        try {
            this.serverSocket = new DatagramSocket(port);
            this.numClients = numClients;
            this.clientData = new HashMap<String, ClientData>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(){
        byte[] data;

        try {
            waitForClients();
            broadcast("game _ start");            

            Iterator iter = clientData.keySet().iterator();
            while (iter.hasNext()) {
                String username = (String) iter.next();
                String message = generateRandomPlayerConfig(username);
                broadcast(message);
            }

            while (true) {
                data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.serverSocket.receive(packet); 
                String message = new String(packet.getData()).trim();
                
                System.out.println(message);
                broadcast(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForClients() throws Exception {
        System.out.println("Waiting for clients...");

        int numReceived = 0;
    
        while (numReceived < this.numClients) {
                byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
           
            this.serverSocket.receive(packet);
            String received = new String(packet.getData()).trim();
            System.out.println(received);
            Scanner scanner = new Scanner(received);
            String msgType = scanner.next();
            String username = scanner.next();
            String command = scanner.next();

            if (command.equals("connect")) {
                numReceived += 1;
                System.out.println(username + " has connected.");
                clientData.put(username, new ClientData(
                    packet.getPort(), 
                    packet.getAddress()
                ));
            }

            scanner.close();
        }
    }

    private void broadcast(String message) throws Exception {
        byte[] data = message.getBytes();
        for (ClientData c : clientData.values()) {
            DatagramPacket packet = new DatagramPacket(data, data.length, c.inetAddress, c.port);
            this.serverSocket.send(packet);
        }
    }

    private String generateRandomPlayerConfig(String username) {
        Random rand = new Random();
        String x = Integer.toString(rand.nextInt(600));
        String y = Integer.toString(rand.nextInt(600));
        String dir = Integer.toString(rand.nextInt(4) + 1);
        String playerType = Integer.toString(rand.nextInt(2) + 1);
        String message = "player " + username + " create " + x + " " + y + " " + dir + " " + playerType;
        return message;
    }
}