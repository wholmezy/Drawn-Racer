package mainGame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;

//Drawn Racer
public class GamePanel extends JPanel implements Runnable, KeyListener {
	//Fields
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	
	public static int squareSize = 600 / 30;
	
	private Thread thread;
	boolean running;
	
	private boolean startGame;
	private boolean anyGameStart;
	private boolean multiGame;
	private boolean multiGameStart2;
	private boolean multGameStart3;
	private boolean start;
	private boolean playerTurn1;
	private boolean playerTurn2;
	private boolean w, a, s, d;
	
	int menuItem;
	int menuItemSize;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private double averageFPS;
	
	public int numLines = WIDTH / squareSize;
	
	private Player player;
	private Player player1;
	private Player player2;
	
	private TileMap tileMap;
	private TileMap tileMap2;
	
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
	
	public void init(){
		
	}
	
	public void run() {
		running = true;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		tileMap = new TileMap("C:\\Users\\Wholm_000\\map2.txt", WIDTH / squareSize);
		tileMap2 = new TileMap("C:\\Users\\Wholm_000\\map2.txt", WIDTH / squareSize);
		
		player = new Player(tileMap);
		player1 = new Player(tileMap2);
		player2 = new Player(tileMap2);
		
		start = false;
		startGame = false;
		anyGameStart = false;
		multiGameStart2 = false;
		
		menuItemSize = 2;
		menuItem = 0;
		
		w = false;
		a = false;
		s = false;
		d = false;
		
		//to fix tilemap position.
		player.update();
		
		player1.update();
		player2.update();
		
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
		tileMap.update();
		
		if(start){
			if(s){
				if(startGame){
					player.setDown(s);
					s = false;
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setDown(s);
						s = false;
					}
					else{
						player2.setDown(s);
						s = false;
					}
				}
			}
			if(w){
				if(startGame){
					player.setUp(w);
					w = false;
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setUp(w);
						w = false;
					}
					else{
						player2.setUp(w);
						w = false;
					}
				}
			}
			if(a){
				if(startGame){
					player.setLeft(a);
					a = false;
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setLeft(a);
						a = false;
					}
					else{
						player2.setLeft(a);
						a = false;
					}
				}
				
			}
			if(d){
				if(startGame){
					player.setRight(d);
					d = false;
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setRight(d);
						d = false;
					}
					else{
						player2.setRight(d);
						d = false;
					}
				}
			}
			if(startGame){
				updatePlayer(player);
			}
			if(multiGameStart2){
				if(playerTurn1){
					updatePlayer(player1);
				}
				else{
					updatePlayer(player2);
				}
			}
			
		}
		
	}
	private void updatePlayer(Player player){
		player.update();
		player.setDown(s);
		player.setUp(w);
		player.setLeft(a);
		player.setRight(d);
		start = false;
	}
	
	private void gameRender() {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 10));
		g.setColor(Color.RED);
		g.drawString("FPS: " + (int) averageFPS, 10, 10);
		g.setColor(Color.BLACK);
		
		
		if(!anyGameStart && !multiGame && !startGame){
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
			//g.drawString("Press Enter to start the game!", WIDTH / 2 - 130, HEIGHT / 2);
			
			
			
			//Game mode select.
			g.setColor(Color.BLACK);
			g.fillRect(WIDTH / 2 - 130, HEIGHT / 2 - 100, 270, 50);
			
			
			g.setColor(Color.WHITE);
			if(menuItem == 0){
				g.fillRect(WIDTH / 2 - 120, HEIGHT / 2 - 95, 250, 40);
				
				g.setColor(Color.BLACK);
			}
			g.drawString("SINGLEPLAYER", WIDTH / 2 - 65, HEIGHT / 2 - 68);
			
			g.setColor(Color.BLACK);
			g.fillRect(WIDTH / 2 - 130,  HEIGHT / 2 - 32,  270,  50);
			
			g.setColor(Color.WHITE);
			if(menuItem == 1){
				
				g.fillRect(WIDTH / 2 - 120, HEIGHT / 2 - 27, 250, 40);
				
				g.setColor(Color.BLACK);
			}
			g.drawString("MULTIPLAYER", WIDTH / 2 - 65, HEIGHT / 2);
			
			g.setColor(Color.BLACK);
		}
		else if(startGame){
			// X start pos, Y start pos, X end pos, Y end pos
			tileMap.draw(g);
			g.setColor(Color.BLACK);
			for(int i = 1; i <= numLines; i++){
				g.drawLine(0, (HEIGHT / squareSize) * i, WIDTH, (HEIGHT / squareSize) * i);
				g.drawLine((HEIGHT / squareSize) * i, 0, (HEIGHT / squareSize) * i, HEIGHT);
			}
			
			player.draw(g);
		}
		else if(multiGame){
			
			//Menu Select.
			g.setColor(Color.BLACK);
			g.fillRect(WIDTH / 2 - 130, HEIGHT / 2 - 100, 270, 50);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
			g.setColor(Color.WHITE);
			if(menuItem == 0){
				g.fillRect(WIDTH / 2 - 120, HEIGHT / 2 - 95, 250, 40);
				
				g.setColor(Color.BLACK);
			}
			g.drawString("2-Player Local", WIDTH / 2 - 65, HEIGHT / 2 - 68);
			
			g.setColor(Color.BLACK);
			g.fillRect(WIDTH / 2 - 130,  HEIGHT / 2 - 32,  270,  50);
			
			g.setColor(Color.WHITE);
			if(menuItem == 1){
				
				g.fillRect(WIDTH / 2 - 120, HEIGHT / 2 - 27, 250, 40);
				
				g.setColor(Color.BLACK);
			}
			g.drawString("3-Player Local", WIDTH / 2 - 65, HEIGHT / 2);
			
			g.setColor(Color.BLACK);
			
			
		}
		else if(multiGameStart2){
			tileMap2.draw(g);
			g.setColor(Color.BLACK);
			for(int i = 1; i <= numLines; i++){
				g.drawLine(0, (HEIGHT / squareSize) * i, WIDTH, (HEIGHT / squareSize) * i);
				g.drawLine((HEIGHT / squareSize) * i, 0, (HEIGHT / squareSize) * i, HEIGHT);
			}
			
			player1.draw(g);
			player2.draw(g);
		}
		
	}
	//Done
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();	
	}
	
	
	
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT){
			if(anyGameStart){
				if(startGame){
					player.setLeft(true);
					
					player.update2();
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setLeft(true);
						
						player1.update2();
					}
					else{
						player2.setLeft(true);
						
						player2.update2();
					}
				}
				a = true;
				
				w = false;
				d = false;
				s = false;
			}
		}
		
		if(keyCode == KeyEvent.VK_RIGHT){
			if(anyGameStart){
				if(startGame){
					player.setRight(true);
					player.update2();
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setRight(true);
						
						player1.update2();
					}
					else{
						player2.setRight(true);
						
						player2.update2();
					}
				}
	
				
				
				
				d = true;
				
				w = false;
				s = false;
				a = false;
			}
		}
		
		if(keyCode == KeyEvent.VK_UP){
			if(!anyGameStart){
				menuItem = (menuItem + 1) % menuItemSize;
			}
			else{
				if(startGame){
					player.setUp(true);
					player.update2();
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setUp(true);
						
						player1.update2();
					}
					else{
						player2.setUp(true);
						
						player2.update2();
					}
				}
				
	
				
				w = true;
				
				s = false;
				d = false;
				a = false;
			}
		}
		
		if(keyCode == KeyEvent.VK_DOWN){
			if(!anyGameStart){
				menuItem = (menuItem + 1) % menuItemSize;
			}
			else{
				if(startGame){
					player.setDown(true);
					//start = true;
					player.update2();
				}
				else if(multiGameStart2){
					if(playerTurn1){
						player1.setDown(true);
						
						player1.update2();
					}
					else{
						player2.setDown(true);
						
						player2.update2();
					}
				}
				
				s = true;
				w = false;
				d = false;
				a = false;
			}
		}
		if(keyCode == KeyEvent.VK_SPACE){
			if(anyGameStart){
				start = true;
				if(multiGameStart2){
					if(playerTurn1){
						playerTurn1 = false;
						playerTurn2 = true;
					}
					if(playerTurn2){
						playerTurn2 = false;
						playerTurn1 = true;
					}
					
				}
			}
		}
		if(keyCode == KeyEvent.VK_ENTER){
			//Menu Select
			if(menuItem == 0 && !multiGame){
				startGame = true;
				anyGameStart = true;
			}
			else if(menuItem == 1 && !multiGame){
				multiGame = true;
			}
			else if(menuItem == 0 && multiGame){
				anyGameStart = true;
				multiGameStart2 = true;
				multiGame = false;
			}
			else if(menuItem == 1 && multiGame){
				
			}
		}
		if(keyCode == KeyEvent.VK_ESCAPE){
			//Get back to menu.
			anyGameStart = false;
			startGame = false;
			multiGame = false;
		}
		
	}
	public void keyReleased(KeyEvent key) {
		
		int keyCode = key.getKeyCode();
		
		
		if(keyCode == KeyEvent.VK_LEFT){
			if(anyGameStart){
				player.setLeft(false);
				player1.setLeft(false);
				player2.setLeft(false);
			}
			
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			if(anyGameStart){
				player.setRight(false);
			
				player1.setRight(false);
			
				player2.setRight(false);
				
			}
		}
		if(keyCode == KeyEvent.VK_DOWN){
			if(anyGameStart){
				player.setDown(false);
			
			
			
				player1.setDown(false);
				
		
				player2.setDown(false);
				
			}
		}
		if(keyCode == KeyEvent.VK_UP){
			if(anyGameStart){
				player.setUp(false);
			
			
				player1.setUp(false);
			
				player2.setUp(false);
				
			}
		}
		
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int getWidth(){ return WIDTH; }
	public int getHeight(){ return HEIGHT; }


}