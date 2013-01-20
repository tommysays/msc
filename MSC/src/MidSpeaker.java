import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * @author Tommy
 */
public class MidSpeaker extends Speaker{
	public MidSpeaker(int x, int y){
		setLoc(x, y);
		counter = 0;
		angleRate = 0;
	}

	public void spawn(){
		Image img = Main.midNotes.get((int)(Math.random() * Main.midNotes.size()));
		DrawPanel.addBullet(new MidBullet(xLoc, yLoc, counter / 2 + (3.14 / 2) + aim, img));
	}
}
