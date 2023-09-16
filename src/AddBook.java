import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import javax.swing.*;

public class AddBook extends JFrame {
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    JTextField textFieldCategory;
    JTextField textFieldName;
    JTextField textFieldAuthor;
    JTextField textFieldCopies;

    public void initialize(){

        JLabel labelAddBook = new JLabel("Add Book", SwingConstants.CENTER);
        labelAddBook.setFont(mainFont);

        JLabel addCategoryJLabel = new JLabel("Category");
        addCategoryJLabel.setFont(mainFont);

        textFieldCategory = new JTextField();
        textFieldCategory.setFont(mainFont);

        JLabel addNameJLabel = new JLabel("Name");
        addNameJLabel.setFont(mainFont);

        textFieldName = new JTextField();
        textFieldName.setFont(mainFont);

        JLabel addAuthorJLabel = new JLabel("Author");
        addAuthorJLabel.setFont(mainFont);

        textFieldAuthor = new JTextField();
        textFieldAuthor.setFont(mainFont);

        JLabel addCopiesJLabel = new JLabel("Copies");
        addCopiesJLabel.setFont(mainFont);

        textFieldCopies = new JTextField();
        textFieldCopies.setFont(mainFont);



        JPanel addBookJPanel = new JPanel();
        addBookJPanel.setLayout(new GridLayout(0, 1, 10, 10));
        addBookJPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        addBookJPanel.add(labelAddBook);
        addBookJPanel.add(addCategoryJLabel);
        addBookJPanel.add(textFieldCategory);
        addBookJPanel.add(addNameJLabel);
        addBookJPanel.add(textFieldName);
        addBookJPanel.add(addAuthorJLabel);
        addBookJPanel.add(textFieldAuthor);
        addBookJPanel.add(addCopiesJLabel);
        addBookJPanel.add(textFieldCopies);

        JButton buttonAdd = new JButton("Add");
        buttonAdd.setFont(mainFont);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = textFieldCategory.getText();
                String name = textFieldName.getText();
                String author = textFieldAuthor.getText();
                int copies = Integer.parseInt(textFieldCopies.getText());

                boolean confirmation = addBookToDB(category, name, author, copies);

                if(confirmation == true) {
                    JOptionPane.showMessageDialog(AddBook.this,
                                            (name + " was successfully added"),
                                            "Completed",
                                            JOptionPane.OK_OPTION);
                } else {
                    JOptionPane.showMessageDialog(AddBook.this,
                                name + " was not completed",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(mainFont);
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonCancel);

        add(addBookJPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Add Book");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean addBookToDB(String category, String name, String author, int copies) {
        //Book book = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
        final String USERNAME = "root";
        final String PASSWORD = "bri70122";

        try{
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            System.out.println("connection successful!");

            String sql = "INSERT INTO books (category, name, author, copies) VALUES (?, ?, ?, ?)";
            PreparedStatement myStmt = connection.prepareStatement(sql);

            myStmt.setString(1, category);
            myStmt.setString(2, name);
            myStmt.setString(3, author);
            myStmt.setInt(4, copies);
            
            System.out.println(myStmt);
            myStmt.executeUpdate();
            System.out.println("complete");

            myStmt.close();
            connection.close();

        } catch(Exception e){
            System.out.println("Database connection failed!");
            return false;
        }
        return true;

    }
    
    public static void main(String[] args) {
        AddBook addBook = new AddBook();
        addBook.initialize();
    }
}
