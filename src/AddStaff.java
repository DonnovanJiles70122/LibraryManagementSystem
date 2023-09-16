import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class AddStaff extends JFrame{
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    JTextField textFieldName;
    JTextField textFieldContact;

    public void initialize(){
        JLabel labelAddStaff = new JLabel("Add Staff", SwingConstants.CENTER);
        labelAddStaff.setFont(mainFont);

        JLabel addNameJLabel = new JLabel("Name");
        addNameJLabel.setFont(mainFont);

        textFieldName = new JTextField();
        textFieldName.setFont(mainFont);

        JLabel addContactJLabel = new JLabel("Contact");
        addContactJLabel.setFont(mainFont);

        textFieldContact = new JTextField();
        textFieldContact.setFont(mainFont);

        JPanel addStaffJPanel = new JPanel();
        addStaffJPanel.setLayout(new GridLayout(0, 1, 10, 10));
        addStaffJPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        addStaffJPanel.add(labelAddStaff);
        addStaffJPanel.add(addNameJLabel);
        addStaffJPanel.add(textFieldName);
        addStaffJPanel.add(addContactJLabel);
        addStaffJPanel.add(textFieldContact);

        JButton buttonAdd = new JButton("Add");
        buttonAdd.setFont(mainFont);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String contact = textFieldContact.getText();

                boolean confirmation = addStaffToDB(name, contact);

                if(confirmation == true) {
                    JOptionPane.showMessageDialog(AddStaff.this,
                                            (name + " was successfully added"),
                                            "Completed",
                                            JOptionPane.OK_OPTION);
                } else {
                    JOptionPane.showMessageDialog(AddStaff.this,
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

        add(addStaffJPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Add Staff");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean addStaffToDB(String name, String contact) {
    //Book book = null;

    final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    final String USERNAME = "root";
    final String PASSWORD = "bri70122";

    try{
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        String sql = "INSERT INTO staff (name, contact) VALUES (?, ?)";
        PreparedStatement myStmt = connection.prepareStatement(sql);

        myStmt.setString(1, name);
        myStmt.setString(2, contact);

        System.out.println(myStmt);
        myStmt.executeUpdate();
        System.out.println("complete");

        myStmt.close();
        connection.close();

        System.out.println("connection successful!");
    } catch(Exception e){
        System.out.println("Database connection failed!");
        return false;
    }
    return true;
    }

    public static void main(String[] args) {
        AddStaff addStaff = new AddStaff();
        addStaff.initialize();
    }
}
