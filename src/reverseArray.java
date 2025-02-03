public class reverseArray {
    class Solution {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            k = k % n;
            reverseString(nums, 0, n-k-1);
            reverseString(nums, n-k, n-1);
            reverseString(nums, 0, n-1);
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
}
