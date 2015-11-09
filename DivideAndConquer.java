/**
Different Ways to Add Parentheses
Pow(x, n)
*/

// Different Ways to Add Parentheses
// Given a string of numbers and operators, return all possible 
// results from computing all the different possible ways to group 
// numbers and operators. The valid operators are +, - and *.
// Input: "2-1-1"
// ((2-1)-1) = 0
// (2-(1-1)) = 2
// Output: [0, 2]
// Source: https://leetcode.com/problems/different-ways-to-add-parentheses/
// Solution adapted from: https://youtu.be/et6DV1D0EKM

// Solution:
public List<Integer> diffWaysToCompute(String input) {
    List<Integer> result = new ArrayList<Integer>();
    
    for (int i = 0; i < input.length(); i++) {
        
        char c = input.charAt(i);
        
        if (c == '+' || c == '-' || c == '*') {
            List<Integer> left = diffWaysToCompute(input.substring(0, i));
            List<Integer> right = diffWaysToCompute(input.substring(i + 1));
            
            for (Integer m : left) {
                for (Integer n : right) {
                    if (c == '+') {
                        result.add(m + n);
                    } else if (c == '-') {
                        result.add(m - n);
                    } else {
                        result.add(m * n);
                    }
                }
            }
        }
    }
    
    if (result.isEmpty()) {
        result.add(Integer.parseInt(input));
    }
    
    return result;
}

// ----------------------------------------------------------------

// Pow(x, n)
// Source: https://leetcode.com/problems/powx-n/

// Solution
public double myPow(double x, int n) {
    if (n == 0) {
        return 1;
    }
    
    if (n == 1) {
        return x;
    }
    
    if (n < 0) {
        return 1 / myPow(x, -n);
    }
    
    if (n % 2 == 0) {
        double temp = myPow(x, n / 2);
        return temp * temp;
    }
    
    return x * myPow(x, n - 1);
}