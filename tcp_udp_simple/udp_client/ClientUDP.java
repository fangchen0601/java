
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.net.*;

public class ClientUDP {
	
	private static final int RETRYCOUNT = 3;
    private static final long SLEEPTIME = 500;   //sleep time between retry, in millisecond
    private static final String DEFALT_HOSTNAME = "127.0.0.1";
    private static final int DEFAULT_PORT = 9997;
    
	public static void main(String[] args) {
		/*
         * for logging
         */
        String log_msg = "";                      
        String log_file = "ClientUDP_log";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
        log_file = log_file + "_" + df.format(new Date()) + ".txt";
        
        String hostname;
        int port;
    
        if(args.length == 0){
        	log_msg = "Using default server name and port since user did not input any parameter";
        	System.out.println(log_msg);
        	Logger.log(log_msg, log_file);
            hostname = DEFALT_HOSTNAME;
            port = DEFAULT_PORT;
        }
        else
        {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }
        log_msg = "Will communicate with server: " + hostname + " Port: " + port + " with UDP";
        System.out.println(log_msg);
        Logger.log(log_msg, log_file);
        
        InetAddress address = null; 			//try to resolve the server hostname, and create InetAddress
        try {                                                      
        	address = InetAddress.getByName(hostname);
        }
        catch (UnknownHostException e ){
        	System.out.println(e);
        	Logger.log(e.toString(), log_file);
        	System.exit(-1);
        }
        System.out.println("InetAddress is: " + address);

        Scanner input = null;
        DatagramSocket socket = null;            
        try {
        	socket  = new DatagramSocket();     //client socket
        	
        	input = new Scanner(new File("./script.txt"));  //read operations from scirpt file
        	
        	while(input.hasNextLine()){
        		Thread.sleep(SLEEPTIME);
        		String line = input.nextLine();
        		log_msg = "Read this line from file: " + line;
                System.out.println(log_msg);
                Logger.log(log_msg, log_file);
                
                for (int i = 0; i < RETRYCOUNT; i ++ ) {
                	//send
                	byte[] sendData = new byte[1024];
                	sendData = line.getBytes();
                	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                	log_msg = "Sending message to server: " + line;
                	System.out.println(log_msg);
                	Logger.log(log_msg, log_file);
                	socket.send(sendPacket);
                
                	//receive
                	log_msg = "Receiving from server...";
                	System.out.println(log_msg);
                	Logger.log(log_msg, log_file);
                	byte[] receiveData = new byte[1024];
                	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                	socket.setSoTimeout(10000);  //set time out 
                	try {
                		socket.receive(receivePacket);
                	}
                	catch (Exception e) {   //if something error during receiving, retry
                		if(i < RETRYCOUNT -1)
                			log_msg = "Exception in receiving from server: " + e.toString() + ".  Will retry sending and receving";
                		else 
                			log_msg = "Reaching  maximum retry count, go to next line of the script file.";
                		System.out.println(log_msg);
                		Logger.log(log_msg, log_file);
                		continue;
                	}
                	
                	
                	String msgFromServer = new String(receivePacket.getData()).trim();
                	if(msgFromServer == null || msgFromServer.equals("")){
                		log_msg = "Do not receive any mssage from server, retry...";
                    	System.out.println(log_msg);
                    	Logger.log(log_msg, log_file);
                    	Thread.sleep(SLEEPTIME);
                    	continue;
                	}
                	else                     //get something from server which is not null or empty, display it, and break retry
                	{
                		log_msg = "Get message from server: " + msgFromServer;
                    	System.out.println(log_msg);
                    	Logger.log(log_msg, log_file);
                		break; 
                	}
                } //end of for retry
        		
        	} //end of while
        	log_msg = "End of the file, exit program.";
            System.out.println(log_msg);
            Logger.log(log_msg, log_file);
        }
        catch (Exception e) {
        	System.out.println(e.toString());
        	Logger.log(e.toString(), log_file);
        	System.exit(-1);
        }
        finally 
        {
        	if(socket!= null)
        		socket.close();
        	if(input!=null)
        		input.close();
        }
        
        
	} //end of main

} //end of class
