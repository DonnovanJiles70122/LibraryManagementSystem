import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class RemoveBook extends JFrame{
    final private Font mainFont = new Font("Arial", Font.PLAIN, 14);
    JTable removeBookJTable;
    DefaultTableModel model;

    public void initialize(){
        JLabel labelAddBook = new JLabel("Remove Book", SwingConstants.CENTER);
        labelAddBook.setFont(mainFont);
        String[] columnNames = {"Category", "Title", "Author"};

        final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
        final String USERNAME = "root";
        final String PASSWORD = "bri70122";

        try{

            int row = 0;
            int col = 3;
            int tracker = 0;

            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            System.out.println("connection successful!");

            String rowCount = "SELECT COUNT(*) FROM books";

            PreparedStatement ps = connection.prepareStatement(rowCount);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                row = Integer.parseInt(rs.getString(1));
            }



            String[][] dataListString = new String[row][col];

            String sql = "SELECT category, name, author, copies FROM books";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                for(int i = 0; i < col; i++){
                    dataListString[tracker][i] = resultSet.getString(i+1);
                }
                tracker++;
            }

            model = new DefaultTableModel(dataListString, columnNames);

            removeBookJTable = new JTable(model);
            removeBookJTable.setFont(mainFont);
            removeBookJTable.setRowMargin(30);
            removeBookJTable.setRowHeight(40);
            removeBookJTable.setBounds(30, 40, 800, 800);

            connection.close();
            resultSet.close();
            rs.close();

        } catch(Exception e){
            System.out.println("Database connection failed!");
        }

        JScrollPane scrollPane = new JScrollPane(removeBookJTable);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = "";
                if(removeBookJTable.getSelectedRow() != -1) {
                    int rowToBeDeleted = removeBookJTable.getSelectedRow();
                    bookTitle = model.getValueAt(rowToBeDeleted, 1).toString();
                    model.removeRow(removeBookJTable.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Selected row deleted");
                }
                 
                try {
                    Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String deleteStatement = "DELETE FROM books WHERE name=?";
                    PreparedStatement myStatement = connection.prepareStatement(deleteStatement);
                    myStatement.setString(1, bookTitle);
                    System.out.println(myStatement);
                    myStatement.executeUpdate();
                    System.out.println("deletion completed");

                } catch(Exception ex){
                    System.out.println("Database connection failed!");
                }

            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(removeButton, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);


    }

    public static void main(String[] args) {
        RemoveBook removeBook = new RemoveBook();
        removeBook.initialize();
    }
    
}
