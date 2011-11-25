package org.tiling.pi.algorithm;

import org.tiling.pi.FractionalPartCalculator;

/**
 * I implement the algorithm for computing individual binary
 * digits of pi as described in <i>"On the Rapid Computation of Various
 * Polylogarithmic Constants"</i> by David Bailey, Peter Borwein and
 * Simon Plouffe. Get it
 * <a href="http://www.cecm.sfu.ca/~pborwein/PAPERS/P123.pdf">here</a>.
 * <p>
 * It is not known at present how many binary digits of pi can be accurately
 * calculated using this implementation!
 */
public final class BbpBinaryPiAlgorithm32BitV2 implements FractionalPartCalculator {

	/**
	 * The "machine epsilon". Used as a termination criterion for infinite sums.
	 * See http://crd.lbl.gov/~dhbailey/piqp.c and http://www.math.byu.edu/~schow/work/IEEEFloatingPoint.htm
	 * for confirmation that this is correct.
	 * Note that this cannot be the only termination criterion!
	 */
	private static double EPSILON = 1e-17;

	/**
	 * Calculates 2^n * pi (mod 1)
	 */
	public final double calculateFractionalPart(int n) {

		int b = 2, c = 4;

		int floor_n_over_c = n / c;

		int b_raised = n; // n - c * k

		int p_k = -2;
		double S;
		double S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0;

		int k = 0;
		for ( ; k <= floor_n_over_c; k++) {

			p_k += 3; // 8k + 1
			S_1 += modPow(b, b_raised, p_k) / (double) p_k; 
			S_1 = S_1 - Math.floor(S_1); // S_1 mod 1

			p_k += 3; // 8k + 4
			S_2 += modPow(b, b_raised, p_k) / (double) p_k; 
			S_2 = S_2 - Math.floor(S_2); // S_2 mod 1

			p_k++; // 8k + 5
			S_3 += modPow(b, b_raised, p_k) / (double) p_k; 
			S_3 = S_3 - Math.floor(S_3); // S_3 mod 1

			p_k++; // 8k + 6
			S_4 += modPow(b, b_raised, p_k) / (double) p_k; 
			S_4 = S_4 - Math.floor(S_4); // S_4 mod 1

			b_raised -= c;
		}

		// Now k == floor_n_over_c + 1

		double b_lowered = 1.0 / pow(b, c * k - n); // c * k - n > 0
		int b_to_the_c = (int) pow(b, c);

		do {
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

		} while (b_lowered > EPSILON);

		S = 4 * S_1 - 2 * S_2 - S_3 - S_4;
		S = S - Math.floor(S);  // S mod 1

		return S;

	}

	/**
	 * Calculates a<sup>n</sup> mod N using the binary scheme for exponentiation. 
	 * See <i>"The Book of Prime Number Records"</i> by Paulo Ribenboim, p38.
	 * Note that 0<sup>0</sup> returns 0 in this implementation.
	 * @throws IllegalArgumentException if any of the arguments are negative.
	 */
	public final static int modPow(int a, int n, int N) throws IllegalArgumentException {

	   if (a < 0 || n < 0 || N < 0)
		  throw new IllegalArgumentException();

	   if (a == 0 || N == 0)
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
	   int r_j = a % N; // r_0
	   for (int j = 1; j <= k; j++) {
		  if (n_bin[k - j] == 0)
			 r_j = r_j * r_j % N;
		  else
			 r_j = a * r_j * r_j % N;
	   }

	   return r_j;

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