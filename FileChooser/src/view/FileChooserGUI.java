package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class FileChooserGUI extends JFrame implements ActionListener {
	public static final String TITLE = "File Chooser";

	
	public FileChooserGUI () {
		super("TITLE");
		
		setupGUI();
		
		setVisible(true);
	}
	
	private void setupGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFileChooser fc = new JFileChooser();
		
		//add some panel like master panel
		//add some components to the master panel
		JPanel masterPanel = makeMasterPanel();
		
		
		masterPanel.add(makeButton("center"), BorderLayout.CENTER);
		//masterPanel.add(makeButton("north"), BorderLayout.NORTH);
		
		//masterPanel.add(makeButton("south"), BorderLayout.SOUTH);
		masterPanel.add(makeButton("west"), BorderLayout.WEST);
		masterPanel.add(makeButton("east"), BorderLayout.EAST);
		
		
		
		
		add(masterPanel);
		//setSize(600, 600);
		pack();
	}
	
	private JPanel makeMasterPanel() {
		JPanel master = new JPanel(new BorderLayout());
		//master.setSize(500, 400);
		
		
		return master;
	}
	
	private JButton makeButton(String label) {
		JButton b = new JButton(label);
		b.addActionListener(this);
		return b;
	}

	@Override
	public void actionPerformed(final ActionEvent theEvent) {
		final Object source = theEvent.getSource();
		if (source.equals("center")) {
			
		}
		
	}
}
