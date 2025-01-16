import java.util.ArrayList;
import java.util.List;

class FizzBuzz {
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
