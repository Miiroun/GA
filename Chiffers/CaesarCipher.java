package Chiffers;
import Other.Statistics;
import Other.Utility;
import Other.Interfaces.StringEncrytionInterface;

public class CaesarCipher implements StringEncrytionInterface {
    int key = 0;

    public char shitChar(char cha, int n) {
        return shiftCharAlfa(cha, n);
    }

    public char shiftCharAlfa(char cha, int n) {
    char lowChar = Character.toLowerCase(cha);
    char c;
    if(Utility.contains(Utility.alphabet, lowChar)) {
        int i = (Utility.indexOf(lowChar) + n  + (10 * Utility.alphabet.length)) % (Utility.alphabet.length);
        c = Utility.alphabet[i];
    } else {
        c =' ';
    }

        return c;
    }

    public char shiftCharSign(char cha, int n) {
        char c = (char) (cha + n);

        return c;
    }

    public String enc(String data) {
        char[] message = new char[data.length()];

        char[] charArray = data.toCharArray(); //blir långsamare och långsamare
        for (int i = 0; i < charArray.length; i++ ) {
            message[i] = shitChar(charArray[i], (1) * key);
        }

        return new String(message);
    }


    public String dec(String data) {
        //System.out.println("dec");
        Statistics.recordStat("CallDecMethod");
        char[] message = new char[data.length()];

        char[] charArray = data.toCharArray(); //blir långsamare och långsamare
        for (int i = 0; i < charArray.length; i++ ) {
            message[i] = shitChar(charArray[i], (-1) * key);
        }

        return new String(message);
    }
    public void setKey(String keyString) {
        key = Integer.valueOf(keyString);
    }

}
