package mainGame;
import java.awt.*;





public class Player {
	
	private int x;
	private int y;
	private int x2;
	private int y2;
	
	private int width;
	private int height;
	
	private int speedX;
	private int speedY;
	private int storeSpeedX;
	private int storeSpeedY;
	
	
	private Color color1;
	private Color color2;
	private Color color3;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean start;
	
	
	
	public Player(){
		width = GamePanel.WIDTH / 30;
		height = GamePanel.HEIGHT / 30;
		
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		
		speedX = 0;
		speedY = 0;
		storeSpeedX = 0;
		storeSpeedY = 0;
		
		color1 = Color.black;
		color2 = Color.GRAY;
		color3 = Color.DARK_GRAY;
		
		start = false;
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

		if(left){
			speedX -= GamePanel.HEIGHT / 30;
		}
		if(right){
			speedX += GamePanel.HEIGHT / 30;
		}
		if(up){
			speedY -= GamePanel.WIDTH / 30;
		}
		if(down){
			speedY += GamePanel.WIDTH / 30;
		}
		y += speedY;
		x += speedX;
			
			
		if(x < width){ x = 0; }
		if(y < height){ y = 0; }
		
		if(x > GamePanel.WIDTH - width){ x = GamePanel.WIDTH - width; }
		if(y > GamePanel.HEIGHT - height) { y = GamePanel.HEIGHT - height; }
		
		
	}
	public void update2(){
		if(left){
			if(x + storeSpeedX > x + speedX - width&&
			   y + storeSpeedY > y + speedY - height && //Up
			   y + storeSpeedY < y + speedY + height){ //Down		
				storeSpeedX -= GamePanel.HEIGHT / 30;
			}
		}
		if(right){
			if(x + storeSpeedX < x + speedX + width &&
			   y + storeSpeedY > y + speedY - height && //Up
			   y + storeSpeedY < y + speedY + height){ //Down
				storeSpeedX += GamePanel.HEIGHT / 30;
			}
		}
		if(up){
			if(y + storeSpeedY > y + speedY - height &&
			   x + storeSpeedX > x + speedX - width && //Left
			   x + storeSpeedX < x + speedX + width){ //Right
				storeSpeedY -= GamePanel.WIDTH / 30;
			}
		}
		if(down){
			if(y + storeSpeedY < y + speedY + height &&
			   x + storeSpeedX > x + speedX - width && //Left
			   x + storeSpeedX < x + speedX + width){ //Right
				storeSpeedY += GamePanel.WIDTH / 30;
			}
		}
		
		if(x2 < width){ x2 = 0; }
		if(y2 < height){ y2 = 0; }
		
		if(x2 > GamePanel.WIDTH - width){ x2 = GamePanel.WIDTH - width; }
		if(y2 > GamePanel.HEIGHT - height) { y2 = GamePanel.HEIGHT - height; }
		
		start = false;
		
		
		
		
	}
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillRect(x, y, width, height);
		g.setColor(color2);
		g.fillRect(x + speedX, y + speedY, width, height);
		g.setColor(color3);
		g.fillRect(x + storeSpeedX, y + storeSpeedY, width, height);
	}
	
}
