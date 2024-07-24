import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class login extends JFrame {
    JLabel l1, l2, l3, l4;
    JTextField u;
    JPasswordField p;
    JButton b1, b2;

    public login() {
        setTitle("LOGIN");
        ImageIcon icon = new ImageIcon("fir2.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(400, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 230, 250));

        l1 = new JLabel("Username");
        l1.setBounds(25, 90, 500, 50);

        l2 = new JLabel("Password");
        l2.setBounds(25, 140, 500, 50);

        l3 = new JLabel("Login");
        l3.setBounds(160, 30, 500, 50);
        l3.setFont(new Font("arial", Font.BOLD, 18));

        l4 = new JLabel("Forgot password!?");
        l4.setBounds(145, 300, 500, 50);
        l4.setForeground(Color.BLUE);
        l4.setCursor(new Cursor(Cursor.HAND_CURSOR));

        l4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
               new forgetpassword();
            }
        });

        add(l1);
        add(l2);
        add(l3);
        add(l4);

        u = new JTextField();
        u.setBounds(100, 100, 200, 30);
        u.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    p.requestFocus();
                }
            }
        });

        p = new JPasswordField();
        p.setBounds(100, 150, 200, 30);

        add(u);
        add(p);

        b1 = new JButton("Signup");
        b1.setBounds(150, 250, 95, 30);
        b2 = new JButton("Login");
        b2.setBounds(150, 200, 95, 30);

        add(b1);
        add(b2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                signup sn = new signup();
                sn.signup();
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = u.getText();
                String enteredPassword = String.valueOf(p.getPassword());
    
                boolean usernameFound = false;
                boolean passwordCorrect = false;
    
                File file = new File("data.txt");
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");
                        if (parts.length == 2 && parts[0].equals(enteredUsername)) {
                            usernameFound = true;
                            if (parts[1].equals(enteredPassword)) {
                                passwordCorrect = true;
                                break;
                            }
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
    
                if (!usernameFound) {
                    JOptionPane.showMessageDialog(null, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!passwordCorrect) {
                    JOptionPane.showMessageDialog(null, "Password incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                    BookingWindow bookingWindow = new BookingWindow(enteredUsername);
                    bookingWindow.setVisible(true);
                }
            }
        });

        setLayout(null);
        setVisible(true);
    }

    public boolean verifyLogin(String username, String password) {
        File file = new File("data.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        new login();
    }
}
