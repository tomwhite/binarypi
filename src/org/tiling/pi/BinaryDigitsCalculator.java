package org.tiling.pi;

public interface BinaryDigitsCalculator {

	/**
	 * Calculates <code>length/code> binary digits starting from the <i>n</i>th.
	 * @param n the <i>n</i>th digit to calculate.
	 * @param length the number of digits to return.
	 * @throws IllegalArgumentException if n is less than 1 or possibly if n is too large (implementation dependent).
 	 */
	public String calculateBinaryDigits(int n, int length);

}