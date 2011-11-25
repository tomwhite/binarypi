package org.tiling.pi.util;

public class Powers {

	private Powers() {
	}
	
	/**
	 * Calculates 2<sup>n</sup> mod m using the binary scheme for exponentiation. 
	 * See <i>"The Book of Prime Number Records"</i> by Paulo Ribenboim, p38.
	 * @throws IllegalArgumentException if any of the arguments are negative.
	 */
	public final static long modPow1(long n, long m) throws IllegalArgumentException {
			final int a = 2;

	   if (n < 0 || m < 0)
		  throw new IllegalArgumentException();

	   if (m == 0)
		  return 0;

	   if (n == 0)
		  return 1;

	   // Find the binary expansion of n
	   // n = n_bin[k] * 2^k + n_bin[k - 1] * 2^(k - 1) + ... + n_bin[1] * 2 + n_bin[0] 
	   long exp = n;
	   int n_bin[] = new int[(int) (Math.log(n) / Math.log(2) + 2)]; // guarantees enough space
	   int k = -1;
	   while (exp != 0) {
		  n_bin[++k] = (int) exp % 2;
		  exp = exp / 2;
	   }

	   // Loop to calculate r_j
	   long r_j = a % m; // r_0
	   for (int j = 1; j <= k; j++) {
		r_j = r_j * r_j % m;
		  if (n_bin[k - j] == 1)
			 r_j = a * r_j % m;
	   }

	   return r_j;

	}
	
	/**
	 * Calculates 2<sup>n</sup> mod m using the right-to-left binary scheme for exponentiation. 
	 * Based on code by Fabrice Bellard.
	 */
	public final static long modPow2(long n, long m) {
		long r = 1;
		long aa = 2;
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
	 * Calculates 2<sup>n</sup> mod m using the right-to-left binary scheme for exponentiation. 
	 * Based on code by Fabrice Bellard.
	 */
	public final static long modPow2i(int n, int m) {
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

	private static final int ntp = 64;
	private static final long[] tp = new long[ntp];
	
	static {
	    tp[0] = 1;
	    for (int i = 1; i < ntp; i++) tp[i] = 2 * tp[i-1];
	}
	
	/**
	 * Calculates 2<sup>n</sup> mod m using the left-to-right binary scheme for exponentiation. 
	 * Based on code by David Bailey.
	 */
	public final static long modPow3(long n, long m) {
	    if (n == 0) return 1;
	    if (m == 1) return 0;
	    
	    int i = 0;
	    while (true) if (tp[i++] > n) break;

	    long pt = tp[i-1];
	    long r = 1;
	    
	    for (int j = 0; j < i; j++) {
	        if (n >= pt){
	          r = (r + r) % m;
	          n = n - pt;
	        }
	        pt = pt >> 1;
	        if (pt >= 1){
	          r = r * r % m;
	        }
	      }

	      return r;	    
	    
	}

	/**
	 * Calculates 2<sup>y</sup> mod N using the left-to-right binary scheme for exponentiation. 
	 * Based on pseudo-code by Crandall and Pomerance.
	 */
	public final static long modPow4(long y, long N) {
	    if (y == 0) return 1;
	    if (y == 1) return 2;
	    if (N == 1) return 0;
	    
	    int D = 0;
	    while (true) {
	    	if (tp[D] > y) break;
	    	D++;
	    }
	    long y_j = tp[D - 2];

	    long z = 2;
	    for (int j = D - 2; j >= 0; j--) {
				z = z * z % N;
				if ((y & y_j) != 0)
					z = (z + z) % N;
				y_j = y_j >> 1;
			}
	    return z;
	    
	}
	
	
	/**
	 * Calculates 2<sup>y</sup> mod N using the left-to-right binary scheme for exponentiation. 
	 * Based on pseudo-code by Crandall and Pomerance.
	 */
	public final static long modPow5(long y, long N) {
    if (y == 0) return 1;
    if (y == 1) return 2;
		long z = 2;
		long a = 1;
		while(true) {
			if ((y & 1) != 0)
				a = z * a % N;
			z = z * z % N;
			y = y >> 1;
			if (y == 1 || y == 0)
				break;
		}
		return a * z % N;
	}	
	
}
