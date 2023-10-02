import java.awt.Color; 
import java.awt.Graphics;
import java.awt.event.KeyEvent; 

public class FallingObject {
	private int xPos; 
	private int yPos; 
	
	public FallingObject() {
		xPos = (int)(Math.random() * 700 + 50); //prevents the falling objects from falling too close to the edge of the frame 
		yPos = 0; 
	}
	
	public void draw(Graphics object) {} //empty method that ensures that the subclasses inherit it 
	
	public void setXAndY(int x, int y) {
		xPos = x; 
		yPos = y;
	}
	
	public int getX() {return xPos;}
	public int getY() {return yPos;}
	
	public void changePos(int x, int y) { 
		yPos += y; 
		xPos += x; 
		xPos = (xPos + 800) % 800;   
		yPos = (yPos + 600) % 600; 
	}


}
