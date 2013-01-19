import java.awt.Graphics;
import java.awt.Image;

public class LowBullet extends Bullet{
	public LowBullet(int x, int y, double direction, Image img){
		super(x, y, direction, img);
		BULLET_WIDTH = 30;
		BULLET_HEIGHT = 30;
		SPD = 2;
	}
	public void paint(Graphics g){
		int width = BULLET_WIDTH + 4;
		int height = BULLET_HEIGHT + 8;
		g.drawImage(img, (int)xLoc - width / 2, (int)yLoc - height / 2, width, height, null);
	}
}
