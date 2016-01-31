import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


public class ServerRPC implements KeyValue {
	
	private String myLogFile;
	
	//the key value pair
	private Hashtable<String, String> valueTable;
	
	public ServerRPC(String logFile) {
		this.myLogFile = logFile;
		
		this.valueTable = 
				new Hashtable<String, String>();
	}
	
	public static void main(String[] args) {
		String log_msg = "";  //for logging
		String log_file = "ServerPRC_log" + "_" + new SimpleDateFormat("yyyy-MM-dd_hhmmss").format(new Date()) + ".txt";
		
        
		
		int port;
		if(args.length == 1 ){                                                   //user provides registry port
			port = Integer.parseInt(args[0]);
			log_msg = "Use provided port number for rmiregistry: " + port;
			Logger.log(log_msg, log_file);
		}
		else {
			log_msg = "Use default port number '1099' for rmiregistry...";
			Logger.log(log_msg, log_file);
			port = 1099;
		}
		
		try {
			
			log_msg = "Creating server...";
			Logger.log(log_msg, log_file);
			ServerRPC obj = new ServerRPC(log_file);                             //create the remote object
			log_msg = "Creating server done";
			Logger.log(log_msg, log_file);
			
			KeyValue stub = (KeyValue) UnicastRemoteObject.exportObject(obj, 0);  //remote object must be exported to the Java RMI runtime
			 
			
			/*
			 * Bind the remote object's stub in the registry
			 * Once a remote object is registered on the server, callers can look up the object by name, 
			 * obtain a remote object reference, and then invoke remote methods on the object.
			 */
			log_msg = "Binding remote object's stub...";
			Logger.log(log_msg, log_file);
            Registry registry = LocateRegistry.getRegistry(port);    //default port 1099 if you start rmiregistry.exe without any parameter
            /*
            if(registry.lookup("KeyValue") != null) {
            	System.out.println("Already bond, unbind first...");
            	registry.unbind("KeyValue");
            }*/
        	registry.rebind("KeyValue", stub);                     //bind stub to port 1099
        	log_msg = "Binding done.";
        	Logger.log(log_msg, log_file);
            
            System.out.println("Server ready");
            
            
		} catch (Exception e) {
			log_msg = e.toString();
			Logger.log(log_msg, log_file);
			System.exit(-1);
		}
	}


	@Override
	public String work(String clientMsg) throws RemoteException {
		String result = "";  //the message is going to return to the client
		
		Logger.log("Got the command: " + clientMsg, this.myLogFile);
		
		
		String command = "";
		String key = "";
		String value = "";
		//validate the client message
		int length = clientMsg.split(" ").length;
		//length == 1, Malformed Request!
		if (length == 1) {
			result = "Error! Malformed Request! The request is: " + clientMsg;
			Logger.log(result, this.myLogFile);
			return result;
		} else if (length == 2) {                                                    //length == 2, the first must be GET or DELETE
			command = clientMsg.split(" ")[0];
			if (command.compareTo("GET") == 0 || command.compareTo("DELETE") == 0) {
				key = clientMsg.split(" ")[1];
			} else {
				result = "Error! Malformed Request! The request is: " + clientMsg;
				Logger.log(result, this.myLogFile);
				return result;
			}
		} else if (length == 3) {                                                    //length == 3, the first must be PUT
			command = clientMsg.split(" ")[0];
			if (command.compareTo("PUT") == 0) {
				key = clientMsg.split(" ")[1];
				value = clientMsg.split(" ")[2];
			} else {
				result = "Error! Malformed Request! The request is: " + clientMsg;
				Logger.log(result, this.myLogFile);
				return result;
			}
		}
		
		
		switch (command) {
			case "PUT":
				if (this.valueTable.containsKey(key)) {
					result = "The server has the key pair, can't PUT again.";
					Logger.log(result, this.myLogFile);
				} else {
					this.valueTable.put(key, value);
					result = "PUT " + key + ", " + value + " SUCESS.";
					Logger.log(result, this.myLogFile);
				}
				break;
			case "GET":
				result = this.valueTable.get(key);
				if (result == null) {
					result = "There is no key: " + key + " in the server store.";
					Logger.log(result, this.myLogFile);
				} else {
					result = "Get the value: " + result + " for the key: " + key;
					Logger.log(result, this.myLogFile);
				}
				break;
			case "DELETE":
				result = this.valueTable.remove(key);
				if (result == null) {
					result = "There is no key: " + key + " in the server store.";
					Logger.log(result, this.myLogFile);
				} else {
					result = "Removed the key: " + key;
					Logger.log(result, this.myLogFile);
				}
				break;
			default:
				result = "Error! Malformed Request! The request is: " + clientMsg;
				Logger.log(result, this.myLogFile);
				break;	
		} //end of switch
		
		return result;
	}
	
	
	

}
