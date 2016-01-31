import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyValue extends Remote {
	
	String work(String clientMsg) throws RemoteException;
} 
