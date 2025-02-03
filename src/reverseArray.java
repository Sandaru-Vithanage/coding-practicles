public class reverseArray {
    class Solution {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            k = k % n;
            System.out.println("Original array: " + java.util.Arrays.toString(nums));
            System.out.println("Rotate steps (k): " + k);

            reverseString(nums, 0, n-k-1);
            System.out.println("After reversing first part: " + java.util.Arrays.toString(nums));

            reverseString(nums, n-k, n-1);
            System.out.println("After reversing second part: " + java.util.Arrays.toString(nums));

            reverseString(nums, 0, n-1);
            System.out.println("After reversing entire array: " + java.util.Arrays.toString(nums));
        }

        public void reverseString(int nums[], int start, int end){
            while(start < end){
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
                start++;
                end--;
            }
        }

        public void rotatetwo(int[] nums, int k) {
            int n = nums.length;
            k = k % n;
            int[] rotated = new int[n];

            for (int i = 0; i < n; i++) {
                rotated[(i + k) % n] = nums[i];
            }

            for (int i = 0; i < n; i++) {
                nums[i] = rotated[i];
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new reverseArray().new Solution();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        solution.rotate(nums, k);
    }
}