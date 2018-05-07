import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;
import java.io.*;
import java.net.*;
import java.nio.charset.*;


public class Chat extends JPanel{
	private static JTextField textField;
	public JTextArea  messagesField;

	public  Socket socket;
	private String message;
	private int port;
	private String name;

	public Chat(String address, int port, String username ){
		try{
			socket = new Socket(address, port);
		}
		catch(Exception f){}

	    name = username;
	    textField = new JTextField(10);
		messagesField = new JTextArea(10,10);
		JScrollPane sp = new JScrollPane(messagesField);
		messagesField.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(messagesField);  
		JScrollBar bar = new JScrollBar();  
		scroller.add(bar); 

		textField.setPreferredSize(new Dimension(130, 20));
		textField.addKeyListener((PlayerPlayer)GameData.getPlayers().get(name));
		messagesField.addKeyListener((PlayerPlayer)GameData.getPlayers().get(name));
		this.add(textField);
		messagesField.setEditable(false);
		this.add(scroller);
		
		Thread thread = new Thread(){
            public void run(){
            	while(true){
			      String server_message;
			      try{
			        BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			        server_message = server.readLine();
			        messagesField.append(server_message+"\n");
			      }
			      catch(IOException e){}
			    }
            }
        };
        thread.start();

			Action action = new AbstractAction(){
			    @Override
			    public void actionPerformed(ActionEvent e)
			    {
			    	try{
				      	//BufferedReader client = new BufferedReader(new InputStreamReader(System.in));
				      	DataOutputStream client_message = new DataOutputStream(socket.getOutputStream());
					      
				      	message = textField.getText();
						  messagesField.append(name+":   "+textField.getText()+"\n");
						  messagesField.setCaretPosition(messagesField.getDocument().getLength());
				      	textField.setText("");
				        message = name + ":   " + message;
				    
				      	client_message.writeBytes(message + "\n");

			    	}
			    	catch(Exception f){}
			    	
			    }
			};
		textField.addActionListener( action );
	}

}