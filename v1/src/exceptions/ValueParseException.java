package exceptions;

public class ValueParseException extends Exception{
	
	private static final String msg = "ATENCION  ==>  Se ha producido un error de parseo: ";

	public ValueParseException() {
		super(msg);
	}
	public ValueParseException(String message){
		super(msg + message + '\n');

	}
	
	public ValueParseException(String message, Throwable cause){
		super(msg + message + '\n', cause);
	}
	
	public ValueParseException(Throwable cause){ 
		super(cause); 
	}

}
