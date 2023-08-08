import java.math.BigInteger;

public class Hacking {

    // atempts at DES
    public static void bruitForceDES() {
        System.out.println("decrypting data");

        byte[] answer = Main.readData("data/startText.txt", false);
        Main.inputArray = Main.readData("data/encryptedText.txt", false);
        Main.keyArray = new byte[8];

        for (int i = 0; i < Math.pow(2, 64); i++) {
            BigInteger bigInteger = BigInteger.valueOf(i);
            Main.keyArray = bigInteger.toByteArray();
            Main.doCryotionBlocks(Main.EncStandard.DES, false);

            if (Main.outputArray == answer) {
                System.out.println("found you");
            }

            if (i % 100000 == 0) {
                System.out.println(i);
            }
        }

        Main.writeData(Main.outputArray, "data/endText.txt", true);
        Main.writeStats();

    }

    public static void hack(Main.EncStandard encStandard) {
        if (encStandard == Main.EncStandard.DES) {
            bruitForceDES();
        }
    }

}
