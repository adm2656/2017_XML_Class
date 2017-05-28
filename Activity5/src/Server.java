import java.util.ArrayList;

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

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	
	Socket client;
	
	Server (Socket socket){
		this.client = socket;
	}
	
	static int portnumber = 8080;
	static String str;
	static String checkfirst;
	static String checklast;
	static String checktime;
	static String checkPname, checkPssn, checkPaddress, checkPphone, checkPnote;
	static String PName, Pssn, Paddress, Pphone, Pnote;
	static ArrayList<Doctor> checkdc = new ArrayList<Doctor>();
	static ArrayList<Patient> checkpatient = new ArrayList<Patient>();
	static ArrayList<Patient> np = new ArrayList<Patient>();
	
	public static void main (String[] args) throws Exception{
		ServerSocket serversocket = new ServerSocket(portnumber);
		getdc();
		while (true){
			Socket clientsocket = serversocket.accept();
			System.out.println("A client has been connected");
			
			new Thread(new Server(clientsocket)).start(); 
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			PrintWriter serverOut = new PrintWriter(client.getOutputStream(), true);
			BufferedReader serverIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String inputLine;

			int i = checkdc.size();
			serverOut.write("Welcome, There are " + checkdc.size() + " doctors;");
			
			for(int j = 0; j < i; j++){
				String first = checkdc.get(j).getFirst();
				String last = checkdc.get(j).getLast();
				try{
					getpatientdata(first, last);
				}catch (Exception e2){
					e2.printStackTrace();
				}
				serverOut.write(" - Dr." + first + " " + last + "(" + checkpatient.size() + " patients waiting);");
				checkpatient.clear();
			}
			serverOut.write("Which doctor do you want to register?");
			serverOut.println();
			
			if ((inputLine = serverIn.readLine()) != null){
				String[] choosedc = inputLine.split(" ");
				if (choosedc.length == 2){
					getdc();
					int count = 0;
					for (int k = 0; k < checkdc.size(); k++){
						if (choosedc[0].equals(checkdc.get(k).getFirst()) && choosedc[1].equals(checkdc.get(k).getLast())){
							serverOut.write("Dr." + choosedc[0] + " " + choosedc[1] + " has been selected. Please enter your name");
							serverOut.println();
							count++;
							PName = serverIn.readLine();
							if(PName != null){
								serverOut.write("Please enter your SSN");
								serverOut.println();
								Pssn = serverIn.readLine();
								if (Pssn != null){
									serverOut.write("Please enter your Address");
									serverOut.println();
									Paddress = serverIn.readLine();
									if(Paddress != null){
										serverOut.write("Please enter your Phone");
										serverOut.println();
										Pphone = serverIn.readLine();
										Pnote = "";
										insertPatientdata(choosedc[0], choosedc[1], PName, Pssn, Paddress, Pphone, Pnote);
										serverOut.write("Register success (Name :" + PName + ", SSN :" + Pssn + ", address :" + Paddress + ", phone :" + Pphone + ")");
										serverOut.println();
										break;
										}
									}
								}else{
									serverOut.write("Invalid input");
									serverOut.println();
								}
							} 
					}
					if (count == 0){
						serverOut.write("There's no doctor called " + choosedc[0] + " " + choosedc[1]);
						serverOut.println();
					}
				}else{
					serverOut.write("Invalid input");
					serverOut.println();
				}
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
    public static void getdc() throws Exception{
    	
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
    
	public static void getpatientdata(String f, String l) throws Exception{
		
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

	public static void insertPatientdata(String f, String l, String name, String ssn, String address, String phone, String note)throws Exception{
		
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
				Patientname.appendChild(doc.createTextNode(name));
				newpatient.appendChild(Patientname);
		
				Element PatientSSN = doc.createElement("SSN");
				PatientSSN.appendChild(doc.createTextNode(ssn));
				newpatient.appendChild(PatientSSN);
		
				Element PatientAddress = doc.createElement("Address");
				PatientAddress.appendChild(doc.createTextNode(address));
				newpatient.appendChild(PatientAddress);
		
				Element PatientPhone = doc.createElement("Phone");
				PatientPhone.appendChild(doc.createTextNode(phone));
				newpatient.appendChild(PatientPhone);
				
				Element PatientNote = doc.createElement("Note");
				PatientNote.appendChild(doc.createTextNode(note));
				newpatient.appendChild(PatientNote);
				}
			}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}

}
