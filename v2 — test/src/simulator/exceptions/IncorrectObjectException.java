package simulator.exceptions;

public class IncorrectObjectException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6579586849584549063L;
	private static final String msg = "ATENTION  ==>  This object is incorrect: ";

	public IncorrectObjectException() {
		super(msg);
	}

	public IncorrectObjectException(String message) {
		super(msg + message + '\n');

	}

	public IncorrectObjectException(String message, Throwable cause) {
		super(msg + message + '\n', cause);
	}

	public IncorrectObjectException(Throwable cause) {
		super(cause);
	}

	public IncorrectObjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
