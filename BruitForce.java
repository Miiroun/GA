
import java.math.BigInteger;

import Chiffers.CaesarCipher;
import Interfaces.AttackInterface;

public class BruitForce implements AttackInterface {
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

    public String[] genNGram(String data) {
                throw new UnsupportedOperationException("not yet implented nGrams function");

    }

    public int evaluteText(String data) {
        // not yet implemented

        // either quadgram or dictonary
        String[] nGrams = genNGram(data);
        int value = 0;

        throw new UnsupportedOperationException("not yet implented evalutate text function");
        //return value;
    }

    public int[] dividers(int nummber) {
        int[] div = new int[0];

        for (int i = 1; i < nummber; i++) {
            if (nummber % i == 0) {
                int[] tempDiv = new int[div.length + 1];
                System.arraycopy(div, 0, tempDiv, 0, div.length);
                div = tempDiv;
                div[div.length - 1] = i;
            }
        }

        return div;

    }

    public static int[][] permutations(int[] array) {
        //maybe create lookuptable if too slow

        if (array.length != 1) {
            int[][] perm = new int[Utility.factorial(array.length)][];
            int n = 0;

            int[] shortArr = new int[array.length - 1];
            System.arraycopy(array, 0, shortArr, 0, array.length - 1);

            int[][] tempPerm = permutations(shortArr);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < tempPerm.length; j++) {
                    int[] tempArr = new int[array.length];
                    if (i != 0) {
                        System.arraycopy(tempPerm[j], 0, tempArr, 0, i); // copys start until select index
                    }
                    tempArr[i] = array[array.length - 1]; // makes select index to last index
                    if (i != array.length - 1) {
                        System.arraycopy(tempPerm[j], i, tempArr, i + 1, array.length - 1 - i); // copys after select
                                                                                                // index
                    }

                    perm[n] = tempArr;
                    n++;
                }

            }
            return perm;
        } else {
            int[][] perm = new int[1][];
            perm[0] = array;
            return perm;
        }

    }

    public String attackCC(String data) {
        String[] message = new String[29];
        CaesarCipher cc = new CaesarCipher();

        for (int i = 0; i < 29; i++) {
            cc.setKey(Integer.toString(i));
            message[29] = cc.dec(data);
        }

        int bestMatch = -1;
        int bestValue = Integer.MAX_VALUE;
        for (int i = 0; i < 29; i++) {
            int value = evaluteText(message[i]);
            if (value > bestValue) {
                bestMatch = i;
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

    @Override
    public String attackCT(String data) {
        int[] div = dividers(data.length());
        ColumnarTransposition ct = new ColumnarTransposition();

        String bestMatch = "";
        int bestValue = Integer.MAX_VALUE;

        int[][] keys = null;
        for (int num : div) { // div is all matrix dimentions
            for (int i = 0; i < Utility.factorial(num); i++) { // nummber of permutations
                int[] key = new int[num];
                for (int j = 0; j < num; j++) { // nummbers that appera in key
                    key[j] = j;
                }

                keys = permutations(key);
                //calculates all keys for current matrix length
                
                for (int j = 0; j < keys.length; j++) {
                    // check the key and decrypt message
                    ct.setKey(keys[j]);
                    String text = ct.dec(data);
                    int value = evaluteText(text);
                    if (value >= bestValue) {
                        bestMatch = text;
                        bestValue = value;
                    }
                }
            }

        }

        //

        return bestMatch;
    }

}
