import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client{
	static String hostname = "127.0.0.1";
	static int portnumber = 8080;
	
	public static void main (String[] args) throws Exception{
		
		try(
			Socket client = new Socket(hostname, portnumber);
			PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
			BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			){
			
			String userinput;
			String[] hellomsg = clientIn.readLine().split(";");
			
			for (int i = 0; i < hellomsg.length; i++){
				System.out.println(hellomsg[i]);
			}
			
			while ((userinput = userIn.readLine()) != null){
				if(userinput.equals("")){
					break;
				}
				clientOut.println(userinput);
				System.out.println(clientIn.readLine());
			}
		}catch (IOException e){
			System.err.println("Couldn't get I/O for the connection to " + hostname);
			System.exit(1);
		}
	}
}
