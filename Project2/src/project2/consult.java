package project2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class consult {

	String str;
	String date, note, pre;
	String checkdate, checknote, checkpre;
	String checkfirst, checklast, checktime;
	String checkPname, checkPssn, checkPaddress, checkPphone;

	
	ArrayList<Appointment> checkap = new ArrayList<Appointment>();
	
	DefaultListModel listModel;
	JList list_1;
	JScrollPane jsp;
	
	private JFrame frame;
	private JTextField textFielddate;
	private JTextArea textAreanote, textAreapre;

	/**
	 * Launch the application.
	 */
	public static void con(String f, String l, String ssn) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					consult window = new consult(f, l, ssn);
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
	public consult(String f, String l, String ssn) {
		initialize(f, l, ssn);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String f, String l, String ssn) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		listModel = new DefaultListModel();
		
		try {
			getappointmentdata(f, l, ssn);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		addToJList();
		
		jsp = new JScrollPane();
		jsp.setBounds(23, 31, 127, 187);
		frame.getContentPane().add(jsp);
		
		list_1 = new JList(listModel);
		jsp.setViewportView(list_1);
		
		JButton btnfinish = new JButton("Finish");
		btnfinish.setBounds(223, 231, 117, 29);
		frame.getContentPane().add(btnfinish);
		
		JLabel lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lbldate.setBounds(226, 37, 33, 16);
		frame.getContentPane().add(lbldate);
		
		textFielddate = new JTextField();
		textFielddate.setEnabled(false);
		textFielddate.setBounds(284, 36, 135, 20);
		frame.getContentPane().add(textFielddate);
		textFielddate.setColumns(10);
		
		JLabel lblnote = new JLabel("Note");
		lblnote.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblnote.setBounds(226, 78, 39, 16);
		frame.getContentPane().add(lblnote);
		
		JTextArea textAreanote = new JTextArea();
		textAreanote.setBounds(286, 79, 129, 64);
		frame.getContentPane().add(textAreanote);
		
		JLabel lblpre = new JLabel("Prescription");
		lblpre.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblpre.setBounds(180, 159, 85, 16);
		frame.getContentPane().add(lblpre);
		
		JTextArea textAreapre = new JTextArea();
		textAreapre.setBounds(286, 160, 129, 64);
		frame.getContentPane().add(textAreapre);
		
		JButton btnselect = new JButton("Select");
		btnselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectdate = list_1.getSelectedIndex();
				if (selectdate == -1){
					JOptionPane.showMessageDialog(null, "Please select a date");
				}
				else {
					textFielddate.setText(checkap.get(selectdate).getdate());
					textAreanote.setText(checkap.get(selectdate).getnote());
					textAreapre.setText(checkap.get(selectdate).getpresception());
				}
			}
		});
		btnselect.setBounds(82, 231, 117, 29);
		frame.getContentPane().add(btnselect);
		
		JButton btnexit = new JButton("Exit");
		btnexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnexit.setBounds(349, 6, 95, 20);
		frame.getContentPane().add(btnexit);
		
		btnfinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectdate = list_1.getSelectedIndex();
				String fdate = checkap.get(selectdate).getdate();
				String fnote = textAreanote.getText();
				String fpre = textAreapre.getText();
				if (fnote.equals("") || fpre.equals("")){
					JOptionPane.showMessageDialog(null, "Please finish the Appointment first");
				}
				else{
					try{
						updateappointmentdata(f, l, ssn, fdate, fnote, fpre);
						checkap.set(selectdate, new Appointment(fdate, fnote, fpre));
						JOptionPane.showMessageDialog(null, "Finish Appointment");
					}catch (Exception x){
						System.out.println(x);
					}
				}
			}
		});
	}
	
	public void getappointmentdata(String f, String l, String ssn) throws Exception{
		
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
						if (qName.equals("SSN")){
							checkPssn = str;
						}
						else if(ssn.equals(checkPssn)){
								if (qName.equals("Date")){
									checkdate = str;
								}
								else if (qName.equals("Note")){
									checknote = str;
								}
								else if (qName.equals("Prescription")){
									checkpre = str;
								}
								else if (qName.equals("Appointment")){
									checkap.add(new Appointment(checkdate, checknote, checkpre));
								}
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
		checkap.sort(Comparator.comparing(Appointment::getdate));
		for (int x = 0; x < checkap.size(); x++){
			listModel.addElement(checkap.get(x).getdate());
		}
	}
	//updateappointmentdata still unfix
	public void updateappointmentdata(String f, String l, String ssn, String Adate, String Anote, String Apre) throws Exception{
		
		NodeList firstname , lastname, ssnlist, datelist;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File("doctor.xml"));
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath path = xPathfactory.newXPath();
		
		firstname = (NodeList) path.evaluate("/Data//Doctor/Firstname/text()", doc, XPathConstants.NODESET);
		lastname = (NodeList) path.evaluate("/Data//Doctor/Lastname/text()", doc, XPathConstants.NODESET); 
		ssnlist = (NodeList) path.evaluate("/Data//Doctor/Patients/Patient/ssn/text()", doc, XPathConstants.NODESET);
		datelist = (NodeList) path.evaluate("/Date//Doctor/Patients/Patient/Appointments/Appointment/Date/text()", doc, XPathConstants.NODESET);
		
		for (int i = 0; i < firstname.getLength(); i++){
			if (f.equals(firstname.item(i).getNodeValue()) && l.equals(lastname.item(i).getNodeValue())){
				for (int j = 0; j < ssnlist.getLength(); j++){
					if (ssn.equals(ssnlist.item(j).getNodeValue())){
						Node root = doc.getElementsByTagName("Appointments").item(i);
						NodeList appointment = root.getChildNodes();
						Node note = appointment.item(j).getChildNodes().item(1);
						Node pre = appointment.item(j).getChildNodes().item(2);
						
						note.setTextContent(Anote);
						pre.setTextContent(Apre);
					}
				}
			}
		}
	
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}
}
