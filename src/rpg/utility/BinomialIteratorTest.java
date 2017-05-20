package rpg.utility;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class BinomialIteratorTest {

	BinomialIterator it;
	
	@Before
	public void setUp(){
		it = new BinomialIterator();
	}

	@Test
	public void binomialCorrectnessTest() {
		int length = 50;
		long [] pyramidBinomial = new long[length];
		long [] factorialBinomial = new long[length];
		for(int i = 0; i < length; i++){
			pyramidBinomial[i] = it.nextLong();
			factorialBinomial[i] = calculateBinomial(i+1);
		}
		System.out.println(Arrays.toString(pyramidBinomial));
		System.out.println(Arrays.toString(factorialBinomial));
		assertArrayEquals(factorialBinomial,pyramidBinomial);
	}
	
	@Test
	public void test(){
		assertEquals(calculateBinomial(2),4L);
	}
	
	public long calculateBinomial(int n){
		long sum = 0;
		for(int i = 0; i<=n; i++){
			sum += nChooseK(n,i);
		}
		return sum;
	}
	
	public long nChooseK(int n, int k){
		return (factorial(n)/(factorial(k)*factorial(n-k)));
	}
	
	public long factorial(long l){
		if(l == 0) return 1;
		long result = 0;
		result += l;
		for(long i = 1; i < l; i++){
			result *= i;
		}
		return result;
	}
}
