import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.*;

public class Q1 {

	private JFrame frame;
	private JTextField textFieldCity;
	private JTextField textFieldPm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Q1 window = new Q1();
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
	public Q1() {
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
		
		JLabel lblQ1 = new JLabel("Q1");
		lblQ1.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblQ1.setBounds(208, 20, 91, 41);
		frame.getContentPane().add(lblQ1);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(76, 95, 61, 16);
		frame.getContentPane().add(lblCity);
		
		JLabel lblPm = new JLabel("PM2.5");
		lblPm.setBounds(76, 152, 61, 16);
		frame.getContentPane().add(lblPm);
		
		textFieldCity = new JTextField();
		textFieldCity.setBounds(169, 90, 130, 26);
		frame.getContentPane().add(textFieldCity);
		textFieldCity.setColumns(10);
		
		textFieldPm = new JTextField();
		textFieldPm.setBounds(169, 147, 130, 26);
		frame.getContentPane().add(textFieldPm);
		textFieldPm.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(170, 210, 117, 29);
		frame.getContentPane().add(btnSend);
		
		btnSend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String city = "", pm = "";
				city = "City : " + textFieldCity.getText();
				pm = "PM2.5 : " + textFieldPm.getText();
				JOptionPane.showMessageDialog(null, city + "\n" + pm);
			}
			
		});
	}
}
