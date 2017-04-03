package model;

public class InvalidHandException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidHandException() {
		
	}
	
	public InvalidHandException(String msg) {
		super(msg);
	}
}
