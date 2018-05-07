import java.util.*;
public class Player extends Entity implements Runnable {
    private int hp;
    private static int[][] mapArea;

    public Player(String username) {
        super(username);
        this.hp = 3;
        this.speed = 0;
        this.width = 30;
        this.height = 30;
        this.canAttack = true;
        this.canSpecialAttack = true;
        Maps map = new Maps();
        mapArea = map.getMap();
    }

    public Player(String username, int xpos, int ypos, int dir, int playerType) {
        super(username);
        this.xpos = xpos;
        this.ypos = ypos;
        this.dir = dir;
        this.hp = 3;
        this.speed = 0;
        this.height = 30;
        this.width = 30;
        this.canAttack = true;
        this.canSpecialAttack = true;
        this.playerType = playerType;
        Maps map = new Maps();
        mapArea = map.getMap();
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void applyDamage(int damage) {
         this.hp -= damage;
        // Random rand = new Random();
        
        // this.xpos = rand.nextInt(600);
        // this.ypos = rand.nextInt(600);
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public void fire(int id) {
        // if(id == 3){

        // }else{
            int xpos = this.getXPos();
            int ypos = this.getYPos();
            int dir = this.getDir();
            int type = id;
            if (dir == Entity.UP) {
                ypos -= this.getHeight();
                xpos += this.getWidth() / 2 - 5;
            } else if (dir == Entity.DOWN) {
                ypos += this.getHeight();
                xpos += this.getWidth() / 2 - 5;
            } else if (dir == Entity.LEFT) {
                xpos -= this.getWidth();
            } else if (dir == Entity.RIGHT) {
                xpos += this.getWidth();
            }
            System.out.println(type);
            //this.canAttack = false;
            Missile missile = new Missile(id, this.username, xpos, ypos, dir, type);
            GameData.addMissile(missile);
        // }
            
        
       
    }

    public void run() {
        while (this.isAlive()) {
            if(this.getSpeed() > 0){
                if(mapArea[this.getXPos()/30][(this.getYPos()-this.getSpeed())/30] != 0 && this.getDir() == Entity.UP ){
                }else if(mapArea[this.getXPos()/30][(this.getYPos()+this.getSpeed()+30)/30] != 0 && this.getDir() == Entity.DOWN ){
                }else if(mapArea[(this.getXPos()-this.getSpeed())/30][this.getYPos()/30] != 0 && this.getDir() == Entity.LEFT ){
                }else if(mapArea[(this.getXPos()+this.getSpeed()+15)/30][this.getYPos()/30] != 0 && this.getDir() == Entity.RIGHT ){   
                }else{
                    this.move();
                }
                
            }
            try{
                
                Thread.sleep((int)1000/GameData.FPS);
            }
            catch(Exception e){}
        }
    }
}