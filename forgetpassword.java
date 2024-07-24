import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class forgetpassword extends JFrame {

    JLabel l1, l2, l3, l4, l5;
    JTextField u; 
    JPasswordField p, cp;

    public forgetpassword() {
        setTitle("Recover Password");
        ImageIcon icon = new ImageIcon("fir2.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(400, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 230, 250));

        l1 = new JLabel("Username");
        l1.setBounds(25, 90, 500, 50);

        l2 = new JLabel("New Password");
        l2.setBounds(25, 140, 500, 50);

        l3 = new JLabel("Recover Password");
        l3.setBounds(140, 30, 500, 50);

        l4 = new JLabel("Confirm Password");
        l4.setBounds(25, 190, 500, 50);

        l5 = new JLabel("< Back");
        l5.setBounds(30, 30, 500, 50);
        l5.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        l5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    new login(); // Ensure 'Login' is the correct class name for your login screen
                }
            }
        });

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);

        u = new JTextField();
        u.setBounds(100, 100, 200, 30);

        p = new JPasswordField();
        p.setBounds(150, 150, 200, 30);

        cp = new JPasswordField();
        cp.setBounds(150, 200, 200, 30);

        add(u);
        add(p);
        add(cp);

        JButton b1 = new JButton("RECOVER");
        b1.setBounds(150, 250, 95, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validationResult = isValidPassword(String.valueOf(p.getPassword()), String.valueOf(cp.getPassword()));
                if ("success".equals(validationResult)) {
                    saveToFile(u.getText(), String.valueOf(p.getPassword()));
                    dispose();
                    new login(); // Ensure 'Login' is the correct class name for your login screen
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
        File file = new File("data.txt");
        File tempFile = new File("temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
            String line;
            boolean userFound = false;

            // Read the file and write to a temporary file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    writer.println(username + "," + password);
                    userFound = true;
                } else {
                    writer.println(line);
                }
            }

            // If the user was not found, append the new entry
            if (!userFound) {
                writer.println(username + "," + password);
            }
        } catch (IOException ex) {
            System.out.println("File error: " + ex.getMessage());
        }

        // Replace the old file with the new file
        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("Failed to delete old file.");
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
        boolean hasSpecialChar = hasSpecialCharacter(password);

        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        if (password.length() < 8) {
            return "Password should be at least 8 characters long.";
        }
        if (!hasSpecialChar) {
            return "Password should contain at least one special character.";
        }
        return "success";
    }

}
