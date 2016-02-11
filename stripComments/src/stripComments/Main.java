package stripComments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(new File("./file.txt"));
		stripComments(s);

	}
	
	public static void stripComments(Scanner s) {
		String result = "";
		String state = "normal";
		boolean addBreakLine = true;
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (state.equals("comment")) {
				addBreakLine = false;
			} else {
				addBreakLine = true;
			}
			int i;
			for (i = 0; i<line.length(); i ++){
				char c = line.charAt(i);
				

				
				if (state.equals("normal")) {
					if (c == '/') {
						state = "expect_star";
					}
					else {
						result = result + c;
					}
				} else if (state.equals("expect_star")) {
					if (c == '*') {
						state = "comment";
					} else if (c == '/') {
						state = "normal";
						break;
					} else {
						state = "normal";
						result = result + c;
					}
				} else if(state.equals("comment")) {
					
					if ( c == '*') {
						state = "expect_slash";
					}
					else {
						continue;
					}
				} else if (state.equals("expect_slash")) {
					if (c == '/') {
						state = "normal";
						
					} else {
						state = "comment";
					}
				}
					
				
			}
			
			if (state.equals("normal") && addBreakLine)
				result = result + "\n";
			
		}
		
		System.out.println(result);
	}

}

