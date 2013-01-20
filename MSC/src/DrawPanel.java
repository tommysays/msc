import com.soundcloud.api.*;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class DrawPanel extends JPanel implements MouseMotionListener{
	private final Color BG_COLOR = Color.BLACK;
	private final Color BOX_COLOR = new Color(200, 220, 255);
	private final int BOX_WIDTH = 10, BOX_HEIGHT = 10;
	private int xLoc = 50, yLoc = 50;
	private int xDest = 50, yDest = 50;
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private static ArrayList<Speaker> speakers = new ArrayList<Speaker>();
	private Timer tmr;
        private Timer mscTmr;
	private TimerTask task;
        private TimerTask musicTask;
	private int interval = 20;
	private int spawnInterval = 2;
	private int counter = 0;
        public static boolean[] spawn = {false, false, false};

        private ApiWrapper wrapper;
        private Connection connection;
        private Content content;
        private String client_id;
        private String client_secret;
        private String username = "jo.paul.91@gmail.com";
        private String password = "nim2006";
                 
        
	/**
	 * Resets the player's position to the center of the screen and clears
	 * all bullets.
	 */
	public void reset(){
		xLoc = (this.getWidth() + BOX_WIDTH) / 2;
		yLoc = (this.getHeight() + BOX_HEIGHT) / 2;
		xDest = xLoc;
		yDest = yLoc;
		bullets = new ArrayList<Bullet>();
		speakers = new ArrayList<Speaker>();
	}

	/**
	 * Starts / unpauses the game. (Starts the timer that runs the game.)
	 */
	public void start() throws IOException, JSONException{
                client_id = "59300a92df9799f95258a9ba20992375";
                client_secret = "1efc4b68c039a18d5ba9305d4ea6a0ba";
                
                wrapper = new ApiWrapper(client_id, client_secret, null, null);
                connection = new Connection(wrapper);
                content = new Content(wrapper);
                connection.Connect(username, password);
                content.getContent();
                //URL url = new URL(content.getDownloadLink(0));
                //mp3downloader.download(url, content.getSongTitle(0) + ".mp3");
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
                        AudioToFreq.PlaySongAndTransform("party.mp3");
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
			sp.animate();
		}
                
                if (spawn[0]) {
                    speakers.get(0).spawn();
                    spawn[0] = false;
                }
		counter++;
		if (counter > spawnInterval){
			if (spawn[1]){
                            speakers.get(1).spawn();
                        }
                        if (spawn[2]) {
                            speakers.get(2).spawn();
                        }
			counter = 0;
		}
		if (xLoc != xDest){
			xLoc -= (xLoc - xDest) / 3;
		}
		if (yLoc != yDest){
			yLoc -= (yLoc - yDest) / 3;
		}
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
		//Three HighSpeakers
		speakers.add(new HighSpeaker(this.getWidth() / 4, 150));
		speakers.add(new HighSpeaker(this.getWidth() / 2, 150));
		speakers.add(new HighSpeaker(this.getWidth() * 3 / 4, 150));

	}

	public void paint(Graphics g){
		g.setColor(new Color(0,0,0,20));
		g.fillRect(0,0, this.getWidth(), this.getHeight());

		for (Speaker sp : speakers){
			sp.paint(g);
		}
		for (Bullet bl : bullets){
			bl.paint(g);
		}
		g.setColor(BOX_COLOR);
		g.fillRect(xLoc - BOX_WIDTH / 2, yLoc - BOX_HEIGHT / 2, BOX_WIDTH, BOX_HEIGHT);
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
