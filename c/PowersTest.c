#include <stdio.h>
#include <time.h>

int main() {
  long int i;
  int j;
  i = 33700049;
  j = 1;
  printf("i: %i\n", i);
  printf("sizeof long: %i\n", sizeof(i));
  printf("sizeof int: %i\n", sizeof(j));
  printf("Powers.modPow2. Duration: %ims\n", time2(1459022517, 46340, 100000));
  return 0;
}

int modPow2(int n, int m) {
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

int time2(int n, int m, int runs) {
  int i;
  clock_t startTime, endTime;
  
  startTime = clock();
  for (i = 0; i < runs; i++) {
	modPow2(n, m);
  }
  endTime = clock();
  return (endTime - startTime);
}
