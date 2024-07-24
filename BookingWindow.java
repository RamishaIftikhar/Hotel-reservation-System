import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;

public class BookingWindow extends JFrame {

    private JTable table;
    private JButton makeReservationButton;
    private JButton showMyBookingButton;
    private String selectedRoomNumber ,us;
    private double roomPrice = 0.0; // Price for selected room

    public BookingWindow(String username) {
        us=username;
        setTitle("Hotel Reservation System - Booking");
        setSize(1200, 700); // Initial window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.BOTH;

        JLabel userLabel = new JLabel("HI " + username.toUpperCase());
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setForeground(Color.RED); // Set color to red
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel headingLabel = new JLabel("Available Rooms");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        String[] columnNames = {"Room Number", "Type", "Price per Night (PKR)", "Category"};
        Object[][] data = {
            {"101", "Single", "1000.0", "Economy"},
            {"102", "Double", "8000.0", "Standard"},
            {"103", "Suite", "15000.0", "Suite"},
            {"104", "Single", "7000.0", "Standard"},
            {"105", "Double", "12000.0", "Deluxe"},
            {"106", "Single", "1100.0", "Economy"},
            {"107", "Double", "8500.0", "Standard"},
            {"108", "Suite", "15500.0", "Suite"},
            {"109", "Single", "7200.0", "Standard"},
            {"110", "Double", "12500.0", "Deluxe"},
            {"111", "Single", "1200.0", "Economy"},
            {"112", "Double", "9000.0", "Standard"},
            {"113", "Suite", "16000.0", "Suite"},
            {"114", "Single", "7300.0", "Standard"},
            {"115", "Double", "13000.0", "Deluxe"},
            {"116", "Single", "1300.0", "Economy"},
            {"117", "Double", "9500.0", "Standard"},
            {"118", "Suite", "16500.0", "Suite"},
            {"119", "Single", "7400.0", "Standard"},
            {"120", "Double", "13500.0", "Deluxe"},
            {"121", "Single", "1400.0", "Economy"},
            {"122", "Double", "10000.0", "Standard"},
            {"123", "Suite", "17000.0", "Suite"},
            {"124", "Single", "7500.0", "Standard"},
            {"125", "Double", "14000.0", "Deluxe"},
            {"126", "Single", "1500.0", "Economy"},
            {"127", "Double", "10500.0", "Standard"},
            {"128", "Suite", "17500.0", "Suite"},
            {"129", "Single", "7600.0", "Standard"},
            {"130", "Double", "14500.0", "Deluxe"},
            {"131", "Single", "1600.0", "Economy"},
            {"132", "Double", "11000.0", "Standard"},
            {"133", "Suite", "18000.0", "Suite"},
            {"134", "Single", "7700.0", "Standard"},
            {"135", "Double", "15000.0", "Deluxe"},
            {"136", "Single", "1700.0", "Economy"},
            {"137", "Double", "11500.0", "Standard"},
            {"138", "Suite", "18500.0", "Suite"},
            {"139", "Single", "7800.0", "Standard"},
            {"140", "Double", "15500.0", "Deluxe"},
            {"141", "Single", "1800.0", "Economy"},
            {"142", "Double", "12000.0", "Standard"},
            {"143", "Suite", "19000.0", "Suite"},
            {"144", "Single", "7900.0", "Standard"},
            {"145", "Double", "16000.0", "Deluxe"},
            {"146", "Single", "1900.0", "Economy"},
            {"147", "Double", "12500.0", "Standard"},
            {"148", "Suite", "19500.0", "Suite"},
            {"149", "Single", "8000.0", "Standard"},
            {"150", "Double", "16500.0", "Deluxe"},
            {"151", "Single", "2000.0", "Economy"},
            {"152", "Double", "13000.0", "Standard"},
            {"153", "Suite", "20000.0", "Suite"},
            {"154", "Single", "8100.0", "Standard"},
            {"155", "Double", "17000.0", "Deluxe"},
            {"156", "Single", "2100.0", "Economy"},
            {"157", "Double", "13500.0", "Standard"},
            {"158", "Suite", "20500.0", "Suite"},
            {"159", "Single", "8200.0", "Standard"},
            {"160", "Double", "17500.0", "Deluxe"},
            {"161", "Single", "2200.0", "Economy"},
            {"162", "Double", "14000.0", "Standard"},
            {"163", "Suite", "21000.0", "Suite"},
            {"164", "Single", "8300.0", "Standard"},
            {"165", "Double", "18000.0", "Deluxe"},
            {"166", "Single", "2300.0", "Economy"},
            {"167", "Double", "14500.0", "Standard"},
            {"168", "Suite", "21500.0", "Suite"},
            {"169", "Single", "8400.0", "Standard"},
            {"170", "Double", "18500.0", "Deluxe"}
    };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(224, 255, 255)); 
        table.setRowHeight(30); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                selectedRoomNumber = (String) table.getValueAt(selectedRow, 0);
                roomPrice = Double.parseDouble((String) table.getValueAt(selectedRow, 2));
                makeReservationButton.setEnabled(true); 
            }
        });

        makeReservationButton = new JButton("Make Reservation");
        makeReservationButton.setEnabled(false);
        makeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedRoomNumber.isEmpty()) {
                    showReservationDialog();
                }
            }
        });

        showMyBookingButton = new JButton("Show My Booking");
        showMyBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookingDetails(username);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(makeReservationButton);
        buttonPanel.add(showMyBookingButton);

        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(userLabel, gbc);

        gbc.gridy = 1;
        mainPanel.add(headingLabel, gbc);

        gbc.weighty = 0.8;
        gbc.gridy = 2;
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(""));
        mainPanel.add(scrollPane, gbc);

        gbc.weighty = 0.1;
        gbc.gridy = 3;
        mainPanel.add(buttonPanel, gbc);

        // Set the content pane and make the window visible
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void showReservationDialog() {
        // Create a dialog for reservation details
        JDialog dialog = new JDialog(this, "Reservation Details", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
    
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
    
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField contactField = new JTextField();
    
        JLabel checkInLabel = new JLabel("Check-in Date:");
        JDateChooser checkInChooser = new JDateChooser();
        JLabel checkOutLabel = new JLabel("Check-out Date:");
        JDateChooser checkOutChooser = new JDateChooser();

        Calendar calendar = Calendar.getInstance();
        checkInChooser.setMinSelectableDate(calendar.getTime());
        checkOutChooser.setMinSelectableDate(calendar.getTime());
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(contactLabel, gbc);
        gbc.gridx = 1;
        dialog.add(contactField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(checkInLabel, gbc);
        gbc.gridx = 1;
        dialog.add(checkInChooser, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(checkOutLabel, gbc);
        gbc.gridx = 1;
        dialog.add(checkOutChooser, gbc);
    
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String contact = contactField.getText();
                java.util.Date checkInDate = checkInChooser.getDate();
                java.util.Date checkOutDate = checkOutChooser.getDate();
    
                if (name.isEmpty() || contact.isEmpty() || checkInDate == null || checkOutDate == null) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                long durationMillis = checkOutDate.getTime() - checkInDate.getTime();
                long durationDays = durationMillis / (1000 * 60 * 60 * 24); 
    
                if (durationDays <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Check-out date must be after check-in date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    

                double totalPrice = durationDays * roomPrice;
    
                String checkInDateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(checkInDate);
                String checkOutDateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(checkOutDate);
    
                saveReservation(name, contact, checkInDateStr, checkOutDateStr, durationDays, totalPrice);

                try {
                    dispose();
                    new Payment(us, selectedRoomNumber, name, contact, totalPrice);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error opening payment window.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
    
                dialog.dispose();
            }
        });
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(confirmButton, gbc);
    
        dialog.setVisible(true);
    }
    
    private void saveReservation(String name, String contact, String checkInDate, String checkOutDate, long durationDays, double totalPrice) {
        try (FileWriter fw = new FileWriter("reservations.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("Username: " + us);
            pw.println("Room Number: " + selectedRoomNumber);
            pw.println("Name: " + name);
            pw.println("Contact Number: " + contact);
            pw.println("Check-in Date: " + checkInDate);
            pw.println("Check-out Date: " + checkOutDate);
            pw.println("Number of Nights: " + durationDays);
            pw.println("Total Price: PKR " + totalPrice);
            pw.println("-------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving reservation.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showBookingDetails(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("reservations.txt"))) {
            StringBuilder bookings = new StringBuilder("Your Bookings:\n\n");
            String line;
            boolean isUserBooking = false;
            
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    isUserBooking = line.substring("Username: ".length()).equals(username);
                }
                if (isUserBooking) {
                    bookings.append(line).append("\n");
                }
            }
    
            if (bookings.toString().trim().isEmpty()) {
                bookings.append("No bookings found for this user.");
            }
  
            JTextArea textArea = new JTextArea(bookings.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(this, scrollPane, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading reservations.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
