import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * @author Tommy
 */
public class MidSpeaker extends Speaker{
	private final Color REG_COLOR = new Color(255, 150, 150);
	private final double LIMIT = (3.14 / 6);
	boolean increment = true;
	public MidSpeaker(int x, int y){
		setLoc(x, y);
		counter = 0;
		angleRate = (3.14 / 80);
	}

	public void spawn(){
		Image img = Main.midNotes.get((int)(Math.random() * Main.midNotes.size()));
		DrawPanel.addBullet(new MidBullet(xLoc, yLoc, counter / 2 + (3.14 / 2) + aim, img));
	}
}
