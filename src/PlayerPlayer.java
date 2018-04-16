import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PlayerPlayer extends Player implements KeyListener {
    private final String messagePrefix;
    private Thread posThread;
    private boolean hasDied;

    public PlayerPlayer(String username) {
        super(username);
        this.messagePrefix = "player " + username + " ";
        this.hasDied = false;
    }

    public PlayerPlayer(String username, int xpos, int ypos, int dir, int playerType) {
        super(username, xpos, ypos, dir, playerType);
        messagePrefix = "player " + username + " ";
        this.hasDied = false;
    	
		
		Thread timerThread = new Thread(){
			public void run(){
				while(true){
					try{
						//System.out.println("waiting to attack. hahaha");
                        resumeAttack();
                        // int playerType = this.getType();
						if(playerType == NINJA){
                            sleep(1000);
                            //Ninja can only attack once per second
						}
						else if(playerType == SAMURAI){
                            sleep(3000);
                            //Samurai can only attack once every 3 seconds
						}
						
					}
					catch(Exception e){}
				}
				
			}
		};
		timerThread.start();

    }

    public void applyDamage(int damage) {
        super.applyDamage(damage);
        if (this.getHp() == 0 && !this.hasDied) {
            GameSockets.sendUdpPacket(this.messagePrefix + "die");
            this.hasDied = true;
        }
    }

    public void fire() {
        if(this.getCanAttack()){
            int xpos = this.getXPos();
            int ypos = this.getYPos();
            int dir = this.getDir();
            int type = this.getPlayerType();
            
            if (dir == Entity.UP) {
                ypos -= this.getHeight();
                xpos += this.getWidth() / 2;
            } else if (dir == Entity.DOWN) {
                ypos += this.getHeight();
                xpos += this.getWidth() / 2;
            } else if (dir == Entity.LEFT) {
                xpos -= this.getWidth();
            } else if (dir == Entity.RIGHT) {
                xpos += this.getWidth();
            }
    
            this.canAttack = false;
            Missile missile = new PlayerMissile(this.username, xpos, ypos, dir, type);
            GameData.addMissile(missile);
        }
    }
    public void fire(int specialAttack) {
        if(this.getCanAttack()){
            int xpos = this.getXPos();
            int ypos = this.getYPos();
            int dir = this.getDir();
            int type = this.getPlayerType();
            
            if (dir == Entity.UP) {
                ypos -= this.getHeight();
                xpos += this.getWidth() / 2;
            } else if (dir == Entity.DOWN) {
                ypos += this.getHeight();
                xpos += this.getWidth() / 2;
            } else if (dir == Entity.LEFT) {
                xpos -= this.getWidth();
            } else if (dir == Entity.RIGHT) {
                xpos += this.getWidth();
            }
            // if(specialAttack == 3){
            //     type = 3;
            // }
            this.canAttack = false;
            Missile missile = new PlayerMissile(this.username, xpos, ypos, dir, type);
            GameData.addMissile(missile);
        }
    }

    @Override
    public void setDirection(int dir) {
        if (this.getDir() != dir) {
            super.setDirection(dir);

            String message = this.messagePrefix + "dir " + Integer.toString(dir);
            GameSockets.sendUdpPacket(message);
        }
    }

    @Override
    public void setSpeed(int speed) {
        if (this.getSpeed() != speed) {
            super.setSpeed(speed);

            String message = this.messagePrefix + "speed " + Integer.toString(speed);
            GameSockets.sendUdpPacket(message);
        }
    }

    private void sendPos() {
        String x = Integer.toString(this.getXPos());
        String y = Integer.toString(this.getYPos());
        String message = this.messagePrefix + "pos " + x + " " + y;
        GameSockets.sendUdpPacket(message);
    }

    public void keyPressed(KeyEvent e) {
        int moveSpeed = 0;
        if(this.getPlayerType() == Entity.NINJA){
            moveSpeed = 7;
        }else if(this.getPlayerType() == Entity.SAMURAI){
            moveSpeed = 3;
        }
        if (!this.isAlive()) return;

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.setDirection(Entity.LEFT);
            this.setSpeed(moveSpeed);
        }
        else if (key == KeyEvent.VK_RIGHT) {
            this.setDirection(Entity.RIGHT);
            this.setSpeed( moveSpeed);
        }
        else if (key == KeyEvent.VK_UP) {
            this.setDirection(Entity.UP);
            this.setSpeed(moveSpeed);
        }
        else if (key == KeyEvent.VK_DOWN) {
            this.setDirection(Entity.DOWN);
            this.setSpeed(moveSpeed);
        } else if (key == KeyEvent.VK_SPACE) {
            this.fire();
        }  else if (key == KeyEvent.VK_S) {
            this.fire(this.getPlayerType()+2);
        }      
    }

    public void keyReleased(KeyEvent e) {    
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.setSpeed(0);
        } else if (key == KeyEvent.VK_RIGHT) {
            this.setSpeed(0);
        } else if (key == KeyEvent.VK_UP) {
            this.setSpeed(0);
        } else if (key == KeyEvent.VK_DOWN) {
            this.setSpeed(0);
        }
    }
    
    public void keyTyped(KeyEvent ke) {}
}