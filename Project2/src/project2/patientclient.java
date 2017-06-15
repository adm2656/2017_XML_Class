package project2;

import java.awt.EventQueue;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Canvas;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class patientclient {
	static String hostname = "127.0.0.1";
	static int portnumber = 10000;
	
	static Socket client;

	private JFrame frame;
	private JTextField textFieldPname;
	private JLabel lblPname;
	private JTextField textFielddcf;
	private JLabel lbldcf;
	private JTextField textFieldPssn;
	private JTextField textFielddcl;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					client = new Socket(hostname, portnumber);
					patientclient window = new patientclient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Oops! We are facing some problem, Please contact with our staff");
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public patientclient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBackground(Color.GRAY);
		frame.setBounds(100, 100, 589, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblappointment = new JLabel("Appointment");
		lblappointment.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblappointment.setBounds(235, 19, 125, 44);
		frame.getContentPane().add(lblappointment);
		
		textFieldPname = new JTextField();
		textFieldPname.setBounds(152, 75, 140, 31);
		frame.getContentPane().add(textFieldPname);
		textFieldPname.setColumns(10);
		
		lblPname = new JLabel("Your Name");
		lblPname.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPname.setBounds(48, 76, 107, 26);
		frame.getContentPane().add(lblPname);
		
		textFielddcf = new JTextField();
		textFielddcf.setColumns(10);
		textFielddcf.setBounds(152, 194, 140, 31);
		frame.getContentPane().add(textFielddcf);

		lbldcf = new JLabel("First Name");
		lbldcf.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lbldcf.setBounds(48, 195, 107, 26);
		frame.getContentPane().add(lbldcf);
		
		Date date = new Date();
		Calendar rightnow = Calendar.getInstance();
		rightnow.setTime(date);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(222, 243, 149, 37);
		for (int i = 0; i < 7; i++){
			rightnow.add(rightnow.DATE, 1);
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			String today = format.format(rightnow.getTime()).toString();
			comboBox.addItem(today);
		}
		frame.getContentPane().add(comboBox);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblDate.setBounds(173, 246, 53, 26);
		frame.getContentPane().add(lblDate);
		
		JButton btnres = new JButton("Reservation");
		btnres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldPname.getText().equals("") || textFieldPssn.getText().equals("") || textFielddcf.getText().equals("") || textFielddcl.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please filled all the information, Thanks");
				}
				else {
					try {
						PrintWriter out = new PrintWriter(client.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
					
						String pname = textFieldPname.getText();
						String pssn = textFieldPssn.getText();
						String dcf = textFielddcf.getText();
						String dcl = textFielddcl.getText();
						String cd = comboBox.getSelectedItem().toString();
					
						String res;

						out.println("reservation");

						while ((res = in.readLine()) != null){
							if (res.equals("ready")){
								out.println(pname + ";" + pssn + ";" + dcf + ";" + dcl + ";" + cd);
								res = in.readLine();
								if (res.equals("success")){
									JOptionPane.showMessageDialog(null, "Reservation Succcess");
									break;
								}
								else if (res.equals("falied")){
									JOptionPane.showMessageDialog(null, "There's no such doctor called " + dcf + "," + dcl);
									break;
									}
								}
							}
					}catch (Exception e1){
						System.out.println(e1);
					}
				}
			}
		});
		btnres.setBounds(235, 290, 117, 29);
		frame.getContentPane().add(btnres);
		
		JLabel lblSSN = new JLabel("Your SSN");
		lblSSN.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblSSN.setBounds(316, 76, 107, 26);
		frame.getContentPane().add(lblSSN);
		
		textFieldPssn = new JTextField();
		textFieldPssn.setColumns(10);
		textFieldPssn.setBounds(409, 75, 140, 31);
		frame.getContentPane().add(textFieldPssn);
		
		JLabel lbldcinfo = new JLabel("Informations of Doctor you want to make an appointment");
		lbldcinfo.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lbldcinfo.setBounds(48, 144, 501, 26);
		frame.getContentPane().add(lbldcinfo);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(37, 125, 527, 7);
		frame.getContentPane().add(separator);
		
		JLabel lbldcl = new JLabel("Last Name");
		lbldcl.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lbldcl.setBounds(316, 195, 107, 26);
		frame.getContentPane().add(lbldcl);
		
		textFielddcl = new JTextField();
		textFielddcl.setColumns(10);
		textFielddcl.setBounds(409, 194, 140, 31);
		frame.getContentPane().add(textFielddcl);
	}
}
