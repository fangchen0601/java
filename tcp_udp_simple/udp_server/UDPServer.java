import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * The server class based on UDP.
 * 
 * @author LAN
 * @version 1.0
 */
public class UDPServer {
	
	/** This is the default port number. */
	private static final int DEFAULT_PORT = 9997;
	
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
		
		DatagramSocket serverSocket = null;
		
		int portNumber = DEFAULT_PORT;
		if(theArgs.length == 1) {
			portNumber = Integer.parseInt(theArgs[0]);
		} 
		if (theArgs.length > 1) {
			System.out.println("Please run without parameter or only 1 parameter!");
			System.exit(1);
		}
		
		try {
			serverSocket = new DatagramSocket(portNumber);
			
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			while (true) {
				receiveData = new byte[1024];
				
				DatagramPacket receivePacket = 
						new DatagramPacket(receiveData, receiveData.length);
				
				System.out.println("Waiting for datagram packet...");
				
				serverSocket.receive(receivePacket);
				
				String clientRequest = new String(receivePacket.getData()).trim();
				
				InetAddress clientAddress = receivePacket.getAddress();
				portNumber = receivePacket.getPort();
				
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
				
				sendData = responseMsg.getBytes();
				DatagramPacket sendPacket = 
						new DatagramPacket(sendData, sendData.length, 
								clientAddress, portNumber);
				serverSocket.send(sendPacket);
				
				Logger.log(responseMsg, logFileName);
			}
		} catch (SocketException e) {
			System.err.println("UDP Port " + portNumber + " is occupied!");
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
