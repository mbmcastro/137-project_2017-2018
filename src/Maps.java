//Reads Map.txt to generate the map of the game
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Maps{
	
	private final static int height = 600;
	private final static int width = 600;
	

	private int [][] map;

	public Maps(){
		String file = new String("../Assets/Maps/Map.txt");
		map = new int[height][width];

		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String currentLine;

			while((currentLine = br.readLine()) != null){
				if(currentLine.isEmpty()){
					continue;
				}

				ArrayList<Integer> row = new ArrayList<>();
				String[] data = currentLine.split(" ");

				for(String str:data){
					if(!(str.isEmpty())){
						int block = Integer.parseInt(str);
						row.add(block);
					}
				}
				temp.add(row);

			}
		
			for(int i=0;i<temp.size();i++){
				for(int j=0;j<(temp.get(i)).size();j++){
					map[j][i] = (temp.get(i)).get(j);
				}
			}

		}catch(Exception e){

		}

		int width2 = temp.get(0).size();
		int height2 = temp.size();
	}

	public int[][] getMap(){
		return this.map;
	}

}