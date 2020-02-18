package exceptions;

public class IncorrectVariableValue extends Exception{
	
	private static final String msg = "ATENCION  ==>  Incorrect value: ";

	public IncorrectVariableValue() {
		super(msg);
	}
	public IncorrectVariableValue(String message){
		super(msg + message + '\n');

	}
	
	public IncorrectVariableValue(String message, Throwable cause){
		super(msg + message + '\n', cause);
	}
	
	public IncorrectVariableValue(Throwable cause){ 
		super(cause); 
	}
}
