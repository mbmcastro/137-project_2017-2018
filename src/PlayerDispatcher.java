import java.util.Scanner;
import java.net.URLStreamHandlerFactory;
import java.util.*;

public class PlayerDispatcher implements Dispatcher {
    private String ddd;
    public void processMessage(String message) {
        Scanner s = new Scanner(message);
        String username = s.next();
        String command = s.next();
        String args = "";
        if (s.hasNext()) args = s.nextLine();

        if (command.equals("create")) {
            this.handleCreate(username, args);
            s.close();
            return;
        }
        
        Player target = GameData.getPlayers().get(username);

        if (command.equals("speed")) {
            this.handleSpeed(target, args);
        } else if (command.equals("dir")) {
            this.handleDir(target, args);
        } else if (command.equals("pos")) {
            this.handlePos(target, args);
        } else if (command.equals("die")) {
            this.handleDie(username);
        } else if (command.equals("inv")){
            this.handleInv(target);
        }

        s.close();
    }

    private void handleInv(Player target){
        target.setPlayerType(3);
        Thread t = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    target.setPlayerType(1);
                }
                catch(Exception e){
                   
                }
            }
        };
        t.start();
    }

    private void handleCreate(String username, String args) {
        Scanner s = new Scanner(args);
        int xpos = s.nextInt();
        int ypos = s.nextInt();
        int dir = s.nextInt();
        int playerType = s.nextInt();

        Player player; 
        if (username.equals(GameData.getUsername())) {
            player = new PlayerPlayer(username, xpos, ypos, dir, playerType);
        } else {
            player = new Player(username, xpos, ypos, dir, playerType);
        }

        GameData.addPlayer(player);
    }

    private void handleSpeed(Player target, String args) {
        Scanner s = new Scanner(args);
        int speed = s.nextInt();
        target.setSpeed(speed);
        s.close();
    }

    private void handleDir(Player target, String args) {
        Scanner s = new Scanner(args);
        int dir = s.nextInt();
        target.setDirection(dir);
        s.close();
    }

    private void handlePos(Player target, String args) {
        Scanner s = new Scanner(args);

        int x = s.nextInt();
        int y = s.nextInt();

        target.setXPos(x);
        target.setYPos(y);

        s.close();
    }

    private void handleDie(String username) {
        System.out.println(GameData.numPlayers);
        if(GameData.numPlayers > 1){
            GameData.numPlayers -= 1;
        }
    }
}