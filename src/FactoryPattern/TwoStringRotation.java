package FactoryPattern;

public class TwoStringRotation {

    public boolean checkTwoStringRotation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        return (s1 + s1).contains(s2);
    }

    public boolean checkTwoStringBrute(String s1, String s2){
        if(s1.length() != s2.length()){
            return false;
        }
        for(int i = 0; i < s1.length(); i++){
            if((s1.substring(i) + s1.substring(0, i)).equals(s2)){
                return true;
            }
        }
        return false;
    }
}
