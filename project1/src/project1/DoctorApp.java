package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Color;

public class DoctorApp {
	
	String str;
	String checkfirst, checklast, checktime;
	String checkPname, checkPssn, checkPaddress, checkPphone, checkPnote;
	String PName, Pssn, Paddress, Pphone, Pnote;
	String editfirst, editlast;
	
	DefaultListModel listModel;
	JList list_1;
	JScrollPane jsp;
	
	
	ArrayList<Patient> p = new ArrayList<Patient>();
	ArrayList<Doctor> checkdc = new ArrayList<Doctor>();
	ArrayList<Patient> checkpatient = new ArrayList<Patient>();
	
	private JFrame frame;
	private JTextField textFieldPName;
	private JTextField textFieldPssn;
	private JTextField textFieldPaddress;
	private JTextField textFieldPphone;
	private JTextField textFieldeditfirst;
	private JTextField textFieldeditlast;
	/**
	 * Launch the application.
	 */
	public static void app(String first, String last, String time) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorApp window = new DoctorApp(first, last, time);
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
	public DoctorApp(String f, String l, String t) {
		initialize(f ,l, t);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String f, String l, String t) {
		frame = new JFrame();
		frame.setBounds(100, 100, 661, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 649, 408);
		frame.getContentPane().add(tabbedPane);
		
		JPanel indexpane = new JPanel();
		tabbedPane.addTab("Index", null, indexpane, null);
		indexpane.setLayout(null);
		
		JLabel lbl1 = new JLabel();
		lbl1.setText("Hello, Dr." + f + " " + l + " glad to see you.");
		lbl1.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lbl1.setBounds(57, 83, 471, 60);
		indexpane.add(lbl1);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnExit.setBounds(464, 285, 135, 51);
		indexpane.add(btnExit);
		
		JLabel lbl2 = new JLabel();
		try {
			getdoctordata();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < checkdc.size(); i++){
			if (f.equals(checkdc.get(i).getFirst()) && l.equals(checkdc.get(i).getLast())){
				if (!t.equals(checkdc.get(i).getLastLogin())){
					lbl2.setText("Last time you login was " + t + ".");
				}
				else if (t.equals(checkdc.get(i).getLastLogin())){
					lbl2.setText("It's your first time login, Welcome");
				}
			}
		}
		lbl2.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lbl2.setBounds(57, 155, 565, 51);
		indexpane.add(lbl2);
		
		JPanel patientspane = new JPanel();
		tabbedPane.addTab("Patients", null, patientspane, null);
		patientspane.setLayout(null);
		
		JLabel lblpatient = new JLabel("Patient name");
		lblpatient.setBounds(290, 62, 93, 16);
		lblpatient.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		patientspane.add(lblpatient);
		
		textFieldPName = new JTextField();
		textFieldPName.setBounds(405, 58, 130, 26);
		patientspane.add(textFieldPName);
		textFieldPName.setColumns(10);
		
		JLabel lblssn = new JLabel("SSN");
		lblssn.setBounds(356, 90, 27, 16);
		lblssn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		patientspane.add(lblssn);
		
		textFieldPssn = new JTextField();
		textFieldPssn.setBounds(405, 86, 130, 26);
		patientspane.add(textFieldPssn);
		textFieldPssn.setColumns(10);
		
		JLabel lbladdress = new JLabel("Address");
		lbladdress.setBounds(325, 118, 58, 16);
		lbladdress.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		patientspane.add(lbladdress);
		
		textFieldPaddress = new JTextField();
		textFieldPaddress.setBounds(405, 114, 130, 26);
		patientspane.add(textFieldPaddress);
		textFieldPaddress.setColumns(10);
		
		JLabel lblphone = new JLabel("Phone number");
		lblphone.setBounds(280, 146, 103, 16);
		lblphone.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		patientspane.add(lblphone);
		
		textFieldPphone = new JTextField();
		textFieldPphone.setBounds(405, 142, 130, 26);
		patientspane.add(textFieldPphone);
		textFieldPphone.setColumns(10);
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setBounds(344, 174, 39, 16);
		lblNote.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		patientspane.add(lblNote);
		
		JTextPane notePane = new JTextPane();
		notePane.setBounds(405, 174, 130, 84);
		patientspane.add(notePane);
		
		JButton btnsave = new JButton("Save");
		btnsave.setBounds(405, 286, 130, 29);
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldPName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Patient's information is not complete");
				}
				
				else if (textFieldPssn.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Patient's information is not complete");
				}
				
				else if (textFieldPaddress.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Patient's information is not complete");
				}
				
				else if (textFieldPphone.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Patient's information is not complete");
				}
				
				else if (notePane.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Patient's information is not complete");
				}
				
				else {
					PName = textFieldPName.getText();	
					Pssn = textFieldPssn.getText();
					Paddress = textFieldPaddress.getText();
					Pphone = textFieldPphone.getText();
					Pnote = notePane.getText();
					
					p.add(new Patient(PName, Pssn, Paddress, Pphone, Pnote));
					int index = list_1.getSelectedIndex();
					if(index != -1){
						if (Pssn.equals(checkpatient.get(index).getSsn())){
							try {
								updatepatient(f, l, index, PName, Pssn, Paddress, Pphone, Pnote);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							checkpatient.set(index, new Patient(PName, Pssn, Paddress, Pphone, Pnote));
							listModel.set(index, checkpatient.get(index).getname());
							JOptionPane.showMessageDialog(null, "Update success");
							p.remove(0);
						}
						else {
							try {	
								insertPatientdata(f, l);
								JOptionPane.showMessageDialog(null, "Data has been saved");
								checkpatient.add(new Patient(PName, Pssn, Paddress, Pphone, Pnote));
								listModel.addElement(checkpatient.get(checkpatient.size() -1).getname());
								p.remove(0);
							} catch (Exception e1) {	
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					else {
						try {	
							insertPatientdata(f, l);
							JOptionPane.showMessageDialog(null, "Data has been saved");
							checkpatient.add(new Patient(PName, Pssn, Paddress, Pphone, Pnote));
							listModel.addElement(checkpatient.get(checkpatient.size() -1).getname());
							p.remove(0);
						} catch (Exception e1) {	
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				
					textFieldPName.setText("");
					textFieldPssn.setText("");
					textFieldPaddress.setText("");
					textFieldPphone.setText("");
					notePane.setText("");
				}
			}
		});
		patientspane.add(btnsave);
		
		JButton btnselect = new JButton("Select");
		btnselect.setBounds(97, 286, 117, 29);
		btnselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list_1.getSelectedIndex();
				textFieldPName.setText(checkpatient.get(index).getname());
				textFieldPssn.setText(checkpatient.get(index).getSsn());
				textFieldPaddress.setText(checkpatient.get(index).getAddress());
				textFieldPphone.setText(checkpatient.get(index).getphone());
				notePane.setText(checkpatient.get(index).getnote());
			}
		});
		patientspane.add(btnselect);
		
		listModel = new DefaultListModel();
		
		try {
			getpatientdata(f, l);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		addToJList();
		
		jsp = new JScrollPane();
		jsp.setBounds(97, 62, 117, 196);
		patientspane.add(jsp);
		
		list_1 = new JList(listModel);
		jsp.setViewportView(list_1);
		
		JButton btndelete = new JButton("delete");
		btndelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list_1.getSelectedIndex() == -1){
					JOptionPane.showMessageDialog(null, "You can't delete data without select it");
				}
				else{
					int c = list_1.getSelectedIndex();
					listModel.remove(c);
					try {
						deletepatientdata(f, l, c);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				textFieldPName.setText("");
				textFieldPssn.setText("");
				textFieldPaddress.setText("");
				textFieldPphone.setText("");
				notePane.setText("");
			}
		});
		btndelete.setBounds(252, 286, 117, 29);
		patientspane.add(btndelete);
		
		JButton btnclean = new JButton("Clean");
		btnclean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldPName.setText("");
				textFieldPssn.setText("");
				textFieldPaddress.setText("");
				textFieldPphone.setText("");
				notePane.setText("");
			}
		});
		btnclean.setBounds(505, 17, 117, 29);
		patientspane.add(btnclean);
		
		JPanel settingpane = new JPanel();
		tabbedPane.addTab("Setting", null, settingpane, null);
		settingpane.setLayout(null);
		
		JLabel lbldelete = new JLabel("Delete this user");
		lbldelete.setForeground(Color.RED);
		lbldelete.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		lbldelete.setBounds(95, 47, 206, 76);
		settingpane.add(lbldelete);
		
		JButton btndeletesuer = new JButton("Delete ");
		btndeletesuer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This user has been deleted");
				try {
					deletedoctor(f, l);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		btndeletesuer.setBounds(363, 76, 117, 29);
		settingpane.add(btndeletesuer);
		
		JLabel lbleditdoctorname = new JLabel("Edit doctor's name");
		lbleditdoctorname.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		lbleditdoctorname.setBounds(95, 165, 241, 55);
		settingpane.add(lbleditdoctorname);
		
		JButton btnedit = new JButton("Edit");
		btnedit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				editfirst = textFieldeditfirst.getText();
				editlast = textFieldeditlast.getText();
				
				int count = 0;
				if(editfirst.equals("") || editlast.equals("")){
					JOptionPane.showMessageDialog(null, "You can't edit a doctor without the name");
				}
				else{
					for (int i = 0; i < checkpatient.size(); i++){
						if (editfirst.equals(checkdc.get(i).getFirst()) && editlast.equals(checkdc.get(i).getLast())){
							JOptionPane.showMessageDialog(null, "This doctor has already exist, please try again");
							count++;
							break;
						}
					}
					if (count == 0){
						try {
							editdoctor(f, l, editfirst, editlast);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Data has been edited, please login again");
						System.exit(0);
					}
				}
			}
		});
		btnedit.setBounds(363, 307, 117, 29);
		settingpane.add(btnedit);
		
		textFieldeditfirst = new JTextField();
		textFieldeditfirst.setBounds(363, 227, 130, 26);
		settingpane.add(textFieldeditfirst);
		textFieldeditfirst.setColumns(10);
		
		textFieldeditlast = new JTextField();
		textFieldeditlast.setBounds(363, 268, 130, 26);
		settingpane.add(textFieldeditlast);
		textFieldeditlast.setColumns(10);
		
		JLabel lblfirst = new JLabel("Firstname");
		lblfirst.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblfirst.setBounds(267, 229, 78, 21);
		settingpane.add(lblfirst);
		
		JLabel lbllast = new JLabel("Lastname");
		lbllast.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lbllast.setBounds(267, 273, 78, 16);
		settingpane.add(lbllast);
	}
	
	public void getdoctordata() throws Exception{
		
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
	
	public void getpatientdata(String f, String l) throws Exception{
		
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
				else if(f.equals(checkfirst) && l.equals(checklast)){
						if (qName.equals("PatientName")){
							checkPname = str;
						}
						else if (qName.equals("SSN")){
							checkPssn = str;
						}
						else if (qName.equals("Address")){
							checkPaddress = str;
						}
						else if (qName.equals("Phone")){
							checkPphone = str;
						}
						else if (qName.equals("Note")){	
							checkPnote = str;
						}
						else if (qName.equals("Patient")){
							checkpatient.add(new Patient(checkPname, checkPssn, checkPaddress, checkPphone, checkPnote));
						}
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
	
	public void addToJList(){
		for (int x = 0; x < checkpatient.size(); x++){
			listModel.addElement(checkpatient.get(x).getname());
		}
	}
	
	public void insertPatientdata(String f, String l)throws Exception{
		
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
		
		for (int i = 0; i < firstname.getLength(); i++){
			if (f.equals(firstname.item(i).getNodeValue()) && l.equals(lastname.item(i).getNodeValue())){
				Node root = doc.getElementsByTagName("Patients").item(i);
				Element newpatient = doc.createElement("Patient");
				root.appendChild(newpatient);
		
				Element Patientname = doc.createElement("PatientName");
				Patientname.appendChild(doc.createTextNode(p.get(0).getname()));
				newpatient.appendChild(Patientname);
		
				Element PatientSSN = doc.createElement("SSN");
				PatientSSN.appendChild(doc.createTextNode(p.get(0).getSsn()));
				newpatient.appendChild(PatientSSN);
		
				Element PatientAddress = doc.createElement("Address");
				PatientAddress.appendChild(doc.createTextNode(p.get(0).getAddress()));
				newpatient.appendChild(PatientAddress);
		
				Element PatientPhone = doc.createElement("Phone");
				PatientPhone.appendChild(doc.createTextNode(p.get(0).getphone()));
				newpatient.appendChild(PatientPhone);
				
				Element PatientNote = doc.createElement("Note");
				PatientNote.appendChild(doc.createTextNode(p.get(0).getnote()));
				newpatient.appendChild(PatientNote);
				}
			}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}
	
	public void deletepatientdata(String f, String l, int c) throws Exception{
		
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
		
		for (int i = 0; i < firstname.getLength(); i++){
			if (f.equals(firstname.item(i).getNodeValue()) && l.equals(lastname.item(i).getNodeValue())){
				Node root = doc.getElementsByTagName("Patients").item(i);
				NodeList patient = root.getChildNodes();
				Node delete = patient.item(c);
				delete.getParentNode().removeChild(delete);
			}
		}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}
	
	public void updatepatient(String f, String l, int c, String PName, String Pssn, String Paddress, String Pphone, String note) throws Exception{
		
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
		
		for (int i = 0; i < firstname.getLength(); i++){
			if (f.equals(firstname.item(i).getNodeValue()) && l.equals(lastname.item(i).getNodeValue())){
				Node root = doc.getElementsByTagName("Patients").item(i);
				NodeList patient = root.getChildNodes();
				Node name = patient.item(c).getChildNodes().item(0);
				Node ssn = patient.item(c).getChildNodes().item(1);
				Node address = patient.item(c).getChildNodes().item(2);
				Node phone = patient.item(c).getChildNodes().item(3);
				Node Pnote = patient.item(c).getChildNodes().item(4);
				
				name.setTextContent(PName);
				ssn.setTextContent(Pssn);
				address.setTextContent(Paddress);
				phone.setTextContent(Pphone);
				Pnote.setTextContent(note);
			}
		}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}

	public void deletedoctor(String f, String l) throws Exception{
		
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
		
		for (int i = 0; i < firstname.getLength(); i++){
			if (f.equals(firstname.item(i).getNodeValue()) && l.equals(lastname.item(i).getNodeValue())){
				Node root = doc.getDocumentElement();
				Node dc = doc.getElementsByTagName("Doctor").item(i);
				root.removeChild(dc);
			}
		}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}

	public void editdoctor(String f, String l, String editfirst, String editlast) throws Exception{
		
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
				 Node first = doc.getElementsByTagName("Firstname").item(i).getChildNodes().item(0);
				 Node last = doc.getElementsByTagName("Lastname").item(i).getChildNodes().item(0);
				 first.setTextContent(editfirst);
				 last.setTextContent(editlast);
 			 }
		}
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
			
		transformer.transform(source, result);
	}
}
