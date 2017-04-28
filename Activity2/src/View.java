import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class View {
	
	
	SAXParserFactory factory;
	SAXParser saxParser;
	ArrayList<PM> pmList=new ArrayList<PM>();
	DefaultListModel listModel;
	JList list;
	JScrollPane jsp;
	String str, site, county, pm, time;
	
	private JFrame frmActivity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frmActivity.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public View() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frmActivity = new JFrame();
		frmActivity.setTitle("Activity2");
		frmActivity.setBounds(100, 100, 425, 652);
		frmActivity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmActivity.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u7A7A\u6C23\u54C1\u8CEA");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("夹发砰", Font.PLAIN, 22));
		label.setBounds(145, 6, 110, 54);
		frmActivity.getContentPane().add(label);
		
		
		//new DefaultListModel		
		listModel = new DefaultListModel();
		
		readXML();
		addToJList();
		
		list = new JList(listModel);
		list.setVisibleRowCount(5);
		list.setBounds(145, 62, 110, 461);
		
		//add to Jlist
		
		jsp = new JScrollPane(list);
		jsp.setBounds(145, 62, 110, 461);
		frmActivity.getContentPane().add(jsp);

		//implements to method
		
		JButton btnNewButton = new JButton("\u67E5\u770B");
		btnNewButton.setFont(new Font("夹发砰", Font.PLAIN, 22));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//msgbox
				int index=list.getSelectedIndex();
				JOptionPane.showMessageDialog(null, "城市：" + pmList.get(index).county + "\n地點：" + pmList.get(index).site + "\n數值：" + pmList.get(index).air);
				
			}
		});
		btnNewButton.setBounds(145, 547, 110, 35);
		frmActivity.getContentPane().add(btnNewButton);
	}
	
	public void readXML() throws Exception{
		factory=SAXParserFactory.newInstance();
		saxParser=factory.newSAXParser();
		
		//Look ppt and read XML from website to Arraylist
		
		DefaultHandler handler = new DefaultHandler(){

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				// TODO Auto-generated method stub
				super.startElement(uri, localName, qName, attributes);
			}
			

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				// TODO Auto-generated method stub
				super.characters(ch, start, length);
				str = new String(ch, start, length);
				}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				// TODO Auto-generated method stub
				super.endElement(uri, localName, qName);
				
				if (qName.equals("Site")){
					site = str;
				}
				else if (qName.equals("county")){
					county = str;
				}
				else if (qName.equals("PM25")){
					pm = str;
				}
				else if (qName.equals("DataCreationDate")){
					time = str;
				}
				if (qName.equals("Data")){
					pmList.add(new PM(site, county, pm, time));
				}
			}	
		};
		
		saxParser.parse(new InputSource(new URL("http://opendata.epa.gov.tw/ws/Data/ATM00625/?%24skip=0&%24top=1000&format=xml").openStream()), handler);

	}
	
	public void addToJList(){
		//insert data to DefaultListModel from arraylist
		for (int x = 0; x < pmList.size(); x++){
			listModel.addElement(pmList.get(x).getSite());
		}
	}
}
