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