package ca.concordia.risk.utilities;

/**
 * The Class ValidMapException to handle exception in Map
 * @author dhruv
 */
public final class ValidMapException extends Exception {

	private static final long serialVersionUID = -4417019573954154838L;


	/**
	 * Instantiates a new valid map exception.
	 */
	public ValidMapException() {
		super();
	}

	/**
	 * Instantiates a new valid map exception.
	 *
	 * @param message the message
	 */
	public ValidMapException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new valid map exception.
	 *
	 * @param cause the cause
	 */
	public ValidMapException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new valid map exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ValidMapException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new valid map exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace of exception
	 */
	public ValidMapException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
