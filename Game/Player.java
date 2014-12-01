package mainGame;
import java.awt.*;
import java.util.Random;

public class Player {
	
	private int x;
	private int y;

	private int width;
	private int height;
	
	private int speedX;
	private int speedY;
	private int randomize;
	private int storeSpeedX;
	private int storeSpeedY;
	private int tempSpeedX;
	private int tempSpeedY;
	private int numTilPowUp;
	private int powDec;
	
	private char powerUp;
	private String colorName;
	Random randomer;
	
	private Color color1;
	private Color color2;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean start;
	private boolean power;
	private boolean havePowerUp;
	private boolean haveUsedPowUp;
	
	private TileMap tileMap;
	private PowerUps powerControl;

	
	
	public Player(TileMap tm, int color){
		width = GamePanel.WIDTH / GamePanel.squareSize;
		height = GamePanel.HEIGHT / GamePanel.squareSize;
		
		x = (GamePanel.WIDTH / GamePanel.squareSize);
		y = (GamePanel.HEIGHT / 5);
		
		numTilPowUp = 0;
		haveUsedPowUp = false;
		havePowerUp = false;
		randomer = new Random();
		
		speedX = 0;
		speedY = 0;
		storeSpeedX = 0;
		storeSpeedY = 0;
		powDec = 0;
		
		
		//Player colors.
		switch(color){
			case 1:
				//Orange
				color1 = new Color(255, 128, 0);
				//Light Orange
				color2 = new Color(255, 204, 153);
				
				colorName = "ORANGE";
				break;
			case 2:
				//Dark Purple
				color1 = new Color(76, 0, 153);
				//Light Purple
				color2 = new Color(229, 204, 255);
				
				colorName = "PURPLE";
				break;
			case 3:
				//Dark Red
				color1 = new Color(155, 0, 0);
				//Light Red
				color2 = new Color(255, 0, 0);
				colorName = "RED";
				break;
			case 4:
				//Dark Blue
				color1 = new Color(0, 0, 153);
				//Light Blue
				color2 = new Color(0, 0, 255);
				colorName = "BLUE";

				break;
			case 5:
				//Dark Green
				color1 = new Color(0, 155, 0);
				//Light Green
				color2 = new Color(0, 255, 0);
				colorName = "GREEN";
				break;
			case 6:
				//Dark Yellow
				color1 = new Color(153, 153, 0);
				//Light Yellow
				color2 = new Color(255, 255, 255);
				colorName = "YELLOW";
				break;
			case 7:
				//Dark Cyan
				color1 = new Color(0, 155, 155);
				//Light Cyan
				color2 = new Color(0, 255, 255);
				colorName = "CYAN";
				break;
			case 8:
				//Dark Brown
				color1 = new Color(153, 76, 0);
				//Light Brown
				color2 = new Color(255, 128, 0);
				colorName = "BROWN";
				break;
			case 9:
				//Dark Wine
				color1 = new Color(153, 0, 76);
				//Light Wine
				color2 = new Color(255, 0, 127);
				colorName = "WINE";
				break;
			default:
				//Dark slate Green
				color1 = new Color(32, 178, 170);
				//Light Pink
				color2 = new Color(102, 205, 170);
				colorName = "PINK";
				break;
		
		
		}
		
		
		
		
		start = false;
		
		tileMap = tm;
		
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getSpeedX() { return speedX; }
	public int getSpeedY() { return speedY; }
	public int getPowDec() { return powDec; }
	public int getPowerUp(){ 
		return Character.getNumericValue(powerUp); 
	}
	public boolean isPowerUp(){	return power; }
	public boolean havePower(){ return haveUsedPowUp; }
	public boolean getStart(){ return start; }
	
	
	
	public void setX(int dx){ x = dx; }
	public void setY(int dy){ y = dy; }
	public void setSpeedX(int x){ speedX = x; }
	public void setSpeedY(int y){ speedY = y; }
	
	public void setStart(boolean b){ start = b; }
	public void decPowDec() { 
		if(powDec > 0){
			powDec--; 
		}
	}
	public void setPowerUp(boolean a){ havePowerUp = a; }
	public void setUsePow(boolean b) { 
		haveUsedPowUp = b; 
		powDec = 2;
	}
	
	public void setLeft(boolean b){ left = b;}
	public void setRight(boolean b){ right = b;}
	public void setDown(boolean b){ down = b;}
	public void setUp(boolean b){ up = b;}
	
	public void update(){
		if(havePowerUp && numTilPowUp >= 3){
			//TODO
			power = true;
		}
		else{
			power = false;
		}
		
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
		
		if(numTilPowUp < 3){
			randomize = randomer.nextInt(590);
			numTilPowUp++;
		}
		
		
		
		
		
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
		
		
		
		//Power up Display
		
		char powerUps[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		
		
		if(havePowerUp || powDec > 0){
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 12));
			//Set text colour to black if powerup is ready.
			if(powDec > 0){
				g.setColor(Color.BLACK);
			}
			else if(numTilPowUp >= 3){
				
				powerUp = powerUps[randomize % powerUps.length];
				
				//powerUp test
				g.setColor(Color.RED);
				g.drawString(Character.toString(powerUp), 50, 50);
				g.setColor(Color.GRAY);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawString(Character.toString(powerUps[randomize % powerUps.length]), carX + 6, carY + 13);
		}
		
		
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
			GamePanel.winner = colorName;
		}
		if(checker == 3){
			havePowerUp = true;
			numTilPowUp = 0;
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
