public class Arrays2D {
    int[][] customerBank = {{1, 2, 3},{4, 5, 6}, {7, 8, 9}};
    int maxAmount = 0;

    public static void main(String[] args) {
        Arrays2D a = new Arrays2D();
        System.out.println(a.printArray());
    }
    public int printArray() {
        for (int[] customers : customerBank) {
            for (int customer : customers) {
                int currentMaxAmount = 0;
                for(int bank : customers){
                    currentMaxAmount = currentMaxAmount + bank;
                    maxAmount = Math.max(maxAmount, currentMaxAmount);
                }
            }
        }
        return maxAmount;
    }
}
