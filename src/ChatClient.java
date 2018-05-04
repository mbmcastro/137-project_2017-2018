import java.io.*;
import java.net.*;


class ChatClient implements Runnable{
  public static Socket socket;
  public static String name;
  public static int port = 9999;

  public static void main(String argv[]) throws Exception {
  	//argv[0] == ip of server
  	//argv[1] == name of client
    	socket = new Socket(argv[0], port);
    	String message;
    	name = argv[1];
    	while(!socket.isClosed()){
      		(new Thread(new ChatClient())).start();
      
     		BufferedReader client = new BufferedReader(new InputStreamReader(System.in));
      		DataOutputStream client_message = new DataOutputStream(socket.getOutputStream());
      
      		message = client.readLine();
      		message = name + ": " + message;
      
      		client_message.writeBytes(message + "\n");
    	}  
  }

  public void run() {
    	while(true){
      		String server_message;
      		try{
        		BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        		server_message = server.readLine();
       
        		System.out.println(server_message);
     		}
      		catch(IOException e){}
    	}
  }

}
