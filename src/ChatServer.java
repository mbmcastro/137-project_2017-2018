import java.io.*;
import java.net.*;

class ChatServer{
  ServerSocket socket;
  Socket[] connection;
  Thread[] serverThreads;
  int numClients;
  boolean stopThread;

  public static void main(String[] args) {
    int clients = Integer.parseInt(args[0]);
    //args == number of clients
    int port = 9999; 
    ChatServer c = new ChatServer(clients, port);
  }

  ChatServer(int numClients, int port) {
    try {
      this.socket = new ServerSocket(port);
      this.connection = new Socket[numClients];
      this.numClients = numClients;
      this.stopThread = false;

      waitForClients(numClients);
      this.serverThreads = new Thread[numClients];
      for (int i = 0; i < numClients; ++i) {
        final int x = i;
        serverThreads[i] = new Thread(new Runnable() {
          public void run() {
            try {
              while(!shouldThreadStop()) {
                BufferedReader messageReader = new BufferedReader(new InputStreamReader(connection[x].getInputStream()));
                String clientMessage = messageReader.readLine();

                System.out.println(clientMessage);
                if (clientMessage.equals("cmd:quit")) break;
                
                broadcast(x, clientMessage);    
              }

              setStopThread(true);
              broadcast(-1, "cmd:stopChat");
              System.out.println("Thread has stopped");
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });

        serverThreads[i].start();
      }

      for (int i = 0; i < numClients; ++i) {
        serverThreads[i].join();
      }

      System.out.println("Goodbye");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void waitForClients(int numClients) throws IOException {
    int numAccept = 0;
    while (numAccept < numClients) {
      this.connection[numAccept] = this.socket.accept();
      numAccept += 1;
      System.out.println("A client joined");
    }
    System.out.println("All clients have joined");
  }

  void broadcast(int source, String clientMessage) throws Exception {
    for (int i = 0; i < this.numClients; ++i) {
      if (i != source) {
        DataOutputStream message = new DataOutputStream(connection[i].getOutputStream());
        message.writeBytes(clientMessage + "\n");
      }
    }
  }

  boolean shouldThreadStop() {
    return this.stopThread;
  }

  synchronized void setStopThread(boolean status) {
    this.stopThread = status;
  }
}
