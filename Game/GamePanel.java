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
	
	public static int squareSize = 50;
	
	public static boolean gameOver;
	
	private Thread thread;
	boolean running;
	
	private boolean anyGameStart;
	private boolean multiGame;
	
	private boolean start;
	private boolean playerTurn1;
	private boolean playerTurn2;
	
	
	
	private boolean w, a, s, d;
	
	int menuItem;
	int menuItemSize;
	
	public static int winningPlayer;
	public int numPlayers;
	private int winnerPlayer;
	
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private double averageFPS;
	
	
	
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
		
		
		//tilemap2 is for multiplayer.
		tileMap = new TileMap("map2.txt", WIDTH / squareSize);
		tileMap2 = new TileMap("map2.txt", WIDTH / squareSize);
		
		
		
		
		// The first number on the constructor is the color, either 1 or else.
		// The second number is which player it is.
		player = new Player(tileMap, 1, 1);
		player1 = new Player(tileMap2, 1, 1);
		player2 = new Player(tileMap2, 2, 2);
		
		//Start is the pauser inbetween moves.
		start = false;
		
		numPlayers = 0;
		winningPlayer = 0;
		playerTurn2 = true;
		anyGameStart = false;
		
		numPlayers = 0;
		
		menuItemSize = 2;
		menuItem = 0;
		
		w = false;
		a = false;
		s = false;
		d = false;
		
		gameOver = false;
		
		//to fix tilemap position.
		player.update(numPlayers);
		
		
		player1.update(numPlayers);
		
		
		player2.update(numPlayers);
		
		
		
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
				if(numPlayers == 1){
					player.setDown(s);
					s = false;
				}
				else if(numPlayers == 2){
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
				if(numPlayers == 1){
					player.setUp(w);
					w = false;
				}
				else if(numPlayers == 2){
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
				if(numPlayers == 1){
					player.setLeft(a);
					a = false;
				}
				else if(numPlayers == 2){
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
				if(numPlayers == 1){
					player.setRight(d);
					d = false;
				}
				else if(numPlayers == 2){
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
			if(numPlayers == 1){
				updatePlayer(player);
			}
			if(numPlayers == 2){
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
		player.update(numPlayers);
		player.setDown(s);
		player.setUp(w);
		player.setLeft(a);
		player.setRight(d);
		start = false;
		if(playerTurn1){
			playerTurn1 = false;
			playerTurn2 = true;
		}
		else{
			playerTurn2 = false;
			playerTurn1 = true;
		}
	}
	
	private void gameRender() {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 10));
		g.setColor(Color.RED);
		g.drawString("FPS: " + (int) averageFPS, 10, 10);
		g.setColor(Color.BLACK);
		
		
		if(!anyGameStart && !multiGame && numPlayers == 0){
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
		else if(numPlayers == 1 && !gameOver){
			// X start pos, Y start pos, X end pos, Y end pos
			tileMap.draw(g);
			g.setColor(Color.BLACK);
			for(int i = 1; i <= squareSize; i++){
				g.drawLine(0, (HEIGHT / squareSize) * i, WIDTH, (HEIGHT / squareSize) * i);
				g.drawLine((HEIGHT / squareSize) * i, 0, (HEIGHT / squareSize) * i, HEIGHT);
			}
			player.draw(g, true);
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
		else if(numPlayers == 2 && !gameOver){
			tileMap2.draw(g);
			g.setColor(Color.BLACK);
			for(int i = 1; i <= squareSize; i++){
				g.drawLine(0, (HEIGHT / squareSize) * i, WIDTH, (HEIGHT / squareSize) * i);
				g.drawLine((HEIGHT / squareSize) * i, 0, (HEIGHT / squareSize) * i, HEIGHT);
			}
			
			player1.draw(g, playerTurn1);
			player2.draw(g, playerTurn2);
		}
		else if(gameOver){
			String winner;
			if(winningPlayer == 1){
				winner = "ORANGE";
			}
			else{
				winner = "PURPLE";
			}
			g.setFont(new Font("impact", Font.PLAIN, 40));
			g.drawString(winner + " WON THE GAME!", WIDTH / 2 - 200, HEIGHT / 2 - 68);
			g.drawString("CONGRATULATIONS. YOU ARE THE BEST!", WIDTH / 2 - 300, HEIGHT / 2);
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
				if(numPlayers == 1){
					player.setLeft(true);
					
					player.update2();
				}
				else if(numPlayers == 2){
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
				if(numPlayers == 1){
					player.setRight(true);
					player.update2();
				}
				else if(numPlayers == 2){
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
				if(numPlayers == 1){
					player.setUp(true);
					player.update2();
				}
				else if(numPlayers == 2){
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
				if(numPlayers == 1){
					player.setDown(true);
					//start = true;
					player.update2();
				}
				else if(numPlayers == 2){
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
				
				
			}	
		}
				
			
		
		if(keyCode == KeyEvent.VK_ENTER){
			//Menu Select
			if(menuItem == 0 && !multiGame){
				numPlayers = 1;
				anyGameStart = true;
			}
			else if(menuItem == 1 && !multiGame){
				multiGame = true;
			}
			else if(menuItem == 0 && multiGame){
				anyGameStart = true;
				numPlayers = 2;
				multiGame = false;
			}
			else if(menuItem == 1 && multiGame){
				
			}
		}
		if(keyCode == KeyEvent.VK_ESCAPE){
			//Get back to menu.
			anyGameStart = false;
			numPlayers = 0;
			multiGame = false;
			gameOver = false;
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