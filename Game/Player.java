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
		/*int currCol = tileMap.getColTile(x);
		int currRow = tileMap.getRowTile(y);
		
		int tox = x + speedX;
		int toy = y + speedY;
		
		int tempx = x;
		int tempy = y;
		
		calculateCorners(x, toy);
		if(speedY < 0){
			if(topLeft || topRight){
				speedY = 0;
				tempy = currRow * tileMap.getTileSize() * height / 2;
			}
			else{
				tempy += speedY;
			}
		}
		if(speedY > 0){
			if(bottomLeft || bottomRight){
				speedY = 0;
				tempy = (currRow + 1) * tileMap.getTileSize() - height / 2;
			}
			else{
				tempy += speedY;
			}
		}
		
		calculateCorners(tox, y);
		if(speedX < 0){
			if(topLeft || bottomLeft){
				speedX = 0;
				tempx = currCol * tileMap.getTileSize() * width / 2;
			}
			else{
				tempx += speedX;
			}
			
		}
		if(speedX > 0){
			if(topRight || bottomRight){
				speedX = 0;
				tempx = (currCol + 1) * tileMap.getTileSize() * width / 2;
			}
			else{
				tempx += speedX;
			}
		}
		x = tempx;
		y = tempy;
		*/
		
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
		
		//if(x < width){ x = 0; }
		//if(y < height){ y = 0; }
		
		//if(x > GamePanel.WIDTH - width){ x = GamePanel.WIDTH - width; }
		//if(y > GamePanel.HEIGHT - height) { y = GamePanel.HEIGHT - height; }
		
		//Collision with map.
		
		
		
		
		
		
	}
	public void update2(){
		
		//Make sure speed stays in circle.
		if(left){
			
			
			//Make sure speed stays in circle.
			if(x + storeSpeedX > x + speedX - width&&
			   y + storeSpeedY > y + speedY - height && //Up
			   y + storeSpeedY < y + speedY + height){ //Down		
				storeSpeedX -= GamePanel.HEIGHT / GamePanel.squareSize;
			}
			else{
				storeSpeedX = speedX; 
			}
		}
		if(right){
			
			
			if(x + storeSpeedX < x + speedX + width &&
			   y + storeSpeedY > y + speedY - height && //Up
			   y + storeSpeedY < y + speedY + height){ //Down
				storeSpeedX += GamePanel.HEIGHT / GamePanel.squareSize;
			}
			else{
				storeSpeedX = speedX; 
			}
		}
		if(up){
			
			if(y + storeSpeedY > y + speedY - height &&
			   x + storeSpeedX > x + speedX - width && //Left
			   x + storeSpeedX < x + speedX + width){ //Right
				storeSpeedY -= GamePanel.WIDTH / GamePanel.squareSize;
			}
			else{
				storeSpeedY = speedY; 
			}
		}
		if(down){
			
			if(y + storeSpeedY < y + speedY + height &&
			   x + storeSpeedX > x + speedX - width && //Left
			   x + storeSpeedX < x + speedX + width){ //Right
				storeSpeedY += GamePanel.WIDTH / GamePanel.squareSize;
			}
			else{
				storeSpeedY = speedY; 
			}
		}
		
		start = false;
		
	}
	public void draw(Graphics2D g){
		
		int tx = tileMap.getx() ;
		int ty = tileMap.gety();
		
		int carX = tx + x + storeSpeedX;
		int carY = ty + y + storeSpeedY;
		int speedX = tx + x;
		int speedY = ty + y;
		
		g.setColor(color1);
		//This is the real one...
		g.fillRect(speedX, speedY, width, height);
		tempSpeedX = speedX;
		tempSpeedY = speedY;
		
		
		
		
		g.setColor(color2);
		//g.fillRect(x + speedX, y + speedY, width, height);
		g.setColor(color2);
		//this is the fake one. . .. .
		g.fillRect(carX, carY, width, height);
		
		
		
		
		
		//Lines between speed and player.
		g.setColor(Color.RED);
		g.drawLine(carX +  width / 2, 
				carY + height / 2, 
				speedX + width / 2, 
				speedY + storeSpeedY + height / 2);
		
		g.drawLine(speedX + width / 2, //end X
				carY + height / 2,  //end Y
				speedX + width / 2,  //start X
				speedY + height / 2); // start Y
	}
	
}
