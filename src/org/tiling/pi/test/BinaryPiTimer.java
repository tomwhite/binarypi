package org.tiling.pi.test;

import org.tiling.pi.FractionalPartCalculator;
import org.tiling.pi.algorithm.BbpBinaryPiAlgorithm32BitV2;
import org.tiling.pi.algorithm.BbpBinaryPiAlgorithm32BitV3;
import org.tiling.pi.algorithm.BbpBinaryPiAlgorithm32BitV4;
import org.tiling.pi.algorithm.BellardBinaryPiAlgorithm32BitV1;
import org.tiling.pi.algorithm.BellardBinaryPiAlgorithm64BitV1;

public class BinaryPiTimer {
	public static void main(String[] args) throws Exception {
		System.out.println("BbpBinaryPiAlgorithm32BitV4. Duration: " + time(new BbpBinaryPiAlgorithm32BitV4(), 20000, 100) + "ms");
		System.out.println("BbpBinaryPiAlgorithm32BitV3. Duration: " + time(new BbpBinaryPiAlgorithm32BitV3(), 20000, 100) + "ms");
		System.out.println("BbpBinaryPiAlgorithm32BitV2. Duration: " + time(new BbpBinaryPiAlgorithm32BitV2(), 20000, 100) + "ms");
		System.out.println("BellardBinaryPiAlgorithm32BitV1. Duration: " + time(new BellardBinaryPiAlgorithm32BitV1(), 20000, 100) + "ms");
		System.out.println("BellardBinaryPiAlgorithm64BitV1. Duration: " + time(new BellardBinaryPiAlgorithm64BitV1(), 20000, 100) + "ms");
	}
	
	private static long time(FractionalPartCalculator fractionalPartCalculator, int n, int runs) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			fractionalPartCalculator.calculateFractionalPart(n);
		}
		return (System.currentTimeMillis() - startTime);		
	}

}
