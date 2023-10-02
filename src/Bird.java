import java.awt.Color; 
import java.awt.Graphics;
import java.awt.event.KeyEvent; 

public class Bird extends FallingObject{
	int numCaught; 
	int numMissed; 
	
	public Bird() {
		setXAndY(100, 545);
		numCaught = 0;
		numMissed = 0; 
	}
	
	public void draw(Graphics body) {
		body.setColor(Color.YELLOW); 
		body.fillOval(getX(), getY(), 50, 50); //creates the body
		
		//creates the wings 
		body.fillOval(getX() + 42, getY() - 5, 15, 35);
		body.fillOval(getX() - 8, getY() - 5, 15, 35);
		
		//creates the eyes
		body.setColor(Color.BLACK);
		body.fillOval(getX() + 7, getY() + 12,  10, 10);
		body.fillOval(getX() + 33, getY() + 12, 10, 10);
		
		body.setColor(Color.ORANGE);
		body.fillOval(getX() + 19, getY() + 17, 15, 10); //creates the beak
		
		//creates the feet 
		body.fillOval(getX() + 10, getY() + 45, 10, 10); 
		body.fillOval(getX() + 30, getY() + 45, 10, 10);
		
		body.setColor(Color.DARK_GRAY);
		body.fillRect(getX(), getY() - 10, 50, 5); //creates the plate the bird is holding 
	}
	
	public int getNumCaught() {return numCaught; }
	
	public int getNumMissed() {return numMissed;}
	
	public void addNumCaught() {numCaught++;}
	
	public void addNumMissed() {numMissed++;}
	
}

