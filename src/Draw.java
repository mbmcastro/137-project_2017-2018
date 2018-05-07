import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;

public class Draw extends GameData implements Runnable{
    private static JPanel panel;
    private static JPanel map;
    private static JFrame frame;
    private static JFrame userManualFrame;
    public JButton userManualButton;
    private static Chat chatPanel;
    private static JPanel controlPanel;
    private static JTextField  currentLife;
    private static String name;
    private static int[][] mapArea;
    public JLabel heart1;
    public JLabel heart2;
    public JLabel heart3;
    public JLabel userManualLabel;
	
    public Draw(String name, String ip, int port){
        BorderLayout layout = new BorderLayout();
        Set set = getPlayers().entrySet();
        Iterator iterator = set.iterator();
        Maps map = new Maps();
        JLabel blank = new JLabel();
        
        chatPanel = new Chat(ip, port, name);
        frame = new JFrame();
        currentLife = new JTextField(10);
        panel = new JPanel();
        mapArea = map.getMap();
        heart1 = new JLabel();
        heart2 = new JLabel();
        heart3 = new JLabel();
        userManualFrame = new JFrame("User Manual");
        userManualLabel = new JLabel();
        userManualButton = new JButton("Help");

        // heart1.setLocation(100,300);
        // heart2.setLocation(100,375);
        // heart3.setLocation(100,450);
        blank.setSize(50,50);
        heart1.setSize(50,50);
        heart2.setSize(50,50);
        heart3.setSize(50,50);
        blank.setIcon(new ImageIcon("../Assets/Images/Objects/Blank.png"));
        heart1.setIcon(new ImageIcon("../Assets/Images/Objects/Heart.png"));
        heart2.setIcon(new ImageIcon("../Assets/Images/Objects/Heart.png"));
        heart3.setIcon(new ImageIcon("../Assets/Images/Objects/Heart.png"));
        userManualLabel.setIcon(new ImageIcon("../Assets/Images/UserManual/UserManual.png"));

        userManualButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                userManualFrame.setVisible(true);
            } 
          } );

        Draw.name = name;
        frame.setLayout(layout); 
        currentLife.setEditable(false);
        currentLife.setSize(30, 170);
        
        userManualFrame.setPreferredSize(new Dimension(775, 600));
        userManualFrame.setResizable(true);
        userManualFrame.setVisible(false);
        userManualFrame.add(userManualLabel);
        userManualFrame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(775, 600));
        panel.setPreferredSize(new Dimension(600, 600));
        chatPanel.setPreferredSize(new Dimension(175, 600));
        frame.setResizable(true);
        frame.setVisible(true);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);

        chatPanel.add(currentLife);
        chatPanel.add(blank);
        chatPanel.add(heart1);
        chatPanel.add(heart2);
        chatPanel.add(heart3);
        chatPanel.add(userManualButton);
        frame.add(chatPanel,BorderLayout.WEST);
        frame.add(panel,BorderLayout.EAST);

        frame.addKeyListener((PlayerPlayer)GameData.getPlayers().get(name));
        currentLife.addKeyListener((PlayerPlayer)GameData.getPlayers().get(name));
        panel.addKeyListener((PlayerPlayer)GameData.getPlayers().get(name));
        chatPanel.addKeyListener((PlayerPlayer)GameData.getPlayers().get(name));
        
        panel.requestFocusInWindow();
        frame.pack();
        
    
        new Thread(this).start();
    }

    public void run(){
         while(GameData.numPlayers > 1){

            int numPlayer=1;
            panel.removeAll();

            for(int i=0;i<20; i++){
				for(int j=0;j<20; j++){
                    Rectangle rec = new Rectangle();
					JLabel block = new JLabel();

					if(mapArea[i][j] == 3){
						block.setIcon(new ImageIcon("../Assets/Images/Objects/Crate.png"));
					}

					rec.setBounds(i*30, j*30, 30, 30);
					block.setLocation(i*30,j*30);
					block.setSize(30,30);

					panel.add(block);
				}
			}

            Set set = getPlayers().entrySet();
            Iterator iterator = set.iterator();

            while(iterator.hasNext()) {                           //iterate all pairs in the hash table

                JLabel player = new JLabel();
                Map.Entry playerEntry = (Map.Entry)iterator.next();
		
                int xpos=((Player)playerEntry.getValue()).getXPos();
                int ypos=((Player)playerEntry.getValue()).getYPos();
                int dir=((Player)playerEntry.getValue()).getDir();
                int playerType=((Player)playerEntry.getValue()).getPlayerType();
               
                int playerHp = GameData.getPlayers().get(name).getHp();
                
                currentLife.setText("Current Life: "+ playerHp);
                if(playerHp == 2){
                    heart3.setIcon(new ImageIcon("../Assets/Images/Objects/Blank.png"));
                }
                if(playerHp == 1){
                    heart2.setIcon(new ImageIcon("../Assets/Images/Objects/Blank.png"));
                }
                if(playerHp == 0){
                    heart1.setIcon(new ImageIcon("../Assets/Images/Objects/Blank.png"));
                }

                player.setLocation(xpos, ypos);                       //sets the location of the player based on the values in the hashtable
                
                if(dir == Entity.UP){
                    if(playerType == Entity.NINJA){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Ninja_01.png")); 
                    }else if(playerType == Entity.SAMURAI){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Samurai_01.png")); 
                    }
                     
                }
                else if(dir == Entity.DOWN){
                    if(playerType == Entity.NINJA){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Ninja_01.png")); 
                    }else if(playerType == Entity.SAMURAI){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Samurai_01.png")); 
                    }
                }
                else if(dir == Entity.LEFT){
                    if(playerType == Entity.NINJA){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Ninja_01.png")); 
                    }else if(playerType == Entity.SAMURAI){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Samurai_01.png")); 
                    }
                }
                else if(dir == Entity.RIGHT){
                    if(playerType == Entity.NINJA){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Ninja_01.png")); 
                    }else if(playerType == Entity.SAMURAI){
                        player.setIcon(new ImageIcon("../Assets/Images/PlayerType/Model/Samurai_01.png")); 
                    }
                }
                 
                 numPlayer++;
                 player.setBounds(xpos,ypos,0,0);
                 player.setSize(30,30); 
                 panel.add(player);                              //adds the player to the panel
            
            }

            Set missileSet = getMissiles().entrySet();
            Iterator missileIterator = missileSet.iterator();
            while(missileIterator.hasNext()){
                JLabel missile = new JLabel();
                Missile missileEntry = (Missile)((Map.Entry)missileIterator.next()).getValue();
                if(!missileEntry.isAlive()){
                    GameData.getMissiles().remove(missileEntry.getId());
                    continue;
                }
                int xpos=missileEntry.getXPos();
                int ypos=missileEntry.getYPos();
                int dir=missileEntry.getDir();
                int id = missileEntry.getId();
                missile.setLocation(xpos-15, ypos);
                if(dir == Entity.UP){
                    missile.setIcon(new ImageIcon("../Assets/Images/Missile/Kunai_up.png"));
                }else if(dir == Entity.DOWN){
                    missile.setIcon(new ImageIcon("../Assets/Images/Missile/Kunai_down.png"));
                }else if(dir == Entity.LEFT){
                    missile.setIcon(new ImageIcon("../Assets/Images/Missile/Kunai_left.png"));
                }else if(dir == Entity.RIGHT){
                    missile.setIcon(new ImageIcon("../Assets/Images/Missile/Kunai_right.png"));
                }

                
                missile.setBounds(xpos,ypos,0,0);
                missile.setSize(30,30); 
                panel.add(missile); 
            }

            panel.repaint();
            try{

            Thread.sleep(1000/GameData.FPS);
            }
            catch(Exception e){}
         }
         panel.removeAll();
         
        panel.repaint();
        String winnerName = "";
        Set set = getPlayers().entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Player playerEntry = (Player)((Map.Entry)iterator.next()).getValue();
         
            if(playerEntry.getHp()>0){
                if(name.equals(playerEntry.getUsername())){
                    JOptionPane.showMessageDialog(frame, "Congratulations."+playerEntry.getUsername());
                    break;
                }else{
                    JOptionPane.showMessageDialog(frame,"Good Luck Next Time! "+name);
                    break;
                }
                
            }
        }
  }
}
