import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static OffByN obn = new OffByN(5);
    static Palindrome panlidrome = new Palindrome();
    @Test
    public void testisPalindrome() {
        assertTrue(panlidrome.isPalindrome("tiffany", obn));
        assertFalse(panlidrome.isPalindrome("abc", obn));
    }
}