package org.tiling.pi;

public class BinaryDigitsFromFractionalPartCalculator implements BinaryDigitsCalculator {

	private final FractionalPartCalculator fractionalPartCalculator;
	
	public BinaryDigitsFromFractionalPartCalculator(FractionalPartCalculator fractionalPartCalculator) {
		this.fractionalPartCalculator = fractionalPartCalculator;
	}
	
	private String calculateBinaryDigits(double fractionalPart, int length) {
		fractionalPart = Math.abs(fractionalPart);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			fractionalPart = 2 * (fractionalPart - Math.floor(fractionalPart));
			sb.append((int) fractionalPart);
		}
		return sb.toString();
	}	

	public String calculateBinaryDigits(int n, int length) {
		return calculateBinaryDigits(fractionalPartCalculator.calculateFractionalPart(n - 1), length);
	}

}
