import java.awt.Graphics;
import java.awt.Image;

public class MidBullet extends Bullet{
	public MidBullet(int x, int y, double direction, Image img){
		super(x, y, direction, img);
		BULLET_WIDTH = 15;
		BULLET_HEIGHT = 25;
		SPD = 9;
	}
        
        @Override
        public void animate(){
                xAcc += WIND;
                yAcc += WIND;
                xVel += xAcc;
                yVel += yAcc;
                if (xVel <= SPD) { xVel = SPD;}
                if (yVel <= SPD) { yVel = SPD;}
		xLoc += xVel * Math.cos(direction);
		yLoc += yVel * Math.sin(direction);
	}
}
