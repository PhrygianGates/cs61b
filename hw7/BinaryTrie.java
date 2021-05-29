import edu.princeton.cs.algs4.Stack;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    private class Node implements Serializable {
        char label;
        int freq;
        Node left;
        Node right;
        Node(char label, int f, Node l, Node r) {
            this.label = label;
            freq = f;
            left = l;
            right = r;
        }
    }
    private Node trie;
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.freq - o2.freq;
            }
        };
        PriorityQueue<Node> pq = new PriorityQueue<>(cmp);
        for (char c : frequencyTable.keySet()) {
            pq.add(new Node(c, frequencyTable.get(c), null, null));
        }
        while (pq.size() > 1) {
            Node a = pq.remove();
            Node b = pq.remove();
            Node c = new Node('\0', a.freq + b.freq, a, b);
            pq.add(c);
        }
        trie = pq.remove();
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        String seq = "";
        Node curr = trie;
        for (int i = 0; i < querySequence.length(); i++) {
            int b = querySequence.bitAt(i);
            if (b == 0) {
                curr = curr.left;
                seq += '0';
            } else {
                curr = curr.right;
                seq += '1';
            }
            if (curr.label != '\0') {
                break;
            }
        }
        return new Match(new BitSequence(seq), curr.label);
    }
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();
        explore(trie, "", table);
        return table;
    }
    private void explore(Node v, String seq, Map<Character, BitSequence> table) {
        if (v.label != '\0') {
            table.put(v.label, new BitSequence(seq));
        } else {
            explore(v.left, seq + '0', table);
            explore(v.right, seq + '1', table);
        }
    }
}
