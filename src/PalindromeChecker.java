public class PalindromeChecker {

    // Method to check if a string is a palindrome
    public static boolean isPalindrome(String input) {
        // Remove all non-alphanumeric characters and convert to lowercase
        String cleanedInput = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Compare the cleaned string with its reverse
        String reversedInput = new StringBuilder(cleanedInput).reverse().toString();
        return cleanedInput.equals(reversedInput);
    }

    public static void main(String[] args) {
        String input1 = "A man, a plan, a canal, Panama";
        String input2 = "Hello, World!";

        // Check if the strings are palindromes
        System.out.println("Is \"" + input1 + "\" a palindrome? " + isPalindrome(input1));
        System.out.println("Is \"" + input2 + "\" a palindrome? " + isPalindrome(input2));
    }
}