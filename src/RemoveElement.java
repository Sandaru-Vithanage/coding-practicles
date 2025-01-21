public class RemoveElement {
    class Solution {
        public int removeElement(int[] nums, int val) {
            int k = 0; // Pointer for the next valid position
            for (int i = 0; i < nums.length; i++) {
                // If the current element is not equal to val, copy it to the k-th position
                if (nums[i] != val) {
                    nums[k] = nums[i];
                    k++;
                }
            }
            return k; // Return the number of valid elements
        }
    }
}
