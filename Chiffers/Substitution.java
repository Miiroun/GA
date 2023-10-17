package Chiffers;
import java.util.Set;
import java.util.TreeSet;

import Interfaces.StringEncrytionInterface;
import Other.Utility;

public class Substitution implements StringEncrytionInterface {

    char[] encKey;
    char[] decKey;

    private void genSimpleKey(String keyString) {
        encKey = new char[29];

        Set<Character> letters = new TreeSet<Character>();
        for (Character c : Utility.alphabet)
            letters.add(c);

        char[] tempInput = keyString.toCharArray(); 
        for (int i = 0; i < tempInput.length; i++) tempInput[i] = Character.toLowerCase(tempInput[i]);

        for (int i = 0; i < tempInput.length; i++) {
            char c = Character.toLowerCase(tempInput[i]);
            if (letters.contains(c)) {letters.remove(c);
            } else {throw new java.lang.NullPointerException("not a valid key, contains:" + c);}
        }


        for (Character c : tempInput)letters.remove(c);

        Character[] tempLetArray = new Character[letters.size()];
        letters.toArray(tempLetArray);
        char[] tempLetArrayChar = new char[tempLetArray.length];
        for (int i = 0; i < tempLetArray.length; i++)
            tempLetArrayChar[i] = Character.toLowerCase(tempLetArray[i]);

        System.arraycopy(tempInput, 0, encKey, 0, tempInput.length);
        System.arraycopy(tempLetArrayChar, 0, encKey, tempInput.length - 1, tempLetArray.length);

        genDecFromEncKey();
    }

    private void genDecFromEncKey() { // something wrong with this
        //String str = new String(encKey);
        decKey =  new char[29];
        //crypt(str, encKey).toCharArray();

        /*
         * for (int i = 0; i < 29; i++) {
         * Character d = null;
         * for (int j = 0; j < 29; j++) {
         * char c = encKey[j];
         * if ( Utility.alphabet[i] == c){
         * //d = Utility.alphabet[Utility.indexOf(c)];
         * d = Utility.alphabet[i];
         * decKey[j] = d;
         * }
         * }
         * 
         * 
         * }
         */
    }

    public String crypt(String data, char[] key) {
        String message = "";

        char[] dataArray = data.toCharArray();

        for (char c : dataArray) {
            if (Utility.contains(Utility.alphabet, c))
                message = message + String.valueOf(key[Utility.indexOf(c)]);
        }

        return message;
    }

    public String enc(String data) {
        return crypt(data, encKey);
    }

    public String dec(String data) {
        return crypt(data, encKey);
    }

    public void setKey(String keyString) {
        genSimpleKey(keyString);
    }

}
