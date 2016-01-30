import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * The server class based on TCP.
 * 
 * @author LAN
 * @version 1.0
 */
public class TCPServer {
	
	/** This is the default port number. */
	private static final int DEFAULT_PORT = 9999;
	
	/**
	 * The driver method.
	 * 
	 * @param theArgs the command line input.
	 * @throws IOException
	 */
	public static void main(String[] theArgs) throws IOException {
		
		String logFileName = "ServerTCP_log";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
        logFileName = logFileName + "_" + df.format(new Date()) + ".txt";
		
		Hashtable<String, String> valueTable = 
				new Hashtable<String, String>();
		
		ServerSocket serverSocket = null;
		
		int portNumber = DEFAULT_PORT;
		if(theArgs.length == 1) {
			portNumber = Integer.parseInt(theArgs[0]);
		} 
		if (theArgs.length > 1) {
			System.out.println("Please run without parameter or only 1 parameter!");
			System.exit(1);
		}
		
		try {
			serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			
			while (true) {
				try {
					System.out.println("Waiting for Client...");
					clientSocket = serverSocket.accept();
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					
					in = new BufferedReader(
									new InputStreamReader(clientSocket.getInputStream()));
					
					String clientRequest;
					while ((clientRequest = in.readLine()) != null ) {
						InetAddress clientAddress = clientSocket.getInetAddress();
						int length = clientRequest.length();
						
						String clientMsg = "\"" + clientRequest + "\"";
						
						String receiveMsg = clientMsg + " Received request of length " 
						+ length + " from <" + clientAddress + ">:<" + portNumber + ">";
						
						Logger.log(receiveMsg, logFileName);
						
						Scanner scanner = new Scanner(clientRequest);
						String operation = scanner.next();
						String responseMsg = "";
						
						if (operation.equals("PUT")) {
							responseMsg = putOperation(scanner, valueTable, clientMsg);
						} else if (operation.equals("GET")) {
							responseMsg = getOperation(scanner, valueTable, clientMsg);
						} else if (operation.equals("DELETE")) {
							responseMsg = deleteOperation(scanner, valueTable, clientMsg);
						} else {
							responseMsg = clientMsg + " Error! Malformed Request!";
						}
						
						out.println(responseMsg);
						Logger.log(responseMsg, logFileName);
					}
				} catch (IOException e) {
					System.err.println("Accept failed.");
					System.exit(1);
				} finally {
					in.close();
					out.close();
					clientSocket.close();	
				}
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port:" + portNumber);
			System.exit(1); 
		} finally {
			serverSocket.close();
		}
	}
	
	/**
	 * The PUT operation. 
	 * All the client request, not as "PUT Key Value"
	 * will be viewed as a malformed request.
	 * 
	 * @param theIn the Scanner of the client request.
	 * @param theTable the HashTable of <key, value>.
	 * @param theMsg the client request.
	 * @return a String the response message.
	 */
	public static String putOperation
	(Scanner theIn, Hashtable<String, String> theTable, String theMsg) { 
		if (!theIn.hasNext()) {
			theMsg = theMsg + " Error! Malformed Request!";
		} else {
			String key = theIn.next();
			if (!theIn.hasNext()) {
				theMsg = theMsg + " Error! Malformed Request!";
			} else {
				String value = theIn.next();
				if (theIn.hasNext()) {
					theMsg = theMsg + " Error! Malformed Request!";
				} else {
					theTable.put(key, value);
					theMsg = theMsg + " Success!";
				}
			}
		}
		return theMsg;
	}

	/**
	 * The GET operation.
	 * All the client request, not as "GET Key"
	 * will be viewed as a malformed request.
	 * 
	 * @param theIn the Scanner of the client request.
	 * @param theTable the HashTable of <key, value>.
	 * @param theMsg the client request.
	 * @return a String the response message.
	 */
	public static String getOperation
	(Scanner theIn, Hashtable<String, String> theTable, String theMsg) {
		if (!theIn.hasNext()) {
			theMsg = theMsg + " Error! Malformed Request!";
		} else {
			String key = theIn.next();
			String value = "";
			if (theIn.hasNext()) {
				theMsg = theMsg + " Error! Malformed Request!";
			} else { 
				if (theTable == null || !theTable.containsKey(key)) {
					theMsg = theMsg + " The key doesn't exist!";
				} else {
					value = theTable.get(key);
					theMsg = theMsg + " Value: " + value;
				}
			}
		}
		return theMsg;
	}
	
	/**
	 * The DELETE operation.
	 * All the client request, not as "DELETE Key"
	 * will be viewed as a malformed request.
	 * 
	 * @param theIn the Scanner of the client request.
	 * @param theTable the HashTable of <key, value>.
	 * @param theMsg the client request.
	 * @return a String the response message.
	 */
	public static String deleteOperation
	(Scanner theIn, Hashtable<String, String> theTable, String theMsg) {
		if (!theIn.hasNext()) {
			theMsg = theMsg + " Error! Malformed Request!";
		} else {
			String key = theIn.next();
			if (theIn.hasNext()) {
				theMsg = theMsg + " Error! Malformed Request!";
			} else { 
				if (theTable == null || !theTable.containsKey(key)) {
					theMsg = theMsg + " The key doesn't exist!";
				} else {
					theTable.remove(key);
					theMsg = theMsg + " Success!";
				}
			}
		}
		return theMsg;
	}
}
