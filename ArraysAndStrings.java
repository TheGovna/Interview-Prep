/**
Maximum Subarray
Merge Intervals
Two Sum
Find the first unique character in a string
Valid Palindrome
Reverse Words in a String
Longest Substring Without Repeating Characters
Implement Trie (Prefix Tree)
Kth Largest Element in an Array
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
		if (charMap[s.charAt(j) >= i]) {
			i = charMap[s.charAt(j)] + 1;
		}

		charMap[s.charAt(j)] = j;
		maxLength = Math.max(j - i + 1, maxLength);
	}

	return maxLength;
}

// ----------------------------------------------------------------

// Implement Trie (Prefix Tree)
// Implement a trie with insert, search, and startsWith methods.
// Source: https://leetcode.com/problems/implement-trie-prefix-tree/

// Solution:
public class Trie {
    HashMap<Character, Trie> map;

    public Trie() {
        // root = new TrieNode();
        this.map = new HashMap<Character, Trie>();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        if (word.isEmpty()) {
            map.put('\0', null);
            return;
        }
        
        Trie t = map.getOrDefault(word.charAt(0), new Trie());
        map.put(word.charAt(0), t);
        t.insert(word.substring(1, word.length()));
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        if (word.isEmpty()) {
            return map.containsKey('\0');
        }
        
        Trie t = map.getOrDefault(word.charAt(0), null);
        
        if (t == null) {
                return false;
        }
        
        return t.search(word.substring(1, word.length()));
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if (prefix.isEmpty()) {
            return true;
        }
        
        Trie t = map.getOrDefault(prefix.charAt(0), null);
        
        if (t == null) {
            return false;
        }
        
        return t.startsWith(prefix.substring(1, prefix.length()));
    }
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

// Given an input string and a dictionary of words, find out if
// the input string can be segmented into a space-separated
// sequence of dictionary words. You need to output the
// minimum number of words.
// dict: {"a", "aaa", "is", "name"}
// output: "aaa is a name"
// wrong output: "a a a is a name"
// Source: http://www.careercup.com/question?id=5359122669109248

// Use trie from above