/**
 * This class provides a method to remove duplicates from a sorted array
 * such that each element appears at most twice.
 */
public class RemoveDuplicates2 {

    /**
     * Removes duplicates from the sorted array such that each element appears at most twice.
     *
     * @param nums the input sorted array
     * @return the length of the array after removing duplicates
     */
    public int removeDuplicates(int[] nums) {
        // Initialize the counter to 2 since the first two elements are always allowed
        int counter = 2;

        // Iterate through the array starting from the third element
        for (int i = 2; i < nums.length; i++) {
            // If the current element is not equal to the element at position counter-2,
            // it means it can be included in the result array
            if (nums[i] != nums[counter - 2]) {
                // Assign the current element to the position indicated by the counter
                nums[counter] = nums[i];
                // Increment the counter
                counter++;
            }
        }

        // Return the length of the array after removing duplicates
        return counter;
    }
}

