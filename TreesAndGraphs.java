/**
Route Between Nodes
Validate BST
Boggle
Invert a binary tree
Search in Rotated Sorted Array
Maximum Depth of Binary Tree
Minimum Depth of Binary Tree
Balanced Binary Tree
Convert Sorted Array to BST
Convert Sorted Linked List to Balanced Search Tree
Print Binary Tree in Level Order
Binary Tree Inorder Traversal
Kth Smallest Element in a BST
Lowest Common Ancestor of Binary Tree
*/

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
// Output:  {"GEEKS", "QUIZ"}
String[] findAllWords(String[][] boggle, Set<String> dictionary) {

}

void findAllWordsFromPosition(int x, int y, String currWord, String[][] boggle, Set<String> dictionary, Stack<int[]> visited) {
	visited.push({x, y});

	if (x > 0 && y > 0 && x < boggle.length && y < boggle[0].length && !visited[x][y]) {
		int[] xDelta = {-1, 0, 1, 1, 1, 0, -1, -1};
		int[] yDelta = {1 , 1, 1, 0,-1,-1, -1,  0};

		String newWord = currWord + boggle[x][y];


		if (dictionary.contains(newWord)) {
			System.out.println(newWord);
		} 

		if (isPrefix(newWord, dictionary)) {
			for (int i = 0; i < xDelta.length; i++) {
				findAllWordsFromPosition(x + xDelta[i], y + yDelta[i], newWord, boggle, dictionary, visited);
			}
		}
	}

	visited.pop();
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

// ----------------------------------------------------------------

// Convert Sorted Array to BST
// Given an array where elements are sorted in ascending order, 
// convert it to a height balanced BST.
// Source: https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/

// Solution:
// O(n) runtime, O(log n) stack space
Node sortedArrayToBST(int[] nums) {
	return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
}

Node sortedArrayToBSTHelper(int[] nums, int start, int end) {
	if (start > end) {
		return null;
	}

	int mid = (start + end) / 2;
	Node root = new Node(nums[mid]);
	root.left = sortedArrayToBSTHelper(nums, start, mid - 1);
	root.right = sortedArrayToBSTHelper(nums, mid + 1, end);

	return root;
}

// ----------------------------------------------------------------

// Convert Sorted Linked List to Balanced Search Tree
// Given a singly linked list where elements are sorted in 
// ascending order, convert it to a height balanced BST.
// Source: https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
ListNode list;

TreeNode sortedListToBST(ListNode head) {
	int n = 0;
	ListNode p = head;

	while (p != null) {
		p = p.next;
		n++;
	}

	list = head;
	return sLTBST(0, n - 1);
}

TreeNode sLTBST(int start, int end) {
	if (start > end) return null;
	int mid = (start + end) / 2;
	TreeNode leftChild = sLTBST(start, mid-1);
	TreeNode parent = new TreeNode(list.val);
	parent.left = leftChild;
	list = list.next;
	parent.right = sLTBST(mid+1, end);
	return parent;
}

// ----------------------------------------------------------------

// Print Binary Tree in Level Order
// Source: http://articles.leetcode.com/2010/09/printing-binary-tree-in-level-order.html

void printLevelOrder(TreeNode root) {
	if (root == null) {
		return;
	}

	Queue<TreeNode> currentLevel = new LinkedList<TreeNode>();
	Queue<TreeNode> nextLevel = new LinkedList<TreeNode>();

	currentLevel.enqueue(root);

	while (!currentLevel.isEmpty()) {
		TreeNode currNode = currentLevel.dequeue();

		if (currNode != null) {
			System.out.print(currNode.value + " ");
			nextLevel.enqueue(currNode.left);
			nextLevel.enqueue(currNode.right);
		}

		if (currentLevel.isEmpty()) {
			System.out.println();
			swap(currentLevel, nextLevel);
		}
	}
}

// ----------------------------------------------------------------

// Binary Tree Inorder Traversal
// Source: https://leetcode.com/problems/binary-tree-inorder-traversal/

// Recursive:
public class Solution {
    List<Integer> result = new ArrayList<Integer>();
    
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }
        
        helper(root);
        
        return result;
    }
    
    public void helper(TreeNode root) {
        if (root.left != null) {
            helper(root.left);
        }
        
        result.add(root.val);
        
        if (root.right != null) {
            helper(root.right);
        }
    }
}

// Iterative (preferable):
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<Integer>();
    Stack<TreeNode> stack = new Stack<TreeNode>();
    
    TreeNode curr = root;
    
    while (!stack.isEmpty() || curr != null) {
        if (curr != null) {
            stack.push(curr);
            curr = curr.left;
        } else {
            TreeNode node = stack.pop();
            result.add(node.val);
            curr = node.right;
        }
    }
    
    return result;
}

// ----------------------------------------------------------------

// Kth Smallest Element in a BST
// Given a binary search tree, write a function kthSmallest to 
// find the kth smallest element in it.
// Source: https://leetcode.com/problems/kth-smallest-element-in-a-bst/

// Iterative (NOTE: pretty much same as inorderTraversal):
public int kthSmallest(TreeNode root, int k) {
    int count = 0;
    Stack<TreeNode> stack = new Stack<TreeNode>();
    
    TreeNode curr = root;
    
    while (!stack.isEmpty() || curr != null) {
        if (curr != null) {
            stack.push(curr);
            curr = curr.left;
        } else {
            TreeNode node = stack.pop();
            
            count++;
            if (count == k) {
                return node.val;
            }
            
            curr = node.right;
        }
    }
    
    return count;
}

// ----------------------------------------------------------------

// Lowest Common Ancestor of Binary Tree
// Given a binary tree (not a binary search tree) and two values 
// say n1 and n2, write a program to find the least common ancestor.
// Source: http://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/

public TreeNode findLCA(TreeNode root, TreeNode n1, TreeNode n2) {
	if (root == null) {
		return null;
	}

	if (root.val == n1.val || root.val == n2.val) {
		return root;
	}

	TreeNode lcaLeft = findLCA(root.left, n1, n2);
	TreeNode lcaRight = findLCA(root.right, n1, n2);

	// If both of the calls from above are non-null,
	// then 1 key is present in 1 subtree and the other key
	// is present in the other subtree.
	// So this node is LCA
	if (lcaLeft != null && lcaRight != null) {
		return root;
	}

	return (lcaLeft != null) ? lcaLeft : lcaRight;
}