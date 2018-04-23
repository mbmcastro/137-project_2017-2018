import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameData{
	private static ConcurrentHashMap<String, Player> players;
	private static ConcurrentHashMap<Integer, Missile> missiles;
	public static int numPlayers;
	private static String username;
	public static final int FPS = 30;

	public static void initialize(String username) {
		players = new ConcurrentHashMap<String, Player>();
		missiles = new ConcurrentHashMap<Integer, Missile>();
		numPlayers = 0;
		GameData.username = username;
	}
	public static void addMissile(Missile missile) {
		missiles.put(missile.getId(), missile);
		new Thread(missile).start();
	}

	public static void addPlayer(Player player){
		players.put(player.getUsername(), player);
		new Thread(player).start();
		numPlayers++;
	}
	public static void updatePlayerPos(String username, int xpos, int ypos, int dir){
		Player player = players.get(username);
		if (player == null) return;

		player.setXPos(xpos);
		player.setYPos(ypos);
		player.setDirection(dir);
	}
	
	public static ConcurrentHashMap<String, Player> getPlayers() {
		return players;
	}

	public static ConcurrentHashMap<Integer, Missile> getMissiles() {
		return missiles;
	}

	public static String getUsername() {
		return GameData.username;
	}
	public static int getNumPlayers(){
		return numPlayers;
	}

	
}