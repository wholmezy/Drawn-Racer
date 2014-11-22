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
		
		int accX = tx + x + storeSpeedX;
		int accY = ty + y + storeSpeedY;
		int carX = tx + x;
		int carY = ty + y;
		
		g.setColor(color1);
		//This is the real one...
		g.fillRect(carX, carY, width, height);
		
		
		
		
		
		g.setColor(color2);
		//g.fillRect(x + speedX, y + speedY, width, height);
		g.setColor(color2);
		//this is the fake one. . .. .
		g.fillRect(accX, accY, width, height);
		
		g.setColor(Color.RED);
		
		// Collision detect!
		int col = tileMap.getColTile(x + storeSpeedX);
		int row = tileMap.getRowTile(y + storeSpeedY);
		
		int hey = 0;
		
		if(col >= 20 || row >= 15 || col < 0 || row < 0){
			hey = 0;
		}
		else{
			hey = tileMap.getTile(row, col); 
		}
		
		g.drawString("Value of col = " + col, GamePanel.WIDTH / 2 - 130, GamePanel.HEIGHT / 2 + 50);
		g.drawString("Value of row = " + row, GamePanel.WIDTH / 2 - 130, GamePanel.HEIGHT / 2 - 50);
		g.drawString("Value of tile = " + hey, GamePanel.WIDTH / 2 - 130, GamePanel.HEIGHT / 2);
		
		if(hey == 0){
			g.fillRect(accX, accY, width, height);
		}
		
		
		tempSpeedX = speedX;
		tempSpeedY = speedY;
		
		g.drawString("Value of tempSpeedX = " + tempSpeedX, GamePanel.WIDTH / 2 - 130, GamePanel.HEIGHT / 2 - 100);
		
		//TODO
		
		//This draws cars from accelopointer to car.
		int yello = 0;
		
		boolean complete = false;
		
		int botR = 1;
		int botL = 1;
		int topR = 1;
		int topL = 1;
		int downS = 1;
		int upS = 1;
		int leftS = 1;
		int rightS = 1;
		
		g.setColor(Color.MAGENTA);
		
		while(!complete){
			
			//BotRight
			if(tempSpeedX > 0 && tempSpeedY > 0){
				
				g.fillRect(carX + width * botR, carY + height * botR, width, height);
				
				botR++;
				
				tempSpeedX -= GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY -= GamePanel.HEIGHT / GamePanel.squareSize;
				
				yello++;
				
			}
			
			//BotLeft
			else if(tempSpeedX < 0 && tempSpeedY > 0){
				
				g.fillRect(carX - width * botL, carY + height * botL, width, height);
				
				botL++;
				
				tempSpeedX += GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY -= GamePanel.HEIGHT / GamePanel.squareSize;
				
				//yello++;
			
			}
			
			//TopRight
			else if(tempSpeedX > 0 && tempSpeedY < 0){
				
				g.fillRect(carX + width * topR, carY - height * topR, width, height);
				
				topR++;
				
				tempSpeedX -= GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY += GamePanel.HEIGHT / GamePanel.squareSize;
				
				//yello++;
			
			}
			
			//TopLeft
			else if(tempSpeedX < 0 && tempSpeedY < 0){
				
				g.fillRect(carX - width * topL, carY - height * topL, width, height);
				
				topL++;
				
				tempSpeedX += GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY += GamePanel.HEIGHT / GamePanel.squareSize;
				
				yello++;
				
			}
			
			//Down
			
			else if(tempSpeedX == 0 && tempSpeedY > 0){
					
				g.fillRect(carX, carY + height * downS, width, height);
				
				downS++;
				
				tempSpeedY -= GamePanel.HEIGHT / GamePanel.squareSize;
				
				//yello++;
			
			}
			
			//Up
			else if(tempSpeedX == 0 && tempSpeedY < 0){
				
				g.fillRect(carX, carY - height * upS, width, height);
				
				upS++;
				
				tempSpeedY += GamePanel.HEIGHT / GamePanel.squareSize;
				
				//yello++;
			
			}
			
			//Right
			else if(tempSpeedX > 0 && tempSpeedY == 0){
				
				g.fillRect(carX + width * rightS, carY, width, height);
				
				rightS++;
				
				tempSpeedX -= GamePanel.WIDTH / GamePanel.squareSize;
				
				//yello++;
			}
			
			//Left
			else if(tempSpeedX < 0 && tempSpeedY == 0){
			
				g.fillRect(carX - width * leftS, carY, width, height);
				
				leftS++;
				
				tempSpeedX += GamePanel.WIDTH / GamePanel.squareSize;
				
				//yello++;
			
			}
			
			
			
			else if(tempSpeedX == 0 && tempSpeedY == 0){
				complete = true;
			}
			
		}
		g.setColor(Color.RED);
		
		g.drawString("Value of yello = " + yello, GamePanel.WIDTH / 2 - 130, GamePanel.HEIGHT / 2 - 75);
		
		//g.fillRect((int) (carX + cubeHalf), (int) (carY + cubeHalf), width, height);
		
		//TopRight
		//g.fillRect(carX + height, carY - height, width, height);
		
		//TopLeft
		//g.fillRect(carX - height, carY - height, width, height);
		
		//BotLeft
		//g.fillRect(carX - height, carY + height, width, height);
		
		//BotRight
		//g.fillRect(carX + height, carY + height, width, height);
		
		
		
		
		
		
		//Lines between acc and player.
		
		
		
		g.drawLine(carX + width / 2, //end X
				carY + height / 2, //end y
				accX + width / 2, //start x
				accY + height / 2); //start Y
	}
	
}
