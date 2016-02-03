package view;

import java.awt.EventQueue;

public class FileChooserMain {

	/*
	 * prevent construction of instances
	 */
	private FileChooserMain() {
		//do nothing
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
                new FileChooserGUI();     
            }			
		});
	}

}
