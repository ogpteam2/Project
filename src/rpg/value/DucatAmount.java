package rpg.value;

import java.math.BigDecimal;
import java.math.RoundingMode;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class DucatAmount implements Comparable<DucatAmount> {

	private static final Weight DUCAT_WEIGHT = new Weight(new BigDecimal(50),Unit.g);
	
	/**
	 * Creates a new ducatamount of the given value.
	 * @param amount
	 * 			The value this ducatamount has to have.
	 * @throws IllegalArgumentException
	 * 			If the value is null an exception is thrown.
	 */
	public DucatAmount(BigDecimal amount) throws IllegalArgumentException {
		if (amount == null)
			throw new IllegalArgumentException();
		else
			this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Creates a ducatamount with a value of 1 ducat.
	 */
	public DucatAmount() {
		this(BigDecimal.ONE);
	}
	
	/**
	 * Creates a ducatamount with a double as argument.
	 * @param amount
	 * 			The value this ducatamount has to have.
	 */
	public DucatAmount(double amount){
		this(new BigDecimal(amount));
	}
	
	/**
	 * Creates a ducatamount with a int as argument.
	 * @param amount
	 * 			The value this ducatamount has to have.
	 */
	public DucatAmount(int amount){
		this(new BigDecimal(amount));
	}
	
	/**
	 * 
	 */
	private final BigDecimal amount;
	
	public BigDecimal getValue() {
		return this.amount;
	}
	
	/**
	 * 
	 * @return the string value of this object
	 */
	public String toString() {
		return (this.getValue().toString() + " ducats");
	}
	
	/******************************************
	 * Weight
	 ******************************************/

	/**
	 * Calculates the weight based on the amount of ducats in this value
	 * and the weight of individual ducats.
	 * @return the weight of this ducatamount
	 */
	public Weight getWeight(){
		return DUCAT_WEIGHT.multiply(this.getValue());
	}
	
	/******************************************
	 * Logic
	 ******************************************/

	@Override
	public int compareTo(DucatAmount other) throws ClassCastException {
		if (other == null) {
			throw new ClassCastException();
		} else {
			return this.getValue().compareTo(other.getValue());
		}
	}
	
	public boolean equals(DucatAmount other) throws ClassCastException {
		return this.compareTo(other) == 0;
	}
	
	public boolean isGreaterThan(DucatAmount other) throws ClassCastException{
		return this.compareTo(other) == 1;
	}

	/******************************************
	 * Arithmetic
	 ******************************************/

	/**
	 * Adds the values of two DucatAmounts together and returns a new DucatAmount with that value.
	 * @param other
	 * 		  Second DucatAmount of which the value is to be added
	 * @return new DucatAmount with the sum of the value of the prime object and other as value
	 * 		|  return.getValue() == this.getValue() + other.getValue()
	 */

	public DucatAmount add(DucatAmount other) {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(this.getValue());
		total = total.add(other.getValue());
		return new DucatAmount(total);
	}

	/**
	 * Subtracts the value of the 
	 * @param other
	 * @return
	 * @throws ArithmeticException
	 */
	public DucatAmount subtract(DucatAmount other) throws ArithmeticException {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(this.getValue());
		total = total.subtract(other.getValue());
		if (total.signum() == -1)
			throw new ArithmeticException("Cannot have a negative amount of ducats!");
		else
			return new DucatAmount(total);
	}
	
	/**
	 * Multiplies the value of this ducatamount and returns a new ducatamount with the multiplied value.
	 * @param factor
	 * 		  Factor to be multiplied by
	 * @return DucatAmount with the multiplied value
	 * 		|  return.getValue() == this.getValue() * factor
	 */
	public DucatAmount multiply(BigDecimal factor){
		BigDecimal result = this.getValue();
		result = result.multiply(factor);
		return new DucatAmount(result);
	}
	
	
	/**
	 * Overloaded method accepting a double as multiplication factor
	 * @see multiply(BigDecimal factor)
	 */
	public DucatAmount multiply(double factor){
		BigDecimal bigFactor = new BigDecimal(factor);
		return multiply(bigFactor);
	}
	
	/**
	 * Overloaded method accepting an integer as multiplication factor
	 * @see multiply(BigDecimal factor)
	 */
	public DucatAmount multiply(int factor){
		BigDecimal bigFactor = new BigDecimal(factor);
		return multiply(bigFactor);
	}

}