package simulator.exceptions;

public class ExistingObjectException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5746227052876454768L;
	private static final String msg = "ATENTION  ==>  This object already exist: ";

	public ExistingObjectException() {
		super(msg);
	}

	public ExistingObjectException(String message) {
		super(msg + message + '\n');

	}

	public ExistingObjectException(String message, Throwable cause) {
		super(msg + message + '\n', cause);
	}

	public ExistingObjectException(Throwable cause) {
		super(cause);
	}

	public ExistingObjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
