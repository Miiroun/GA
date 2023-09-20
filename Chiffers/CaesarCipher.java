package Chiffers;
import Interfaces.StringEncrytionInterface;

public class CaesarCipher implements StringEncrytionInterface {
    int key = 0;

    public char shitChar(char cha, int n) {
        //int i = Utility.indexOf(cha) + n + Utility.signs.length;
        char c = (char) (cha + n);//Utility.signs[i % Utility.signs.length];

        return c;
    }

    public String enc(String data) {
        String message = "";

        for (char c : data.toCharArray()) {
            message = message + shitChar(c, key);
        }

        return message;   
    }


    public String dec(String data) {
        String message = "";

        for (char c : data.toCharArray()) {
            message = message + shitChar(c, -key);
        }

        return message;
    }
    public void setKey(String keyString) {
        key = Integer.valueOf(keyString);
    }

}
