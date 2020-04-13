package simulator.exceptions;

public class NonExistingObjectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6937850570524236776L;
	private static final String msg = "ATENTION  ==>  Interaction with non existing object: ";

	public NonExistingObjectException() {
		super(msg);
	}

	public NonExistingObjectException(String message) {
		super(msg + message + '\n');

	}

	public NonExistingObjectException(String message, Throwable cause) {
		super(msg + message + '\n', cause);
	}

	public NonExistingObjectException(Throwable cause) {
		super(cause);
	}

	public NonExistingObjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
