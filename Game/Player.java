package mainGame;
import java.awt.*;





public class Player {
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private int speedX;
	private int speedY;
	
	
	private Color color1;
	private Color color2;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	
	
	public Player(){
		width = GamePanel.WIDTH / 30;
		height = GamePanel.HEIGHT / 30;
		
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		
		speedX = 0;
		speedY = 0;
		
		color1 = Color.black;
		color2 = Color.GRAY;
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	
	public void setX(int dx){ x = dx; }
	public void setY(int dy){ y = dy; }
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
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillRect(x, y, width, height);
		g.setColor(color2);
		g.fillRect(x + speedX, y + speedY, width, height);
	}
	
}
