public class Utility {
    public  float isText(String message) {
        float chance = 0.0f;
        return chance;
    }

    public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyzåäö".toCharArray();
        public static final char[] nummbers = "0123456789".toCharArray();
    //{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','å','ä','ö'};
    public static int indexOf(char c) {
        int num = 0;
        for (int i = 0; i < 29; i++) {
            if (c == alphabet[i]) {
                num = i;
            }
        }
        return num;
    }

}
