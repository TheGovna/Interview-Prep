/**
Valid Parentheses
*/

// Valid Parentheses
// Given a string containing just the characters '(', ')', '{', 
// '}', '[' and ']', determine if the input string is valid.
// The brackets must close in the correct order, "()" and "()[]{}" 
// are all valid but "(]" and "([)]" are not.
// Source: https://leetcode.com/problems/valid-parentheses/

// Solution:
boolean isValid(String s) {
    HashMap<Character, Character> map = new HashMap();
    map.put(')', '(');
    map.put('}', '{');
    map.put(']', '[');
    
    Stack<Character> stack = new Stack();
    
    for (Character c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else if (c == ')' || c == '}' || c == ']') {
            if (stack.isEmpty() || (stack.peek() != map.get(c))) {
                return false;
            } else {
                stack.pop();
            }
        }
    }
    
    return stack.isEmpty();
}

// ----------------------------------------------------------------

