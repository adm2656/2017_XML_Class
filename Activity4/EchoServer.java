import java.net.*;

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

public class EchoServer {
	
	static int portnumber = 8888;
	static String str;
	static String checkfirst;
	static String checklast;
	static String checktime;
	static String checkPname, checkPssn, checkPaddress, checkPphone, checkPnote;
	static String PName, Pssn, Paddress, Pphone, Pnote;
	static ArrayList<Doctor> checkdc = new ArrayList<Doctor>();
	static ArrayList<Patient> checkpatient = new ArrayList<Patient>();
	static ArrayList<Patient> np = new ArrayList<Patient>();
	
    public static void main(String[] args) throws IOException {
        
        try (
            ServerSocket serverSocket = new ServerSocket(portnumber);

            //Server invokes serverSocket.accept to wait for a client
            //when a client connects, serverSocket.accept returns a new socket
            //that the server can use to communicate with the client
            Socket clientSocket = serverSocket.accept();

            //To use the socket, first create a PrinterWriter and a BufferedReader for new clientSocket
            PrintWriter serverOut = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("A client has connected.");
            String inputLine;

            try{
            	getdc();
            }catch (Exception e1) {
				// TODO Auto-generated catch block
            	e1.printStackTrace();
            }
            
            int count = 0;
            serverOut.println("Please enter doctor's firstname and lastname (split by space)");
       		if ((inputLine = serverIn.readLine()) != null){
       			String[] name = inputLine.split(" ");
       			for (int i = 0; i < checkdc.size(); i++){
       				if (name[0].equals(checkdc.get(i).getFirst()) && name[1].equals(checkdc.get(i).getLast())){
       					count++;
       		            serverOut.println("What do you want to do? view or edit (view/edit)");
       		            inputLine = serverIn.readLine();
       		            switch (inputLine){
       		            	case "view" :
       		            		try {
       		            			getpatientdata(checkdc.get(i).getFirst(), checkdc.get(i).getLast());
       		            		} catch (Exception e) {
       		            			// TODO Auto-generated catch block
       		            			e.printStackTrace();
       		            		}
       		            		serverOut.println("Dr." + checkdc.get(i).getFirst() + "," + checkdc.get(i).getLast() +  " has " + checkpatient.size() + " patient(s)" + ". Do you want to see patient's data? (yes/no)");
       		            		String choose = serverIn.readLine();
       		            		switch(choose){
       		            			case "yes" :
       		            				serverOut.println("There is/are " + checkpatient.size() + " patient(s)" + ", Which one do you want to see?");
       		            				String p = serverIn.readLine();
       		            				int patient = Integer.parseInt(p);
       		            				if (patient > checkpatient.size()){
       		            					serverOut.println("invalid choose");
       		            				}
       		            				else if (patient == 0){
       		            					serverOut.println("invalid choose");
       		            				}
       		            				else{
       		            					serverOut.println("Patient name : " + checkpatient.get(patient-1).getname() + ", Patient SSN : " + checkpatient.get(patient-1).getSsn() + ", Patient phone : " + checkpatient.get(patient-1).getphone());
       		            				}
       		            				break;
       		            			case "no" :
       		            				serverOut.print("See you next time");
       		            				serverOut.flush();
       		            				clientSocket.close();
       		            				break;
       		            			default :
       		            				serverOut.println("invalid input");
       		            				serverOut.flush();
       		            				clientSocket.close();
       		            		}
       		            		break;
       		            	case "edit" :
       		            		try {
       		            			getpatientdata(checkdc.get(i).getFirst(), checkdc.get(i).getLast());
       		            		} catch (Exception e) {
       		            			// TODO Auto-generated catch block
       		            			e.printStackTrace();
       		            		}
       		            		serverOut.println("Dr." + checkdc.get(i).getFirst() + "," + checkdc.get(i).getLast() +  " has " + checkpatient.size() + " patient(s)" + ". You want to add, edit or delete (add/edit/delete)");
       		            		String operate = serverIn.readLine();
       		            		switch(operate){
       		            			case "add" :
       		            				serverOut.println("Please enter Patient's Name, SSN, address, Phone, note (split by space)");
       		            				String[] pinput = serverIn.readLine().split(" ");
       		            				if (pinput.length != 5){
       		            					serverOut.println("invalid input");
       		            					serverOut.flush();
       		            					clientSocket.close();
       		            				}
       		            				else{
       		            					PName = pinput[0];
       		            					Pssn = pinput[1];
       		            					Paddress = pinput[2];
       		            					Pphone = pinput[3];
       		            					Pnote = pinput[4];
       		            					np.add(new Patient(PName, Pssn, Paddress, Pphone, Pnote));
       		            					try {
												insertPatientdata(checkdc.get(i).getFirst(), checkdc.get(i).getLast());
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
       		            					serverOut.println("add success");
       		       		            		serverOut.flush();
       		       		            		clientSocket.close();
       		            				}
       		            				break;
       		            			case "edit" :
       		            				serverOut.println("Which one do you want to edit? ");
       		            				String editchoose = serverIn.readLine();
       		            				int editpatient = Integer.parseInt(editchoose);
       		            				if (editpatient > checkpatient.size() || editpatient == 0){
       		            					serverOut.println("invalid input");
       		            					serverOut.flush();
       		            					clientSocket.close();
       		            				}
       		            				else {
       		            					serverOut.println("Please enter updated Patient's name, address, phone, note, you cannot change patient's SSN in this application (split by space)");
       		            					String[] updatepatient = serverIn.readLine().split(" ");
       		            					if (updatepatient.length != 4){
          		            					serverOut.println("invalid input");
           		       		            		serverOut.flush();
           		       		            		clientSocket.close();
       		            					}
       		            					else{
       		            						String doctorfirst = checkdc.get(i).getFirst();
       		            						String doctorlast = checkdc.get(i).getLast();
       		            						String npname = updatepatient[0];
       		            						String ssn = checkpatient.get(editpatient-1).getSsn();
       		            						String address = updatepatient[1];
       		            						String phone = updatepatient[2];
       		            						String note = updatepatient[3];
       		            						try {
       		            							updatepatient(doctorfirst, doctorlast, editpatient-1, npname, ssn, address, phone, note);
       		            						} catch (Exception e) {
       		            							// TODO Auto-generated catch block
       		            								e.printStackTrace();
       		            						}
       		            						serverOut.println("edit success");
       		            						serverOut.flush();
       		            						clientSocket.close();
       		            					}
       		            				}
       		            				break;
       		            			case "delete" :
       		            				serverOut.println("Whick one do you want to delete?");
       		            				String deletechoose = serverIn.readLine();
       		            				int delete = Integer.parseInt(deletechoose);
       		            				if (delete > checkpatient.size() || delete == 0){
       		    		            		serverOut.println("invalid input");
       		       		            		serverOut.flush();
       		       		            		clientSocket.close();
       		            				}
       		            				else{
       		            					String doctorfirst = checkdc.get(i).getFirst();
       		            					String doctorlast = checkdc.get(i).getLast();
       		            					try {
												deletepatientdata(doctorfirst, doctorlast, delete-1);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
   		            						serverOut.println("delete success");
   		            						serverOut.flush();
   		            						clientSocket.close();
       		            					}
       		            			default :
       		            				serverOut.println("invalid operation");
       		            				serverOut.flush();
       		            				clientSocket.close();
       		            		}
       		            		break;	
       		            	default :
       		            		serverOut.println("invalid input");
       		            		serverOut.flush();
       		            		clientSocket.close();
       		            }
       				}
       				else if (name.length < 2 || name[1] == null){
       					serverOut.println("invalid input");
       				}
       			}
   				if (count == 0) {
   					serverOut.println("There is no doctor called " + name[0] + "," + name[1]);
   				}
       		}
       		else{
       			serverOut.flush();
       			clientSocket.close();
       		}
       		checkdc.clear();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portnumber + " or listening for a connection");
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

	public static void updatepatient(String f, String l, int c, String PName, String Pssn, String Paddress, String Pphone, String note) throws Exception{
		
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
	
	public static void insertPatientdata(String f, String l)throws Exception{
		
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
				Patientname.appendChild(doc.createTextNode(np.get(0).getname()));
				newpatient.appendChild(Patientname);
		
				Element PatientSSN = doc.createElement("SSN");
				PatientSSN.appendChild(doc.createTextNode(np.get(0).getSsn()));
				newpatient.appendChild(PatientSSN);
		
				Element PatientAddress = doc.createElement("Address");
				PatientAddress.appendChild(doc.createTextNode(np.get(0).getAddress()));
				newpatient.appendChild(PatientAddress);
		
				Element PatientPhone = doc.createElement("Phone");
				PatientPhone.appendChild(doc.createTextNode(np.get(0).getphone()));
				newpatient.appendChild(PatientPhone);
				
				Element PatientNote = doc.createElement("Note");
				PatientNote.appendChild(doc.createTextNode(np.get(0).getnote()));
				newpatient.appendChild(PatientNote);
				}
			}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("doctor.xml"));
		
		transformer.transform(source, result);
	}

	public static void deletepatientdata(String f, String l, int c) throws Exception{
		
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
	
}	
