package moel;

public abstract class AbstractQuoteObject implements DoSomething {
	private final String myName;
	
	private final String myQuote;
	
	public AbstractQuoteObject(String a, String b) {
		myName = a;
		myQuote = b;
	}
	
	//get
	public String getMyName() {
		return this.myName;
	}
	
	//get
	public String getMyQuote() {
		return this.myQuote;
	}
	
	//tostring
	
	@Override
	public abstract void doSomething();
	
	
}
