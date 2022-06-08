import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JTable table1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void connect()

    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();


        }
        catch (SQLException ex)
        {
            ex.printStackTrace();

        }


    }

    void table_load()

    {
        try {
            pst = con.prepareStatement(sql: "select * from test");
            ResultSet rs = pst.executeQuery();
            Object DbUtils;
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



//SAVE BUTTON
    public Employee() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String empname, salary, mobile;
                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();

                try {
                    pst = con.prepareStatement( sql: "insert into test(empname,salary,mobile)values(?,?,?)");
                    pst.setString(parameterIndex: 1, empname);
                    pst.setString(parameterIndex: 2, salary);
                    pst.setString(parameterIndex: 3, mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog( parentComponent: null, message: "Record Addedddd!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
            void table_load()
            {
                try {
                    pst = con.prepareStatement(sql:"select * from test");
                    ResultSet rs = pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        //SEARCH BUTTON
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {

                    String empid = txtid.getText();

                    pst = con.prepareStatement(sql:"select empname,salary,mobile from test where id = ?");
                    pst.setString(parameterIndex:1, empid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String empname = rs.getString(columnIndex:1);
                        String emsalary = rs.getString(columnIndex:2);
                        String emmobile = rs.getString(columnIndex:3);

                        txtName.setText(empname);
                        txtName.setText(emsalary);
                        txtName.setText(emmobile);


                    } else {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(parentComponent:null, message:"Invalid Employee Number");


                    }

                }
                catch (SQLException ex) {



                }

            }
        });
        
        //UPDATE BUTTON 
        
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String empid, empname, salary, mobile;
                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement(sql: "update test set empname = ?, salary = ?, mobile = ? where id = ?");
                    pst.setString(parameterIndex: 1, empname);
                    pst.setString(parameterIndex: 2, salary);
                    pst.setString(parameterIndex: 3, mobile);
                    pst.setString(parameterIndex: 4, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(parentComponet: null, message: "Record Updated!!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();

                }

                catch (SQLException e1) {

                    e1.printStackTrace();
                }

            }
        });
        
        //DELETE BUTTON
        
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String empid;

                empid = txtid.getText();

                try {
                    pst = con.prepareStatement(sql: "delete from test where id = ?");
                    pst.setString(parameterIndex: 1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(parentComponet: null, message: "Record Deleted!!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();

                }

                catch (SQLException e1) {

                    e1.printStackTrace();
                }

            }
        });
    }
}
