import java.net.*;
import java.io.*;

public class Client {
    public String hostname;
    public int port;
    Socket socket;
    SocketAddress sockaddr;
    
    BufferedReader br;
    DataOutputStream out;
    
    public Client(String _hostname, int _port) throws Exception{         //the constructor
        hostname = _hostname;
        port = _port;
        socket = new Socket(hostname, port);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new DataOutputStream(socket.getOutputStream());
    }
    
    public void close() throws Exception{
        socket.close();
    }
    
    public void sendToServer(String msg) throws Exception{   //send message to server
        
        out.writeBytes(msg + '\n');
        
    }
    
    public String receiveFromServer() throws Exception{   //get message from server
        String myMsg = "";
        try {

        myMsg = br.readLine();
        }
        catch (Exception e) {}
        finally {
        	return myMsg;
        }
    }
}
