package org.tiling.pi.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.tiling.pi.BinaryDigitsCalculator;

/**
 * BinaryPiSource provides a reliable and accurate source of the binary digits of pi.
 * The digits are read from a text file in the constructor and it is possible to
 * only read a specified number of digits. Note that digits are those <i>after</i> the
 * decimal point, and indexing is 1-based.
 */
public class BinaryPiSource implements BinaryDigitsCalculator {

	// Binary expansion of pi stored in a String (by the constructor)
	private String pi_2 = "11.00";
	private int firstDigitOffset = pi_2.indexOf('.');
	
	public BinaryPiSource() throws IOException {
		this(new BufferedReader(new FileReader("pi2.txt")));
	}

	/**
	 * Constructs a source of binary digits of pi. 
	 * @param reader the source of binary digits of pi. Looks for characters
	 * '0' '1' and '.' in the stream - all other characters are ignored. Parsing
	 * begins after the character sequence '1' '1' '.' '0' '0' has been found.
	 * @throws IOException if there is a read error or if the binary expansion
	 * of pi cannot be found.
	 */
	public BinaryPiSource(Reader reader) throws IOException {
		this(reader, -1);
	}

	/**
	 * Constructs a source of binary digits of pi. 
	 * @param reader the source of binary digits of pi. Looks for characters
	 * '0' '1' and '.' in the stream - all other characters are ignored. Parsing
	 * begins after the character sequence '1' '1' '.' '0' '0' has been found.
	 * @param maxDigits the (maximum) number of characters to read from reader.
	 * @throws IOException if there is a read error or if the binary expansion
	 * of pi cannot be found.
	 */
	public BinaryPiSource(Reader reader, int maxDigits) throws IOException {

		int digitsRead = 2; // '0' and '0' have already been read!

		if (maxDigits == -1 || digitsRead < maxDigits) {

			boolean startFound = false;
			int c;
			int[] cPrev = new int[4];

			StringBuffer sb = new StringBuffer(pi_2);

			while ((c = reader.read()) != -1 && (maxDigits == -1 || digitsRead < maxDigits)) {
				switch (c) {
					case '0':
					case '1':
					case '.':
						if (startFound) {
							sb.append((char) c);
							digitsRead++;
						} else if (cPrev[0] == pi_2.charAt(0) && cPrev[1] == pi_2.charAt(1)
										&& cPrev[2] == pi_2.charAt(2) && cPrev[3] == pi_2.charAt(3) 
										&& c == pi_2.charAt(4)) {
							startFound = true;
						} else {
							cPrev[0] = cPrev[1];
							cPrev[1] = cPrev[2];
							cPrev[2] = cPrev[3];
							cPrev[3] = c;
						}
						break;
					default:
				}
			}

			pi_2 = sb.toString();

			if (!startFound) {
				throw new IOException("The binary expansion of pi was not found.");
			}

			reader.close();

		}

	}

	/**
	 * Returns the number of binary digits stored.
	 */
	public int getNumberOfBinaryDigits() {
		return pi_2.length() - firstDigitOffset - 1;
	}

	public static void main(String[] args) {
		
		final int length = 16;

		if (args.length == 1) {

			try {
				int digit = Integer.parseInt(args[0]);
				System.out.println(new BinaryPiSource(new BufferedReader(new InputStreamReader(System.in)),
											digit + length).calculateBinaryDigits(digit, length));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} else if (args.length == 2) {

			try {
				int digit = Integer.parseInt(args[1]);
				System.out.println(new BinaryPiSource(new BufferedReader(new FileReader(args[0])),
											digit + length).calculateBinaryDigits(digit, length));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} else {
			System.err.println("Usage: java BinaryPiSource [file] startDigit");
			System.exit(1);
		}

	}

	public String calculateBinaryDigits(int n, int length) {
		if (n < 1 || n + length >= getNumberOfBinaryDigits()) {
			throw new IllegalArgumentException("Can only calculate binary digits between 1 and "
									 + getNumberOfBinaryDigits() + " inclusive.");
		}

		return pi_2.substring(n + firstDigitOffset, n + firstDigitOffset + length);
	}
}