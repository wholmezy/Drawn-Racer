package mainGame;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.ArrayList;

//Drawn Racer
public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	
	public static int squareSize = 50;
	
	public static boolean gameOver;
	
	private Thread thread;
	boolean running;
	
	private boolean anyGameStart;
	private boolean usePowerUp;
	private boolean multiGame;
	private boolean start;
	private boolean w, a, s, d;
	
	private int menuItem;
	private int menuItemSize;
	private int playerTurn;
	
	
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private double averageFPS;
	
	

	private ArrayList<Player> playerList;
	public static int winningPlayer;
	public int numPlayers;
	public static String winner;
	
	private TileMap tileMap;
	private TileMap tileMap2;
	private PowerUps powerControl;
	
	
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
		
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		
		//tilemap2 is for multiplayer.
		tileMap = new TileMap("map2.txt", WIDTH / squareSize);
		tileMap2 = new TileMap("map2.txt", WIDTH / squareSize);
		
		playerList = new ArrayList<Player>();
		
		
		running = true;
		anyGameStart = false;
		usePowerUp = false;
		//Start is the pauser inbetween moves.
		start = false;
		
		w = false;
		a = false;
		s = false;
		d = false;
		
		gameOver = false;
		
		numPlayers = 0;
		winningPlayer = 0;
		playerTurn = 0;
		
		
		menuItemSize = 3;
		menuItem = 1;
		
		
		
	}
	
	public void run() {
		
		init();
		
		//Fix FPS.
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
					
				playerList.get(playerTurn).setDown(s);
				
				s = false;
			}
			if(w){
					
				playerList.get(playerTurn).setUp(w);
				
				w = false;
			}
			if(a){
				
				if(anyGameStart){	
					playerList.get(playerTurn).setLeft(a);
				
					a = false;
				}
				
			}
			if(d){
				if(anyGameStart){
					
					playerList.get(playerTurn).setRight(d);
				
				d = false;
				}
			}
			//TODO
			
			if(anyGameStart){
				updatePlayer(playerList.get(playerTurn));
				for(int i = 0; i < playerList.size(); i++){
					if(playerList.get(i).getPowDec() > 0){
						PowerUpHandler(playerList, playerList.get(i).getPowerUp());
						playerList.get(i).decPowDec();
					}
				}
				for(int i = 0; i < playerList.size(); i++){
					if(playerList.get(i).isPowerUp() && usePowerUp){
						PowerUpHandler(playerList, playerList.get(i).getPowerUp());
						usePowerUp = false;
						playerList.get(i).setUsePow(true);
						playerList.get(i).setPowerUp(false);
					}
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
		playerTurn = (playerTurn + 1) % numPlayers;
	}
	
	
	
	private void gameRender() {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 10));
		g.setColor(Color.RED);
		g.drawString("FPS: " + (int) averageFPS, 10, 10);
		g.setColor(Color.BLACK);
		
		//StartScreen
		if(!anyGameStart && !multiGame && numPlayers == 0){
			
			g.setFont(new Font("impact", Font.ITALIC, 60));
			g.drawString("SLOW RACER", WIDTH / 2 - 150, HEIGHT / 2 - 155);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
			//g.drawString("Press Enter to start the game!", WIDTH / 2 - 130, HEIGHT / 2);
			
			
			
			//Game mode select.
			g.setColor(Color.BLACK);
			g.fillRect(WIDTH / 2 - 130, HEIGHT / 2 - 100, 270, 50);
			
			
			g.setColor(Color.WHITE);
			if(menuItem == 1){
				g.fillRect(WIDTH / 2 - 120, HEIGHT / 2 - 95, 250, 40);
				
				g.setColor(Color.BLACK);
			}
			g.drawString("SINGLEPLAYER", WIDTH / 2 - 45, HEIGHT / 2 - 68);
			
			g.setColor(Color.BLACK);
			g.fillRect(WIDTH / 2 - 130,  HEIGHT / 2 - 32,  270,  50);
			
			g.setColor(Color.WHITE);
			if(menuItem == 2){
				
				g.fillRect(WIDTH / 2 - 120, HEIGHT / 2 - 27, 250, 40);
				
				g.setColor(Color.BLACK);
			}
			g.drawString("MULTIPLAYER", WIDTH / 2 - 45, HEIGHT / 2);
			
			g.setColor(Color.BLACK);
		}
		//singlePlayer screen
		else if(numPlayers == 1 && !gameOver){
			// X start pos, Y start pos, X end pos, Y end pos
			tileMap.draw(g);
			g.setColor(Color.BLACK);
			for(int i = 1; i <= squareSize; i++){
				g.drawLine(0, (HEIGHT / squareSize) * i, WIDTH, (HEIGHT / squareSize) * i);
				g.drawLine((HEIGHT / squareSize) * i, 0, (HEIGHT / squareSize) * i, HEIGHT);
			}
			playerList.get(playerTurn).draw(g, true);
		}
		//Second menu for multiplayer games.
		else if(multiGame){
			g.setFont(new Font("impact", Font.ITALIC, 60));
			g.drawString("SLOW RACER", WIDTH / 2 - 150, HEIGHT / 2 - 155);
			
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 30));
			
			//TODO: Add up / down keys for better feeling of control.
			
			g.setColor(Color.BLACK);
			g.drawString("Number Of Players", WIDTH / 2 - 110, HEIGHT / 2 - 68);
			g.drawString(Integer.toString(menuItem), WIDTH / 2, HEIGHT / 2);
			
			
			
			
		}
		//multiPlayer screen.
		else if(numPlayers > 1 && !gameOver){
			tileMap2.draw(g);
			g.setColor(Color.BLACK);
			for(int i = 1; i <= squareSize; i++){
				g.drawLine(0, (HEIGHT / squareSize) * i, WIDTH, (HEIGHT / squareSize) * i);
				g.drawLine((HEIGHT / squareSize) * i, 0, (HEIGHT / squareSize) * i, HEIGHT);
			}
			for(int i = 0; i < numPlayers; i++){
				playerList.get(i).draw(g, false);
			}
			playerList.get(playerTurn).draw(g, true);
		}
		//Game over screen.
		else if(gameOver){
			
			
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
				
				playerList.get(playerTurn).setLeft(true);
				playerList.get(playerTurn).update2();
					
				
				a = true;
				
				w = false;
				d = false;
				s = false;
			}
		}
		
		if(keyCode == KeyEvent.VK_RIGHT){
			if(anyGameStart){
				
				
				playerList.get(playerTurn).setRight(true);
				playerList.get(playerTurn).update2();
				
				d = true;
				
				w = false;
				s = false;
				a = false;
			}
		}
		
		if(keyCode == KeyEvent.VK_UP){
			if(!anyGameStart){
				if(menuItem >= menuItemSize - 1){
					menuItem = 1;
				}
				else{
					menuItem = ((menuItem) % menuItemSize) + 1;
				}
			}
			else{
				
				
					
				playerList.get(playerTurn).setUp(true);
				playerList.get(playerTurn).update2();
				
				
	
				
				w = true;
				
				s = false;
				d = false;
				a = false;
			}
			
		}
		
		if(keyCode == KeyEvent.VK_DOWN){
			if(!anyGameStart){
				
				if(menuItem < 2){
					menuItem = menuItemSize - 1;
				}
				else{
					menuItem = (menuItem % menuItemSize) - 1;
				}
			}
			else{
				
				playerList.get(playerTurn).setDown(true);
				playerList.get(playerTurn).update2();
					
		
				
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
			if(menuItem == 1 && !multiGame){
				numPlayers = 1;
				anyGameStart = true;
				for(int i = 0; i < numPlayers; i++){
					playerList.add(new Player(tileMap, i));
				}
			}
			else if(menuItem == 2 && !multiGame){
				multiGame = true;
				menuItemSize = 10;
			}
			else if(multiGame){
				
				anyGameStart = true;
				numPlayers = menuItem;
				multiGame = false;
				for(int i = 0; i < numPlayers; i++){
					playerList.add(new Player(tileMap, i));
				}
				
			}
			else if(menuItem == 2 && multiGame){
				
			}
		}
		if(keyCode == KeyEvent.VK_ESCAPE){
			//Get back to menu.
			anyGameStart = false;
			numPlayers = 0;
			multiGame = false;
			gameOver = false;
			menuItemSize = 3;
			menuItem = 1;
		}
		if(keyCode == KeyEvent.VK_E){
			if(anyGameStart){
				usePowerUp = true;
				
			}
		}
		
	}
	public void keyReleased(KeyEvent key) {
		
		int keyCode = key.getKeyCode();
		
		
		if(keyCode == KeyEvent.VK_LEFT){
			if(anyGameStart){
				
				for(int i = 0; i < numPlayers; i++){
					playerList.get(i).setLeft(false);
				}
			}
			
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			if(anyGameStart){
				
			
				for(int i = 0; i < numPlayers; i++){
					playerList.get(i).setRight(false);
				}
			}
		}
		if(keyCode == KeyEvent.VK_DOWN){
			if(anyGameStart){
				
			
			
			
				for(int i = 0; i < numPlayers; i++){
					playerList.get(i).setDown(false);
				}
				
			}
		}
		if(keyCode == KeyEvent.VK_UP){
			if(anyGameStart){
				
			
			
				for(int i = 0; i < numPlayers; i++){
					playerList.get(i).setUp(false);
				}
				
			}
		}
		
	}
	
	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int getWidth(){ return WIDTH; }
	public int getHeight(){ return HEIGHT; }
	
	public void PowerUpHandler(ArrayList<Player> players, int whatPowerUp){
		if(whatPowerUp == 'g'){
			powerUpG(players);
		}
		else if(whatPowerUp == 's'){
			powerUpS(players);
		}
		else if(whatPowerUp == 'u'){
			powerUpU(players);
		}
		else if(whatPowerUp == 'r'){
			powerUpR(players);
		}
		else{
			
		}
		
	}
	
	//Gravity.
	public void powerUpG(ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++){
			int y = players.get(i).getSpeedY();
			players.get(i).setSpeedY(y + HEIGHT / squareSize);
		}
	}
	//Speeder
	public void powerUpS(ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++){
			int x = players.get(i).getSpeedX();
			players.get(i).setSpeedX(x + WIDTH / squareSize);
		}
	}
	//UpYouGo!
	public void powerUpU(ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++){
			int y = players.get(i).getSpeedY();
			players.get(i).setSpeedY(y - HEIGHT / squareSize);
		}
	}
	//Retardate.
	public void powerUpR(ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++){
			int x = players.get(i).getSpeedX();
			players.get(i).setSpeedX(x - WIDTH / squareSize);
		}
	}

}