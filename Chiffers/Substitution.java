package Chiffers;
import java.util.Set;
import java.util.TreeSet;

import Other.Statistics;
import Other.Utility;
import Other.Interfaces.StringEncrytionInterface;

public class Substitution implements StringEncrytionInterface {

    char[] encKey;

    private char[] genSimpleKey(String keyString) {
        char[] key = new char[29];

        Set<Character> letters = new TreeSet<Character>();
        for (Character c : Utility.alphabet)
            letters.add(c);

        char[] inputArray = keyString.toCharArray(); 
        for (int i = 0; i < inputArray.length; i++) inputArray[i] = Character.toLowerCase(inputArray[i]);

        for (int i = 0; i < inputArray.length; i++) {
            char c = Character.toLowerCase(inputArray[i]);
            if (letters.contains(c)) {letters.remove(c);
            } else {throw new java.lang.NullPointerException("not a valid key, contains:" + c);}
        }


        for (Character c : inputArray)letters.remove(c);

        Character[] tempLetArray = new Character[letters.size()];
        letters.toArray(tempLetArray);
        char[] tempLetArrayChar = new char[tempLetArray.length];
        for (int i = 0; i < tempLetArray.length; i++)tempLetArrayChar[i] = tempLetArray[i];

        System.arraycopy(inputArray, 0, key, 0, inputArray.length);
        System.arraycopy(tempLetArrayChar, 0, key, inputArray.length, tempLetArrayChar.length);

        return key;
    }

    public String crypt(String data, char[] key, char[] alphabet) {
        String message = "";

        char[] dataArray = data.toCharArray();
        for (char c : dataArray) {
            if (Utility.contains(Utility.alphabet, c))
                message = message + String.valueOf(key[Utility.indexOf(c, alphabet)]);
        }

        return message;
    }

    public String enc(String data) {
        return crypt(data, encKey, Utility.alphabet);
    }

    public String dec(String data) {
        Statistics.recordStat("CallDecMethod");
        return crypt(data, Utility.alphabet, encKey);
    }

    public void setKey(String keyString) {
        encKey = genSimpleKey(keyString);
    }

}
