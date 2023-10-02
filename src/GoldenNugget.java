import java.awt.Color;
import java.awt.Graphics;

public class GoldenNugget extends FallingObject{
	int fallRate; 
	int numCaught; 
	
	public GoldenNugget(int fallRate) {
		super(); 
		this.fallRate = fallRate; 
	}
	
	public void draw(Graphics nugget) {
		nugget.setColor(Color.ORANGE);
		nugget.fillOval(getX(), getY(), 15, 15);
	}
	
	public int getFallRate() {return fallRate;}
	
	public void setFallRate(int newFallRate) {fallRate += newFallRate;}
	
}
