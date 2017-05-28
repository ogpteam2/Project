package rpg.exception;

/**
 * An exception that implements an item exception.
 * 
 * @author Robbe, Elias
 *
 */
public class ItemTransferException extends Exception {

	/**
	 * A variable referencing the message of this exception.
	 */
	private String message = null;
	
	/**
	 * Initializing the exception with given message.
	 * 
	 * @param message
	 * 		  The message to give to the exception.
	 */
	public ItemTransferException(String message){
		this.message = message;
	}
	
	/**
	 * Initialize the exception without a message.
	 */
	public ItemTransferException(){
		
	}
	
	/**
	 * A variable referencing the serialVersionUID.
	 */
	private static final long serialVersionUID = 6976599573438594655L;

}
