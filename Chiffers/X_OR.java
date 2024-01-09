package Chiffers;
import Other.Statistics;
import Other.Utility;
import Other.Interfaces.StringEncrytionInterface;

public class X_OR implements StringEncrytionInterface {
    public char[] key;


    public String crypt(String data, int factor){
        char[] dataArray = data.toString().toCharArray();
        char[] message = new char[dataArray.length];

        for (int i = 0; i < message.length; i++) {  //inte fel med denna functionen, inte detta iallafall
            message[i] = Utility.alphabet[(Utility.indexOf(dataArray[i])     + factor * (Utility.indexOf(key[ i % key.length])) +29)%29];
            
        }

        String messString = new String(message);
        return messString;
    }

        public String enc(String data) {
            return crypt(data, 1);
        }
    
    public String dec(String data) {
        Statistics.recordStat("CallDecMethod");

        return crypt(data, -1);
    }
    

    
    public void setKey(String keyString) {
        key = keyString.toCharArray();
    }
    
}
