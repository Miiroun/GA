public class CaesarCipher implements EncrytionInterface {

    public char[] key = "a".toCharArray(); 

    public byte[] enc(byte[] data) {
        char[] message = data.toString().toCharArray();

        for (int i = 0; i < message.length; i++) {  //inte efel med denna functionen, inte detta iallafall
            message[i] = (char) (message[i]     + key[ i % key.length]);
            
        }

        return message.toString().getBytes();
    }

    public byte[] dec(byte[] data) {
                char[] message = data.toString().toCharArray();

        for (int i = 0; i < message.length; i++) {
            message[i] = (char) (message[i]    - key[ i % key.length]);
            
        }

        return message.toString().getBytes();
    }

    public CaesarCipher() {
        //key = Main.keyArray.toString().toCharArray();
        key[0] = 0;
    }
    
}
