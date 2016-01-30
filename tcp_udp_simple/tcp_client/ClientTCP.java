import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;



public class ClientTCP {

    private static final int RETRYCOUNT = 3;
    private static final long SLEEPTIME = 500;   //sleep time between retry, in millisecond
    private static final String DEFALT_HOSTNAME = "127.0.0.1";
    private static final int DEFAULT_PORT = 9999;

	public static void main(String[] args) {
        /*
         * for logging
         */
        String log_msg = "";                      
        String log_file = "ClientTCP_log";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
        log_file = log_file + "_" + df.format(new Date()) + ".txt";
        
        String hostname;
        int port;
    
        if(args.length == 0){                      //if use does not provide any parameter when running, give him a default server and port
        	log_msg = "Using default server name and port since user did not input any parameter";
        	System.out.println(log_msg);
        	Logger.log(log_msg, log_file);
            hostname = DEFALT_HOSTNAME;
            port = DEFAULT_PORT;
        }
        else                                       //the command takes 2 parameters, one is server name, one is port
        {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }
        log_msg = "Will communicate with server: " + hostname + " Port: " + port;
        System.out.println(log_msg);
        Logger.log(log_msg, log_file);
        
        Socket s = null;
        try {  											//to see if the server and port is on-line, if not, exist program
        	s = new Socket(hostname, port);
        }
        catch (Exception e)
        {
        	log_msg = e.getMessage();
        	System.out.println(log_msg);
        	Logger.log(log_msg, log_file);
        	System.exit(-2);
        }
        finally
        {
        	if(s == null) {System.exit(-2);}   
        	else {
        		try {s.close();}
        		catch(Exception e){
        			log_msg = e.getMessage();
                	System.out.println(log_msg);
                	Logger.log(log_msg, log_file);
                	System.exit(-2);
        		}
        	}
        }
        
        try {
            Scanner input = new Scanner(new File("./script.txt"));    //read the file 
            while(input.hasNextLine()){
            	Thread.sleep(SLEEPTIME);
                String line = input.nextLine();      				//read one line from script
                log_msg = "Read this line from file: " + line;
                System.out.println(log_msg);
                Logger.log(log_msg, log_file);
                
                Client c = new Client(hostname, port); 
                
                for (int i = 0; i < RETRYCOUNT; i ++ ) {    			//the re-try logic
                    log_msg = "Sending message to server: " + line;
                    System.out.println(log_msg);
                    Logger.log(log_msg, log_file);
                    c.sendToServer(line);								//SendToServer
                
                    log_msg = "Receiving from server...";
                    System.out.println(log_msg);
                    Logger.log(log_msg, log_file);
                    String receivedMsg = null;
                    receivedMsg = c.receiveFromServer();				//ReceiveFromServer();
                            
                    if(receivedMsg == null)								//if we got nothing from server, sleep and retry send&receive
                    {
                    	log_msg = "Do not receive any mssage from server, retry...";
                    	System.out.println(log_msg);
                    	Logger.log(log_msg, log_file);
                    	Thread.sleep(SLEEPTIME);
                    	continue;
                    }
                    else												//if we got something from server, do not retry, proceed to next line of the script file
                    {
                    	log_msg = "Get message from server: " + receivedMsg;
                    	System.out.println(log_msg);
                    	Logger.log(log_msg, log_file);
                    	break;
                    }
                } //end of re-try
                c.close();
                
            } //while read file is not null
            log_msg = "End of the file, exit program.";
            System.out.println(log_msg);
            Logger.log(log_msg, log_file);
            input.close();
        }
        catch(Exception e) {
            System.out.println(e.toString());
            Logger.log(e.toString(), log_file);
            System.exit(-1);
        }
        finally{}

    }

}
