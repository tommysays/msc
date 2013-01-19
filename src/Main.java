import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;


public class Main extends JFrame{
	private final Color BG_COLOR = Color.BLACK;
	private final Color BOX_COLOR = new Color(200, 220, 255);
	private final int BOX_WIDTH = 10, BOX_HEIGHT = 10;
	public static void main(String[] args){
		Main frm = new Main();
		frm.start();
	}
	public void start(){
		setSize(500,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		DrawPanel pnl = new DrawPanel();
		pnl.addMouseMotionListener(pnl);
		add(pnl);
		setVisible(true);
		pnl.reset();
		pnl.initSpeakers();
		pnl.start();
	}
}
