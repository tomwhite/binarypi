package org.tiling.pi;

public class BinaryDigitsPrinter {
	
	public static String formatWithSpaces(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			sb.append(s.charAt(i));
			if (i < s.length() - 1 && (i + 1) % 4 == 0) {
				sb.append(' ');
			}
		}
		return sb.toString();
	}

	public static String toHexString(String binaryDigits) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i + 4 <= binaryDigits.length(); i += 4) {
			sb.append(Integer.toHexString(Integer.parseInt(binaryDigits.substring(i, i + 4), 2)).toUpperCase());
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			printUsage();
			return;
		}
		Object calculator = Class.forName(args[0]).newInstance();
		BinaryDigitsCalculator binaryDigitsCalculator;
		if (calculator instanceof BinaryDigitsCalculator) {
			binaryDigitsCalculator = (BinaryDigitsCalculator) calculator;
		} else if (calculator instanceof FractionalPartCalculator) {
			binaryDigitsCalculator = new BinaryDigitsFromFractionalPartCalculator((FractionalPartCalculator) calculator);
		} else {
			printUsage();
			return;
		}
		int n = Integer.parseInt(args[1]);
		String binaryDigits = binaryDigitsCalculator.calculateBinaryDigits(n, 48);
		System.out.println("Binary digits from position " + n + ":");
		System.out.println(formatWithSpaces(binaryDigits));
		System.out.println(toHexString(binaryDigits));
	}
	
	private static void printUsage() {
		System.err.println("Usage: " + BinaryDigitsPrinter.class.getName() + " BinaryDigitsCalculator-or-FractionalPartCalculator-classname nth-binary-digit");
		System.exit(1);		
	}
}
