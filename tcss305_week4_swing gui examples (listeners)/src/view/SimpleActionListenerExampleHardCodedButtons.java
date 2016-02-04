package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleActionListenerExampleHardCodedButtons extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String RED_QUOTE = "red quote";
	
	private static final String GREEN_QUOTE = "green quote";
	
	private static final String BLUE_QUOTE = "blue quote";

	private JButton myRedButton;

	private JButton myBlueButton;

	private JButton myGreenButton;

	private JLabel myQuoteLable;
	
	private static JFrame window;
	
	public SimpleActionListenerExampleHardCodedButtons() {
		super();
		
		myRedButton = new JButton("red");
		myBlueButton = new JButton("blue");
		myGreenButton = new JButton("green");
		myQuoteLable = new JLabel();
		
		window  = new JFrame();
		//setUpComponents();
		//setVisible(true);
	}
	
	public void setUpComponents() {
		setLayout(new BorderLayout());
		
		myRedButton.addActionListener(new RedButtonListener());
		
		class BlueButtonListener implements ActionListener {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				// TODO Auto-generated method stub
				myQuoteLable.setForeground(Color.BLUE);
				myQuoteLable.setText(BLUE_QUOTE);
			} 
		}
		BlueButtonListener bbl = new BlueButtonListener();
		myBlueButton.addActionListener(bbl);
		
		myGreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				// TODO Auto-generated method stub
				myQuoteLable.setForeground(Color.GREEN);
				myQuoteLable.setText(GREEN_QUOTE);
			} 
			});
		
		JPanel labelPanel = new JPanel();
		labelPanel.add(myQuoteLable);
		add(labelPanel, BorderLayout.SOUTH);
		
		
		final JPanel buttonPanel = new JPanel(new GridLayout(3,1));
		buttonPanel.add(myRedButton);
		buttonPanel.add(myBlueButton);
		buttonPanel.add(myGreenButton);
		
		final JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.add(buttonPanel, BorderLayout.WEST);
		add(westPanel, BorderLayout.NORTH);
		
		

		
		//setVisible(true);
		
	}
	
	private void addQuoteButton(final String theQuote, final JButton theButton) {
		theButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				
				
				myQuoteLable.setText(theQuote);
			}  
		});
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				SimpleActionListenerExampleHardCodedButtons a = new SimpleActionListenerExampleHardCodedButtons();
				a.setUpComponents();
				
				JFrame window = new JFrame();
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setContentPane(a);
				
				
				
				window.pack();
				window.setVisible(true);
			}
			
			
		});
		
	} //main
	
	class RedButtonListener implements ActionListener{

		@Override
		public void actionPerformed(final ActionEvent theEvent) {
			
			myQuoteLable.setForeground(Color.RED);
			myQuoteLable.setText(RED_QUOTE);
		}  
		
	} //inner class
}
