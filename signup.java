import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
public class signup extends JFrame {

    JLabel l1, l2, l3, l4, l5;
    JTextField u; 
    JPasswordField p, cp;

    public void signup() {

        setTitle("Signup");
        ImageIcon icon = new ImageIcon("fir2.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(400, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 230, 250));

        l1 = new JLabel();
        l1.setText("Username");
        l1.setBounds(25, 90, 500, 50);

        l2 = new JLabel();
        l2.setText("Password");
        l2.setBounds(25, 140, 500, 50);

        l3 = new JLabel();
        l3.setText("Signup");
        l3.setBounds(160, 30, 500, 50);
        l3.setFont(new Font("arial",Font.BOLD,18));

        l4 = new JLabel();
        l4.setText("Confirm Password");
        l4.setBounds(25, 190, 500, 50);

        l5 = new JLabel();
        l5.setText("< Back");
        l5.setBounds(30, 30, 500, 50);
        l5.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        l5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    new login();
                }
            }
        });
        

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);

        JTextField u = new JTextField();
        u.setBounds(100, 100, 200, 30);
        JPasswordField p = new JPasswordField();
        p.setBounds(100, 150, 200, 30);
        JPasswordField cp = new JPasswordField();
        cp.setBounds(150, 200, 200, 30);

        add(u);
        add(p);
        add(cp);

        JButton b1 = new JButton("Signup");
        b1.setBounds(150, 250, 95, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validationResult = isValidPassword(String.valueOf(p.getPassword()), String.valueOf(cp.getPassword()));
                if ("success".equals(validationResult)) {
                    saveToFile(u.getText(), String.valueOf(p.getPassword()) );
                    dispose();
                    new login();
                } else {
                    JOptionPane.showMessageDialog(null, validationResult, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(b1);
        setLayout(null);
        setVisible(true);
    }

    public void saveToFile(String username, String password) {
        try (FileWriter writer = new FileWriter("data.txt", true)) {
            writer.write(username + "," + password + System.lineSeparator());
        } catch (IOException ex) {
            System.out.println("File not found");
        }
    }
    public boolean hasSpecialCharacter(String password) {
        for (char ch : password.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                return true;
            }
        }
        return false; 
    }

    public String isValidPassword(String password, String confirmPassword) {
        boolean Flag;
        if (hasSpecialCharacter(password)) {
            Flag = true;
        } 
        else {
            Flag = false;
        }

        if (!password.equals(confirmPassword) && !Flag && password.length() < 8)
            return "Passwords do not match, and password should be at least 8 characters long with at least one special character.";
        else if (!password.equals(confirmPassword) && !Flag && password.length() >= 8)
            return "Passwords do not match, and password should contain at least one special character.";
        else if (!password.equals(confirmPassword) && password.length() < 8 && Flag)
            return "Passwords do not match, and password should be at least 8 characters long.";
        else if (password.equals(confirmPassword) && password.length() < 8 && !Flag)
            return "password should be at least 8 characters long with at least one special character.";
        else if (password.equals(confirmPassword) && password.length() >= 8 && !Flag)
            return " password should contain at least one special character.";
        else if (password.equals(confirmPassword) && password.length() < 8 && Flag)
            return " password should be at least 8 characters long";
        else if (!password.equals(confirmPassword) && password.length() >= 8 && Flag)
            return " Password and confirm password not match";

        return "success";
    }
}
