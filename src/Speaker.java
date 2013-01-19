import java.awt.Graphics;

/**
 * The main bullet-throwing object.
 */
public abstract class Speaker{
	protected double counter = 0;
	protected int xLoc = 0, yLoc = 0;

	/**
	 * Sets the location of the speaker.
	 *
	 * @param x New xLoc of speaker.
	 * @param y New yLoc of speaker.
	 */
	public void setLoc(int x, int y){
		xLoc = x;
		yLoc = y;
	}

	/**
	 * Spawns a bullet.
	 */
	public abstract void spawn();

	/**
	 * Does something.
	 */
	public abstract void animate();

	/**
	 * Paints the speaker.
	 */
	public void paint(Graphics g){}
}
