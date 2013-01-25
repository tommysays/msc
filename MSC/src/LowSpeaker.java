import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class LowSpeaker extends Speaker{
	private final double LIMIT = (3.14 / 2);
	boolean increment = true;
        private double lastTime;
        
	public LowSpeaker(int x, int y){
		REG_COLOR = new Color(255, 220, 200);
		setLoc(x, y);
		counter = 0;
                lastTime = 0;
	}

	/**
	 * Creates one regular bullet.
	 */
	public void spawn(){
            if (System.currentTimeMillis() - lastTime > 500) {
                for (int i = 0; i < 5; ++i){
                    Image img = Main.lowNotes.get((int)(Math.random() * Main.lowNotes.size()));
                    DrawPanel.addBullet(new LowBullet(xLoc, yLoc, (3.14 / 4) + ((i + .5) / 10.0) * (3.14 / 1) + aim, img));
                }
                lastTime = System.currentTimeMillis();
            }
	}
        public void animate(){}
}
