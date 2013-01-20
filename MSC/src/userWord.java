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
    protected Connection connection;
    protected String username;
    protected String password;
    JButton SUBMIT;
    JPanel panel;
    JLabel label1, label2;
    final JTextField text1, text2;
    public userWord(Connection connection) {
        this.connection = connection;
        setLayout(null);
        label1 = new JLabel("Username :");
        label2 = new JLabel("Password :");
        SUBMIT = new JButton("Submit");
        text1 = new JTextField(35);
        text2 = new JPasswordField(35);
        label1.setBounds(160, 160, 120, 20);
        label2.setBounds(160, 190, 120, 20);
        SUBMIT.setBounds(200, 220, 120, 20);
        text1.setBounds(250, 160, 120, 20);
        text2.setBounds(250, 190, 120, 20);
        add(label1);
        add(text1);
        add(label2);
        add(text2);
        add(SUBMIT);

        SUBMIT.addActionListener(this);
        setTitle("SoundCloud Login Form");
    }
    public String getUsername() { return username;}
    public String getPassword() { return password;}
    public void actionPerformed(ActionEvent ae) {
        username = text1.getText();
        password = text2.getText();
        try {
            connection.Connect(username, password);
            dispose();
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, "The username/password combination was wrong. And your mother doesn't love you.");
        } 
    }
}
