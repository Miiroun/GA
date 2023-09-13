import Interfaces.StringEncrytionInterface;

public class CaesarCipher implements StringEncrytionInterface {
    int key = 0;


    public char shitChar(char cha, int n) {
        char c = Character.toLowerCase(cha);

        c = Utility.alphabet[( Utility.indexOf(c) + n + 29) % 29];

        if (cha == ' ') {
            c = cha;
        }

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
            message = message + shitChar(c, (-1)*key);
        }

        return message;
    }
    public void setKey(String keyString) {
        key = Integer.valueOf(keyString);
    }

}
