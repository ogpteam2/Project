package rpg.exception;

public class ItemTransferException extends Exception {

	private String message = null;
	
	public ItemTransferException(String message){
		this.message = message;
	}
	
	public ItemTransferException(){
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6976599573438594655L;

}
