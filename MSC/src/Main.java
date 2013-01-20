import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;


public class Main extends JFrame{
	private final Color BG_COLOR = Color.BLACK;
	private final Color BOX_COLOR = new Color(200, 220, 255);
	private final int BOX_WIDTH = 10, BOX_HEIGHT = 10;
	private static DrawPanel drawPnl;
        private static MainMenu menuPnl;
        private static CloudMenu cloudPnl;
	private static Main frm;
	private static Cursor blankCursor;
        private static CardLayout cl;
	public static ArrayList<Image> highNotes = new ArrayList<Image>();
	public static ArrayList<Image> midNotes = new ArrayList<Image>();
	public static ArrayList<Image> lowNotes = new ArrayList<Image>();
        public static boolean useCloudMenu = false;
	public static void main(String[] args) throws IOException, JSONException{
                frm = new Main();
		loadLowNotes();
		loadMidNotes();
		loadHighNotes();
		frm.start();
	}
	private static void loadLowNotes(){
		BufferedImage img;
		int numLowNotes = 6;
		try{
			for (int i = 1; i <= numLowNotes; ++i){
				img = ImageIO.read(frm.getClass().getResource("images/lowNotes/lNote0" + i +
									   ".png"));
				lowNotes.add(img);
			}
		} catch(IOException ie){
			System.err.println("Error when reading low note images.");
			ie.printStackTrace();
		}
	}
	private static void loadMidNotes(){
		BufferedImage img;
		int numMidNotes = 1;
		try{
			for (int i = 1; i <= numMidNotes; ++i){
				img = ImageIO.read(frm.getClass().getResource("images/midNotes/mNote0" + i +
									   ".png"));
				midNotes.add(img);
			}
		} catch(IOException ie){
			System.err.println("Error when reading mid note images.");
			ie.printStackTrace();
		}
	}
	private static void loadHighNotes(){
		BufferedImage img;
		int numHighNotes = 6;
		try{
			for (int i = 1; i <= numHighNotes; ++i){
				img = ImageIO.read(frm.getClass().getResource("images/highNotes/hNote0" + i +
									   ".png"));
				highNotes.add(img);
			}
		} catch(IOException ie){
			System.err.println("Error when reading high note images.");
			ie.printStackTrace();
		}
	}

	public void start() throws IOException, JSONException{
            setSize(500,750);
            setTitle("2 Fast 2 Musical");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            createBlankCursor();
            
            drawPnl = new DrawPanel();
            drawPnl.addMouseMotionListener(drawPnl);
            menuPnl = new MainMenu();
            cloudPnl = new CloudMenu();
            
            setLayout(cl = new CardLayout());
            add(menuPnl, "menu");
            add(drawPnl, "draw");
            add(cloudPnl, "cloud");
            menuPnl.start();
            setVisible(true);
	}
        public static void change(String name){
            if (name.equals("draw")){
                drawPnl.reset();
                drawPnl.initSpeakers();
                drawPnl.setCursor(blankCursor);
                try{
                    drawPnl.start();
                } catch(Exception e){
                    System.err.println("Error when starting drawPnl");
                }
            } else if (name.equals("menu")){
                menuPnl.start();
            } else if (name.equals("cloud")){
                cloudPnl.start();
            }
            cl.show(frm.getContentPane(), name);
            frm.repaint();
        }

	/**
	 * Initializes the blankCursor.
	 */
	private void createBlankCursor(){
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			cursorImg, new Point(0, 0), "blank cursor");
	}
	public static void gameOver() throws IOException, JSONException{
            int score = drawPnl.getScore();
            drawPnl.reset();
            drawPnl.stop();
            drawPnl.setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(drawPnl, "Game over!\nYour score was:\n" +score,
                                frm.getTitle(), JOptionPane.INFORMATION_MESSAGE);
//            int result = JOptionPane.showConfirmDialog(frm, "Play again?");
            int result = JOptionPane.showConfirmDialog(drawPnl, "Play again?",
                                 frm.getTitle(), JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.OK_OPTION){
                drawPnl.setCursor(blankCursor);
                drawPnl.initSpeakers();
                drawPnl.start();
            } else{
                if (useCloudMenu){
                    Main.change("cloud");
                } else{
                    Main.change("menu");
                }
            }
	}
}
