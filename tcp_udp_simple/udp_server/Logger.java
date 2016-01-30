import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {
	public static void log(String logMsg, String fileName) {
		
		logMsg = LocalDateTime.now() + ": " + logMsg;
		System.out.println(logMsg);
		
		try {
			File f = new File("./" + fileName);
			FileWriter fw = new FileWriter(f, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(logMsg + "\n");
	        bw.close();
		} catch (Exception e) {
			System.out.println("Difficulties openning "
					+ "the log file!" + e);
			System.exit(1);
		}
	}
}
