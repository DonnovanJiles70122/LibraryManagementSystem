import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class LoginForm extends JFrame{
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField textFieldEmail;
    JPasswordField passwordFieldInput;

    public void initialize() {

        // Creating labels and text fields for email and password inputs for login page.
        JLabel labelLoginForm = new JLabel("Login form", SwingConstants.CENTER);
        labelLoginForm.setFont(mainFont);

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(mainFont);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(mainFont);

        JLabel labelPassword = new JLabel("Password");
        labelPassword.setFont(mainFont);

        passwordFieldInput = new JPasswordField();
        passwordFieldInput.setFont(mainFont);


        // placing the email and password labels and text fields on a panel to be displayed
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(labelLoginForm);
        formPanel.add(labelEmail);
        formPanel.add(textFieldEmail);
        formPanel.add(labelPassword);
        formPanel.add(passwordFieldInput);

        /********* Button Panel ************/
        // creating a button and adding functionality when the buttons pressed
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setFont(mainFont);
        buttonLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // getting the email and password the user inputted
                String email = textFieldEmail.getText();
                String password = String.valueOf(passwordFieldInput.getPassword());

                // this function connects to the MySQL DB and checks to see if 
                // the username and password is present in the DB
                User user = getAuthenticatedUser(email, password);

                // the user exist in the DB a new window will pop up displaying the users information
                if (user != null) {
                    DashBoard dashBoard = new DashBoard();
                    dashBoard.initialize();
                    dispose();
                // if the user doesn't exist in the DB an error message window will pop up
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                                "Email or Password Invalid",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        // adding cancel & login button to the button panel and creating functionality
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
        buttonPanel.add(buttonLogin);
        buttonPanel.add(buttonCancel);

        /********* Initialize the frame ************/
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);




        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMaximumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
        final String USERNAME = "root";
        final String PASSWORD = "bri70122";
        
        try{
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            System.out.println("connection successful!");

            // search DB for user information
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            // execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // if query returned create a new user and fill information
            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            // close connection
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.out.println("Database connection failed!");
        }

        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.initialize();
    }
}
