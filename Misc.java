/**
LRU Cache
Trie
*/

// LRU Cache
//Design and implement a data structure for Least Recently Used (LRU) 
// cache. It should support the following operations: get and set.
// get(key) - Get the value (will always be positive) of the key if 
// the key exists in the cache, otherwise return -1.
// set(key, value) - Set or insert the value if the key is not 
// already present. When the cache reached its capacity, it should 
//invalidate the least recently used item before inserting a new item.
// Source: https://leetcode.com/problems/lru-cache/

// Solution:
public class LRUCache {
    HashMap<Integer, Node> map = new HashMap();
    int capacity;
    Node head = null;
    Node tail = null;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    
    public int get(int key) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }
        
        return -1;
    }
    
    public void remove(Node n) {
        if (n.next == null && n.previous == null) {
            head = null;
            tail = null;
            return;
        }
        
        if (n == head) {
            head = n.next;
            head.previous = null;
        } else if (n == tail) {
            tail = n.previous;
            tail.next = null;
        } else {
            n.previous.next = n.next;
            n.next.previous = n.previous;
        }
    }
    
    public void setHead(Node n) {
        if (head == null) {
            head = n;
            tail = n;
        } else {
            n.next = head;
            head.previous = n;
            head = n;
            head.previous = null;
        }
    }
    
    public void set(int key, int value) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            n.value = value;
            map.put(key, n);
            remove(n);
            setHead(n);
        } else {
            Node n = new Node(key, value);
            
            if (map.size() >= capacity) {
                map.remove(tail.key);
                remove(tail);
            }
            
            map.put(key, n);
            setHead(n);
        }
    }
    
    class Node {
        int key;
        int value;
        Node next;
        Node previous;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
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