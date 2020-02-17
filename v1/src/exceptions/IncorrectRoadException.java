package exceptions;

public class IncorrectRoadException extends Exception{
	
	private static final String msg = "ATENCION  ==>  Road destination junction and actual Juctions are not equal: ";

	public IncorrectRoadException() {
		super(msg);
	}
	public IncorrectRoadException(String message){
		super(msg + message + '\n');

	}
	
	public IncorrectRoadException(String message, Throwable cause){
		super(msg + message + '\n', cause);
	}
	
	public IncorrectRoadException(Throwable cause){ 
		super(cause); 
	}
}
