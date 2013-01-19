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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Main extends JFrame{
	private final Color BG_COLOR = Color.BLACK;
	private final Color BOX_COLOR = new Color(200, 220, 255);
	private final int BOX_WIDTH = 10, BOX_HEIGHT = 10;
	private static DrawPanel pnl;
	private static Main frm;
	private static Cursor blankCursor;
	public static ArrayList<Image> highNotes = new ArrayList<Image>();
	public static ArrayList<Image> midNotes = new ArrayList<Image>();
	public static ArrayList<Image> lowNotes = new ArrayList<Image>();
	public static void main(String[] args){
		loadLowNotes();
		loadMidNotes();
		loadHighNotes();
		frm = new Main();
		frm.start();
	}
	private static void loadLowNotes(){
		BufferedImage img;
		int numLowNotes = 6;
		try{
			for (int i = 1; i <= numLowNotes; ++i){
				img = ImageIO.read(new File("images/lowNotes/lNote0" + i +
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
				img = ImageIO.read(new File("images/midNotes/mNote0" + i +
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
				img = ImageIO.read(new File("images/highNotes/hNote0" + i +
									   ".png"));
				highNotes.add(img);
			}
		} catch(IOException ie){
			System.err.println("Error when reading high note images.");
			ie.printStackTrace();
		}
	}

	public void start(){
		setSize(500,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		createBlankCursor();
		pnl = new DrawPanel();
		pnl.addMouseMotionListener(pnl);
		pnl.setCursor(blankCursor);
		add(pnl);
		setVisible(true);
		pnl.reset();
		pnl.initSpeakers();
		pnl.start();
	}

	/**
	 * Initializes the blankCursor.
	 */
	private void createBlankCursor(){
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			cursorImg, new Point(0, 0), "blank cursor");
	}
	public static void gameOver(){
		pnl.reset();
		pnl.stop();
		pnl.setCursor(Cursor.getDefaultCursor());
		JOptionPane.showMessageDialog(frm, "Game over!\nWhat a noob.");
		pnl.setCursor(blankCursor);
		pnl.initSpeakers();
		pnl.start();
	}
}
