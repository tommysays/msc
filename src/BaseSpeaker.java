import java.awt.Color;
import java.awt.Graphics;

public class BaseSpeaker extends Speaker{
	private final Color BASE_COLOR = new Color(255, 220, 220);
	private final double LIMIT = 1.57;
	boolean increment = true;
	public BaseSpeaker(int x, int y){
		setLoc(x, y);
		counter = 0;
	}

	public void paint(Graphics g){
		g.setColor(BASE_COLOR);
		g.fillRect(xLoc - 25, yLoc - 25, 50, 50);
	}

	public void spawn(){
		DrawPanel.addBullet(new Bullet(xLoc, yLoc, counter / 2 + (3.14 / 2)));
	}
	public void animate(){
		if (increment){
			counter += (3.14 / 40);
		} else{
			counter -= (3.14 / 40);
		}
		if (Math.abs(counter) >= LIMIT){
			increment = !increment;
		}
	}
}
