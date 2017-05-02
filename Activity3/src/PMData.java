
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PMData extends Thread{
	ArrayList<PM> list;
    Random ran;
    int index;
    String time, updatetime;
    String city, pm;
    String keyword;
    PMData(){
		list = new ArrayList<PM>();
    }
    public void run(){
    	try {
    		index++;
            ran = new Random();
            int update = ran.nextInt(2001) + 6000;
    		list.clear();
    		readXML(index);
    		updatetime = getDateTime();
        	System.out.println("第" + index + "次更新資料");
    		Thread.sleep(update);
    		if (index < 8){
    			this.run();
    		}
    		else{
    			Thread.interrupted();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public String search(String keyword){
    	int i;
    	String data = "";
    	for (i = 0; i < list.size(); i++){
    		if (keyword.equals(list.get(i).getCity())){
    			data = list.get(i).getPmNum();
    		}
    	}
    	return this.getDateTime() + "->" + keyword + "的空氣品質為:" + data + "(資料更新時間:"+ updatetime +")";
    }
    
    public String getDateTime(){
    	Date date = new Date();
    	time = date.toString();
    	return time;
    }
    
    void readXML(int index) throws SAXException, IOException, ParserConfigurationException{
    	SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxparser = factory.newSAXParser();
		String filename = ("pm2.5" + index + ".xml").toString();
		
		DefaultHandler handler = new DefaultHandler(){
			String str;
			public void characters(char[] ch, int start, int length) throws SAXException {
				str = new String(ch,start,length);
			}

			public void endElement(String uri, String lName, String qName) throws SAXException {
				if (qName.equals("city")){
					city = str;
				}
				if (qName.equals("PM2.5")){
					pm = str;
				}
				else if (qName.equals("data")){
					list.add(new PM(city, pm));
				}
			}

			public void startElement(String uri, String lName, String qName, Attributes attributes)
					throws SAXException {
				
			}
			
		};
		File file = new File(filename);
		saxparser.parse(file, handler);

    }
}
