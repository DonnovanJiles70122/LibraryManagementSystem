import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DashBoard extends JFrame {
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    final private Insets buttonInsets = new Insets(20,20,20,20);

    public void initialize() {
        // Create books available, staff details, add book, add staff, remove book, edit admin and remove staff button

        JLabel dashBoardJLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        dashBoardJLabel.setFont(mainFont);

        JButton booksAvailableButton = new JButton("Books Available");
        booksAvailableButton.setFont(mainFont);
        booksAvailableButton.setMargin(buttonInsets);

        JButton staffDetailButton = new JButton("Staff Detail");
        staffDetailButton.setFont(mainFont);
        staffDetailButton.setMargin(buttonInsets);

        JButton addBookButton = new JButton("Add Books");
        addBookButton.setFont(mainFont);
        addBookButton.setMargin(buttonInsets);
        addBookButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                AddBook addBook = new AddBook();
                addBook.initialize();
            }
        });

        JButton addStaffButton = new JButton("Add Staff");
        addStaffButton.setFont(mainFont);
        addStaffButton.setMargin(buttonInsets);
        addStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                AddStaff addStaff = new AddStaff();
                addStaff.initialize();
            }
        });

        JButton removeBookButton = new JButton("Remove Book");
        removeBookButton.setFont(mainFont);
        removeBookButton.setMargin(buttonInsets);
        removeBookButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                RemoveBook removeBook = new RemoveBook();
                removeBook.initialize();
            }
        });

        JButton removeStaffButton = new JButton("Remove Staff");
        removeStaffButton.setFont(mainFont);
        removeStaffButton.setMargin(buttonInsets);



        JPanel dashBoardPanel = new JPanel();
        dashBoardPanel.setLayout(new GridLayout(0,1,10,10));
        dashBoardPanel.add(dashBoardJLabel);
        dashBoardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonJPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 50, 20));
        buttonJPanel.add(booksAvailableButton);
        buttonJPanel.add(staffDetailButton);
        buttonJPanel.add(addBookButton);
        buttonJPanel.add(addStaffButton);
        buttonJPanel.add(removeBookButton);
        buttonJPanel.add(removeStaffButton);


        add(dashBoardPanel, BorderLayout.NORTH);
        add(buttonJPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        DashBoard dashBoard = new DashBoard();
        dashBoard.initialize();
    }
    
}
