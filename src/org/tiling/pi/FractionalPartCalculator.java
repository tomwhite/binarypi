package org.tiling.pi;

public interface FractionalPartCalculator {

	/**
	 * Calculates <i>b</i><sup><i>n</i></sup><i>S</i>, where <i>b</i> is the base (typically 2), and <i>S</i> is the series.
	 * The returned value can be used to calculate the digits in the base <i>b</i> expansion of <i>S</i>, beginning at position <i>n</i> + 1.
	 * @param n the <i>n</i>th digit to calculate.
	 * @throws IllegalArgumentException if <i>n</i> + 1 is less than 0, or possibly if n is too large (implementation dependent).
 	 */
	public double calculateFractionalPart(int n);

}