package org.tiling.pi.test;

import org.tiling.pi.util.Powers;

public class PowersTimer {

	public static void main(String[] args) {
		final long n = 1082370972359218828L; // big number
		final long m = 3037000499L; //sqrt (2 ^ 63 - 1)
		final int runs = 100000;

		System.out.println("Powers.modPow1. Duration: " + time1(n, m, runs) + "ms");
		System.out.println("Powers.modPow2. Duration: " + time2(n, m, runs) + "ms");
		System.out.println("Powers.modPow2i. Duration: " + time2i(1459022517, 46340, runs) + "ms");
		System.out.println("Powers.modPow3. Duration: " + time3(n, m, runs) + "ms");
		System.out.println("Powers.modPow4. Duration: " + time4(n, m, runs) + "ms");
		System.out.println("Powers.modPow5. Duration: " + time5(n, m, runs) + "ms");
	}
	
	private static long time1(long n, long m, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			Powers.modPow2(n, m);
		}
		return (System.currentTimeMillis() - startTime);
	}

	private static long time2(long n, long m, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			Powers.modPow2(n, m);
		}
		return (System.currentTimeMillis() - startTime);
	}

	private static long time2i(int n, int m, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			Powers.modPow2i(n, m);
		}
		return (System.currentTimeMillis() - startTime);
	}

	private static long time3(long n, long m, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			Powers.modPow3(n, m);
		}
		return (System.currentTimeMillis() - startTime);
	}

	private static long time4(long n, long m, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			Powers.modPow4(n, m);
		}
		return (System.currentTimeMillis() - startTime);
	}

	private static long time5(long n, long m, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			Powers.modPow5(n, m);
		}
		return (System.currentTimeMillis() - startTime);
	}

}
