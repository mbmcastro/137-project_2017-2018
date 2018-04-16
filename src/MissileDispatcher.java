import java.util.Scanner;

public class MissileDispatcher implements Dispatcher {
    public void processMessage(String message) {
        Scanner s = new Scanner(message);
        int id = s.nextInt();

        String command = s.next();
        String args = "";
        if (s.hasNext()) args = s.nextLine();

        if(command.equals("create")){
            this.handleCreateMissile(id, args);
        } else if (command.equals("pos")) {
            this.handlePosMissile(id, args);
        } else if (command.equals("hitPlayer")) {
            this.handleHitPlayer(id, args);
        } else if (command.equals("kill")) {
            this.handleKill(id);
        }

        s.close();
    }

    private void handlePosMissile(int id, String args) {
        Scanner s = new Scanner(args);
        int x = s.nextInt();
        int y = s.nextInt();

        Missile target = GameData.getMissiles().get(id);
        target.setXPos(x);
        target.setYPos(y);

        s.close();
    }
    private void handleCreateMissile(int id, String args){
        Scanner s = new Scanner(args);
        String name = s.next();
        if (name.equals(GameData.getUsername())) return;
        GameData.getPlayers().get(name).fire(id);
    }
    private void handleHitPlayer(int id, String args) {
        Scanner s = new Scanner(args);
        String username = s.next();

        Player player = GameData.getPlayers().get(username);
        Missile missile = GameData.getMissiles().get(id);
        
        if (missile == null || player == null) {
            s.close();
            return;
        }

        player.applyDamage(missile.getDamage());
        System.out.println("Missile " + missile.getUsername() + " collided with player " + player.getUsername() + ".");
        System.out.println("Player HP " + player.getHp());
        missile.setAlive(false);

        s.close();
    }
    private void handleKill(int id) {
        GameData.getMissiles().get(id).setAlive(false);;
    }
}