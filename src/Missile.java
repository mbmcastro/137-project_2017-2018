import java.util.*;

public class Missile extends Entity implements Runnable {
    private int id;
    private boolean alive;
    private int damage;

    public Missile(String username, int xpos, int ypos, int dir, int type) {
        super(username);
        this.xpos = xpos;
        this.ypos = ypos;
        this.dir = dir;
        this.id = this.hashCode();
        this.alive = true;
        this.speed = 5;
        this.damage = 1;
        this.width = 10;
        this.height = 10;
        this.playerType = type;
    }

    public Missile(int id, String username, int xpos, int ypos, int dir, int type) {
        super(username);
        this.xpos = xpos;
        this.ypos = ypos;
        this.dir = dir;
        this.id = id;
        this.alive = true;
        this.speed = 5;
        this.damage = 1;
        this.width = 10;
        this.height = 10;
        this.playerType = type;
    }

    public int getId() {
        return this.id;
    }

    public int getDamage() {
        return this.damage;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void hitPlayer(Player player) {
        this.setSpeed(0);
    }

    protected void tick() {
        this.move();
        
        Iterator iterator = GameData.getPlayers().entrySet().iterator();
        while (iterator.hasNext()) {
            Player player = (Player) ((Map.Entry) iterator.next()).getValue();
            
            if (player.getUsername().equals(this.username)) continue;

            if (this.collidesWith(player)) {
                System.out.println("Missile " + this.username + " collided with player " + player.getUsername() + ".");
                System.out.println("Player HP " + player.getHp());
                this.hitPlayer(player);
                break;
            }
        }
    }

    public void run() {
        int count = 0;
        while (this.isAlive()) {
            tick();
            if(this.playerType == 4){
                this.speed = 15;
                if(count == 25){
                    this.setAlive(false);
                    this.playerType = 0;
                }
                
            }else if(this.playerType == 3){
                this.speed = 30;
                if(count == 30){
                    this.setAlive(false);
                    this.playerType = 0;
                }
            } 
            else if(this.getPlayerType() == Entity.NINJA){
                this.speed = 5;
                if(count == 2){
                    this.setAlive(false);
                }
            }  else if(this.getPlayerType() == Entity.SAMURAI){
                this.speed = 5;
                if(count == 12){
                    this.setAlive(false);
                }
            } 
            
            count++;
            try {
                Thread.sleep((int)1000/GameData.FPS);
            } catch(Exception e){}
        }
    }
}