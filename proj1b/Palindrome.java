public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return helper(deque);
    }
    private boolean helper(Deque<Character> deque) {
        if (deque.size() <= 1) {
            return true;
        } else if (deque.removeFirst() == deque.removeLast()) {
            return helper(deque);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return helper(deque, cc);
    }
    private boolean helper(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() <= 1) {
            return true;
        } else if (cc.equalChars(deque.removeFirst(), deque.removeLast())) {
            return helper(deque, cc);
        } else {
            return false;
        }
    }
}
