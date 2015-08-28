package tree.gui;

public class InvalidInputException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8510691956358390718L;

	/**
	 * this exception should be thrown if the user make an invalid input
	 * 
	 * @param message
	 *            a message to specifie what the user made wrong
	 */
	public InvalidInputException(String message) {
		super(message);
	}
}
