public class Utility {
    public  float isText(String message) {
        float chance = 0.0f;
        return chance;
    }

    public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyzåäö".toCharArray();
    public static char[] signs = "abcdefghijklmnopqrstuvwxyzåäöABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ0123456789 ,.;:!?'-+=%<>[](){}/\r\n\t".toCharArray(); //ÃƒÆ†â¢Â‚€¬¶¤–“œ„
    
    //{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','å','ä','ö'};
    public static int indexOf(char c) {
        Integer num = null;
        for (int i = 0; i < signs.length; i++) {
            if (c == signs[i]) {
                num = i;
            }
        }
        
        if ( num == null){throw new java.lang.NullPointerException("didn't find character for:'" + c + "'");}

        return num;
    }

}
