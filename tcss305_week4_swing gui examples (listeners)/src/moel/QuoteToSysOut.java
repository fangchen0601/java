package moel;

public class QuoteToSysOut extends AbstractQuoteObject {
	
	public QuoteToSysOut(String a, String b) {
		super(a,b);
	}
	
	
	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		System.err.print(this.getMyQuote());
	}

}
