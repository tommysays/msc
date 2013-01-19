import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Emulates a bullet that follows a function to determine its path.
 */
public class Bullet{
	protected double xLoc, yLoc;
	protected double direction;
	protected Image img;
	protected final Color BULLET_COLOR = Color.RED;
	protected int BULLET_WIDTH = 8;
	protected int BULLET_HEIGHT = 8;
	protected double SPD = 3;
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

	public Bullet(int x, int y, double direction, Image img){
		xLoc = x;
		yLoc = y;
		this.direction = direction;
		this.img = img;
	}

	public int getX(){
		return (int)xLoc;
	}
	public int getY(){
		return (int)yLoc;
	}
	public int getWidth(){
		return BULLET_WIDTH;
	}
	public int getHeight(){
		return BULLET_HEIGHT;
	}
	
	public void paint(Graphics g){
		if (img == null){
		g.setColor(BULLET_COLOR);
		g.fillOval((int)xLoc - BULLET_WIDTH / 2, (int)yLoc - BULLET_HEIGHT / 2, BULLET_WIDTH, BULLET_HEIGHT);
		} else{
			g.drawImage(img, (int)xLoc - BULLET_WIDTH / 2, (int)yLoc - BULLET_HEIGHT / 2, BULLET_WIDTH, BULLET_HEIGHT, null);
		}

	}

	/**
	 * Moves the bullet based on some function.
	 */
	public void animate(){
		xLoc += SPD * Math.cos(direction);
		yLoc += SPD * Math.sin(direction);
	}
}
