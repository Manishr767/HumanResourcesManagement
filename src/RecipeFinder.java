import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class RecipeFinder extends JFrame {
    // Components
    private JTextField searchField;
    private JComboBox<String> categoryDropdown;
    private JTextArea resultArea;
    private JTextField ratingField;
    private JTextArea feedbackArea;

    public RecipeFinder() {
        // Frame properties
        setTitle("Recipe Finder");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Recipe Finder", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Center panel for search
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        searchField = new JTextField();
        categoryDropdown = new JComboBox<>(new String[]{"All", "Vegetarian", "Non-Vegetarian", "Vegan", "Desserts"});
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search by Recipe Name or Ingredients:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Select Category:"));
        searchPanel.add(categoryDropdown);

        buttonPanel.add(searchButton);

        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Results area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Feedback and rating panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        ratingField = new JTextField();
        feedbackArea = new JTextArea();
        JButton submitFeedbackButton = new JButton("Submit Feedback");

        feedbackPanel.add(new JLabel("Rate Recipe (1-5):"), BorderLayout.NORTH);
        feedbackPanel.add(ratingField, BorderLayout.CENTER);
        feedbackPanel.add(new JLabel("Leave Feedback:"), BorderLayout.SOUTH);
        feedbackPanel.add(feedbackArea, BorderLayout.SOUTH);
        feedbackPanel.add(submitFeedbackButton, BorderLayout.SOUTH);

        add(feedbackPanel, BorderLayout.EAST);

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRecipes();
            }
        });

        submitFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });

        setVisible(true);
    }

    // Method to connect to the database
    private Connection connectToDatabase() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Update 'xe' with your DB service name
        String user = "your_username"; // Replace with your SQL Developer username
        String password = "your_password"; // Replace with your SQL Developer password

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
            return null;
        }
    }

    // Method to search recipes
    private void searchRecipes() {
        String searchText = searchField.getText();
        String selectedCategory = (String) categoryDropdown.getSelectedItem();
        Connection con = connectToDatabase();

        if (con != null) {
            try {
                String sql = "SELECT r.recipe_name, r.category, r.description FROM recipes r " +
                        "LEFT JOIN ingredients i ON r.recipe_id = i.recipe_id " +
                        "WHERE (r.recipe_name LIKE ? OR i.ingredient_name LIKE ?)";
                if (!"All".equals(selectedCategory)) {
                    sql += " AND r.category = ?";
                }

                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + searchText + "%");
                stmt.setString(2, "%" + searchText + "%");
                if (!"All".equals(selectedCategory)) {
                    stmt.setString(3, selectedCategory);
                }

                ResultSet rs = stmt.executeQuery();
                StringBuilder results = new StringBuilder();

                while (rs.next()) {
                    results.append("Recipe: ").append(rs.getString("recipe_name")).append("\n");
                    results.append("Category: ").append(rs.getString("category")).append("\n");
                    results.append("Description: ").append(rs.getString("description")).append("\n\n");
                }

                if (results.length() == 0) {
                    resultArea.setText("No recipes found!");
                } else {
                    resultArea.setText(results.toString());
                }

                rs.close();
                stmt.close();
                con.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching recipes: " + e.getMessage());
            }
        }
    }

    // Method to submit feedback
    private void submitFeedback() {
        String selectedRecipe = JOptionPane.showInputDialog(this, "Enter the recipe name to leave feedback:");
        String rating = ratingField.getText();
        String feedback = feedbackArea.getText();

        if (selectedRecipe == null || selectedRecipe.isEmpty() || rating.isEmpty() || feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields to submit feedback.");
            return;
        }

        Connection con = connectToDatabase();

        if (con != null) {
            try {
                String sql = "INSERT INTO feedback (recipe_id, rating, comment) " +
                        "VALUES ((SELECT recipe_id FROM recipes WHERE recipe_name = ?), ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, selectedRecipe);
                stmt.setDouble(2, Double.parseDouble(rating));
                stmt.setString(3, feedback);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Recipe not found.");
                }

                stmt.close();
                con.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error submitting feedback: " + e.getMessage());
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeFinder());
    }
}
