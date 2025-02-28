public class SelectionSortSolution {
    public static int[] selectionSort(int[] nums){
        for(int i = 0 ; i< nums.length -1 ; i++){
            for(int j = i+1; j<nums.length; j++){
                if(nums[j] < nums[i]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] temp = selectionSort(new int[]{5, 2, 3, 1, 4});
        for(int i = 0; i < temp.length; i++){
            System.out.print(temp[i] + " ");
        }
    }
}
