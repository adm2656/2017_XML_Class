import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.*;
public class Q2 {

	private JFrame frame;
	private JTextField textFieldCity;
	private JTextField textFieldPm;
	
	ArrayList<String> arPm = new ArrayList<String>();
	ArrayList<String> arCity = new ArrayList<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Q2 window = new Q2();
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
	public Q2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblQ2 = new JLabel("Q2");
		lblQ2.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblQ2.setBounds(208, 36, 29, 26);
		frame.getContentPane().add(lblQ2);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblCity.setBounds(94, 94, 61, 21);
		frame.getContentPane().add(lblCity);
		
		JLabel lblPm = new JLabel("PM2.5");
		lblPm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblPm.setBounds(94, 159, 61, 21);
		frame.getContentPane().add(lblPm);
		
		textFieldCity = new JTextField();
		textFieldCity.setBounds(175, 92, 130, 26);
		frame.getContentPane().add(textFieldCity);
		textFieldCity.setColumns(10);
		
		textFieldPm = new JTextField();
		textFieldPm.setBounds(175, 157, 130, 26);
		frame.getContentPane().add(textFieldPm);
		textFieldPm.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String city = "", pm = "";
				city = textFieldCity.getText();
				pm = textFieldPm.getText();
				
				arCity.add(city);
				arPm.add(pm);
				
				textFieldCity.setText("");
				textFieldPm.setText("");
				
			}
		});
		btnEnter.setBounds(94, 217, 117, 29);
		frame.getContentPane().add(btnEnter);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String filename = "PM2.5.xml";
				File saveFile = new File(filename);
				
				try{
					FileWriter br = new FileWriter(saveFile);
					br.write("<?xml version='1.0'?>");
					br.write("<air>");
					int i = arCity.size();
					for (int x = 0; i>x; x++){
						br.write("<data>");
						br.write("<city>");
						br.write(arCity.get(x));
						br.write("</city>");
						br.write("<PM>");
						br.write(arPm.get(x));
						br.write("</PM>");
						br.write("</data>");
					}
					br.write("</air>");
					br.close();
					
				}catch (IOException ex){
					
				}
			}
		});
		btnSave.setBounds(238, 217, 117, 29);
		frame.getContentPane().add(btnSave);
	}
}
