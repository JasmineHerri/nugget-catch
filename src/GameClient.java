//Jasmine Herri 
//Period 5, AP CS A 
//June 13, 2021 
//Early Bird Game 

//This program will run the Early Bird game where a bird graphic which is controlled by the user will try to catch golden nuggets that are falling
//from the sky. If the user fails to catch 3 nuggets the game is over. 
//Bombs will also be falling from the sky and if the user catches on the game is over 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import java.util.ArrayList;

public class GameClient implements KeyListener{
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
		long startLoopTime = 0; 
		final int TARGET_FPS = 30; 
		final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS; 
	
		startMessage();
		
		while (!done) { //will run no faster than 30 times per second (33.33 ms)
			startLoopTime = System.nanoTime(); 
			movePlayer(player); //checks what the player's move is and if the player chose to quit 
			resetBackground();

			//will keep track of all three nuggets and if they were caught or not; when three nuggets are missed the game will end 
			nugget1 = checkNugget(nugget1, player);
			nugget2 = checkNugget(nugget2, player);
			nugget3 = checkNugget(nugget3, player);
			
			//will keep track of all three bombs and if they were caught; if so, the while loop will end 
			b1 = checkBomb(b1, player);
			b2 = checkBomb(b2, player);
			b3 = checkBomb(b3, player);
			
			resetScorePortion(); //re-color the white strip at the bottom so the objects will disappear when they reach the ground
			player.draw(g); //draw the bird's current position 
			displayTotals(player.getNumCaught(), player.getNumMissed()); //display the scores at the bottom 

									
			try { //how many ms remains before the loop starts again 
				long delay = (startLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000;
				Thread.sleep(delay);
			} catch(Exception e) {}
		}
		
		g.drawString("Game Over", 380, 300); 
	}
	
	public static GoldenNugget checkNugget(GoldenNugget nugget, Bird player) {
		nugget.changePos(0, nugget.getFallRate() + 1);
		nugget.draw(g); //draws the nugget as it falls
		
		if (didCatch(player, nugget)) { //checks if the nugget was caught 
			nugget = new GoldenNugget(nugget.getFallRate()); //will create a new nugget at a random location 
			player.addNumCaught(); //increment the number of nuggets caught in player  
		}	
		else if (nugget.getY() == 590) {//this is where the nugget hits the ground --> registers if the nugget wasn't caught 
			player.addNumMissed(); //increment the number of nuggets missed in player  
			nugget = new GoldenNugget(nugget.getFallRate());  //will create a new nugget at a random location 
			if (player.getNumMissed() == 3) done = true; //when the player misses a total of 3 nuggets game over 
		}
		return nugget; //returns the new nugget with the new location 
	}
	
	public static Bomb checkBomb(Bomb b, Bird player) {		
		b.changePos(0, b.getFallRate() + 1);
		b.draw(g); //draws the bomb falling 
		
		if (didCatch(player, b)) { //if the userr catches a bomb game over 
			keysDown.add(42); //will stop the game by "pressing" the space bar 
			done = true; 
		}
		else if (b.getY() == 550) b = new Bomb(b.getFallRate()); //creates a new bomb when the previous one hits the ground
		
		return b; //returns the new bomb that has a new location  
	}
	
	public static void resetBackground() { //will draw the background of the game 
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 800, 600);
		drawClouds(100, 200);
		drawClouds(350, 125);
		drawClouds(600, 200);
	}
	
	public static void resetScorePortion() { //colors the bottom portion of the screen that has the scores so that the nugget/bomb disappear when they hit the bottom of the cyan screen 
		g.setColor(Color.WHITE); 
		g.fillRect(0, 600, 800, 30);

	}
	
	public static void movePlayer(Bird player) { //will move the bird object according to which arrow the user presses (left/right) 
		if (keysDown.contains(37))player.changePos(-10, 0);
		if (keysDown.contains(39))player.changePos(10, 0);
		if (keysDown.contains(32)) done = true; //if the space key is pressed the game will end
		
	}
	
	public static boolean didCatch(Bird player, FallingObject o) {//checks if the nugget/bomb were caught
		boolean didCatch = false; 
		if ((o instanceof GoldenNugget && 537 == o.getY() + 15) || (o instanceof Bomb && 537 == o.getY() + 29)) { //compares the height of the plate to the height of the bottom of the nugget/bomb 
			if (o.getX() >= player.getX() && o.getX() <= player.getX() + 50) { //checks to see if the nugget/bomb is within the plate's horizontal surface 
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
	
	public static void displayTotals(int numCaught, int numMissed) { //displays the number of nuggets cuaght and the number of nuggets missed 
		g.drawString("Score: " + numCaught, 15, 620);
		g.drawString("Number of Nuggets Missed: " + numMissed + " of 3", 550, 620);

	}
	
	public static void startMessage() { //will display the start message until the user hits enter and is ready to play the game
		boolean notReady = true; 
		
		while (notReady) {
			g.drawString("Welcome to Early Bird!", 320, 300);
			g.drawString("Catch the golden nuggets but don't miss! Be careful of the bombs!", 200, 315);
			g.drawString("Press enter to begin and the space bar to quit", 290, 330);
			if (keysDown.contains(10)) notReady = false; 
		} 
	}
		
	public void keyPressed(KeyEvent e) { //will keep track of what the user has pressed 
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

