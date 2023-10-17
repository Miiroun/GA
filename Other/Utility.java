package Other;
public class Utility {
    public  float isText(String message) {
        float chance = 0.0f;
        return chance;
    }

    public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyzåäö".toCharArray();
    public static final char[] signs = "abcdefghijklmnopqrstuvwxyzåäöABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ0123456789 ,.;:!?'-+=%<>[](){}/\r\n\t".toCharArray(); //ÃƒÆ†â¢Â‚€¬¶¤–“œ„
    
    //{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','å','ä','ö'};
    public static int indexOf(char c, char[] alf) {
        Integer num = null;
        for (int i = 0; i < alf.length; i++) {
            if (Character.toLowerCase(c) == alf[i]) {
                num = i;
            }
        }
        
        if ( num == null){throw new java.lang.NullPointerException("didn't find character for:'" + c + "'");}

        return num;
    }

    public static int indexOf(char c) {
        return indexOf(c, alphabet);
    }


    public static int factorial(int n){    
        if (n == 0)    
          return 1;    
        else    
          return(n * factorial(n-1));    
       }
       
    public static boolean contains(char[] dataArray, char test) {
        boolean cont = false;
        
        for (char object : dataArray) {
            if( object == test) cont = true;
        }

        return cont;
    }

}
