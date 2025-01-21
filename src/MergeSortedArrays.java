import java.util.Arrays;

public class MergeSortedArrays {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        // Pointers for nums1 and nums2
        int p1 = m - 1; // Last element of the "real" part of nums1
        int p2 = n - 1; // Last element of nums2
        // Pointer for the last position in nums1
        int p = m + n - 1;

        // Debugging: Print initial states
        System.out.println("Initial nums1: " + Arrays.toString(nums1));
        System.out.println("Initial nums2: " + Arrays.toString(nums2));

        // While there are elements to compare in both arrays
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;

            // Debugging: Print the state of nums1 after each iteration
            System.out.println("After processing: " + Arrays.toString(nums1));
        }

        // If there are remaining elements in nums2, copy them
        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p2--;
            p--;

            // Debugging: Print the state of nums1 after copying remaining elements
            System.out.println("Copying remaining elements: " + Arrays.toString(nums1));
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;

        // Call the merge function
        merge(nums1, m, nums2, n);

        // Print the final result
        System.out.println("Final merged nums1: " + Arrays.toString(nums1));
    }
}
