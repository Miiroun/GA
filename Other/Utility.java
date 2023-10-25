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

    public static int[][] permutations(int[] array) {
        // maybe create lookuptable if too slow

        if (array.length != 1) {
            int[][] perm = new int[Utility.factorial(array.length)][];
            int n = 0;

            int[] shortArr = new int[array.length - 1];
            System.arraycopy(array, 0, shortArr, 0, array.length - 1);

            int[][] tempPerm = permutations(shortArr);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < tempPerm.length; j++) {
                    int[] tempArr = new int[array.length];
                    if (i != 0) {
                        System.arraycopy(tempPerm[j], 0, tempArr, 0, i); // copys start until select index
                    }
                    tempArr[i] = array[array.length - 1]; // makes select index to last index
                    if (i != array.length - 1) {
                        System.arraycopy(tempPerm[j], i, tempArr, i + 1, array.length - 1 - i); // copys after select
                                                                                                // index
                    }

                    perm[n] = tempArr;
                    n++;
                }

            }
            return perm;
        } else {
            int[][] perm = new int[1][];
            perm[0] = array;
            return perm;
        }

    }

    public static int[] dividers(int nummber) {
        int[] div = new int[0];

        for (int i = 1; i < nummber; i++) {
            if (nummber % i == 0) {
                int[] tempDiv = new int[div.length + 1];
                System.arraycopy(div, 0, tempDiv, 0, div.length);
                div = tempDiv;
                div[div.length - 1] = i;
            }
        }

        return div;

    }

    public static String removeSigns(String data) {
        String message = "";
        
        for (char c : data.toCharArray()) {
            if(contains(alphabet, c)) message += Character.toString(c);
        }

        return message;
    }


}
