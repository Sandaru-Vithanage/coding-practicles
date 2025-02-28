public class BubbleSortSolution {
    public static int[] bubbleSort(int[] nums){
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < nums.length - i; j++){
                if(nums[j] > nums[j + 1]){
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] temp = bubbleSort(new int[]{5, 2, 3, 1, 4});
        for(int i = 0; i < temp.length; i++){
            System.out.print(temp[i] + " ");
        }
    }
}