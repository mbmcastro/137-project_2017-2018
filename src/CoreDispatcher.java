import java.util.Scanner;
import java.util.HashMap;

public class CoreDispatcher implements Dispatcher, Runnable {
    public HashMap<String, Dispatcher> routingTable;

    public CoreDispatcher() {
        this.routingTable = new HashMap<String, Dispatcher>();
        new Thread(this).start();
        routingTable.put("player", new PlayerDispatcher());
        routingTable.put("missile", new MissileDispatcher());

    }

    public void processMessage(String message) {
        Scanner s = new Scanner(message);
        String msgType = s.next();
        routingTable.get(msgType).processMessage(s.nextLine());
    }

    public void run() {
        try{
            while (true) {
                String message = GameSockets.receiveUdpPacket();
                new Thread(new Runnable() {
                    public void run() {
                        processMessage(message);
                    }
                }).start();
            }      
        } catch(Exception e){}
    }
}