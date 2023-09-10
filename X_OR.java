public class X_OR implements StringEncrytionInterface {
    public char[] key = "a".toCharArray(); 

    public String enc(String data) {
        char[] message = data.toString().toCharArray();

        for (int i = 0; i < message.length; i++) {  //inte efel med denna functionen, inte detta iallafall
            message[i] = (char) (message[i]     + key[ i % key.length]);
            
        }

        return message.toString();
    }

    public X_OR() {
        key[0] = 0;

    }

    public String dec(String data) {
                char[] message = data.toString().toCharArray();

        for (int i = 0; i < message.length; i++) {
            message[i] = (char) (message[i]    - key[ i % key.length]);
            
        }

        return message.toString();
    }
    

    
    public void setKey(String keyString) {
        throw new UnsupportedOperationException("Unimplemented method 'setKey'");
    }
    
}
