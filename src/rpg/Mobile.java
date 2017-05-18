package rpg;

import java.util.regex.*;

import be.kuleuven.cs.som.annotate.*;

abstract public class Mobile {
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	public Mobile(String name) throws IllegalArgumentException{
		setName(name);
	}
	
	
	/************************************************
	 * Name
	 ************************************************/
	
	/**
	 * Variable for storing the name of the mobile
	 */
	
	private String name = null;
	
	public Pattern getValidNamePattern(){
		return Pattern.compile(".");
	}
	
	/**
	 * Checks if the given string is a valid name for the mobile.
	 * 
	 *  @return True if the given string is a valid name. Validity to
	 *  be defined per subclass.
	 */
	
	public boolean isValidName(String name){
		Pattern validNamePattern = this.getValidNamePattern();
		return validNamePattern.matcher(name).matches();
	}
	
	@Basic
	@Raw
	public String getName(){
		return this.name;
	}

	public void setName(String name) throws IllegalArgumentException{
		if(isValidName(name)){
			this.name = name;
		}
		else{
			throw new IllegalArgumentException("Invalid name");
		}
	}
}
