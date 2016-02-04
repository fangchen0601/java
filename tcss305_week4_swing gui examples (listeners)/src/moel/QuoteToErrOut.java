package moel;

public class QuoteToErrOut extends AbstractQuoteObject {
	
	public QuoteToErrOut(String a, String b) {
		super(a,b);
	}
	
	
	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.print(this.getMyQuote());
	}

}
