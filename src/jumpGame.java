public class jumpGame {
    public boolean canJump(int[] nums) {
        // Initialize the maximum reachable index to 0
        int maxReach = 0;
        int n = nums.length;

        // Iterate through each index in the array
        for (int i = 0; i < n; i++) {
            // If the current index is beyond the maximum reachable index, return false
            if (i > maxReach) return false;
            // Update the maximum reachable index
            maxReach = Math.max(maxReach, i + nums[i]);
            // If the maximum reachable index is beyond or at the last index, return true
            if (maxReach >= n - 1) return true;
        }

        // If the loop completes, return false as the end is not reachable
        return false;
    }
}
