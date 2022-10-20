package student_management;
import java.sql.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Student_management {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtYear;
	private JTextField txtMajor;
	private JTable table;
	private JTextField txtID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student_management window = new Student_management();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Student_management() {
		initialize();
		Connect();
		table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	 
	public void Connect()
	    {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/student_management", "root","");
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

	  public void table_load()
	    {
	     try
	     {
	    pst = con.prepareStatement("select * from student");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	}
	     catch (SQLException e)
	     {
	     e.printStackTrace();
	  }
	    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 714, 443);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Management");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(181, 11, 325, 76);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(26, 79, 330, 177);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Student Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 35, 112, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Year");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 87, 112, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Major");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 126, 112, 28);
		panel.add(lblNewLabel_1_2);
		
		txtName = new JTextField();
		txtName.setBounds(132, 34, 174, 20);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtYear = new JTextField();
		txtYear.setColumns(10);
		txtYear.setBounds(132, 86, 174, 20);
		panel.add(txtYear);
		
		txtMajor = new JTextField();
		txtMajor.setColumns(10);
		txtMajor.setBounds(132, 132, 174, 20);
		panel.add(txtMajor);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,year,major;
				name = txtName.getText();
				year = txtYear.getText();
				major = txtMajor.getText();
				try {
				pst = con.prepareStatement("insert into student(name,year,major)values(?,?,?)");
				pst.setString(1, name);
				pst.setString(2, year);
				pst.setString(3, major);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
				table_load();
				          
				txtName.setText("");
				txtYear.setText("");
				txtMajor.setText("");
				txtName.requestFocus();
				   }
				 
				catch (SQLException e1)
				        {
				e1.printStackTrace();
				        
			}}
		});
		btnNewButton.setBounds(26, 267, 100, 50);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(136, 267, 100, 50);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(246, 267, 100, 50);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(366, 78, 322, 248);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(26, 328, 318, 44);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Student ID");
		lblNewLabel_1_1_1.setBounds(10, 18, 96, 17);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblNewLabel_1_1_1);
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = txtID.getText();
		 
		                pst = con.prepareStatement("select name,Year,Major from student where id = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true)
		            {
		              
		                String name = rs.getString(1);
		                String Year = rs.getString(2);
		                String Major = rs.getString(3);
		                
		                txtName.setText(name);
		                txtYear.setText(Year);
		                txtMajor.setText(Major);
		                
		                
		            }  
		            else
		            {
		             txtName.setText("");
		             txtYear.setText("");
		                txtMajor.setText("");
		                
		            }
		            
		 
		 
		        }
		catch (SQLException ex) {
		          
		        }
				
			}
		});
		txtID.setBounds(83, 16, 214, 20);
		txtID.setColumns(10);
		panel_1.add(txtID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Name,Year,Major,id;
				Name = txtName.getText();
				Year = txtYear.getText();
				Major = txtMajor.getText();
				id  = txtID.getText();
				try {
				pst = con.prepareStatement("update book set name= ?,Year=?,Major=? where id =?");
				pst.setString(1, Name);
				            pst.setString(2, Year);
				            pst.setString(3, Major);
				            pst.setString(4, id);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Record Update!!!!!");
				            table_load();
				          
				            txtName.setText("");
				            txtYear.setText("");
				            txtMajor.setText("");
				            txtName.requestFocus();
				}
				 
				            catch (SQLException e1) {
				e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(406, 337, 100, 56);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

                String id;
                id  = txtID.getText();
                try {
                	pst = con.prepareStatement("delete from student where id =?");
				    pst.setString(1, id);
				    pst.executeUpdate();
				    JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
				    table_load();
				  
				    txtName.setText("");
				    txtYear.setText("");
				    txtMajor.setText("");
				    txtName.requestFocus();
}

    catch (SQLException e1) {
e1.printStackTrace();
}
			}
		});
		btnDelete.setBounds(538, 337, 100, 56);
		frame.getContentPane().add(btnDelete);
	}
}
