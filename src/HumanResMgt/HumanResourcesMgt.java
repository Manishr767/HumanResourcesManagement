package HumanResMgt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;

public class HumanResourcesMgt extends JFrame {
    JTextField nameTxtF, ssnTxtF, addressTxtF, nationalityTxtF, positionTxtF,idTxtF;
    File selectedFile;
    JLabel imageLabel;
    public HumanResourcesMgt() {
        // Set JFrame properties
        setTitle("Human Resources Management");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
         Image backgroundImage = new ImageIcon("backImg.png").getImage();
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout()); // Use BorderLayout for better component organization

        // Main Panel (Holds the form fields)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        JLabel headerLabel = new JLabel("Human Resources Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add padding below the header
        add(headerLabel, BorderLayout.NORTH);

        // Row 1: ID Field
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(new JLabel("ID: "), gbc);
         idTxtF = new JTextField(); gbc.gridx = 1; mainPanel.add(idTxtF, gbc);

        // Row 2: Load Picture Button and Image Display
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(new JLabel("Photo: "), gbc);
        JButton loadBtn = new JButton("Load Picture");
        gbc.gridx = 1; mainPanel.add(loadBtn, gbc);

        imageLabel = new JLabel("No Picture Selected", SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border to image label
        imageLabel.setPreferredSize(new Dimension(200, 150)); // Set a fixed size
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; mainPanel.add(imageLabel, gbc);
        gbc.gridwidth = 1; // Reset grid width for future rows

        // Row 3: Name Field
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(new JLabel("Name: "), gbc);
        nameTxtF = new JTextField(); gbc.gridx = 1; mainPanel.add(nameTxtF, gbc);

        // Row 4: SSN Field
        gbc.gridx = 0; gbc.gridy = 4; mainPanel.add(new JLabel("SSN: "), gbc);
        ssnTxtF = new JTextField(); gbc.gridx = 1; mainPanel.add(ssnTxtF, gbc);

        // Row 5: Address Field
        gbc.gridx = 0; gbc.gridy = 5; mainPanel.add(new JLabel("Address: "), gbc);
        addressTxtF = new JTextField(); gbc.gridx = 1; mainPanel.add(addressTxtF, gbc);

        // Row 6: Nationality Field
        gbc.gridx = 0; gbc.gridy = 6; mainPanel.add(new JLabel("Nationality: "), gbc);
        nationalityTxtF = new JTextField(); gbc.gridx = 1; mainPanel.add(nationalityTxtF, gbc);

        // Row 7: Position Field
        gbc.gridx = 0; gbc.gridy = 7; mainPanel.add(new JLabel("Position: "), gbc);
        positionTxtF = new JTextField(); gbc.gridx = 1; mainPanel.add(positionTxtF, gbc);

        // Footer Panel (Buttons)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.black);
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(33, 150, 243));
        searchBtn.setForeground(Color.black);
        footerPanel.add(saveBtn);
        footerPanel.add(searchBtn);

        // Add main panel and footer panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // ActionListener for the load picture button
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(HumanResourcesMgt.this) == JFileChooser.APPROVE_OPTION) {
                     selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imgIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image img = imgIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img)); // Set image to imageLabel
                }
            }
        });

        // ActionListener for the save button
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        // Make the JFrame visible
        setVisible(true);
    }

    // Method to connect to the database
    public Connection makeConnection() {
        String url = "jdbc:oracle:thin:@sola.uc.ac.kr:10:xe"; // Correct format for Oracle's JDBC URL
        String userid = "s2312075";
        String pwd = "11";
        Connection con = null;
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded successfully");

            // Establish database connection
            System.out.println("Preparing to connect to the database...");
            con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("Database connected successfully");

        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load the Oracle JDBC driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return con;
    }

    // Method to save the employee to the database
    private void saveEmployee() {
        String id = idTxtF.getText();
        String name = nameTxtF.getText();
        String ssn = ssnTxtF.getText();
        String address = addressTxtF.getText();
        String nationality = nationalityTxtF.getText();
        String position = positionTxtF.getText();

        // Ensure a photo is selected
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a photo before saving.");
            return;
        }

        // Convert the selected photo into a byte array
        byte[] photoBytes = null;
        try {
            photoBytes = java.nio.file.Files.readAllBytes(selectedFile.toPath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading photo file: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }

        // Database connection
        Connection con = makeConnection();
        if (con != null) {
            try {
                String sql = "INSERT INTO employees (id, name, ssn, address, nationality, position, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setString(3, ssn);
                stmt.setString(4, address);
                stmt.setString(5, nationality);
                stmt.setString(6, position);
                stmt.setBytes(7, photoBytes); // Save the photo as a byte array

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Employee saved successfully!");
                    clearFields(); // Clear fields after saving
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saving employee: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

//
    public void searchEmployee(){
        String ssn = JOptionPane.showInputDialog(this,"Enter ssn tp search");
        try{
            Connection con = makeConnection();
            String sql = "Select * From employees where ssn =?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,ssn);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                idTxtF.setText(String.valueOf(rs.getInt("id")));
                nameTxtF.setText(rs.getString("name"));

                ssnTxtF.setText(rs.getString("ssn"));
                addressTxtF.setText(rs.getString("address"));
                nationalityTxtF.setText(rs.getString("nationality"));
                positionTxtF.setText(rs.getString("position"));
                byte[] photoBytes = rs.getBytes("photo");
                if(photoBytes !=null ){
                    ImageIcon imageIcon= new ImageIcon(photoBytes);
                    Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(),imageLabel.getHeight(),Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                }



            }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showInputDialog("this","Error searching for "+ssn);
        }
    }

    // Method to clear the input fields
    private void clearFields() {
        idTxtF.setText("");
        nameTxtF.setText("");
        ssnTxtF.setText("");
        addressTxtF.setText("");
        nationalityTxtF.setText("");
        positionTxtF.setText("");
        selectedFile = null; // Clear the selected file
        imageLabel.setIcon(null); // Clear the image label
    }
    public static void main(String[] args) {
        new HumanResourcesMgt();
    }
}