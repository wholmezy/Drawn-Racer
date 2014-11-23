package mainGame;
import java.io.*;
import java.awt.*;

public class TileMap {
	private int x;
	private int y;
	
	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	
	private Color color1 = Color.WHITE;
	private Color color2 = Color.BLUE;
	
	public TileMap(String s, int tileSize){
		this.tileSize = tileSize;
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(s));
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			
			map = new int[mapHeight][mapWidth];
			
			String delimiters = " ";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			br.close();
		}
		catch(Exception e){}
	}
	
	public int getx() { return x; }
	public int gety() { return y; }
	
	public int getColTile(int x){
		return x / tileSize;
	}
	public int getRowTile(int y){
		return y / tileSize;
	}
	public int getTile(int row, int col){
		return map[row][col];
	}
	public int getTileSize(){
		return tileSize;
	}
	public int getMapHeight() { return mapHeight; }
	public int getMapWidth() { return mapWidth; }
	
	
	public void setx(int i) { x = i; }
	public void sety(int i) { y = i; }
	
	public void update(){
		
	}
	public void draw(Graphics2D g){
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				
				int rc = map[row][col];
				
				if(rc == 0){
					g.setColor(color2);
				}
				if(rc == 1){
					g.setColor(color1);
				}
				
				g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
			}
		}
	}
	
}	
