package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.io.*;
import java.util.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Login {

	private JFrame frame;
	private JTextField textFieldFirst;
	private JTextField textFieldLast;

	String first;
	String last;
	String time;
	String str;
	String checkfirst, checklast, checktime;
	Date date;
	File file;
	
	ArrayList<Doctor> dc = new ArrayList<Doctor>();
	ArrayList<Doctor> checkdc = new ArrayList<Doctor>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 428, 260);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				first = textFieldFirst.getText();
				last = textFieldLast.getText();
				date = new Date();
				time = date.toString();
				
				file = new File("doctor.xml");
				
				if (first.equals("") || last.equals("")){
					JOptionPane.showMessageDialog(null, "Please entet your name");
				}
				
				else if (!first.equals("") && !last.equals("")){
					if (!file.exists()){
						JOptionPane.showMessageDialog(null, "Please create your id first");
						textFieldFirst.setText("");
						textFieldLast.setText("");
					}
					else if (file.exists()){
						try {
							getdata();
							int count = 0;
							for (int i = 0; i < checkdc.size(); i++){
								if (first.equals(checkdc.get(i).getFirst()) && last.equals(checkdc.get(i).getLast())){
										count++;
										DoctorApp nw = new DoctorApp(first, last, checkdc.get(i).getLastLogin());
										nw.app(first, last, checkdc.get(i).getLastLogin());
										frame.dispose();
										newtime(first, last, time);
										break;
								}
							}
							if (count == 0){
								JOptionPane.showMessageDialog(null, "We don't have a doctor called " + first + " " + last + "\nPlease check again or contact with our staff");
								for (int p = 0; p < dc.size(); p++){
									dc.remove(p);
								}
							}
							textFieldFirst.setText("");
							textFieldLast.setText("");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnLogin.setBounds(70, 169, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblWelcome.setBounds(125, 17, 149, 45);
		frame.getContentPane().add(lblWelcome);
		
		textFieldFirst = new JTextField();
		textFieldFirst.setBounds(191, 78, 130, 26);
		frame.getContentPane().add(textFieldFirst);
		textFieldFirst.setColumns(10);
		
		textFieldLast = new JTextField();
		textFieldLast.setBounds(191, 118, 130, 26);
		frame.getContentPane().add(textFieldLast);
		textFieldLast.setColumns(10);
		
		JLabel lblFirst = new JLabel("First Name");
		lblFirst.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblFirst.setBounds(90, 81, 89, 19);
		frame.getContentPane().add(lblFirst);
		
		JLabel lblLast = new JLabel("Last Name");
		lblLast.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblLast.setBounds(90, 122, 89, 16);
		frame.getContentPane().add(lblLast);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					first = textFieldFirst.getText();
					last = textFieldLast.getText();
					date = new Date();
					time = date.toString();
					
					file = new File("doctor.xml");
					
					if (first.equals("") || last.equals("")){
						JOptionPane.showMessageDialog(null, "You can't create an id without your name");
					}
					else if (!first.equals("") && !last.equals("")){
						if (!file.exists()){
							dc.add(new Doctor(first, last, time));
							try {
								createXml();
								DoctorApp nw = new DoctorApp(first, last, time);
								nw.app(first, last, time);
								frame.dispose();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if (file.exists()){
							dc.add(new Doctor(first, last, time));
							try {
								getdata();
								int count = 0;
								for (int i = 0; i < checkdc.size(); i++){
									if (first.equals(checkdc.get(i).getFirst()) && last.equals(checkdc.get(i).getLast())){
										JOptionPane.showMessageDialog(null, "You already have an account, Please login");
										dc.remove(0);
										count++;
										break;		
									}
								}
								if (count == 0){
									insertDoctorData(); 
									DoctorApp nw = new DoctorApp(first, last, time);
									nw.app(first, last, time);
									frame.dispose();
								}
							} catch (Exception e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
					}
			}
		});
		btnCreate.setBounds(224, 169, 117, 29);
		frame.getContentPane().add(btnCreate);
	}
	
	public void createXml()throws Exception{
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Data");
			doc.appendChild(rootElement);
			
			Element doctor = doc.createElement("Doctor");
			rootElement.appendChild(doctor);
			
			Element Dfirstname = doc.createElement("Firstname");
			Dfirstname.appendChild(doc.createTextNode(dc.get(0).getFirst()));
			doctor.appendChild(Dfirstname);
			
			Element Dlastname = doc.createElement("Lastname");
			Dlastname.appendChild(doc.createTextNode(dc.get(0).getLast()));
			doctor.appendChild(Dlastname);
			
			Element lastlogin = doc.createElement("Lastlogin");
			lastlogin.appendChild(doc.createTextNode(dc.get(0).getLastLogin()));
			doctor.appendChild(lastlogin);
			
			Element patients = doc.createElement("Patients");
			doctor.appendChild(patients);
						
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("doctor.xml"));
			
			transformer.transform(source, result);

	}	
	
	public void insertDoctorData()throws Exception{
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File("doctor.xml"));
			
			Node root = doc.getElementsByTagName("Data").item(0);
			Element newdoctor = doc.createElement("Doctor");
			root.appendChild(newdoctor);
			
			Element Dfirstname = doc.createElement("Firstname");
			Dfirstname.appendChild(doc.createTextNode(dc.get(0).getFirst()));
			newdoctor.appendChild(Dfirstname);
			
			Element Dlastname = doc.createElement("Lastname");
			Dlastname.appendChild(doc.createTextNode(dc.get(0).getLast()));
			newdoctor.appendChild(Dlastname);
			
			Element lastlogin = doc.createElement("Lastlogin");
			lastlogin.appendChild(doc.createTextNode(dc.get(0).getLastLogin()));
			newdoctor.appendChild(lastlogin);
			
			Element patients = doc.createElement("Patients");
			newdoctor.appendChild(patients);
						
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("doctor.xml"));
			
			transformer.transform(source, result);
	}
	
	public void getdata() throws Exception{
			
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxparser = factory.newSAXParser();
		
		DefaultHandler handler = new DefaultHandler(){

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				// TODO Auto-generated method stub
				super.startElement(uri, localName, qName, attributes);
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				// TODO Auto-generated method stub
				super.endElement(uri, localName, qName);
				if (qName.equals("Firstname")){
					checkfirst = str;
				}
				else if (qName.equals("Lastname")){
					checklast = str;
				}
				else if (qName.equals("Lastlogin")){
					checktime = str;
				}
				else if (qName.equals("Doctor")){
					checkdc.add(new Doctor(checkfirst, checklast, checktime));
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				// TODO Auto-generated method stub
				super.characters(ch, start, length);
				str = new String(ch, start, length);
			}
			
		};
		saxparser.parse(new File("doctor.xml"), handler);
	}
	
	public void newtime(String f, String l, String t)throws Exception{
		
		NodeList firstname , lastname;
		int numberofdoctor;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File("doctor.xml"));
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath path = xPathfactory.newXPath();
		
		NodeList doctor = doc.getElementsByTagName("Doctor");
		numberofdoctor = doctor.getLength();
		
		firstname = (NodeList) path.evaluate("/Data//Doctor/Firstname/text()", doc, XPathConstants.NODESET);
		lastname = (NodeList) path.evaluate("/Data//Doctor/Lastname/text()", doc, XPathConstants.NODESET); 
		
		for (int i = 0; i < numberofdoctor; i++){
			 if (f.equals(firstname.item(i).getNodeValue()) && l.equals(lastname.item(i).getNodeValue())){
				 Node time = doc.getElementsByTagName("Lastlogin").item(i).getChildNodes().item(0);
				 time.setNodeValue(t);
 			 }
		}
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
			
		transformer.transform(source, result);
	}
}
