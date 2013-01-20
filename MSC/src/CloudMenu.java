import com.soundcloud.api.ApiWrapper;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Tommy
 */
public class CloudMenu extends JPanel implements ActionListener{
    public static final JFileChooser fc = new JFileChooser();
    
    private ApiWrapper wrapper;
    private String client_id;
    private String client_secret;
    private String username = "";
    private String password = "";
    private boolean loading = false;
    private int numDots = 0;
    private Timer tmr;
    private TimerTask task;
    
    public static void main(String[] args){
        JFrame frm = new JFrame("lsdfj");
        frm.setSize(500,750);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainMenu pnl = new MainMenu();
        pnl.start();
        frm.add(pnl);
        frm.setVisible(true);
    }
    public void start(){
        client_id = "59300a92df9799f95258a9ba20992375";
        client_secret = "1efc4b68c039a18d5ba9305d4ea6a0ba";

        
        fc.setCurrentDirectory(new File("./"));
        setLayout(null);
        JButton localBtn = new JButton("Play from local file");
        localBtn.addActionListener(this);
        localBtn.setActionCommand("local");
        localBtn.setBounds(100, 350, 300, 60);
        JButton cloudBtn = new JButton("Login to SoundCloud");
        cloudBtn.addActionListener(this);
        cloudBtn.setActionCommand("cloudLogin");
        cloudBtn.setBounds(100, 430, 300, 60);
        JButton cloudSelectBtn = new JButton("Play from SoundCloud Library");
        cloudSelectBtn.addActionListener(this);
        cloudSelectBtn.setActionCommand("cloudSelect");
        cloudSelectBtn.setBounds(100, 510, 300, 60);
        add(localBtn);
        add(cloudBtn);
        add(cloudSelectBtn);
    }
    public void paint(Graphics g){
        try{
            BufferedImage img;
            img = ImageIO.read(getClass().getResource("images/lava.png"));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
            img = ImageIO.read(getClass().getResource("images/Logo.png"));
            g.drawImage(img, 10, 50, this.getWidth() - 10, 250, null);
            if (loading){
                for (int i = 0; i < numDots; ++i){
                    g.setColor(new Color(120 + (30 * i), 180, 200));
                    g.fillOval(i * 70 + 125, 270, 40, 40);
                }
            }
        } catch(Exception e){
            System.err.println("could not load img for main menu");
        }
        this.paintComponents(g);
    }
    public void tick(){
        if (numDots > 3){
            numDots = 0;
        } else{
            numDots++;
        }
        repaint();
    }
    public void actionPerformed(ActionEvent ae){
        String command = ae.getActionCommand();
        if (command.equals("local")){
            int response = fc.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION){
                DrawPanel.setInputFile(fc.getSelectedFile());
                Main.change("draw");
            }
        } else if (command.equals("cloudLogin")){
            //TODO choose from cloud.
            wrapper = new ApiWrapper(client_id, client_secret, null, null);
            MainMenu.connection = new Connection(wrapper);
            
//            openLoginWindow();
            JLabel nameLbl = new JLabel("Username:");
            JTextField nameFld = new JTextField();
            JLabel passLbl = new JLabel("Password:");
            JPasswordField passFld = new JPasswordField();
            JComponent[] inputs = {nameLbl, nameFld, passLbl, passFld};
            JOptionPane.showMessageDialog(null, inputs, "Login", JOptionPane.PLAIN_MESSAGE);
            try {
                MainMenu.content = new Content(wrapper);
            } catch (IOException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                MainMenu.connection.Connect(nameFld.getText(), passFld.getText());
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Access Denied");
                return;
            }
            try {
                MainMenu.content.getContent();
            } catch (IOException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Main.useCloudMenu = true;
            Main.change("cloud");
        } else if (command.equals("cloudSelect")){
            String[] names = new String[MainMenu.content.getTotalSongs()];
            for (int i = 0; i < names.length; ++i){
                names[i] = MainMenu.content.getSongTitle(i);
            }
            JComboBox box = new JComboBox(names);
            box.setEditable(false);
            JComponent[] inputs = {box};
            JOptionPane.showMessageDialog(null, inputs, "Select a song", JOptionPane.PLAIN_MESSAGE);
            final String selected = (String)(box.getSelectedItem());
            for (int i =0; i < MainMenu.content.getTotalSongs(); ++i){
                if (MainMenu.content.getSongTitle(i).equals(selected)){
                    try{
                        JOptionPane.showMessageDialog(null, "Loading song.");
                        tmr = new Timer();
                        task = new TimerTask(){
                            public void run(){tick();}
                        };
                        tmr.schedule(task, 0, 500);
                        loading = true;
                        final URL link = new URL(MainMenu.content.getDownloadLink(i));
                        
                        TimerTask loadTask = new TimerTask(){
                            public void run(){
                                setEnabled(false);
                                mp3downloader.download(link, selected + ".mp3");
                                DrawPanel.setInputFile(new File(selected + ".mp3"));
                                tmr.cancel();
                                loading = false;
                                JOptionPane.showMessageDialog(null, "Song loaded!.");
                                Main.change("draw");
                                setEnabled(true);
                            }
                        };
                        Timer tmr2 = new Timer();
                        tmr2.schedule(loadTask, 0);
                    } catch(Exception e){
                        System.err.println("Error in downloading song.");
                    }
                }
            }
        }
    }
    public void openLoginWindow() {
        userWord frame = new userWord(MainMenu.connection);
        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
