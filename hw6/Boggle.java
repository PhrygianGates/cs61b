import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "trivial_words.txt";
    static char[][] board;
    static TrieNode root;
    static List<String> words;

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        In input = new In(boardFilePath);
        String[] lines = input.readAllLines();
        int m = lines.length, n = lines[0].length();
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = lines[i].charAt(j);
            }
        }
        words = new LinkedList<>();
        boolean[][] visit = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (root.children.containsKey(board[i][j])) {
                    search(i, j, root.children.get(board[i][j]), visit, Character.toString(board[i][j]));
                }
            }
        }
        //need to sort
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        return words.subList(0, k);
    }
    public static void buildTrie(String path) {
        In input = new In(path);
        String[] words = input.readAllLines();
        root = new TrieNode('\0');
        for (String s : words) {
            root.insert(s);
        }
    }
    public static boolean isSafe(int i, int j, boolean[][] visit) {
        if(i < 0 || j < 0 || i >= visit.length || j >= visit[0].length || visit[i][j]) {
            return false;
        }
        return true;
    }
    public static void search(int i, int j, TrieNode n, boolean[][] visit, String word) {
        if (n.leaf && word.length() > 2) {
            words.add(word);
            return;
        }
        visit[i][j] = true;
        if (isSafe(i - 1, j, visit) && n.children.containsKey(board[i - 1][j])) {
            search(i - 1, j, n.children.get(board[i - 1][j]), visit, word + board[i - 1][j]);
        }
        if (isSafe(i - 1, j - 1, visit) && n.children.containsKey(board[i - 1][j - 1])) {
            search(i - 1, j - 1, n.children.get(board[i - 1][j - 1]), visit, word + board[i - 1][j - 1]);
        }
        if (isSafe(i, j - 1, visit) && n.children.containsKey(board[i][j - 1])) {
            search(i, j - 1, n.children.get(board[i][j - 1]), visit, word + board[i][j - 1]);
        }
        if (isSafe(i + 1, j - 1, visit) && n.children.containsKey(board[i + 1][j - 1])) {
            search(i + 1, j - 1, n.children.get(board[i + 1][j - 1]), visit, word + board[i + 1][j - 1]);
        }
        if (isSafe(i + 1, j, visit) && n.children.containsKey(board[i + 1][j])) {
            search(i + 1, j, n.children.get(board[i + 1][j]), visit, word + board[i + 1][j]);
        }
        if (isSafe(i + 1, j + 1, visit) && n.children.containsKey(board[i + 1][j + 1])) {
            search(i + 1, j + 1, n.children.get(board[i + 1][j + 1]), visit, word + board[i + 1][j + 1]);
        }
        if (isSafe(i, j + 1, visit) && n.children.containsKey(board[i][j + 1])) {
            search(i, j + 1, n.children.get(board[i][j + 1]), visit, word + board[i][j + 1]);
        }
        if (isSafe(i - 1, j + 1, visit) && n.children.containsKey(board[i - 1][j + 1])) {
            search(i - 1, j + 1, n.children.get(board[i - 1][j + 1]), visit, word + board[i - 1][j + 1]);
        }
        visit[i][j] = false;
    }
    public static void main(String[] args) {
        buildTrie(dictPath);
        List<String> res = solve(2, "exampleBoard2.txt");
        System.out.println(res);
    }
}
