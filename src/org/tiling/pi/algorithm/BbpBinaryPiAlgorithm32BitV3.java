package org.tiling.pi.algorithm;

import org.tiling.pi.FractionalPartCalculator;

/**
 * I am a re-implementation of {@link BbpBinaryPiAlgorithm32BitV2} with a faster modPow implementation.
 */
public final class BbpBinaryPiAlgorithm32BitV3 implements FractionalPartCalculator {

	/**
	 * The "machine epsilon". Used as a termination criterion for infinite sums.
	 * See http://crd.lbl.gov/~dhbailey/piqp.c and http://www.math.byu.edu/~schow/work/IEEEFloatingPoint.htm
	 * for confirmation that this is correct.
	 * Note that this cannot be the only termination criterion!
	 */
	private static double EPSILON = 1e-17;

	/**
	 * Calculates 2^n * pi (mod 1)
	 * This is only guaranteed to produce correct results for 8 * floor (n / 4) + 6 <= 46340.
	 * That is, n <= 23167.
	 */
	public final double calculateFractionalPart(int n) {

		final int b = 2, c = 4;

		final int floor_n_over_c = n / c;

		int b_raised = n; // n - c * k

		int p_k = -2;
		double S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0;

		int k = 0;
		for ( ; k <= floor_n_over_c; k++) {

			p_k += 3; // 8k + 1
			S_1 += modPow(b_raised, p_k) / (double) p_k; 
			S_1 = S_1 - Math.floor(S_1); // S_1 mod 1

			p_k += 3; // 8k + 4
			S_2 += modPow(b_raised, p_k) / (double) p_k; 
			S_2 = S_2 - Math.floor(S_2); // S_2 mod 1

			p_k++; // 8k + 5
			S_3 += modPow(b_raised, p_k) / (double) p_k; 
			S_3 = S_3 - Math.floor(S_3); // S_3 mod 1

			p_k++; // 8k + 6
			S_4 += modPow(b_raised, p_k) / (double) p_k; 
			S_4 = S_4 - Math.floor(S_4); // S_4 mod 1

			b_raised -= c;
		}

		// Now k == floor_n_over_c + 1

		double b_lowered = 1.0 / pow(b, c * k - n); // c * k - n > 0
		int b_to_the_c = (int) pow(b, c);

		for (int count = 0 ; count < 100; count++) {
			p_k += 3; // 8k + 1
			S_1 += b_lowered / p_k;
			S_1 = S_1 - Math.floor(S_1); // S_1 mod 1

			p_k += 3; // 8k + 4
			S_2 += b_lowered / p_k;
			S_2 = S_2 - Math.floor(S_2); // S_2 mod 1

			p_k++; // 8k + 5
			S_3 += b_lowered / p_k;
			S_3 = S_3 - Math.floor(S_3); // S_3 mod 1

			p_k++; // 8k + 6
			S_4 += b_lowered / p_k;
			S_4 = S_4 - Math.floor(S_4); // S_4 mod 1

			b_lowered /= b_to_the_c;
			if (b_lowered < EPSILON) {
				break;
			}

		}

		double S = 4 * S_1 - 2 * S_2 - S_3 - S_4;
		S = S - Math.floor(S);  // S mod 1
		return S;

	}

	/**
	 * This is only guaranteed to produce correct results for <code>m</code> <= 46340.
	 */	
	public final static int modPow(int n, int m) {
		int r = 1;
		int aa = 2;
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

	/**
	 * Calculates a<sup>n</sup> using the binary scheme for exponentiation. 
	 * See <i>"The Book of Prime Number Records"</i> by Paulo Ribenboim, p38.
	 * Note that 0<sup>0</sup> returns 0 in this implementation. Beware of
	 * overflow - no warning is given!
	 * @throws IllegalArgumentException if any of the arguments are negative.
	 */
	public final static long pow(int a, int n) throws IllegalArgumentException {

	   if (a < 0 || n < 0)
		  throw new IllegalArgumentException();

	   if (a == 0)
		  return 0;

	   if (n == 0)
		  return 1;

	   // Find the binary expansion of n
	   // n = n_bin[k] * 2^k + n_bin[k - 1] * 2^(k - 1) + ... + n_bin[1] * 2 + n_bin[0] 
	   int exp = n;
	   int n_bin[] = new int[(int) (Math.log(n) / Math.log(2) + 2)]; // guarantees enough space
	   int k = -1;
	   while (exp != 0) {
		  n_bin[++k] = exp % 2;
		  exp = exp / 2;
	   }

	   // Loop to calculate r_j
	   long r_j = a; // r_0
	   for (int j = 1; j <= k; j++) {
		  if (n_bin[k - j] == 0)
			 r_j = r_j * r_j;
		  else
			 r_j = a * r_j * r_j;
	   }

	   return r_j;

	}

}