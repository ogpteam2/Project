package rpg.utility;

import java.util.ArrayList;

public class PrimeGenerator implements IDGenerator {
	
	private int position;
	
	private ArrayList<Long> primeList = new ArrayList<Long>();
	
	/**
	 * Iterator that generates consecutive primes.
	 */
	
	public PrimeGenerator(){
		position = -1;
		primeList.add(2L);
	}
	
	@Override
	public long generateID(){
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}
	
	/**
	 * There are an infinite amount of prime numbers, so in theory this method should always be true.
	 * Java's long primitive does have a finite size though, so 
	 */
	
	@Override
	public boolean hasNextID() {
		return true;
	}
	
	
	@Override
	public long nextID() {
		position++;
		try{
			return primeList.get(position);
		} catch(IndexOutOfBoundsException e){
			primeList.add(generateNextPrime(primeList));
		}
		return primeList.get(position);
	}
	
	/**
	 * Resets the position to the first known prime.
	 * 
	 * @effect Resets the position
	 * 		   position = 0
	 */
	public void reset(){
		this.position = 0;
	}
	
	private long generateNextPrime(ArrayList<Long> previousPrimes){
		long current = previousPrimes.get(previousPrimes.size()-1);
		SEARCH:
		while(true){
			current++;
			for(long p : previousPrimes){
				if (current % p == 0) continue SEARCH;
			}
			return current;
		}
	}
}
