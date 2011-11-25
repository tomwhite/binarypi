package org.tiling.pi.algorithm.parallel;

import org.tiling.computefarm.JobRunner;
import org.tiling.computefarm.JobRunnerFactory;
import org.tiling.pi.FractionalPartCalculator;

/**
 * I am a parallel version of {@link org.tiling.pi.algorithm.BellardBinaryPiAlgorithm64BitV1}.
 */
public final class BellardBinaryPiAlgorithm64Bit implements FractionalPartCalculator {

	/**
	 * Calculates 2^n * pi (mod 1).
	 * This is only guaranteed to produce correct results for 10 * floor (n / 10) + 9 <= 3037000499.
	 * That is, n <= 3037000490.
	 * (Note that since n is an int, the maximum value it may take is actually 2147483647.
	 * This restriction may be lifted at a later date, so the interface takes a long.) 
	 */
	public final double calculateFractionalPart(int n) {
		if (n < 6) {
			throw new IllegalArgumentException("n must be at least 6");
		}
		
		n = n - 6;
		
		BellardBinaryPiAlgorithm64BitJob job = new BellardBinaryPiAlgorithm64BitJob(n);
		JobRunner jobRunner = JobRunnerFactory.newInstance().newJobRunner(job);
		jobRunner.run();
		
		return job.getS();

	}




}