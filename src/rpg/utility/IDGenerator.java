package rpg.utility;

public interface IDGenerator {
	
	/**
	 * Calculates an ID based on the specification.
	 * @return next ID in the sequence.
	 */
	public long nextID();
	
	/**
	 * Indicates if the generator can generate another ID.
	 * @return true if a new ID can be generated.
	 */
	public boolean hasNextID();
	
	/**
	 * Resets the generator to it's initial state. nextID() will generate
	 * an ID as if the generator was just initialised.
	 */
	public void reset();
}
