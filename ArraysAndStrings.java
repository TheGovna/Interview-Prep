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
	// value = target = nums[i]
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

// Attempt 1 (Probabaly works, but there are cleaner ways):
char getFirstUnique(String str) {
	HashMap<Character, boolean> isUniqueMap = new HashMap<Character, boolean>();
	HashMap<Character, Integer> firstOccurrenceMap = new HashMap<char, Integer>();

	for(int i = 0; i < str.length; i++) {
		if (!isUniqueMap.containsKey(str.charAt(i))) {
			isUniqueMap.put(str.charAt(i), true);
			firstOccurrenceMap.put(str.charAt(i), i);
		} else {
			isUniqueMap.put(str.charAt(i), false);
		}
	}

	int indexOfFirstOccurrence = firstOccurrenceMap.keySet().size() - 1;

	char firstChar;
	for(char c : isUniqueMap.keySet()) {
		if (isUniqueMap.get(c) && firstOccurrenceMap.get(c) < indexOfFirstOccurrence) {
			indexOfFirstOccurrence = firstOccurrenceMap.get(c);
			firstChar = c;
		}
	}

	return firstChar;
}

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

// Attempt 1:
// works fine, requires knowledge of REGEX
String reverseWords(String s) {
	String str = s.replaceAll("\\s+", " ");
	StringBuilder sb = new StringBuilder();
	String[] sArray = str.split(" ");

	for(int i = sArray.length - 1; i >= 0; i--) {
		sb.append(sArray[i] + " ");
	}

	return sb.toString().trim();
}

// Solution:
// Doesn't require regex knowledge
String reverseWords(String s) {
	StringBuilder sb = new StringBuilder();
	int j = s.length();

	for(int i = s.length() - 1; i >= 0; i--) {
		if (s.charAt(i) == ' ') {
			j = i;
		} else if (i == 0 || s.charAt(i - 1) == ' ') {
			if (sb.length() != 0) {
				sb.append(' ');
			}
			sb.append(s.substring(i, j));
		}
	}

	return sb.toString();
}