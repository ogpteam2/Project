package rpg;

import java.util.regex.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * An abstract class of entities that can move (mobiles).
 * 
 * @invar Each mobile must have a properly spelled name.  
 * 		  |isValidName(getName())
 * @author Robbe, Elias
 * @version 1.0
 *
 */
abstract public class Mobile {
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	public Mobile(String name) throws IllegalArgumentException{
		setName(name);
	}
	
	
	/************************************************
	 * Name - defensive programming
	 ************************************************/
	
	/**
	 * Return the name of this mobile.
	 */
	@Raw @Basic 
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check whether the name is a valid name.
	 * 
	 * @param name
	 * 		  The name to be checked.
	 * @return True if the given string is a valid name for the mobile.
	 * @throws IllegalArgumentException
	 * 		   This name that was given is not a valid name.
	 * 		   | (name == null || !(getValidNamePattern.matcher(name).matches())  
	 * @note  The valid names will be decided in the each subclass .
	 */
	@Raw
	abstract public boolean isValidName(String name) throws IllegalArgumentException;
	

	/**
	 * A pattern which names of mobiles should be made of.
	 * 
	 * @return a pattern of acceptable characters for the name.
	 * 		   | result == Pattern.compile(".")
	 */
	@Raw @Immutable 
	public static Pattern getValidNamePattern(){
		return validNamePattern;
	}
	

	/**
	 * Sets the name of the mobile to the given name.
	 * 
	 * @param name
	 * 		  The new name of the mobile.
	 * @Post If the given name is a valid one set the name to the new name of the mobile.
	 * 		 | if(isValidName(name))
	 * 		 | then (new).getName.equals(name)
	 * @throws IllegalArgumentException
	 * 		   The given name is not valid.
	 * 		   | !(isValidName(name))
	 * 		   
	 */
	@Raw 
	public void setName(String name) throws IllegalArgumentException{
		if(isValidName(name)){
			this.name = name;
		}
		else{
			throw new IllegalArgumentException("Invalid name");
		}
	}
	
	/**
	 * Variable for storing the name of the mobile.
	 */
	private String name = null;
	
	/**
	 * Variable for storing the pattern to which names should abide by.
	 */
	private static final Pattern validNamePattern = Pattern.compile(".+");
}
