package mainGame;
import java.awt.*;

public class Player {
	
	private int x;
	private int y;

	private int width;
	private int height;
	
	private int speedX;
	private int speedY;
	private int storeSpeedX;
	private int storeSpeedY;
	private int tempSpeedX;
	private int tempSpeedY;
	
	private Color color1;
	private Color color2;
	private Color color3;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean start;
	
	private TileMap tileMap;
	
	private boolean topLeft;
	private boolean topRight;
	private boolean bottomLeft;
	private boolean bottomRight;
	
	public Player(TileMap tm){
		width = GamePanel.WIDTH / GamePanel.squareSize;
		height = GamePanel.HEIGHT / GamePanel.squareSize;
		
		x = GamePanel.WIDTH / GamePanel.squareSize;
		y = GamePanel.HEIGHT / 2;
		
		speedX = 0;
		speedY = 0;
		storeSpeedX = 0;
		storeSpeedY = 0;
		
		color1 = Color.black;
		color2 = Color.GRAY;
		color3 = Color.DARK_GRAY;
		
		start = false;
		
		tileMap = tm;
		
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	public boolean getStart(){ return start; }
	
	public void setX(int dx){ x = dx; }
	public void setY(int dy){ y = dy; }
	
	public void setStart(boolean b){ start = b; }
	
	public void setLeft(boolean b){ left = b;}
	public void setRight(boolean b){ right = b;}
	public void setDown(boolean b){ down = b;}
	public void setUp(boolean b){ up = b;}
	
	
	private void calculateCorners(int x, int y){
		int leftTile = tileMap.getColTile(x - width / 2);
		int rightTile = tileMap.getColTile(x + width / 2 - 1);
		int topTile = tileMap.getRowTile(y - height / 2);
		int bottomTile = tileMap.getRowTile(y + height / 2 - 1);
		topLeft = tileMap.getTile(topTile, leftTile) == 0;
		topRight = tileMap.getTile(topTile,  rightTile) == 0;
		bottomLeft = tileMap.getTile(bottomTile, leftTile) == 0;	
		bottomRight = tileMap.getTile(bottomTile, rightTile) == 0 ;
	}
	
	public void update(){

		//move map
		tileMap.setx(GamePanel.WIDTH / 2 - x);
		tileMap.sety(GamePanel.HEIGHT / 2  - y);
		
		if(left){
			speedX -= GamePanel.HEIGHT / GamePanel.squareSize;

		}
		if(right){
			speedX += GamePanel.HEIGHT / GamePanel.squareSize;
		}
		if(up){		
			speedY -= GamePanel.WIDTH / GamePanel.squareSize;

		}
		if(down){	
			speedY += GamePanel.WIDTH / GamePanel.squareSize;
		}
		
		if(storeSpeedY == tempSpeedY){
			y += storeSpeedY;
			speedY = storeSpeedY;
		}
		else{
			y += speedY;
		}
		if(storeSpeedX == tempSpeedX){
			x += storeSpeedX;
			speedX = storeSpeedX;
		}
		else{
			x += speedX;
		}
		
		
		
		
		
		
		
	}
	public void update2(){
		
		//Make sure speed stays in circle.
		if(left){
			
			storeSpeedX = speedX - width;
			storeSpeedY = speedY;
		}
		if(right){
			storeSpeedX = speedX + width;
			storeSpeedY = speedY;
		}
		if(up){
			storeSpeedY = speedY - height;
			storeSpeedX = speedX;
		}
		if(down){
			storeSpeedY = speedY + height;
			storeSpeedX = speedX;
		}
		
		
		start = false;
		
	}
	public void draw(Graphics2D g){
		
		int tx = tileMap.getx() ;
		int ty = tileMap.gety();
		
		int speedX = tx + x + storeSpeedX;
		int speedY = ty + y + storeSpeedY;
		int carX = tx + x;
		int carY = ty + y;
		
		g.setColor(color1);
		//This is the real one...
		g.fillRect(carX, carY, width, height);
		tempSpeedX = speedX;
		tempSpeedY = speedY;
		
		
		
		
		g.setColor(color2);
		//g.fillRect(x + speedX, y + speedY, width, height);
		g.setColor(color2);
		//this is the fake one. . .. .
		g.fillRect(speedX, speedY, width, height);
		
		
		
		
		
		//Lines between speed and player.
		g.setColor(Color.RED);
		g.drawLine(speedX +  width / 2, 
				speedY + height / 2, 
				carX + width / 2, 
				carY + storeSpeedY + height / 2);
		
		g.drawLine(carX + width / 2, //end X
				speedY + height / 2,  //end Y
				carX + width / 2,  //start X
				carY + height / 2); // start Y
	}
	
}
