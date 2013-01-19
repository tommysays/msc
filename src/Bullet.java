import java.awt.Color;
import java.awt.Graphics;

/**
 * Emulates a bullet that follows a function to determine its path.
 */
public class Bullet{
	private double xLoc, yLoc;
	private double direction;
	private final Color BULLET_COLOR = Color.RED;
	private final int BULLET_WIDTH = 8;
	private final int BULLET_HEIGHT = 8;
	private final double SPD = 5;
	/**
	 * Creates a bullet at the specified location with a certain direction.
	 *
	 * @param x xLoc of bullet.
	 * @param y yLoc of bullet.
	 * @param direction Direction, in radians, of bullet.
	 */
	public Bullet(int x, int y, double direction){
		xLoc = x;
		yLoc = y;
		this.direction = direction;
	}

	public int getX(){
		return (int)xLoc;
	}
	public int getY(){
		return (int)yLoc;
	}
	
	public void paint(Graphics g){
		g.setColor(BULLET_COLOR);
		g.fillOval((int)xLoc - BULLET_WIDTH / 2, (int)yLoc - BULLET_HEIGHT / 2, BULLET_WIDTH, BULLET_HEIGHT);
	}

	/**
	 * Moves the bullet based on some function.
	 */
	public void animate(){
		xLoc += SPD * Math.cos(direction);
		yLoc += SPD * Math.sin(direction);
	}
}
