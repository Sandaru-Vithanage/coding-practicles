import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a method to generate a list of strings representing the FizzBuzz sequence up to a given number.
 */
class FizzBuzz {
    /**
     * Generates the FizzBuzz sequence up to the given number.
     *
     * @param n the number up to which the FizzBuzz sequence is generated
     * @return a list of strings representing the FizzBuzz sequence
     */
    public List<String> fizzBuzz(int n) {
        List<String> fizzBuzzList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                fizzBuzzList.add("FizzBuzz");
            } else if (i % 5 == 0) {
                fizzBuzzList.add("Buzz");
            } else if (i % 3 == 0) {
                fizzBuzzList.add("Fizz");
            } else {
                fizzBuzzList.add(String.valueOf(i));
            }
        }
        return fizzBuzzList;
    }
}