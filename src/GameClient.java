public class GameClient{
	public static void main(String args[]){
		if (args.length != 3) {
            System.out.println("Usage: gameClient <name> <ip> <port>");
            return;
        }
		try{
			GameSockets.initialize(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[2]));
			//args[1] = ip, args[2] = port
		}
		catch(Exception e){}

		GameData.initialize(args[0]);
		GameClient x= new GameClient(args[0], args[1], Integer.parseInt(args[2]));
		//args[0] = name
	}
	public GameClient(String name, String ip, int port){
		GameSockets.sendUdpPacket("game " +name+" connect");
		try {
			while(!GameSockets.receiveUdpPacket().equals("game _ start")) {}
			System.out.println("Recieved start packet");
		} catch (Exception e) {e.printStackTrace();}

		new Thread(new CoreDispatcher()).start();
		Draw x = new Draw(name, ip, port);
	}
	
}