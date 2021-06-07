import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    char label;
    boolean leaf;
    Map<Character, TrieNode> children;
    public TrieNode(char l) {
        label = l;
        children = new HashMap<>();
        leaf = true;
    }
    private TrieNode add(char l) {
        children.put(l, new TrieNode(l));
        return children.get(l);
    }
    public void insert(String s) {
        char c = s.charAt(0);
        if (!children.containsKey(c)) {
            TrieNode curr = this;
            for (int i = 0; i < s.length(); i++) {
                curr.leaf = false;
                c = s.charAt(i);
                curr = curr.add(c);
            }
        } else if (s.length() > 1){
            leaf = false;
            children.get(c).insert(s.substring(1));
        }
    }
}
