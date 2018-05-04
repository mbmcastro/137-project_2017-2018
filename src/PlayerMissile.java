import java.util.*;

public class PlayerMissile extends Missile {
    private final String messagePrefix;

    public PlayerMissile(String username, int xpos, int ypos, int dir, int type) {
        super(username, xpos, ypos, dir, type);
        this.messagePrefix = "missile " + Integer.toString(this.getId()) + " ";
        String message = this.messagePrefix + "create "+ this.getUsername();
        
        GameSockets.sendUdpPacket(message);
    }

    public void hitPlayer(Player player) {
        super.hitPlayer(player);

        String message = this.messagePrefix + "hitPlayer " + player.getUsername();
        GameSockets.sendUdpPacket(message);
    }

    protected void tick() {
        super.tick();

        if(this.xpos <=0 || this.xpos>650 ||this.ypos<=0 || this.ypos>650){
            GameSockets.sendUdpPacket(this.messagePrefix + "kill");
        }
    }
}