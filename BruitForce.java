
import java.math.BigInteger;

import Chiffers.CaesarCipher;
import Interfaces.AttackInterface;

public class BruitForce implements AttackInterface{
    // atempts at DES
    public static void attackDES() {
        System.out.println("decrypting data");

        byte[] answer = Main.readData("data/startText.txt", false);
        Main.inputArray = Main.readData("data/encryptedText.txt", false);
        Main.keyArray = new byte[8];

        for (int i = 0; i < Math.pow(2, 64); i++) {
            BigInteger bigInteger = BigInteger.valueOf(i);
            Main.keyArray = bigInteger.toByteArray();
            Main.doCryotionBlocks(false);

            if (Main.outputArray == answer) {
                System.out.println("found you");
            }

            if (i % 100000 == 0) {
                System.out.println(i);
            }
        }

        Main.writeData(Main.outputArray, "data/endText.txt", true);
        Statistics.recordeStats();

    }

    public int evaluteText(String data) {
        //not yet implemented
        return 0;
    }

    public String attackCC(String data) {
        String[] message = new String[29];
        CaesarCipher cc = new CaesarCipher();

        for(int i = 0; i < 29; i++) {
            cc.setKey(Integer.toString(i));
            message[29] = cc.dec(data);
        }

        int bestMatch = -1;
        int bestValue = Integer.MAX_VALUE;
        for(int i = 0; i < 29; i++) {
            int value = evaluteText(data);
            if(value > bestValue) {
                bestMatch  = i;
                bestValue = value;
            }
        }

        return message[bestMatch];
    }

    @Override
    public String attackST(String data) {
        throw new UnsupportedOperationException("Unimplemented method 'attackST'");
    }

    @Override
    public String attackXO(String Data) {
        throw new UnsupportedOperationException("Unimplemented method 'attackXO'");
    }

}
