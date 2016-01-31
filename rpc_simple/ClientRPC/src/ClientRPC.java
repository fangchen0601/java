import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.*;
import java.util.*;

public class ClientRPC {
	public static final int SLEEP_TIME = 1000;   //sleep time in milliseconds
	
	public static final String SCRIPT_FILE_NAME = "./script.txt";
	
			
	public static void main(String[] args) {
		/*
         * for logging
         */
        String log_msg = "";                      
        String log_file = "ClientRPC_log";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
        log_file = log_file + "_" + df.format(new Date()) + ".txt";
        
        String host = "";
        if(args.length != 1) {
        	log_msg = "Server IP is not provided as a parameter, use default ip address: 127.0.0.1";
        	Logger.log(log_msg, log_file);
        	host = "127.0.0.1";
        } else {
        	host = args[0];
        	log_msg = "Will call server: " + host;
        	Logger.log(log_msg, log_file);
        }
        
        try {
            Registry registry = LocateRegistry.getRegistry(host);     //get registry
            KeyValue stub = (KeyValue) registry.lookup("KeyValue");   //obtain the stub for the remote object from the server's registry
            
            //String response = stub.work("abcdedf");
            
            //System.out.println("response: " + response);
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
        
        System.out.println("*****************************************");
        Scanner input = null;
        try {
            input = new Scanner(new File(SCRIPT_FILE_NAME));    //read the file 
            while(input.hasNextLine()){ 
            	Thread.sleep(SLEEP_TIME);
            	String line = input.nextLine();
            	log_msg = "Read a line from script file: " + line;
            	Logger.log(log_msg, log_file);
            	//String[] commands = line.split(",");
            	//log_msg = "Got " + commands.length + " concurrent commands and will call the server concurrently...";
            	
            	try {
                    Registry registry = LocateRegistry.getRegistry(host);     //get registry
                    KeyValue stub = (KeyValue) registry.lookup("KeyValue");   //obtain the stub for the remote object from the server's registry
                    
                    log_msg = "Send to server: " + line;
                	Logger.log(log_msg, log_file);
                	String response = stub.work(line);                     //invoke remote method
                	
                	log_msg = "Get response from server: " + response;
                	Logger.log(log_msg, log_file);
                	
                    
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                    e.printStackTrace();
                    System.exit(-1);
                }
            	
            			
            	System.out.println("*****************************************");
            	
            	
            	} //end of while
            }//try
        catch(Exception e) {
        	Logger.log(e.getMessage(), log_file); System.exit(-1);
        	}
        finally {
			input.close();
			}    
        }//end of main
		
}//class
