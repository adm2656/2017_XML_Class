import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EchoClient {
	
	static String hostname = "127.0.0.1";
	static int portnumber = 8888;
	
    public static void main(String[] args) throws IOException {

        try (
            //create a new Socket echoSocket
            Socket echoSocket = new Socket(hostname, portnumber);

            //create a new PrintWriter out.
            //True tells PrintWriter to send out the line rather than to wait for the buffer to become full
            PrintWriter clientOut = new PrintWriter(echoSocket.getOutputStream(), true);
            
            //Create a new BufferedReader in to read from the Socket echoSocket via intermediate InputStreamReader
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            //sending and receiving using echoSocket
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        	){
            //Read in lines of text from the keyboard, i.e. System.in
        	
            String userInput;
            
            System.out.println(clientIn.readLine());
            
            while ((userInput = stdIn.readLine()) != null) {
            	if (userInput.equals("")){
            		break;
            	}
            	clientOut.println(userInput);
                System.out.println(clientIn.readLine());
                
            }
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
            System.exit(1);
        }
    }
}
