package rpg.utility;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class BinomialIterator implements PrimitiveIterator.OfLong {

	private ArrayList<Long> pyramidRow;
	
	private long current;
	
	public BinomialIterator(){
		reset();
	}
	
	/**
	 * Binomial coefficients can be calculated endlessly. However, the java long type has a finite size.
	 * When the value of a long grows larger than 2^63 the sign indication bit is flipped, so the value
	 * becomes negative. As values should only be positive, sign is an indication for overflow.
	 * 
	 * @return True if the value has not overflowed into the sign indication bit.
	 * 			| if(next < 0) return false
	 * 			| else return true
	 */
	@Override
	public boolean hasNext() {
		long nextValue = calculateRowSum(pyramidRow);
		if(nextValue < 0) return false;
		else return true;
	}

	/**
	 * Advances the iterator. First calculates the row sum of the current pyramid row. Then stores that value in current. 
	 * Finally replaces the row that was used to calculate with a new row, so the next iteration can calculate using that.
	 * @Effect attribute current is updated with the next binomial coefficient
	 * @Effect the pyramidRow is replaced by the value that will be used to calculate the binomialcoefficient of the following iteration
	 * @return the next binomial coefficient
	 */
	
	@Override
	public long nextLong() {
		long nextLong = calculateRowSum(pyramidRow);
		setCurrent(nextLong);
		replaceRow(calculateNextRow());
		return nextLong;
	}
	
	/**
	 * Resets the iterator to the first binomial coefficient.
	 * @effect pyramidRow is reset to the basic row of [1,1]
	 */
	public void reset(){
		ArrayList<Long> nextRow = new ArrayList<Long>();
		nextRow.add(1L);
		nextRow.add(1L);
		setCurrent(2L);
		replaceRow(nextRow);
	}
	
	private ArrayList<Long> calculateNextRow(){
		ArrayList<Long> nextRow = new ArrayList<Long>();
		nextRow.add(1L);
		for(int i = 0; i<pyramidRow.size()-1;i++){
			nextRow.add(pyramidRow.get(i) + pyramidRow.get(i+1));
		}
		nextRow.add(1L);
		return nextRow;
	}
	
	/**
	 * Replaces the current pyramidRow with another.
	 * @param nextRow
	 * 		  The pyramidrow the current pyramidrow will be replaced with.
	 */
	@Raw
	private void replaceRow(ArrayList<Long> nextRow){
		this.pyramidRow = nextRow;
	}
	
	private long calculateRowSum(ArrayList<Long> row){
		long rowSum = 0L;
		for(long p : row){
			rowSum += p;
		}
		return rowSum;
	}

	@Basic
	@Raw
	public long getCurrent() {
		return current;
	}

	@Basic
	private void setCurrent(long current) {
		this.current = current;
	}
}
