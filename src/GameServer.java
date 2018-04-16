public class GameServer implements Runnable{
    static UDPServer udpServer;

    public static void main(String[] args) {
        int port, numClients;

        if (args.length != 2) {
            System.out.println("Usage: java GameServer <port> <numClients>");
            return;
        }

        port = Integer.parseInt(args[0]);
        numClients = Integer.parseInt(args[1]);
        Thread thread = new Thread(){
            public void run(){
                String[] arguments = new String[]{args[1], args[0]};
                ChatServer.main(arguments);
            }
        };

        thread.start();
        
        try {
            
            Thread udpServerThread = new Thread(new UDPServer(port, numClients));
            udpServerThread.start();
            udpServerThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        
    }
}