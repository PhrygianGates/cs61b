import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    static OffByOne obo = new OffByOne();
    static Palindrome panlidrome = new Palindrome();

    @Test
    public void testequalChars() {
        assertTrue(obo.equalChars('a', 'b'));
        assertFalse(obo.equalChars('a', 'c'));
        assertTrue(obo.equalChars('&', '%'));
    }

    @Test
    public void testisPalindrome() {
        assertTrue(panlidrome.isPalindrome("flake", obo));
        assertFalse(panlidrome.isPalindrome("abc", obo));
    }
}
