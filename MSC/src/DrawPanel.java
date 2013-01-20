import com.soundcloud.api.*;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.JSONException;

public class DrawPanel extends JPanel implements MouseMotionListener{
	private Color BG_COLOR = new Color(0,0,0,20);
        private Color targetColor = Color.CYAN;
        private double lastTime = 0;
	private final Color BOX_COLOR = new Color(200, 220, 255);
	private final int BOX_WIDTH = 10, BOX_HEIGHT = 10;
	private int xLoc = 50, yLoc = 50;
	private int xDest = 50, yDest = 50;
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private static ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        private static File musicFile;
	private Timer tmr;
        private Timer mscTmr;
	private TimerTask task;
        private TimerTask musicTask;
        private int score = 0;
	private int interval = 20;
        public static boolean[] spawn = {false, false, false};
        private String client_id;
        private String client_secret;
        private String username = "jo.paul.91@gmail.com";
        private String password = "nim2006";
                 
        public int getScore(){
            return score;
        }
	/**
	 * Resets the player's position to the center of the screen and clears
	 * all bullets.
	 */
	public void reset(){
		xLoc = (this.getWidth() + BOX_WIDTH) / 2;
		yLoc = (this.getHeight() + BOX_HEIGHT) / 2;
		xDest = xLoc;
		yDest = yLoc;
                score = 0;
		bullets = new ArrayList<>();
		speakers = new ArrayList<>();
	}

	/**
	 * Starts / unpauses the game. (Starts the timer that runs the game.)
	 */
	public void start() throws IOException, JSONException{
                client_id = "59300a92df9799f95258a9ba20992375";
                client_secret = "1efc4b68c039a18d5ba9305d4ea6a0ba";
		tmr = new Timer();
                mscTmr = new Timer();
		task = new TimerTask(){
			public void run(){try {
                            tick();
                        } catch (IOException ex) {
                            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
}
		};
                musicTask = new TimerTask(){
                    public void run(){
                        AudioToFreq.running = true;
                        AudioToFreq.PlaySongAndTransform(musicFile);
                    }
                };
		tmr.schedule(task, 0, interval);
                mscTmr.schedule(musicTask, 0);
	}

	/**
	 * Stops / pauses the game.
	 */
	public void stop(){
		task.cancel();
                musicTask.cancel();
                AudioToFreq.running = false;
	}

	private void tick() throws IOException, JSONException{
		for (int i = 0; i < bullets.size(); ++i){
			Bullet bl = bullets.get(i);
			int bx = bl.getX();
			int by = bl.getY();
			int bWidth = bl.getWidth();
			int bHeight = bl.getHeight();
			if (xLoc >= bx - bWidth / 2 && xLoc <= bx + bWidth / 2){
				if (yLoc >= by - bHeight / 2 && yLoc <= by + bHeight / 2){
					Main.gameOver();
					return;
				}
			}
			bl.animate();
			if (bl.getX() < 0 || bl.getX() > this.getWidth() ||
					bl.getY() < 0 || bl.getY() > this.getHeight()){
				bullets.remove(i);
				i--;
			}
		}
		for (Speaker sp : speakers){
                    sp.adjustAim(xLoc, yLoc);
                    sp.animate();
		}
                
                if (spawn[0]) {
                    speakers.get(0).spawn();
                    spawn[0] = false;
                }
                if (spawn[1]){
                    speakers.get(1).spawn();
                    System.out.println("midspawn");
                    spawn[1] = false;
                }
                if (spawn[2]){
                    speakers.get(2).spawn();
                    speakers.get(3).spawn();
                    spawn[2] = false;
                }
                
                //Cursor movement.
		if (xLoc != xDest){
			xLoc -= (xLoc - xDest) / 3;
		}
		if (yLoc != yDest){
			yLoc -= (yLoc - yDest) / 3;
		}
                
                //Background logic
                if (System.currentTimeMillis() - lastTime > 1000) {
                    targetColor = new Color((int)(Math.random()*127),(int)(Math.random()*127),(int)(Math.random()*127), 20);
                    lastTime = System.currentTimeMillis();
                }
                if (BG_COLOR.getRed() > targetColor.getRed()) {
                    BG_COLOR = new Color(BG_COLOR.getRed()-1, BG_COLOR.getBlue(),BG_COLOR.getGreen(), 20);
                } else  {
                    BG_COLOR = new Color(BG_COLOR.getRed()+1, BG_COLOR.getBlue(),BG_COLOR.getGreen(), 20);
                }
                if (BG_COLOR.getGreen() > targetColor.getGreen()) {
                    BG_COLOR = new Color(BG_COLOR.getRed(), BG_COLOR.getBlue(),BG_COLOR.getGreen()-1, 20);
                } else  {
                    BG_COLOR = new Color(BG_COLOR.getRed(), BG_COLOR.getBlue(),BG_COLOR.getGreen()+1, 20);
                }
                if (BG_COLOR.getBlue() > targetColor.getBlue()) {
                    BG_COLOR = new Color(BG_COLOR.getRed(), BG_COLOR.getBlue()-1,BG_COLOR.getGreen(), 20);
                } else  {
                    BG_COLOR = new Color(BG_COLOR.getRed(), BG_COLOR.getBlue()+1,BG_COLOR.getGreen(), 20);
                }
                score += 5;
		repaint();
	}

	/**
	 * Adds a bullet to the arraylist of bullets.
	 *
	 * @param bl The bullet to add.
	 */
	public static void addBullet(Bullet bl){
		bullets.add(bl);
	}

	/**
	 * Adds speakers to the game.
	 * Currently only adds 1 base speaker.
	 */
	public void initSpeakers(){
		//Top-Middle LowSpeaker.
		speakers.add(new LowSpeaker(this.getWidth() / 2, 50));
                //Mid speaker.
                speakers.add(new MidSpeaker(this.getWidth() / 2, 120));
		//Two HighSpeakers
		speakers.add(new HighSpeaker(this.getWidth() / 4, 150));
		speakers.add(new HighSpeaker(this.getWidth() * 3 / 4, 150));
		

	}

        @Override
	public void paint(Graphics g){
                g.setColor(BG_COLOR);
                g.fillRect(0,0, this.getWidth(), this.getHeight());

try{
            for (Speaker sp : speakers){
            	sp.paint(g);
            }
            for (Bullet bl : bullets){
            	bl.paint(g);
            }
} catch (Exception e) {
    
}
            g.setColor(BOX_COLOR);
            g.fillRect(xLoc - BOX_WIDTH / 2, yLoc - BOX_HEIGHT / 2, BOX_WIDTH, BOX_HEIGHT);
            
            g.setColor(Color.BLACK);
            g.fillRect(this.getWidth() - 100, this.getHeight() - 30, 100, 30);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, this.getWidth() - 90, this.getHeight() - 5);
	}
        public static void setInputFile(File aFile){
            musicFile = aFile;
        }
	public void mouseMoved(MouseEvent me){
		xDest = me.getPoint().x - BOX_WIDTH / 2;
		yDest = me.getPoint().y - BOX_HEIGHT / 2;
//		repaint();
	}
	public void mouseDragged(MouseEvent me){
		xDest = me.getPoint().x - BOX_WIDTH / 2;
		yDest = me.getPoint().y - BOX_HEIGHT / 2;
//		repaint();
	}
}
