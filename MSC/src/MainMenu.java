import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 *
 * @author Tommy
 */
public class MainMenu extends JPanel implements ActionListener{
    public static void main(String[] args){
        JFrame frm = new JFrame("lsdfj");
        frm.setSize(500,750);
        MainMenu pnl = new MainMenu();
        pnl.start();
        frm.add(pnl);
        frm.setVisible(true);
    }
    public void start(){
        setLayout(null);
        JButton localBtn = new JButton("Play from local file");
        localBtn.addActionListener(this);
        localBtn.setActionCommand("local");
        localBtn.setBounds(100, 400, 300, 70);
        JButton cloudBtn = new JButton("Play with SoundCloud");
        cloudBtn.addActionListener(this);
        cloudBtn.setActionCommand("cloud");
        cloudBtn.setBounds(100, 500, 300, 70);
        add(localBtn);
        add(cloudBtn);
    }
    public void actionPerformed(ActionEvent ae){
        String command = ae.getActionCommand();
        if (command.equals("local")){
            //TODO choose from local files
        } else if (command.equals("cloud")){
            //TODO choose from cloud.
        }
    }
}
