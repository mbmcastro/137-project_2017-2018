import java.awt.Rectangle;
import java.util.*;

public class Entity{
	protected final String username;
	protected int dir, speed;
	protected int xpos, ypos;
	protected int width, height;
	protected boolean canAttack;
	protected int playerType;

	private static int[][] mapArea;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	public static final int NINJA = 1;
	public static final int SAMURAI = 2;

	public Entity(String username){
		this.username = username;								//sets the username of the player
		this.canAttack = true;
		Maps map = new Maps();
		mapArea = map.getMap();

		// Random rand = new Random();
		
		// this.playerType = 0;
		// Thread timerThread = new Thread(){
		// 	public void run(){
		// 		while(true){
		// 			try{
		// 				//System.out.println("waiting to attack. hahaha");
		// 				resumeAttack();
		// 				if(this.playerType == NINJA){
		// 					sleep(1000);
		// 				}
		// 				else if(this.playerType == SAMURAI){
		// 					sleep(3000);
		// 				}else if(this.playerType == 0){
		// 					sleep(5000);
		// 				}
						
		// 			}
		// 			catch(Exception e){}
		// 		}
				
		// 	}
		// };
		// timerThread.start();
		// AttackTimer timer = new AttackTimer();
	}

	public void resumeAttack(){
		this.canAttack = true;
		
	}
	public void stopAttack(){
		this.canAttack = false;
	}
	public boolean getCanAttack(){
		return this.canAttack;
	}
	public int getPlayerType(){
		return this.playerType;
	}
	public int getType(){
		return this.playerType;
	}

	public int getXPos(){										//fuction that returns the Position of the player in x-axis
		return this.xpos;
	}
	public int getYPos(){										//fuction that returns the Position of the player in y-axis
		return this.ypos;
	}
	public int getDir(){										//function that returns which direction the player is facing: 1-UP, 2-DOWN, 3-LEFT, 4-RIGHT
		return this.dir;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int getSpeed() {
		return this.speed;
	}
	public String getUsername(){
		return this.username;
	}

	public void setXPos(int xpos) {
		this.xpos = xpos;
	}
	public void setYPos(int ypos) {
		this.ypos = ypos;
	}
	public void setDirection(int dir) {
		this.dir = dir;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void move(){							//function that updates the Position of the player

		if(this.dir == Entity.UP){
			if(this.ypos - this.speed > 0){
			
					this.ypos -= this.speed;
		
				
			}
		}
		else if(this.dir == Entity.DOWN){
			if(this.ypos + this.speed <600){
				this.ypos += this.speed;
			}
		}
		else if(this.dir == Entity.LEFT){
			if(this.xpos - this.speed > 0){
				this.xpos -= this.speed;
			}
		}
		else if(this.dir == Entity.RIGHT){
			if(this.xpos + this.speed <600){
				this.xpos += this.speed;
			}
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(xpos, ypos, width, height);
	}

	public boolean collidesWith(Entity entity) {
		return this.getBounds().intersects(entity.getBounds());
	}
}