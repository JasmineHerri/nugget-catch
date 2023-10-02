import java.awt.Color;
import java.awt.Graphics;

public class Bomb extends GoldenNugget{
	int fallRate; 
	
	public Bomb(int fallRate) {
		super(fallRate); 
	}
	
	public void draw(Graphics bomb) {
		bomb.setColor(Color.BLACK);
		bomb.fillOval(getX(), getY() + 9, 25, 25); //creates the bomb body 
		bomb.fillRect(getX() + 8, getY() + 5, 10, 5); //bomb fuse and body connector 
		bomb.fillRect(getX() + 12, getY(), 2, 5); //bomb fuse
	}

}
