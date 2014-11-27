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
	
	private int numPlayer;
	
	private Color color1;
	private Color color2;
	private Color color3;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean start;
	
	private TileMap tileMap;
	
	public Player(TileMap tm, int color, int numPerson){
		width = GamePanel.WIDTH / GamePanel.squareSize;
		height = GamePanel.HEIGHT / GamePanel.squareSize;
		
		x = (GamePanel.WIDTH / GamePanel.squareSize);
		y = (GamePanel.HEIGHT / 5);
		
		numPlayer = numPerson;
		
		speedX = 0;
		speedY = 0;
		storeSpeedX = 0;
		storeSpeedY = 0;
		
		if(color == 1){
			//Orange
			color1 = new Color(255, 128, 0);
			//Light Orange
			color2 = new Color(255, 204, 153);
			//Dark Orange
			color3 = new Color(153, 76, 0);
		}
		else{
			//Dark Purple
			color1 = new Color(76, 0, 153);
			//Light Purple
			color2 = new Color(229, 204, 255);
			//Dark Purple
			color3 = new Color(76, 0, 153);
		}
		
		
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
	
	public void update(int numPlayers){

		//move map
		if(GamePanel.squareSize < 50){
			tileMap.setx(GamePanel.WIDTH / 2 - x);
			tileMap.sety(GamePanel.HEIGHT / 2  - y);
		}
		
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
		
		speedX = drawCheck(speedX, speedY, 1);
		speedY = drawCheck(speedX, speedY, 2);
		
		x += speedX;
		y += speedY;
		
	}
	public void update2(){
		
		storeSpeedX = speedX;
		storeSpeedY = speedY;
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
	

	
	public void draw(Graphics2D g, boolean turn){
		
		int tx = tileMap.getx() ;
		int ty = tileMap.gety();
		
		int accX = tx + x + storeSpeedX;
		int accY = ty + y + storeSpeedY;
		int carX = tx + x;
		int carY = ty + y;
		
		
		
		if(!turn){
			g.setColor(Color.DARK_GRAY);
		}
		else{
			g.setColor(color2);
		}
		//Current Acceleration of car.
		g.fillRect(accX, accY, width, height);
		
		
		
		g.setColor(color1);
		//The car
		g.fillRect(carX, carY, width, height);
		
		
		
		g.setColor(color2);
		
		
		//g.setColor(Color.RED);
		
		
		//Lines between acc and player.
		
		/*g.drawLine(carX + width / 2, //end X
				carY + height / 2, //end y
				accX + width / 2, //start x
				accY + height / 2); //start Y*/
	}
	
	public void checkMap(int x, int y){
		
		
		int checker;
		
		int colC = tileMap.getColTile(x);
		int rowC = tileMap.getRowTile(y);
		if(colC >= tileMap.getMapWidth() || colC <= 0 || rowC >= tileMap.getMapHeight() || rowC <= 0){
			checker = 0;
		}
		else{
			checker = tileMap.getTile(rowC, colC); 
		}
		if(checker == 0){
			speedX = 0;
			speedY = 0;
		}
		
		if(checker == 2){
			GamePanel.gameOver = true;
			GamePanel.winningPlayer = numPlayer;
		}
		
		
	}
	
	
	
	public int drawCheck(int speedX, int speedY, int xory){
		
		tempSpeedX = speedX;
		tempSpeedY = speedY;
		
		
		//This draws cars from accelopointer to car.
		
		boolean complete = false;
		
		int botR = 1;
		int botL = 1;
		int topR = 1;
		int topL = 1;
		int downS = 1;
		int upS = 1;
		int leftS = 1;
		int rightS = 1;
		
	
		while(!complete){
			
			
			checkMap(x + tempSpeedX, y + tempSpeedY);
			
			//BotRight
			if(tempSpeedX > 0 && tempSpeedY > 0){
				
				checkMap(x + width * botR, y + height * botR);
				
				
				//g.fillRect(carX + width * botR, carY + height * botR, width, height);
				
				botR++;
				
				tempSpeedX -= GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY -= GamePanel.HEIGHT / GamePanel.squareSize;
				
			}
			
			//BotLeft
			else if(tempSpeedX < 0 && tempSpeedY > 0){
				
				
				
				checkMap(x - width * botL, y + height * botL);
				
				
				
				
				//g.fillRect(carX - width * botL, carY + height * botL, width, height);
				
				botL++;
				
				tempSpeedX += GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY -= GamePanel.HEIGHT / GamePanel.squareSize;
				
				
			
			}
			
			//TopRight
			else if(tempSpeedX > 0 && tempSpeedY < 0){
				
				
				
				checkMap(x + width * topR, y - height * topR);
				
				
				//g.fillRect(carX + width * topR, carY - height * topR, width, height);
				
				topR++;
				
				tempSpeedX -= GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY += GamePanel.HEIGHT / GamePanel.squareSize;
				
			}
			
			//TopLeft
			else if(tempSpeedX < 0 && tempSpeedY < 0){
				
				
				
				checkMap(x - width * topL, y - height * topL);
				//g.fillRect(carX - width * topL, carY - height * topL, width, height);
				
				topL++;
				
				tempSpeedX += GamePanel.WIDTH / GamePanel.squareSize;
				tempSpeedY += GamePanel.HEIGHT / GamePanel.squareSize;
				
				
			}
			
			//Down
			
			else if(tempSpeedX == 0 && tempSpeedY > 0){
					
			
				
				checkMap(x, y + height * downS);
				
				
				
				//g.fillRect(carX, carY + height * downS, width, height);
				
				downS++;
				
				tempSpeedY -= GamePanel.HEIGHT / GamePanel.squareSize;
				
			
			}
			
			//Up
			else if(tempSpeedX == 0 && tempSpeedY < 0){
				
				
				
				checkMap(x, y - height * upS);
				
				
				
				//g.fillRect(carX, carY - height * upS, width, height);
				
				upS++;
				
				tempSpeedY += GamePanel.HEIGHT / GamePanel.squareSize;
			
			}
			
			//Right
			else if(tempSpeedX > 0 && tempSpeedY == 0){
				
				
				checkMap(x + width * rightS, y);
				
				
				
				
				//g.fillRect(carX + width * rightS, carY, width, height);
				
				rightS++;
				
				tempSpeedX -= GamePanel.WIDTH / GamePanel.squareSize;
				
			}
			
			//Left
			else if(tempSpeedX < 0 && tempSpeedY == 0){
			
				
				checkMap(x - width * leftS, y);
				
				
				
				
				//g.fillRect(carX - width * leftS, carY, width, height);
				
				leftS++;
				
				tempSpeedX += GamePanel.WIDTH / GamePanel.squareSize;
				
			
			}
			
			
			
			else if(tempSpeedX == 0 && tempSpeedY == 0){
				complete = true;
			}
			
		}
		if(xory == 1){
			return speedX;
		}
		else{
			return speedY;
		}
	}
	
}
