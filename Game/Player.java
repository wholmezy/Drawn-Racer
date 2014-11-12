package mainGame;
import java.awt.*;





public class Player {
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private int speedX;
	private int speedY;
	
	private boolean turnLeft;
	private boolean turnRight;
	
	private Color color1;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean nextTurn;
	
	
	
	public Player(){
		width = GamePanel.WIDTH / 30;
		height = GamePanel.HEIGHT / 30;
		
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		
		speedX = 0;
		speedY = 0;

		
		left = false;
		right = false;
		
		color1 = Color.black;
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	public void setX(int dx){ x = dx; }
	public void setY(int dy){ y = dy; }
	public void setLeft(boolean b){ left = b; nextTurn = b; }
	public void setRight(boolean b){ right = b; nextTurn = b; }
	public void setDown(boolean b){ down = b; nextTurn = b; }
	public void setUp(boolean b){ up = b; nextTurn = b; }
	
	
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
			
			
			if(x < width){ x = width; }
			if(y < height){ y = height; }
			
			if(x > GamePanel.WIDTH - width){ x = GamePanel.WIDTH - width; }
			if(y > GamePanel.HEIGHT - height) { y = GamePanel.HEIGHT - height; }
		
	}
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillRect(x, y, width, height);
	}
	
}
