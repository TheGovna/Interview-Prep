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