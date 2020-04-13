package simulator.exceptions;

public class IncorrectVariableValueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -875160171497949171L;
	private static final String msg = "ATENTION  ==>  Incorrect value: ";

	public IncorrectVariableValueException() {
		super(msg);
	}

	public IncorrectVariableValueException(String message) {
		super(msg + message + '\n');

	}

	public IncorrectVariableValueException(String message, Throwable cause) {
		super(msg + message + '\n', cause);
	}

	public IncorrectVariableValueException(Throwable cause) {
		super(cause);
	}

	public IncorrectVariableValueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
