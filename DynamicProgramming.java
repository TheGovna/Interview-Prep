/**
Climbing Stairs
Best Time to Buy and Sell Stock
*/

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

// ----------------------------------------------------------------

// Best Time to Buy and Sell Stock
// Say you have an array for which the ith element is the price of a
// given stock on day i. If you were only permitted to complete at
// most one transaction (i.e. buy one and sell one share of the
// stock), design an algorithm to find the maximum profit.
// Source: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

// Solution:
int maxProfit(int[] prices) {
    if (prices.length == 0 || prices.length == 1) {
        return 0;
    }
    
    int[] diffs = new int[prices.length - 1];
    
    for (int i = 1; i < diffs.length + 1; i++) {
        diffs[i - 1] = prices[i] - prices[i - 1];
    }
    
    return maxProfitWithDiffs(diffs);
}

int maxProfitWithDiffs(int[] diffs) {
    int maxProfitSoFar = diffs[0];
    int maxProfitEndingHere = diffs[0];
    
    for (int i = 1; i < diffs.length; i++) {
        maxProfitEndingHere = Math.max(diffs[i], maxProfitEndingHere + diffs[i]);
        maxProfitSoFar = Math.max(maxProfitSoFar, maxProfitEndingHere);
    }
    
    return Math.max(maxProfitSoFar, 0);
}