/**
Maximum Subarray
Merge Intervals
Two Sum
Find the first unique character in a string
Valid Palindrome
Reverse Words in a String
Longest Substring Without Repeating Characters
Kth Largest Element in an Array
Permutations of a String
Longest Palindromic Substring
One Edit Distance
Integer to Roman
Roman to Integer
*/

// Maximum Subarray
// Find the contiguous subarray within an array (containing at least
// one number) which has the largest sum.
// Input: [−2,1,−3,4,−1,2,1,−5,4]
// Output: 6 (since subarray with largest sum is [4,−1,2,1])
// Source: https://leetcode.com/problems/maximum-subarray/

// Attempt 1 (Wrong):
int maxSubarray(int[] nums) {
	int maxSum = nums[0];
	int sumSoFar = nums[0];

	if (nums.length == 1) {
		return sumSoFar;
	}

	for (int i = 1; i < nums.length; i++) {
		sumSoFar += nums[i];

		if (sumSoFar >= maxSum) {
			maxSum = sumSoFar;
		} else {
			sumSoFar = 0;
		}
	}

	return maxSum;
}

// Solution:
int maxSubarray(int[] nums) {
	int maxSoFar = nums[0];
	int maxEndingHere = nums[0];

	for (int i = 1; i < nums.length; i++) {
		maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
		maxSoFar = Math.max(maxSoFar, maxEndingHere);
	}

	return maxSoFar;
}

// ----------------------------------------------------------------

// Merge Intervals
// Given a collection of intervals, merge all overlapping intervals.
// Input: [1,3],[2,6],[8,10],[15,18]
// Output: [1,6],[8,10],[15,18]
// Source: https://leetcode.com/problems/merge-intervals/

// Attempt 1 (Time limit exceeded):
List<Interval> merge(List<Interval> intervals) {
	List<Interval> mergedIntervals = new ArrayList<Interval>();

	// Sort the intervals
	Collections.sort(intervals, (a, b)->(a.start - b.start));

	Stack<Interval> stack = new Stack<Interval>();

	for (int i = 0; i < intervals.size(); i++) {
		if (i == 0) {
			stack.push(intervals.get(i));
		} else {
			Interval currentInterval = intervals.get(i);
			Interval topInterval = stack.peek();

			if (currentInterval.start >= topInterval.end) {
				topInterval.end = Math.max(currentInterval.end, topInterval.end);
			} else {
				stack.push(currentInterval);
			}
		}
	}

	while (!stack.isEmpty()) {
		mergedIntervals.add(stack.pop());
	}

	return mergedIntervals;
}

// Solution:
List<Interval> merge(List<Interval> intervals) {
	List<Interval> mergedIntervals = new ArrayList<Interval>();

	if (intervals.isEmpty()) {
		return intervals;
	}

	// Sort the intervals
	Collections.sort(intervals, (a, b)->(a.start - b.start));

	// Use this temporary Interval to merge Intervals
	int mergedStart = intervals.get(0).start;
	int mergedEnd = intervals.get(0).end;

	for (Interval interval : intervals) {
		if (interval.start <= mergedEnd) {
			mergedEnd = Math.max(mergedEnd, interval.end);
		} else {
			mergedIntervals.add(new Interval(mergedStart, mergedEnd));
			mergedStart = interval.start;
			mergedEnd = interval.end;
		}
	}

	// Add the last merged intervals to the solution
	mergedIntervals.add(new Interval(mergedStart, mergedEnd));
	return mergedIntervals;
}

// ----------------------------------------------------------------

// Two Sum
// Given an array of integers, find two numbers such that they add
// up to a specified target number. Return indices of the two numbers
// such that they add up to the target, where index1 < index2.
// index1 and index2 are not zero-based.
// Source: https://leetcode.com/problems/two-sum/

// Attempt 1 (Works, but there's a faster way):
int[] twoSum(int[] nums, int target) {
	// key = nums[i]
	// value = target - nums[i]
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	for(int i = 0; i < nums.length; i++) {
		if (map.values.contains(nums[i])) {
			// return this pair
			int[] result = new int[2];

			for (int j = 0; j < nums.length; j++) {
				if (nums[j] + nums[i] == target) {
					result[0] = j + 1;
					result[1] = i + 1;
					Arrays.sort(result);
					return result;
				}
			}
		}

		map.put(nums[i], target - nums[i]);
	}

	return null;
}

// Solution:
int[] twoSum(int[] nums, int target) {
	// key = nums[i]
	// value = i
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	for(int i = 0; i < nums.length; i++) {
		int x = nums[i];
		if (map.containsKey(target - x)) {
			return new int[] {map.get(target - x) + 1, i + 1};
		}

		map.put(x, i);
	}

	return null;
}

// ----------------------------------------------------------------

// Find the first unique character in a string
// Source: Aaron (Palantir coding challenge)

// Solution:
char getFirstUnique(String str) {
	HashMap<Character, Integer> map = new HashMap<Character, Integer>();

	for(int i = 0; i < str.length; i++) {
		char c = word.charAt(i);

		if (map.containsKey(c)) {
			map.put(c, map.get(c) + 1);
		} else {
			map.put(c, 1);
		}
	}

	// HashMap doesn't preserve order, so go through string again
	for(int i = 0; i < str.length; i++) {
		char c = str.charAt(i);
		if(map.get(c) == 1) {
			return c;
		}
	}

	return null;
}

// ----------------------------------------------------------------

// Valid Palindrome
// Given a string, determine if it is a palindrome, considering only
// alphanumeric chars and ignoring cases.
// Input: "A man, a plan, a canal: Panama"
// Output: true
// Input: "race a car"
// Output: false
// Source: https://leetcode.com/problems/valid-palindrome/

// Attempt 1 (works, but there's an O(1) space solution):
boolean isPalindrome(String s) {
	if(s.isEmpty()) {
		return true;
	}

	StringBuilder sb = new StringBuilder();

	for(Character c : s.toCharArray()) {
		if(Character.isLetterOrDigit(c)) {
			sb.append(c);
		}
	}

	String lcString = sb.toString().toLowerCase();

	for(int i = 0; i < lcString.length() / 2; i++) {
		if(lcString.charAt(i) != lcString.charAt((lcString.length() -1 - i))) {
			return false;
		}
	}

	return true;
}

// Solution:
boolean isPalindrome(String s) {
	int i = 0;
	int j = s.length() - 1;

	while(i < j) {
		while((i < j) && !Character.isLetterOrDigit(s.charAt(i))) {
			i++;
		}

		while((i < j) && !Character.isLetterOrDigit(s.charAt(j))) {
			j--;
		}

		if(Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) {
			return false;
		}

		i++;
		j--;
	}

	return true;
}

// ----------------------------------------------------------------

// Reverse Words in a String
// Given an input string, reverse the string word by word
// Input: "the sky is blue"
// Output: "blue is sky the"
// Source: https://leetcode.com/problems/reverse-words-in-a-string/

// Solution:
public String reverseWords(String s) {
    String str = s.replaceAll("\\s+", " ").trim();
    if (str.length() == 0) {
        return "";
    }
    
    char[] strArray = str.toCharArray();
    reverse(strArray, 0, strArray.length - 1);
    
    int start = 0;
    for (int i = 0; i < strArray.length; i++) {
        if (strArray[i] == ' ') {
            reverse(strArray, start, i - 1);
            start = i + 1;
        }
    }
    
    reverse(strArray, start, strArray.length - 1);
    
    return new String(strArray);
}

public static void reverse(char[] arr, int i, int j) {
    while (i < j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        i++;
        j--;
    }
}

// ----------------------------------------------------------------

// Longest Substring Without Repeating Characters
// Given a string, find the length of the longest substring without 
// repeating characters. For example, the longest substring without 
// repeating letters for "abcabcbb" is "abc", which the length is 3. 
// For "bbbbb" the longest substring is "b", with the length of 1.
// Source: https://leetcode.com/problems/longest-substring-without-repeating-characters/

// Attempt 1:
// Works, but takes O(n) space
int lengthOfLongestSubstring(String s) {
	if (s.length() < 2) {
		return s.length();
	}

	HashMap<Character, Integer> map = new HashMap();
	int maxLength = 0;
	map.put(s.charAt(0), 0);

	int i = 0;
	for (int j = 1; j < s.length(); j++) {
		if (map.containsKey(s.charAt(j)) && (map.get(s.charAt(j)) >= i)) {
			maxLength = Math.max(j - i, maxLength);
			i = map.get(s.charAt(j)) + 1;
			map.put(s.charAt(j), j);
		} else {
			map.put(s.charAt(j), j);

			if (j == s.length() - 1) {
				maxLength = Math.max(j - i + 1, maxLength);
			}
		}
	}

	return maxLength;
}

// Solution:
// Similar logic as above, but O(1) space
int lengthOfLongestSubstring(String s) {
	int[] charMap = new int[256];
	Arrays.fill(charMap, -1);
	int i = 0; maxLength = 0;

	for (int j = 0; j < s.length(); j++) {
		if (charMap[s.charAt(j)] >= i) {
			i = charMap[s.charAt(j)] + 1;
		}

		charMap[s.charAt(j)] = j;
		maxLength = Math.max(j - i + 1, maxLength);
	}

	return maxLength;
}

// ----------------------------------------------------------------

// Kth Largest Element in an Array (good problem!)
// Find the kth largest element in an unsorted array. Note that it 
// is the kth largest element in the sorted order, not the kth 
// distinct element.

// Solution:
// Average case time is O(n), worst case time is O(n^2).
int findKthLargest(int[] nums, int k) {
	if (k < 1 || nums == null) {
		return 0;
    }
        
    return getKth(nums.length - k + 1, nums, 0, nums.length - 1);
}

int getKth(int k, int[] nums, int start, int end) { 
// k here represents the kth index in the array.
    int pivot = nums[end];
    int left = start;
    int right = end;
    
    while (true) {
        while (nums[left] <= pivot && left < right) {
            left++;
        }
        
        while (nums[right] >= pivot && left < right) {
            right--;
        }
        
        if (left == right) {
            break;
        }
        
        swap(nums, left, right);
    }
    
    swap(nums, left, end);
    
    if (k == left + 1) {
        return pivot;
    } else if (k < left + 1) {
        return getKth(k, nums, start, left - 1);
    } else {
        return getKth(k, nums, left + 1, end);
    }
}

void swap(int[] nums, int i1, int i2) {
    int temp = nums[i1];
    nums[i1] = nums[i2];
    nums[i2] = temp;
}

// ----------------------------------------------------------------

// Number of words in a file (Pinterest interview)
// Given a file name, print out the number of words in the file in
// O(1) memory.

// Solution (probably):
int numWords(String fileName) {
	try {
		FileInputStream fs = new FileInputStream(new File(fileName));
		boolean inWord = false;
		int numWords = 0;
		byte[] b = new byte[1];
		
		while (fs.read(b) > 0) {
			if (!inWord && isCharacter(b[0])) {
				inWord = true;
				numWords++;
			} else if (inWord && !isCharacter(b[0])) {
				inWord = false;
			}
		}
		
		return numWords;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -1;
}

boolean isCharacter(byte b) {
	return !Character.isWhitespace((char) b);
}

// ----------------------------------------------------------------

// Permutations of a String
// Source: http://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
// Solution Source: http://www.ericleschinski.com/c/java_permutations_recursion/

// Shortest solution:
public static void permutation(String s) {
	permutation("", s);
}

private static void permutation(String prefix, String s) {
	int n = s.length();

	if (n == 0) {
		System.out.println(prefix);
	} else {
		for (int i = 0; i < n; i++) {
			permutation(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1), n);
		}
	}
}

/*
"", "abcd"
	"a", "bcd" ---> i = 0
		"ab"
			"abc"
				abcd, ""
			abd
				abdc, ""
		"ac"
			acb
				acbd
			acd
				acdb
		"ad"
	"b", "acd" ---> i = 1
	"c", "abd" ---> i = 2
	"d", "abc" ---> i = 3
*/

// Solution w/out Java I/O
/**
 * List permutation of a string
 * 
 * @param s the input string
 * @return  the list of permutation
 */
public static ArrayList<String> permutation(String s) {
    // The result
    ArrayList<String> res = new ArrayList<String>();
    // If input string's length is 1, return {s}
    if (s.length() == 1) {
        res.add(s);
    } else if (s.length() > 1) {
        int lastIndex = s.length() - 1;
        // Find out the last character
        String last = s.substring(lastIndex);
        // Rest of the string
        String rest = s.substring(0, lastIndex);
        // Perform permutation on the rest string and
        // merge with the last character
        res = merge(permutation(rest), last);
    }
    return res;
}

/**
 * @param list a result of permutation, e.g. {"ab", "ba"}
 * @param c    the last character
 * @return     a merged new list, e.g. {"cab", "acb" ... }
 */
public static ArrayList<String> merge(ArrayList<String> list, String c) {
    ArrayList<String> res = new ArrayList<String>();
    // Loop through all the string in the list
    for (String s : list) {
        // For each string, insert the last character to all possible postions
        // and add them to the new list
        for (int i = 0; i <= s.length(); ++i) {
            String ps = new StringBuffer(s).insert(i, c).toString();
            res.add(ps);
        }
    }
    return res;
}

// Solution w/out recursion
// LeetCode problem: https://leetcode.com/problems/permutations/
public List<List<Integer>> permute(int[] num) {
	List<List<Integer>> result = new ArrayList<List<Integer>>();
 
	//start from an empty list
	result.add(new ArrayList<Integer>());
 
	for (int i = 0; i < num.length; i++) {
		//list of list in current iteration of the array num
		ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
 
		for (List<Integer> l : result) {
			// # of locations to insert is largest index + 1
			for (int j = 0; j < l.size()+1; j++) {
				// + add num[i] to different locations
				l.add(j, num[i]);
 
				ArrayList<Integer> temp = new ArrayList<Integer>(l);
				current.add(temp);
 
				//System.out.println(temp);
 
				// - remove num[i] add
				l.remove(j);
			}
		}
 
		result = new ArrayList<List<Integer>>(current);
	}
 
	return result;
}

// ----------------------------------------------------------------

// Longest Palindromic Substring
// Source: https://oj.leetcode.com/problems/longest-palindromic-substring/

// Solution:
String longestPalindrome(String s) {
	if (s == null) return null;

	String longest = s.substring(0, 1);

	for (int i = 0; i < s.length(); i++) {
		// odd cases like 121
		String palindrome = longestPalindromeFromIndices(s, i, i);
		if (palindrome.length() > longest.length()) {
			longest = palindrome;
		}

		// even cases like 1221
		String palindrome = longestPalindromeFromIndices(s, i, i + 1);
		if (palindrome.length() > longest.length()) {
			longest = palindrome;
		}
	}

	return longest;
}

String longestPalindromeFromIndices(String s, int left, int right) {
	if (left > right) return null;

	while (left >= 0 && right < s.length()
		&& s.charAt(left) == s.charAt(right)) {
	left--;
	right++;
	}

	return s.substring(left + 1, right);
}

// ----------------------------------------------------------------

// One Edit Distance
// Given two strings S and T, determine if they are both one edit
// distance apart

// Assume X represents the one-edit character. There are three
// one-edit distance operations that could be applied to S:
// 1) Modify operation - modify a character to X in S.
//		S = "abcde"
//		T = "abXde"
// 2) Insert operation - X was inserted before a character in S.
//		S = "abcde"
//		T = "abcXde"
// 3) Append operation - X was appended to the end of S.
//		S = "abcde"
//		S = "abcdeX"
boolean isOneEditDistance(String s, String t) {
	int m = s.length();
	int n = t.length();

	if (m > n) {
		return isOneEditDistance(t, s);
	}

	if (n - m > 1) {
		return false;
	}

	int i = 0;
	int shift = n - m;
	while(i < m && s.charAt(i) == t.charAt(i)) {
		i++;
	}

	// Encountered Append Operation
	if (i == m) {
		return shift > 0;
	}

	// Encountered Modify Operation
	if (shift == 0) {
		i++;
	}

	// Encountered Modify or Insert Operations
	while (i < m && s.charAt(i) == t.charAt(i + shift)) {
		i++;
	}

	return i == m;

}

// ----------------------------------------------------------------

// Integer to Roman
// Given an integer, convert it to a roman numeral.
// Source: https://leetcode.com/problems/integer-to-roman/

// Solution:
String intToRoman(int num) {
    final int[] values = {
        1000, 900, 500, 400,
        100, 90, 50, 40,
        10, 9, 5, 4,
        1 };
    final String[] symbols = {
        "M", "CM", "D", "CD",
        "C", "XC", "L", "XL",
        "X", "IX", "V", "IV",
        "I" };
    
    StringBuilder sb = new StringBuilder();
    
    int i = 0;
    while (num > 0) {
        int temp = num / values[i];
        
        for (int j = 0; j < temp; j++) {
            sb.append(symbols[i]);
            num -= values[i];
        }
        
        i++;
    }
    
    return sb.toString();
}

// ----------------------------------------------------------------

// Roman to Integer
// Given a roman numeral, convert it to an integer.
// https://leetcode.com/problems/roman-to-integer/

// Solution:
public class Solution {
    HashMap<Character, Integer> map = new HashMap();
    
    public int romanToInt(String s) {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        
        int total = 0;
        int prev = 0;
        
        for (Character c : s.toCharArray()) {
            int current = map.get(c);
            if (current > prev) {
                total += current - 2 * prev;
            } else {
                total += current;
            }
            
            prev = current;
        }
        
        return total;
    }
}