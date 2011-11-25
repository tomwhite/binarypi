#include <stdio.h>
#include <time.h>

#define EPSILON 1e-17

int main() {
  printf("BbpBinaryPiAlgorithm32BitV4. Duration: %ims\n", timer(20000, 100));
  return 0;
}

double calculateFractionalPart(int n) {

    int b = 2, c = 4, b_to_the_c = 16;
    int floor_n_over_c;
    int b_raised;
    int p_k;
    double S_1, S_2, S_3, S_4;
    int k;
    double b_lowered;
    int count;
    double S;

	floor_n_over_c = n / c;

	b_raised = n; // n - c * k

	p_k = -2;
	S_1 = 0.0, S_2 = 0.0, S_3 = 0.0, S_4 = 0.0;

	k = 0;
	for ( ; k <= floor_n_over_c; k++) {

		p_k += 3; // 8k + 1
		S_1 += modPow(b_raised, p_k) / (double) p_k; 
		S_1 = S_1 - (int) S_1; // S_1 mod 1

		p_k += 3; // 8k + 4
		S_2 += modPow(b_raised, p_k) / (double) p_k; 
		S_2 = S_2 - (int) S_2; // S_2 mod 1

		p_k++; // 8k + 5
		S_3 += modPow(b_raised, p_k) / (double) p_k; 
		S_3 = S_3 - (int) S_3; // S_3 mod 1

		p_k++; // 8k + 6
		S_4 += modPow(b_raised, p_k) / (double) p_k; 
		S_4 = S_4 - (int) S_4; // S_4 mod 1

		b_raised -= c;
	}

	// Now k == floor_n_over_c + 1

	b_lowered = 1.0 / pow(b, c * k - n); // c * k - n > 0

	for (count = 0 ; count < 100; count++) {
		p_k += 3; // 8k + 1
		S_1 += b_lowered / p_k;
		S_1 = S_1 - (int) S_1; // S_1 mod 1

		p_k += 3; // 8k + 4
		S_2 += b_lowered / p_k;
		S_2 = S_2 - (int) S_2; // S_2 mod 1

		p_k++; // 8k + 5
		S_3 += b_lowered / p_k;
		S_3 = S_3 - (int) S_3; // S_3 mod 1

		p_k++; // 8k + 6
		S_4 += b_lowered / p_k;
		S_4 = S_4 - (int) S_4; // S_4 mod 1

		b_lowered /= b_to_the_c;
		if (b_lowered < EPSILON) {
			break;
		}

	}

	S = 4 * S_1 - 2 * S_2 - S_3 - S_4;
	S = S - (int) S; // S mod 1
	return S;

}

int modPow(int n, int m) {
	int r,aa;
	r = 1;
	aa = 2;
	while (1) {
		if (n & 1)
			r = r * aa % m;
		n = n >> 1;
		if (n == 0)
			break;
		aa = aa * aa % m;
	}
	return r; 	
}

int timer(int n, int runs) {
  int i;
  clock_t startTime, endTime;
  
  startTime = clock();
  for (i = 0; i < runs; i++) {
	calculateFractionalPart(n);
  }
  endTime = clock();
  return (endTime - startTime);
}
