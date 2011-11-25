package org.tiling.pi.algorithm;

import org.tiling.pi.FractionalPartCalculator;

/**
 * I am an improvement over the Bailey, Borwein and Plouffe formula for pi due to <a href="http://fabrice.bellard.free.fr/pi/">Fabrice Bellard</a>.
 */
public final class BellardBinaryPiAlgorithm32BitV1 implements FractionalPartCalculator {

	/**
	 * The "machine epsilon". Used as a termination criterion for infinite sums.
	 * See http://crd.lbl.gov/~dhbailey/piqp.c and http://www.math.byu.edu/~schow/work/IEEEFloatingPoint.htm
	 * for confirmation that this is correct.
	 * Note that this cannot be the only termination criterion!
	 */
	private static double EPSILON = 1e-17;

	private static final int b = 2, c = 10, b_to_the_c = 1024;

	/**
	 * Calculates 2^n * pi (mod 1).
	 * This is only guaranteed to produce correct results for 10 * floor (n / 10) + 9 <= 46340.
	 * That is, n <= 46331. 
	 */
	public final double calculateFractionalPart(int n) {
		if (n < 6) {
			throw new IllegalArgumentException("n must be at least 6");
		}
		
		n = n - 6;
		
		final int floor_n_over_c = n / c;

		int b_raised = n; // n - c * k

		int p_k_4 = -1;
		int p_k_10 = -1;
		double S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0, S_5 = 0.0, S_6 = 0.0, S_7 = 0.0;

		int k = 0;
		for ( ; k <= floor_n_over_c; k++) {
			
			if ((k & 1) == 0) { // even

				p_k_4 += 2; // 4k + 1
				S_1 += modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_1 = S_1 - (int) S_1; // S_1 mod 1
	
				p_k_4 += 2; // 4k + 3
				S_2 += modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_2 = S_2 - (int) S_2; // S_2 mod 1
	
				p_k_10 += 2; // 10k + 1
				S_3 += modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_3 = S_3 - (int) S_3; // S_3 mod 1
	
				p_k_10 += 2; // 10k + 3
				S_4 += modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_4 = S_4 - (int) S_4; // S_4 mod 1
	
				p_k_10 += 2; // 10k + 5
				S_5 += modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_5 = S_5 - (int) S_5; // S_5 mod 1
	
				p_k_10 += 2; // 10k + 7
				S_6 += modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_6 = S_6 - (int) S_6; // S_6 mod 1
	
				p_k_10 += 2; // 10k + 9
				S_7 += modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_7 = S_7 - (int) S_7; // S_7 mod 1
	
			} else { //odd
				
				// TODO: replace Math.floor with casts.
				
				p_k_4 += 2; // 4k + 1
				S_1 -= modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_1 = S_1 - Math.floor(S_1); // S_1 mod 1
	
				p_k_4 += 2; // 4k + 3
				S_2 -= modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_2 = S_2 - Math.floor(S_2); // S_2 mod 1
	
				p_k_10 += 2; // 10k + 1
				S_3 -= modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_3 = S_3 - Math.floor(S_3); // S_3 mod 1
	
				p_k_10 += 2; // 10k + 3
				S_4 -= modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_4 = S_4 - Math.floor(S_4); // S_4 mod 1
	
				p_k_10 += 2; // 10k + 5
				S_5 -= modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_5 = S_5 - Math.floor(S_5); // S_5 mod 1
	
				p_k_10 += 2; // 10k + 7
				S_6 -= modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_6 = S_6 - Math.floor(S_6); // S_6 mod 1
	
				p_k_10 += 2; // 10k + 9
				S_7 -= modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_7 = S_7 - Math.floor(S_7); // S_7 mod 1
	
			}

			b_raised -= c;
			
		}

		// Now k == floor_n_over_c + 1

		double b_lowered = 1.0 / Math.pow(b, c * k - n); // c * k - n > 0

		for (int count = 0 ; count < 100; count++) {
			
			if ((k & 1) == 0) { // even

				p_k_4 += 2; // 4k + 1
				S_1 += b_lowered / p_k_4; 
				S_1 = S_1 - (int) S_1; // S_1 mod 1
	
				p_k_4 += 2; // 4k + 3
				S_2 += b_lowered / p_k_4; 
				S_2 = S_2 - (int) S_2; // S_2 mod 1
	
				p_k_10 += 2; // 10k + 1
				S_3 += b_lowered / p_k_10; 
				S_3 = S_3 - (int) S_3; // S_3 mod 1
	
				p_k_10 += 2; // 10k + 3
				S_4 += b_lowered / p_k_10;
				S_4 = S_4 - (int) S_4; // S_4 mod 1
	
				p_k_10 += 2; // 10k + 5
				S_5 += b_lowered / p_k_10;
				S_5 = S_5 - (int) S_5; // S_5 mod 1
	
				p_k_10 += 2; // 10k + 7
				S_6 += b_lowered / p_k_10;
				S_6 = S_6 - (int) S_6; // S_6 mod 1
	
				p_k_10 += 2; // 10k + 9
				S_7 += b_lowered / p_k_10;
				S_7 = S_7 - (int) S_7; // S_7 mod 1
	
			} else { //odd
				
				// TODO: replace Math.floor with casts.
				
				p_k_4 += 2; // 4k + 1
				S_1 -= b_lowered / p_k_4; 
				S_1 = S_1 - Math.floor(S_1); // S_1 mod 1
	
				p_k_4 += 2; // 4k + 3
				S_2 -= b_lowered / p_k_4; 
				S_2 = S_2 - Math.floor(S_2); // S_2 mod 1
	
				p_k_10 += 2; // 10k + 1
				S_3 -= b_lowered / p_k_10;
				S_3 = S_3 - Math.floor(S_3); // S_3 mod 1
	
				p_k_10 += 2; // 10k + 3
				S_4 -= b_lowered / p_k_10;
				S_4 = S_4 - Math.floor(S_4); // S_4 mod 1
	
				p_k_10 += 2; // 10k + 5
				S_5 -= b_lowered / p_k_10;
				S_5 = S_5 - Math.floor(S_5); // S_5 mod 1
	
				p_k_10 += 2; // 10k + 7
				S_6 -= b_lowered / p_k_10;
				S_6 = S_6 - Math.floor(S_6); // S_6 mod 1
	
				p_k_10 += 2; // 10k + 9
				S_7 -= b_lowered / p_k_10;
				S_7 = S_7 - Math.floor(S_7); // S_7 mod 1
	
			}

			b_lowered /= b_to_the_c;
			if (b_lowered < EPSILON) {
				break;
			}

			k++;
		}

		double S = -32 * S_1 - S_2 + 256 * S_3
				- 64 * S_4 - 4 * S_5 -4 * S_6 + S_7;
		S = S - Math.floor(S);  // S mod 1
		return S;

	}

	/**
	 * This is only guaranteed to produce correct results for <code>m</code> <= 46340.
	 */	
	private final static int modPow(int n, int m) {
		int r = 1;
		int aa = b;
		while (true) {
			if ((n & 1) != 0)
				r = r * aa % m;
			n = n >> 1;
			if (n == 0)
				break;
			aa = aa * aa % m;
		}
		return r; 	
	}

}