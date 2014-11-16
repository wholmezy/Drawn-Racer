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
		
		//if(x < width){ x = 0; }
		//if(y < height){ y = 0; }
		
		//if(x > GamePanel.WIDTH - width){ x = GamePanel.WIDTH - width; }
		//if(y > GamePanel.HEIGHT - height) { y = GamePanel.HEIGHT - height; }
		
		

		
		
	}
	public void update2(){
		
		if(left){
			
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
		
		g.setColor(color1);
		g.fillRect(tx + x, ty + y, width, height);
		tempSpeedX = speedX;
		tempSpeedY = speedY;
		
		g.setColor(color2);
		//g.fillRect(x + speedX, y + speedY, width, height);
		g.setColor(color2);
		g.fillRect(tx + x + storeSpeedX, ty + y + storeSpeedY, width, height);
	}
	
}
