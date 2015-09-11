// 4.1: Route Between Nodes
// Given a directed graph, design an algorithm to determine if there's a route between two nodes.

// Attempt 1:
boolean pathExists(Node n1, Node n2) {
	Queue<Node> queue = new LinkedList<Node>();

	queue.enqueue(n1);

	while(!queue.isEmpty()) {
		Node top = queue.dequeue();

		if (top == n2) {
			return true;
		}

		for (Node child : top.children) {
			queue.enqueue(child);
		}
	}

	return false;
}

// Solution:
enum State {
	Unvisited,
	Visited,
	Visiting
}

boolean pathExists(Graph g, Node n1, Node n2) {
	if (n1 == n2) return true;

	Queue<Node> queue = new LinkedList<Node>();

	for (Node n : g.getNodes()) {
		n.state = State.Unvisited;
	}

	n1.state = State.Visiting;
	queue.enqueue(n1);
	Node top;

	while(!queue.isEmpty()) {
		Node top = queue.dequeue();

		if (top != null) {
			for (Node child : top.children) {
				if (child.state == State.Unvisited) {
					if (child == n2) {
						return true;
					} else {
						child.state = State.Visiting;
						queue.enqueue(child);
					}
				}
			}

			top.state = State.Visited;	
		}
	}

	return false;
}

// ----------------------------------------------------------------

// 4.5: Validate BST
// Implement a function to check if a binary tree is a binary search tree.

// Attempt 1:
boolean checkBST(Node root) {
	if (root == null) {
		return true;
	}

	if (root.left && root.left.value >= root.value) {
		return false;
	}	

	if (root.right && root.right.value <= root.value) {
		return false;
	}

	return checkBST(root.left) && checkBST(root.right);
}

// Solution:
boolean checkBST(Node root) {
	return checkBSTHelper(root, -1 * Math.Infinity, Math.Infinity);
}

boolean checkBSTHelper(Node root, int minValue, int maxValue) {
	if (root == null) {
		return true;
	}

	if ((root.value < minValue) || (root.value > maxValue)) {
		return false;
	}

	return checkBSTHelper(root.left, minValue, root.value) && checkBSTHelper(root.right, root.value, maxValue);
}

// ----------------------------------------------------------------

// Boggle (Find all possible words in a board of characters)
// Source: http://ideone.com/Pj5yR6

// Ex: Input: dictionary[] = {"GEEKS", "FOR", "QUIZ", "GO"};
//        boggle[][]   = {{'G','I','Z'},
//                        {'U','E','K'},
//                        {'Q','S','E'}};
//       isWord(str): returns true if str is present in dictionary
//                    else false.
// Output:  Following words of dictionary are present
//          GEEKS
//          QUIZ

// Basic idea using dynamic programming:
// - Keep track of a 2D String array representing the boggle board,
//   a 2D boolean array representing the visited portions of the
//   boggle board, and a String array representing the dictionary.
// - Every element in the boggle board is a potential starting point
//   for a word in the dictionary. 
// - What we can do is that for every position (i, j) in the boggle 
//   board, we attempt to build a word starting with the letter at
//   (i, j). 
// - Create an empty string s. Do the following:
//   1) Make sure that (i, j) is a valid coordinate in the boggle
//      board and that (i, j) isn't already visited. If any of these
//      conditions aren't met, we cannot build a word with s
//   2) Append s to the letter at boggle board (i, j). If the newly
//      appended s is in the dictionary, we found a word! Add it to
//      the list of other words we already found.
//   3) Set (i, j) as visited.
//   4) Repeat steps 1-4 for all 8 coordinates adjacent to (i, j). 
ArrayList<String> findAllWords(String[][] boggle, String[] dictionary) {
	int m = boggle.length;
	int n = boggle[0].length;
	boolean visited = new boolean[m][n];
	ArrayList<String> result = new ArrayList<String>();

	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			String str = "";
			ArrayList<String> foundWords = findWordsFromStartingPoint(boggle, dictionary, visited, i, j, m, n, str, new ArrayList<String>());

			result.addAll(foundWords);
		}
	}

	return result;
}

ArrayList<String> findWordsFromStartingPoint(String[][] boggle, String[] dictionary, boolean[][] visited, int i, int j, int m, int n, String str, ArrayList<String> foundWordsSoFar) {
	if (i > m || i < 0 || j > n || j < 0 || visited[i][j]) {
		return null;
	}

	int[] x = {-1, 0, 1, 1, 1, 0, -1};
	int[] y = {1, 1, 1, 0, -1, -1, -1, 0};

	str += str + boggle[i][j];

	if (dictionary.contains(str)) {
		foundWordsSoFar.append(str);
	}

	visited[i][j] = true;

	for (int k = 0; i < 8; k++) {
		// work on this some more later...
	}
}

// ----------------------------------------------------------------

// Invert a binary tree
// Details: https://leetcode.com/problems/invert-binary-tree/

// Attempt 1:
// This works but there's a shorter way
Node invertTree(Node root) {
	if (root == null) {
		return null;
	}

	if (root.left == null && root.right == null) {
		return root;
	}

	invertTreeHelper(root);
	return root;
}

void invertTreeHelper(Node root) {
	if (root != null) {
		if (root.left != null && root.right == null) {
			root.right = root.left;
			root.left = null;
			invertTreeHelper(root.right);
		} else if (root.right != null && root.left == null) {
			root.left = root.right;
			root.right = null;
			invertTreeHelper(root.left);
		} else {
			Node temp = root.left;
			root.left = root.right;
			root.right = temp;
			invertTreeHelper(root.left);
			invertTreeHelper(root.right);
		}
	}
}

// Solution:
Node invertTree(Node root) {
	if (root == null) {
		return null;
	}

	if (root.left != null) {
		invertTree(root.left);
	}

	if (root.right != null) {
		invertTree(root.right);
	}

	if (root.left != null || root.right != null) {
		swapChildren(root);
	}

	return root;
}

// ----------------------------------------------------------------

// Search in Rotated Sorted Array
// Supposed a sorted array is rotated at some pivot unknown to you
// beforehand (ex: [0, 1, 2, 4, 5, 6, 7] might become
// [4, 5, 6, 7, 0, 1, 2]). You are given a target value to search.
// If found in the array return its index, otherwise return -1.
// Assume no duplicates.

// Attempt 1:
// This works, but also know that I didn't need to call
// binarySearch; I could have just kept with the rotated version.
int search(int[] nums, int target) {
    return rotatedBinarySearch(nums, target, 0, nums.length - 1);
}

int rotatedBinarySearch(int[] nums, int target, int leftIndex, int rightIndex) {
    int middleIndex = (leftIndex + rightIndex) / 2;
       
    if (leftIndex > rightIndex) {
        return -1;
    }
        
    if (nums[middleIndex] == target) {
        return middleIndex;
    }
        
    if (nums[leftIndex] <= nums[middleIndex]) {
        // left half of array is sorted
            
        if (target >= nums[leftIndex] && target < nums[middleIndex]) {
            // target is located in left half of array
            // binary search on left half of array
            return binarySearch(nums, target, leftIndex, middleIndex - 1);
        } else {
            // target is located in right half of array
            return rotatedBinarySearch(nums, target, middleIndex + 1, rightIndex);
        }
    } else {
        // right half of array is sorted
            
        if (target > nums[middleIndex] && target <= nums[rightIndex]) {
            // target is located in right half of array
            // binary search on right half of array
            return binarySearch(nums, target, middleIndex + 1, rightIndex);
        } else {
            // target is located in left half of array
            return rotatedBinarySearch(nums, target, leftIndex, middleIndex - 1);
        }
    }
}

int binarySearch(int[] nums, int target, int leftIndex, int rightIndex) {
    if (leftIndex > rightIndex) {
        return -1;
    }
        
    int middleIndex = (leftIndex + rightIndex) / 2;
        
    if (nums[middleIndex] == target) {
        return middleIndex;
    } else if (target < nums[middleIndex]) {
        return binarySearch(nums, target, leftIndex, middleIndex - 1);
    } else {
        return binarySearch(nums, target, middleIndex + 1, rightIndex);
    }
}

// ----------------------------------------------------------------

// Maximum Depth of Binary Tree
// Given a binary tree, find its maximum depth.
// The maximum depth is the number of nodes along the longest path 
// from the root node down to the farthest leaf node.
// Source: https://leetcode.com/problems/maximum-depth-of-binary-tree/

// Attempt 1:
// Works
int maxDepth(Node root) {
	if (root == null) {
		return 0;
	}

	return Math.max(1 + maxDepth(root.left), 1 + maxDepth(root.right));
}

// ----------------------------------------------------------------

// Minimum Depth of Binary Tree
// Given a binary tree, find its minimum depth.
// The minimum depth is the number of nodes along the shortest path 
// from the root node down to the nearest leaf node.
// Source: https://leetcode.com/problems/minimum-depth-of-binary-tree/

// Attempt 1:
// Works; O(n) runtime, O(log n) space - depth-first traversal
int minDepth(Node root) {
    if (root == null) {
        return 0;
    }
    
    // Why do we need to make these checks?    
    if (root.left == null) {
        return minDepth(root.right) + 1;
    }
       
    if (root.right == null) {
        return minDepth(root.left) + 1;
    }
        
    return Math.min(1 + minDepth(root.left), 1 + minDepth(root.right));
}

// Solution
// O(n) runtime, O(n) space - breadth-first traversal
// Look over carefully!
int minDepth(Node root) {
	if (root == null) return 0;
	Queue<Node> q = new LinkedList();
	q.add(root);
	Node rightMost = root;
	int depth = 1;

	while (!q.isEmpty()) {
		Node node = q.poll();

		if (node.left == null && node.right == null) {
			break;
		}

		if (node.left != null) {
			q.add(node.left);
		}

		if (node.right != null) {
			q.add(node.right);
		}

		if (node == rightMost) {
			depth++;
			rightMost = (node.right != null) ? node.right : node.left;
		}
	}

	return depth
}

// ----------------------------------------------------------------

// Balanced Binary Tree
// Given a binary tree, determine if it is height-balanced.
// For this problem, a height-balanced binary tree is defined as a 
// binary tree in which the depth of the two subtrees of every node 
// never differ by more than 1.
// Source: https://leetcode.com/problems/balanced-binary-tree/

// Attempt 1:
// Works, but O(n^2) runtime, O(n) stack space
// See above for implementation of maxDepth.
boolean isBalanced(Node root) {
	if (root == null) {
		return true;
	}

	if (Math.abs(maxDepth(root.left) - maxDepth(root.right)) > 1) {
		return false;
	}

	return isBalanced(root.left) && isBalanced(root.right);
}

// Solution:
// O(n) runtime, O(n) space
boolean isBalanced(Node root) {
	return maxDepth(root) != -1;
}

int maxDepth(Node root) {
	if (root == null) {
		return 0;
	}

	int L = maxDepth(root.left);
	if (L == -1) {
		return -1;
	}

	int R = maxDepth(root.right);
	if (R == -1) {
		return -1;
	}

	return (Math.abs(L - R) <= 1) ? (Math.max(L, R) + 1) : -1;
}