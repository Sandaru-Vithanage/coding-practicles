public class RansomNote {
    class Solution {
        public static boolean canConstruct(String ransomNote, String magazine) {
            int[] map = new int[128];
            char[] m = magazine.toCharArray();
            char[] r = ransomNote.toCharArray();
            for(int i : m) {
                map[i]++;
            }
            
            for(int i : r) {
                if(map[i]==0) return false;
                map[i]--;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(Solution.canConstruct("aa", "aab"));
    }
}
