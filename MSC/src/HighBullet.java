import java.awt.Graphics;
import java.awt.Image;

public class HighBullet extends Bullet{
	public HighBullet(int x, int y, double direction, Image img){
		super(x, y, direction, img);
		BULLET_WIDTH = 10;
		BULLET_HEIGHT = 20;
		SPD = 4;
	}
}
