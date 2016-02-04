package moel;

import javax.swing.JOptionPane;

public class QuoteToJOptionOut extends AbstractQuoteObject {
	
	public QuoteToJOptionOut(String a, String b) {
		super(a,b);
	}
	
	
	@Override
	public void doSomething() {
		JOptionPane.showMessageDialog(null, getMyQuote());
		
	}

}
