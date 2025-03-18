public class RemoveOccurences {
    public static void main(String[] args) {
        String input = "Hello, World!";
        char charToRemove = 'l';
        // Remove all occurrences of the character
        String result = input.replace(String.valueOf(charToRemove), "");

        System.out.println("Original string: " + input);
        System.out.println("String after removing '" + charToRemove + "': " + result);
    }

    public static String removeCharacter(String input, char charToRemove) {
        return input.replace(String.valueOf(charToRemove), "");
    }

}
