import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import java.util.ArrayList;

public class OGClient implements KeyListener{
	public static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	public static DrawingPanel dp = new DrawingPanel(800,630);
	public static Graphics g = dp.getGraphics();
	static boolean done = false; 
	
	public static void main(String[] args) {
		dp.addKeyListener(new GameClient());
		Bird player = new Bird();
		GoldenNugget nugget1 = new GoldenNugget(0); 
		GoldenNugget nugget2 = new GoldenNugget(1);
		GoldenNugget nugget3 = new GoldenNugget(2); 

		Bomb b1 = new Bomb(0);
		Bomb b2 = new Bomb(1);
		Bomb b3 = new Bomb(2);
		
		//int fallRate = 1; 
		//int numCaught = 0; 
		//int numMissed = 0; 
		long startLoopTime = 0; 
		final int TARGET_FPS = 30; 
		final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS; 
		//boolean done = false; 
	
		startMessage();
		
		while (!done) { //will run no faster than 30 times per second (33.33 ms)
			startLoopTime = System.nanoTime(); 
			done = movePlayer(player); //checks what the player's move is and if the player chose to quit 
			resetBackground();

			//nugget1.changePos(0, nugget1.getFallRate() + 1);//the higher the level, the faster the object falls 
			//nugget1.draw(g); //draws the nugget as it falls
			//b1.changePos(0, b1.getFallRate() + 1);
			//b1.draw(g);
			
			nugget1 = checkNugget(nugget1, player);
			nugget2 = checkNugget(nugget2, player);
			nugget3 = checkNugget(nugget3, player);
			
			b1 = checkBomb(b1, player);
			b2 = checkBomb(b2, player);
			b3 = checkBomb(b3, player);
			
			/*if (didCatch(player, nugget)) {
				nugget = new GoldenNugget();
				numCaught++; 
				if (numCaught > 0 && numCaught % 5 == 0) {//after the user catches 5 nuggets the fallRate goes up and the objects will fall faster 
					fallRate++; 
				}
			}	
			else if (nugget.getY() == 590) {//this is where the nugget hits the ground 
				numMissed++;
				nugget = new GoldenNugget(); 
				if (numMissed == 3) done = true; 
			}
			else if (didCatch(player, b)) {
				keysDown.add(42); //will stop the game 
				done = true; 
			}
			else if (b.getY() == 550) b = new Bomb(); //creates a new bomb when the old previous one hits the ground
			*/
			
			g.setColor(Color.WHITE); //colors the bottom portion of the screen that has the scores so that the nugget/bomb disappear when they hit the bottom of the cyan screen 
			g.fillRect(0, 600, 800, 30);
			player.draw(g);
			displayTotals(player.getNumCaught(), player.getNumMissed());
									
			try { //how many ms remains before the loop starts again 
				long delay = (startLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000;
				Thread.sleep(delay);
			} catch(Exception e) {}
		}
		g.drawString("Game Over", 380, 300);
	}
	
	public static GoldenNugget checkNugget(GoldenNugget nugget, Bird player) {
		nugget.changePos(0, nugget.getFallRate() + 1);//the higher the level, the faster the object falls 
		nugget.draw(g); //draws the nugget as it falls
		
		if (didCatch(player, nugget)) {
			nugget = new GoldenNugget(nugget.getFallRate());
			player.addNumCaught(); 
			/*if (player.getNumCaught() > 0 && player.getNumCaught() % 5 == 0) {//after the user catches 5 nuggets the fallRate goes up and the objects will fall faster 
				nugget.setFallRate(1); 
				//nugget.setFallRate(1);
			}*/
		}	
		else if (nugget.getY() == 590) {//this is where the nugget hits the ground 
			player.addNumMissed();
			nugget = new GoldenNugget(nugget.getFallRate()); 
			if (player.getNumMissed() == 3) done = true; 
		}
		return nugget; 
	}
	
	public static Bomb checkBomb(Bomb b, Bird player) {		
		b.changePos(0, b.getFallRate() + 1);
		b.draw(g);
		
		if (didCatch(player, b)) {
			keysDown.add(42); //will stop the game 
			done = true; 
		}
		else if (b.getY() == 550) b = new Bomb(b.getFallRate()); //creates a new bomb when the old previous one hits the ground
		
		return b; 
	}
	
	public static void resetBackground() {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 800, 600);
		drawClouds(100, 200);
		drawClouds(350, 125);
		drawClouds(600, 200);
	}
	
	public static boolean movePlayer(Bird player) {
		boolean done = false;
		if (keysDown.contains(37))player.changePos(-10, 0);
		if (keysDown.contains(39))player.changePos(10, 0);
		if (keysDown.contains(32)) done = true; //if the space key is pressed the game will end
		return done; 
		
	}
	
	public static boolean didCatch(Bird player, FallingObject o) {
		boolean didCatch = false; 
		if ((o instanceof GoldenNugget && 537 == o.getY() + 15) || (o instanceof Bomb && 537 == o.getY() + 29)) { //compares the height of the plate to the height of the bottom of the nugget/bomb 
			if (o.getX() >= player.getX() && o.getX() <= player.getX() + 50) { //checks to see if the nugget/bomb is within the plate's surface 
				didCatch = true;
			}
		}
		return didCatch; 
	}
	
	public static void drawClouds(int x, int y) {//makes a cloud shape using white ovals and circles 
		Graphics cloud = dp.getGraphics();
		cloud.setColor(Color.WHITE);
		cloud.fillOval(x, y - 30, 120, 80);
		cloud.fillOval(x + 95, y - 10, 100, 60);
		cloud.fillOval(x - 55, y - 15, 80, 60);
		cloud.fillOval(x + 30, y - 40, 90, 60);
	}
	
	public static void displayTotals(int numCaught, int numMissed) {
		g.drawString("Score: " + numCaught, 15, 620);
		g.drawString("Number of Nuggets Missed: " + numMissed + " of 3", 550, 620);

	}
	
	public static void startMessage() {
		boolean notReady = true; 
		
		while (notReady) {
			g.drawString("Welcome to Early Bird!", 320, 300);
			g.drawString("Catch the golden nuggets but don't miss! Be careful of the bombs!", 200, 315);
			g.drawString("Press enter to begin and the space bar to quit", 290, 330);
			if (keysDown.contains(10)) notReady = false; 
		} 
	}
		
	public void keyPressed(KeyEvent e) {
		int val = e.getKeyCode(); 
		System.out.println("pressed " + val);
		
		if (!keysDown.contains(new Integer (val))) {
			keysDown.add(val);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int val = e.getKeyCode(); 
		keysDown.remove(new Integer (val));
		System.out.println("released "+ val);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
}

