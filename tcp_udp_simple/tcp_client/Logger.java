import java.io.*;
import java.time.LocalDateTime;

public class Logger {
    public static void log(String log_msg, String log_file){
        
        try {
            
            log_msg = LocalDateTime.now() + ": " +log_msg;   //put a data and time in each line of the log file
            
            File f = new File("./" + log_file);
            
            FileWriter fw = new FileWriter(f,true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(log_msg + "\n");
           
            bw.close();
        }
        catch (Exception e) {}
        finally {}
    }
}
