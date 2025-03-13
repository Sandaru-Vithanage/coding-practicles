import java.util.Arrays;

public class Anagram {
    public boolean areAnagrams(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] charArray1 = s1.toCharArray();
        char[] charArray2 = s2.toCharArray();
        Arrays.sort(charArray1);
        Arrays.sort(charArray2);
        return Arrays.equals(charArray1, charArray2);
    }

    public static void main(String[] args) {
        Anagram anagram = new Anagram();
        System.out.println(anagram.areAnagrams("listen", "silent")); // true
        System.out.println(anagram.areAnagrams("hello", "world"));   // false
    }
}