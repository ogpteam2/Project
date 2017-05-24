package rpg.utility;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class BinomialGenerator implements IDGenerator {

	private ArrayList<Long> pyramidRow;
	
	private long currentValue;
	private int currentRowNum;
	
	/**
	 * An iterator that calculates sequential binomial coefficients. These can be calculated as specified
	 * by the assignment with factorials. However, factorials generate insanely large 
	 */
	
	public BinomialGenerator(){
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
	public boolean hasNextID() {
		long nextValue = calculateRowSum(pyramidRow);
		if(nextValue < 0) return false;
		else return true;
	}

	/**
	 * Advances the iterator. First calculates the next row in the pyramid. Then calculates the sum of 
	 * @Effect attribute current is updated with the next binomial coefficient
	 * @Effect the pyramidRow is replaced by the value that will be used to calculate the binomialcoefficient of the following iteration
	 * @Effect Advances the row number by one.
	 * @return the next binomial coefficient
	 */
	
	@Override
	public long nextID() {
		replaceRow(calculateNextRow());
		advanceCurrentRowNum();
		long nextLong = calculateRowSum(pyramidRow);
		setCurrentValue(nextLong);
		return nextLong;
	}
	
	/**
	 * Resets the iterator to the first binomial coefficient.
	 * @effect the pyramidrow is re-initialised to an empty arraylist
	 * @effect the pyramid row number is set to 1
	 */
	public void reset(){
		replaceRow(new ArrayList<Long>());
		setCurrentRowNum(1);
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
	public long getCurrentValue() {
		return currentValue;
	}

	
	private void setCurrentValue(long currentValue) {
		this.currentValue = currentValue;
	}
	
	/**
	 * Returns the current row number.
	 * @return the current row number.
	 */
	@Raw
	@Basic
	public int getCurrentRowNum() {
		return currentRowNum;
	}
	
	
	/**
	 * The value of the current row number is directly set
	 * @param currentRowNum value to set the row number to
	 */
	private void setCurrentRowNum(int currentRowNum){
		this.currentRowNum = currentRowNum;
	}
	
	/**
	 * Advances the row counter by one
	 * @Post one is added to the currentRowNum
	 */
	private void advanceCurrentRowNum(){
		this.currentRowNum++;
	}
}
