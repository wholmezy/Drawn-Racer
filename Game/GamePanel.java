package mainGame;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;

//Drawn Racer
public class GamePanel extends JPanel implements Runnable, KeyListener {
	//Fields
	public static int WIDTH = 600;
	public static int HEIGHT = 600;
	
	public int squareSize = 600 / 30;
	
	private Thread thread;
	boolean running;
	
	private boolean nextTurn;
	private boolean startGame;
	private boolean start;
	private boolean w, a, s, d;
	
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private double averageFPS;
	
	public int numLines = 30;
	
	private Player player;
	

	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}
	
	public void run() {
		
		player = new Player();
		
		running = true;
		
		nextTurn = false;
		startGame = false;
		start = false;
		
		w = false;
		a = false;
		s = false;
		d = false;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000 / FPS;
		
		while(running){
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			
			waitTime = targetTime - URDTimeMillis;
			
			try{
				Thread.sleep(waitTime);
			}
			catch(Exception e){
			}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
		
		
		
	}
	
	
	private void gameUpdate() {
		
		if(start){
			if(s){
				player.setDown(s);
				s = false;
			}
			if(w){
				player.setUp(w);
				w = false;
			}
			if(a){
				player.setLeft(a);
				a = false;
			}
			if(d){
				player.setRight(d);
				d = false;
			}
			player.update();
			player.setDown(s);
			player.setUp(w);
			player.setLeft(a);
			player.setRight(d);
			nextTurn = false;
			start = false;
			
		}
		
	}
	
	private void gameRender() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 10));
		g.setColor(Color.RED);
		g.drawString("FPS: " + (int) averageFPS, 10, 10);
		g.setColor(Color.BLACK);
		
		
		if(!startGame){
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
			g.drawString("Press Enter to start the game!", WIDTH / 2 - 130, HEIGHT / 2);
		}
		else{
			// X start pos, Y start pos, X end pos, Y end pos
			for(int i = 1; i <= numLines; i++){
				g.drawLine(0, (HEIGHT / 30) * i, WIDTH, (HEIGHT / 30) * i);
				g.drawLine((HEIGHT / 30) * i, 0, (HEIGHT / 30) * i, HEIGHT);
			}
			
			player.draw(g);
		}
		
	}
	//DOne
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();	
	}

	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT){
			
			a = true;

			w = false;
			d = false;
			s = false;
			//start = true;
			player.update2();
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			
			d = true;

			w = false;
			s = false;
			a = false;
			//start = true;
			player.update2();
		}
		if(keyCode == KeyEvent.VK_UP){
			
			w = true;

			s = false;
			d = false;
			a = false;
			//start = true;
			player.update2();
		}
		if(keyCode == KeyEvent.VK_DOWN){
			
			//start = true;
			player.update2();
			
			s = true;
			
			w = false;
			d = false;
			a = false;
		}
		if(keyCode == KeyEvent.VK_SPACE){
			start = true;
		}
		if(keyCode == KeyEvent.VK_ENTER){
			startGame = true;
			
		}
		
	}
	public void keyReleased(KeyEvent key) {
		int keyCode = key.getKeyCode();
		
		
		if(keyCode == KeyEvent.VK_LEFT){
			player.setLeft(false);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			
			player.setDown(false);
			
		}
		if(keyCode == KeyEvent.VK_UP){
		
			player.setUp(false);
			
		}
		
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int getWidth(){ return WIDTH; }
	public int getHeight(){ return HEIGHT; }


}