package org.tiling.pi.algorithm.parallel;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.tiling.computefarm.CancelledException;
import org.tiling.computefarm.CannotTakeResultException;
import org.tiling.computefarm.CannotWriteTaskException;
import org.tiling.computefarm.ComputeSpace;
import org.tiling.computefarm.Job;

public class BellardBinaryPiAlgorithm64BitJob implements Job {
	
  private static final Logger logger = Logger.getLogger(BellardBinaryPiAlgorithm64BitJob.class.getName());
	
	/**
	 * The "machine epsilon". Used as a termination criterion for infinite sums.
	 * See http://crd.lbl.gov/~dhbailey/piqp.c and http://www.math.byu.edu/~schow/work/IEEEFloatingPoint.htm
	 * for confirmation that this is correct.
	 * Note that this cannot be the only termination criterion!
	 */
	private static double EPSILON = 1e-17;
	
	protected static final int b = 2, c = 10, b_to_the_c = 1024;
  
	private final int n;
	private final int taskSize;
	private final int floor_n_over_c;
	
	private double S;
	
	public BellardBinaryPiAlgorithm64BitJob(int n) {
		this.n = n;
		this.taskSize = 100000;
		this.floor_n_over_c = n / c;
	}

	public void generateTasks(ComputeSpace computeSpace) {
		for (int taskNumber = 0; taskNumber <= floor_n_over_c / taskSize; taskNumber++) {
			int k_start = taskNumber * taskSize;
			int k_end = Math.min(k_start + taskSize - 1, floor_n_over_c);
			try {
				computeSpace.write(new BellardBinaryPiAlgorithm64BitTask(n, k_start,
						k_end));
			} catch (CannotWriteTaskException e) {
				logger.log(Level.INFO, "Problem writing task " + taskNumber + " to space.", e);
				break;
			} catch (CancelledException e) {
				logger.info("Cancelled after writing " + taskNumber + " tasks to space.");
				break;
			}
		}
	}

	public void collectResults(ComputeSpace computeSpace) {
		double S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0, S_5 = 0.0, S_6 = 0.0, S_7 = 0.0;
		for (int resultNumber = 0; resultNumber <= floor_n_over_c / taskSize; resultNumber++) {
			try {
				double[] result = (double[]) computeSpace.take();
				int i = 0;

				S_1 += result[i++];
				S_2 += result[i++];
				S_3 += result[i++];
				S_4 += result[i++];
				S_5 += result[i++];
				S_6 += result[i++];
				S_7 += result[i++];
				
				S_1 = S_1 - (int) S_1;
				S_2 = S_2 - (int) S_2;
				S_3 = S_3 - (int) S_3;
				S_4 = S_4 - (int) S_4;
				S_5 = S_5 - (int) S_5;
				S_6 = S_6 - (int) S_6;
				S_7 = S_7 - (int) S_7;
				
			} catch (CannotTakeResultException e) {
			  logger.log(Level.INFO, "Problem taking result " + resultNumber + " from space.", e);
				break;
			} catch (CancelledException e) {
				logger.info("Cancelled after retrieving " + resultNumber + " results from space.");
				break;
      }
		}	   		
		
		double[] part2 = computeSecondSummation(n);
		int i = 0;

		S_1 += part2[i++];
		S_2 += part2[i++];
		S_3 += part2[i++];
		S_4 += part2[i++];
		S_5 += part2[i++];
		S_6 += part2[i++];
		S_7 += part2[i++];
		
		S_1 = S_1 - (int) S_1;
		S_2 = S_2 - (int) S_2;
		S_3 = S_3 - (int) S_3;
		S_4 = S_4 - (int) S_4;
		S_5 = S_5 - (int) S_5;
		S_6 = S_6 - (int) S_6;
		S_7 = S_7 - (int) S_7;

		S = -32 * S_1 - S_2 + 256 * S_3
				- 64 * S_4 - 4 * S_5 -4 * S_6 + S_7;
		S = S - Math.floor(S);  // S mod 1
		
	}
	
	public double getS() {
		return S;
	}

	private double[] computeSecondSummation(int n) {
		double S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0, S_5 = 0.0, S_6 = 0.0, S_7 = 0.0;

		int k = n / c + 1;
		int p_k_4 = 4 * k - 1;
		int p_k_10 = 10 * k - 1;		
		
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
		return new double[] { S_1, S_2, S_3, S_4, S_5, S_6, S_7 };
	}

	/**
	 * This is only guaranteed to produce correct results for <code>m</code> <= 3037000499.
	 */	
	protected final static long modPow(long n, long m) {
		long r = 1;
		long aa = b;
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
