import java.io.*;
import java.net.*;


class ChatClient implements Runnable{
  public static Socket socket;
  public static String name;

  public static void main(String argv[]) throws Exception {
    socket = new Socket(argv[0], Integer.parseInt(argv[1]));
    String message;
    name = argv[2];
    
    (new Thread(new ChatClient())).start();
    while(!socket.isClosed()){
      
      BufferedReader client = new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream client_message = new DataOutputStream(socket.getOutputStream());
      
      message = client.readLine();
      if (!message.equals("cmd:quit")) {
        message = name + ": " + message;
      }
      
      client_message.writeBytes(message + "\n");
    }  
    System.out.println("Good bye");
  }

  public void run() {
    while(true){
      String server_message;
      try{
        BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        server_message = server.readLine();
        if (server_message.equals("cmd:stopChat")) {
          System.out.println("Disconnecting...");
          socket.close();
          break;
        }
        System.out.println(server_message);
      }
      catch(IOException e){}

    }
    System.out.println("Close using Ctrl+C");
  }

}