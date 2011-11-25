package org.tiling.pi.test;

import org.tiling.pi.BinaryDigitsPrinter;

import junit.framework.TestCase;

public class BinaryDigitsPrinterTest extends TestCase {
	public void testFormatWithSpaces() {
		assertEquals("", BinaryDigitsPrinter.formatWithSpaces(""));
		assertEquals("0", BinaryDigitsPrinter.formatWithSpaces("0"));
		assertEquals("01", BinaryDigitsPrinter.formatWithSpaces("01"));
		assertEquals("010", BinaryDigitsPrinter.formatWithSpaces("010"));
		assertEquals("0101", BinaryDigitsPrinter.formatWithSpaces("0101"));
		assertEquals("0101 0", BinaryDigitsPrinter.formatWithSpaces("01010"));
		assertEquals("0101 01", BinaryDigitsPrinter.formatWithSpaces("010101"));
		assertEquals("0101 010", BinaryDigitsPrinter.formatWithSpaces("0101010"));
		assertEquals("0101 0101", BinaryDigitsPrinter.formatWithSpaces("01010101"));
		assertEquals("0101 0101 0", BinaryDigitsPrinter.formatWithSpaces("010101010"));
	}
	
	public void testToHexString() {
		assertEquals("", BinaryDigitsPrinter.toHexString(""));
		assertEquals("", BinaryDigitsPrinter.toHexString("1"));
		assertEquals("", BinaryDigitsPrinter.toHexString("01"));
		assertEquals("", BinaryDigitsPrinter.toHexString("101"));
		assertEquals("D", BinaryDigitsPrinter.toHexString("1101"));
		assertEquals("D", BinaryDigitsPrinter.toHexString("11011"));
		assertEquals("D", BinaryDigitsPrinter.toHexString("110111"));
		assertEquals("D", BinaryDigitsPrinter.toHexString("1101110"));
		assertEquals("DF", BinaryDigitsPrinter.toHexString("11011111"));
	}
}
