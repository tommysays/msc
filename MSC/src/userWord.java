/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
/**
*
* @author Sagar Patel
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class userWord extends JFrame implements ActionListener {
protected String username;
protected String password;
JButton SUBMIT;
JPanel panel;
JLabel label1, label2;
final JTextField text1, text2;
public userWord() {
setLayout(null);
label1 = new JLabel("Username :");
label2 = new JLabel("Password :");
SUBMIT = new JButton("Submit");
text1 = new JTextField(35);
text2 = new JPasswordField(35);
label1.setBounds(160, 308, 120, 20);
label2.setBounds(160, 333, 120, 20);
SUBMIT.setBounds(200, 373, 120, 20);
text1.setBounds(250, 308, 120, 20);
text2.setBounds(250, 333, 120, 20);
add(label1);
add(text1);
add(label2);
add(text2);
add(SUBMIT);

SUBMIT.addActionListener(this);
setTitle("SoundCloud Login Form");
}
public void actionPerformed(ActionEvent ae) {
username = text1.getText();
password = text2.getText();
}
}
class LoginDemo {
    public static void main(String arg[]) {
    userWord frame = new userWord();
    frame.setSize(500, 750);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}