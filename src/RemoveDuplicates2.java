public class RemoveDuplicates2 {

        public int removeDuplicates(int[] nums) {
            int counter = 2;
            for(int i=2;i < nums.length; i++){
                if(nums[i] != nums[counter-2]){
                    nums[counter] = nums[i];
                    counter++;
                }
            }
            return counter;
        }
    }

