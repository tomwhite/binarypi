package org.tiling.pi.algorithm.parallel;

import org.tiling.computefarm.Task;

public class BellardBinaryPiAlgorithm64BitTask implements Task {
	
	private static final int c = BellardBinaryPiAlgorithm64BitJob.c;

	private final int n;
	private final int k_start;
	private final int k_end;

	public BellardBinaryPiAlgorithm64BitTask(int n, int k_start, int k_end) {
		this.n = n;
		this.k_start = k_start;
		this.k_end = k_end;
	}

	public Object execute() {
		double S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0, S_5 = 0.0, S_6 = 0.0, S_7 = 0.0;

		int k = k_start;
		int p_k_4 = 4 * k - 1;
		int p_k_10 = 10 * k - 1;
		int b_raised = n - c * k;
		for ( ; k <= k_end; k++) {
			
			if ((k & 1) == 0) { // even

				p_k_4 += 2; // 4k + 1
				S_1 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_1 = S_1 - (int) S_1; // S_1 mod 1
	
				p_k_4 += 2; // 4k + 3
				S_2 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_2 = S_2 - (int) S_2; // S_2 mod 1
	
				p_k_10 += 2; // 10k + 1
				S_3 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_3 = S_3 - (int) S_3; // S_3 mod 1
	
				p_k_10 += 2; // 10k + 3
				S_4 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_4 = S_4 - (int) S_4; // S_4 mod 1
	
				p_k_10 += 2; // 10k + 5
				S_5 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_5 = S_5 - (int) S_5; // S_5 mod 1
	
				p_k_10 += 2; // 10k + 7
				S_6 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_6 = S_6 - (int) S_6; // S_6 mod 1
	
				p_k_10 += 2; // 10k + 9
				S_7 += BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_7 = S_7 - (int) S_7; // S_7 mod 1
	
			} else { //odd
				
				// TODO: replace Math.floor with casts.
				
				p_k_4 += 2; // 4k + 1
				S_1 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_1 = S_1 - Math.floor(S_1); // S_1 mod 1
	
				p_k_4 += 2; // 4k + 3
				S_2 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_4) / (double) p_k_4; 
				S_2 = S_2 - Math.floor(S_2); // S_2 mod 1
	
				p_k_10 += 2; // 10k + 1
				S_3 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_3 = S_3 - Math.floor(S_3); // S_3 mod 1
	
				p_k_10 += 2; // 10k + 3
				S_4 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_4 = S_4 - Math.floor(S_4); // S_4 mod 1
	
				p_k_10 += 2; // 10k + 5
				S_5 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_5 = S_5 - Math.floor(S_5); // S_5 mod 1
	
				p_k_10 += 2; // 10k + 7
				S_6 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_6 = S_6 - Math.floor(S_6); // S_6 mod 1
	
				p_k_10 += 2; // 10k + 9
				S_7 -= BellardBinaryPiAlgorithm64BitJob.modPow(b_raised, p_k_10) / (double) p_k_10; 
				S_7 = S_7 - Math.floor(S_7); // S_7 mod 1
	
			}

			b_raised -= c;
			
		}
		return new double[] { S_1, S_2, S_3, S_4, S_5, S_6, S_7 };

	}

}
