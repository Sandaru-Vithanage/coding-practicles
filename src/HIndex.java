import java.util.Arrays;

public class HIndex {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int len = citations.length;
        int i = 0;
        while (i < len && i < citations[len - i - 1]) {
            i++;
        }
        return i;
    }
}
