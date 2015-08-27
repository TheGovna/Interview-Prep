// Climbing Stairs
// You are climbing a stair case. It takes n steps to reach the top. 
// Each time you can either climb 1 or 2 steps. In hoany distinct ways
// can you climb to the top?
// Source: https://leetcode.com/problems/climbing-stairs/

// if n = 0 -> climb 0 steps -> return 1
// if n = 1 -> climb 1 step -> return 1
// if n = 2 -> climb 1 + 1 or 2 + 0 -> return 2
// if n = 3 -> climb 1 + 1 + 1 or 2 + 1 or 1 + 2 -> return 3
// if n = 4 -> climb 1 + 1 + 1 + 1 or 1 + 1 + 2 or 1 + 2 + 1 or 2 + 1 + 1 or 2 + 2 -> return 5
// IT'S THE FIBONACCI SEQUENCE!!!

// Solution:
int climbStairs() {
	if (n == 0 || n == 1) {
		return 1;
	}

	int[] array = new int[n + 1];
	array[0] = 1;
	array[1] = 1;

	for(int i = 2; i < array.length; i++) {
		array[i] = array[i - 1] + array[i - 2];
	}

	return array[n];
}