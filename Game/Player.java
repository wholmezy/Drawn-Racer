package mainGame;
import java.awt.*;





public class Player {
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private int speed;
	private boolean turnLeft;
	private boolean turnRight;
	
	private Color color1;
	
	
	
	public Player(){
		width = GamePanel.WIDTH / 30;
		height = GamePanel.HEIGHT / 30;
		
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		
		speed = 1;
		turnLeft = false;
		turnRight = false;
		
		color1 = Color.black;
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	public void setX(int dx){ x = dx; }
	public void setY(int dy){ y = dy; }
	
	
	public void update(){
		x += speed * GamePanel.WIDTH;
		if(turnLeft == true){
			y += GamePanel.HEIGHT;
		}
		if(turnRight == true){
			y += -GamePanel.HEIGHT;
		}
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
