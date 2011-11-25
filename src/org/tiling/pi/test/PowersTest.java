package org.tiling.pi.test;

import java.math.BigInteger;

import org.tiling.pi.util.Powers;

import junit.framework.TestCase;

public class PowersTest extends TestCase {
	public void test1() {
		for (int i = 0; i < 1000; i++) {
			checkModPow1(i, i + 2);			
		}
		for (int i = 0; i < 1000; i++) {
			checkModPow1(i, 3037000499L);			
		}
	}
	
	public void test2() {
		for (int i = 0; i < 1000; i++) {
			checkModPow2(i, i + 2);			
		}
		for (int i = 0; i < 1000; i++) {
			checkModPow2(i, 3037000499L);			
		}
	}
	
	public void test3() {
		for (int i = 0; i < 1000; i++) {
			checkModPow3(i, i + 2);			
		}
		for (int i = 0; i < 1000; i++) {
			checkModPow3(i, 3037000499L);			
		}
	}
	
	public void test4() {
		for (int i = 0; i < 1000; i++) {
			checkModPow4(i, i + 2);			
		}
		for (int i = 0; i < 1000; i++) {
			checkModPow4(i, 3037000499L);			
		}
	}
	
	public void test5() {
		for (int i = 0; i < 1000; i++) {
			checkModPow5(i, i + 2);			
		}
		for (int i = 0; i < 1000; i++) {
			checkModPow5(i, 3037000499L);			
		}
	}
	
	private void checkModPow(long n, long m, long modPow) {
		assertEquals("2^" + n + " (mod " + m + ")", BigInteger.valueOf(2).modPow(BigInteger.valueOf(n), BigInteger.valueOf(m)).longValue(), modPow);
	}

	private void checkModPow1(long n, long m) {
		checkModPow(n, m, Powers.modPow1(n, m));
	}

	private void checkModPow2(long n, long m) {
		checkModPow(n, m, Powers.modPow2(n, m));
	}

	private void checkModPow3(long n, long m) {
		checkModPow(n, m, Powers.modPow3(n, m));
	}

	private void checkModPow4(long n, long m) {
		checkModPow(n, m, Powers.modPow4(n, m));
	}

	private void checkModPow5(long n, long m) {
		checkModPow(n, m, Powers.modPow5(n, m));
	}
}
