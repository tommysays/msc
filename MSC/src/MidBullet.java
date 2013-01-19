import java.awt.Graphics;
import java.awt.Image;

public class MidBullet extends Bullet{
	public MidBullet(int x, int y, double direction, Image img){
		super(x, y, direction, img);
		BULLET_WIDTH = 20;
		BULLET_HEIGHT = 25;
		SPD = 3;
	}
}
