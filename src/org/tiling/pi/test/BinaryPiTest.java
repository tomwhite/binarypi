package org.tiling.pi.test;

import junit.framework.TestCase;

import org.tiling.pi.BinaryDigitsCalculator;
import org.tiling.pi.BinaryDigitsFromFractionalPartCalculator;
import org.tiling.pi.BinaryDigitsPrinter;
import org.tiling.pi.FractionalPartCalculator;
import org.tiling.pi.algorithm.BbpBinaryPiAlgorithm32BitV2;
import org.tiling.pi.algorithm.BbpBinaryPiAlgorithm32BitV3;
import org.tiling.pi.algorithm.BbpBinaryPiAlgorithm32BitV4;
import org.tiling.pi.algorithm.BellardBinaryPiAlgorithm32BitV1;
import org.tiling.pi.algorithm.BellardBinaryPiAlgorithm64BitV1;
import org.tiling.pi.algorithm.BinaryPiSource;
import org.tiling.pi.algorithm.parallel.BellardBinaryPiAlgorithm64Bit;

/**
 * I check that implementations of {@link BinaryDigitsCalculator} produce the
 * same expansion of pi as {@link BinaryPiSource} within their working ranges.
 */
public class BinaryPiTest extends TestCase {

	private void check(FractionalPartCalculator fractionalPartCalculator, int failPosition) throws Exception {

		BinaryDigitsCalculator algorithm = new BinaryDigitsFromFractionalPartCalculator(fractionalPartCalculator);

		BinaryPiSource source = new BinaryPiSource();

		int chunkSize = 16;
		for (int i = 8; (i + chunkSize) < source.getNumberOfBinaryDigits(); i += chunkSize) {
			String actualDigits = algorithm.calculateBinaryDigits(i, chunkSize);
			String expectedDigits = source.calculateBinaryDigits(i, chunkSize);
			if (i <= failPosition && failPosition < i + chunkSize) {
				assertFalse("Expected mismatch starting at digit " + i, expectedDigits.equals(actualDigits));				
				break;
			}
			assertEquals("Mismatch starting at digit " + i, expectedDigits, actualDigits);				
			if (i / chunkSize % 100 == 0) {
				System.out.println(i + "/" + source.getNumberOfBinaryDigits());
			}
		}
		
	}

	public void testBellardBinaryPiAlgorithm32BitV1() throws Exception {
		check(new BellardBinaryPiAlgorithm32BitV1(), -1);
	}

	public void testBbpBinaryPiAlgorithm32BitV2() throws Exception {
		check(new BbpBinaryPiAlgorithm32BitV2(), 16427);
	}

	public void testBbpBinaryPiAlgorithm32BitV3() throws Exception {
		check(new BbpBinaryPiAlgorithm32BitV3(), 23276);
	}

	public void testBbpBinaryPiAlgorithm32BitV4() throws Exception {
		check(new BbpBinaryPiAlgorithm32BitV4(), 23276);
	}

	public void testBellardBinaryPiAlgorithm64BitV1() throws Exception {
		check(new BellardBinaryPiAlgorithm64BitV1(), -1);
		
		// From "On the Rapid Computation of Various Polylogarithmic Constants"
		// by David Bailey, Peter Borwein and Simon Plouffe
		// http://www.cecm.sfu.ca/~pborwein/PAPERS/P123.pdf
		BinaryDigitsCalculator calculator = 
			new BinaryDigitsFromFractionalPartCalculator(new BellardBinaryPiAlgorithm64BitV1());
		assertEquals("10^6", "26C65E52", BinaryDigitsPrinter.toHexString(calculator.calculateBinaryDigits(1000000 * 4 - 3, 32)));
		assertEquals("10^7", "17AF5863", BinaryDigitsPrinter.toHexString(calculator.calculateBinaryDigits(10000000 * 4 - 3, 32)));
	}
	
	public void testBellardBinaryPiAlgorithm64Bit() throws Exception {
//		check(new BellardBinaryPiAlgorithm64Bit(), -1);
		
		// From "On the Rapid Computation of Various Polylogarithmic Constants"
		// by David Bailey, Peter Borwein and Simon Plouffe
		// http://www.cecm.sfu.ca/~pborwein/PAPERS/P123.pdf
		BinaryDigitsCalculator calculator = 
			new BinaryDigitsFromFractionalPartCalculator(new BellardBinaryPiAlgorithm64Bit());
		assertEquals("10^6", "26C65E52", BinaryDigitsPrinter.toHexString(calculator.calculateBinaryDigits(1000000 * 4 - 3, 32)));
		assertEquals("10^7", "17AF5863", BinaryDigitsPrinter.toHexString(calculator.calculateBinaryDigits(10000000 * 4 - 3, 32)));
	}
	
	
	
}