package attacks;

import java.math.BigInteger;
import java.util.ArrayList;

import Chiffers.CaesarCipher;
import Chiffers.ColumnarTransposition;
import Interfaces.AttackInterface;
import Other.Main;
import Other.Statistics;
import Other.Utility;

public class BruitForce implements AttackInterface {
    int[][][][] swe4Grams;

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

    public int[][][][] readNGrams(String path) {
        // reads the ngram in files
        int[][][][] grams = new int[29][29][29][29];
        // int count = 0;

        String[] splits = Main.readData(path).split("\n", 0);

        for (String split : splits) {
            grams = setNgramValue(grams, split.substring(0, 4), Integer.valueOf(split.substring(5)));
            // count += Integer.valueOf(split.substring(5));
        }

        // 79367664
        // System.out.println(count); //sould divide by this and then multiply by 1000
        return grams;
    }

    public int getNgramValue(int[][][][] grams, String nGramS) {
        char[] nGram = nGramS.toCharArray();
        
        for (char c : nGram) {
            aa: {
                for (char d : Utility.alphabet) {
                    if (c == d) {
                        break aa;
                    }
                }
                return 1000000000; // a big nummer to discurage signes not known
            }
        }


        int value = grams[Utility.indexOf(nGram[0])][Utility.indexOf(nGram[1])][Utility.indexOf(nGram[2])][Utility
                .indexOf(nGram[3])];

        return value;

    }

    public int[][][][] setNgramValue(int[][][][] grams, String nGramS, int value) {
        char[] nGram = nGramS.toCharArray();

        for (char c : nGram) {
            aa: {
                for (char d : Utility.alphabet) {
                    if (Character.toLowerCase(c) == d) {
                        break aa;
                    }
                }
                return grams; // should maybe print something when find characters like this
            }
        }


        // maybe not do swe4gram here but the loocal ngrams for our string
        grams[Utility.indexOf(nGram[0])][Utility.indexOf(nGram[1])][Utility.indexOf(nGram[2])][Utility
                .indexOf(nGram[3])] = value;

        return grams;
    }

    public String[] genNGram(String data) {
        String[] words = data.split(" ", 0);

        ArrayList<String> grams = new ArrayList<String>() {
        };

        for (String word : words) {
            for (int i = 0; i <= word.length() - 4; i++) {
                char[] temp = { word.charAt(i), word.charAt(i + 1), word.charAt(i + 2), word.charAt(i + 3) };
                grams.add(new String(temp));
            }
        }

        String[] gramArray = new String[grams.size()];
        gramArray = grams.toArray(gramArray);

        return gramArray;
    }

    public int[][][][] countNGrams(String[] grams) {
        int[][][][] nGramCount = new int[29][29][29][29];

        for (String gramS : grams) {
            nGramCount = setNgramValue(nGramCount, gramS, getNgramValue(nGramCount, gramS) + 1);
        }

        return nGramCount;

    }

    public double compareNGrams(int[][][][] nGrams1, int[][][][] nGrams2, int nG2Count) {
        double value = 0;
        double factor = (10000000/nG2Count);
        for (int i4 = 0; i4 < nGrams2.length; i4++) {
            for (int i3 = 0; i3 < nGrams2.length; i3++) {
                for (int i2 = 0; i2 < nGrams2.length; i2++) {
                    for (int i1 = 0; i1 < nGrams2.length; i1++) { //793 is precomupted and 10 000 / ngrams in data set
                        double temp = (nGrams1[i4][i3][i2][i1])  - (nGrams2[i4][i3][i2][i1] * factor);
                        value = value + Math.abs(temp);
                    }
                }
            }
        }

        return value;
    }

    public double evaluteText(String data) {
        Statistics.addDataCount("evaText", 1);
        // not yet implemented

        if (swe4Grams == null) {
            swe4Grams = readNGrams("data/nGrams/swedish_quadgrams.txt");
        }

        String[] Grams = genNGram(data); 
        int[][][][] nGramValues = countNGrams(Grams);

        double value = compareNGrams(swe4Grams, nGramValues, Grams.length);

        return value;
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
        // maybe create lookuptable if too slow

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
            message[i] = cc.dec(data);
        }

        int bestMatch = -1;
        double bestValue = Double.MAX_VALUE;
        double value;
        for (int i = 0; i < 29; i++) {
            value = evaluteText(message[i]);
            if(value < bestValue) { //something wrong with value function, contiusly decrease
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
            int[] key = new int[num];
            for (int j = 0; j < num; j++) { // nummbers that appera in key
                key[j] = j;
            }

            keys = permutations(key);
            // calculates all keys for current matrix length

            for (int j = 0; j < keys.length; j++) {
                // check the key and decrypt message
                ct.setKey(keys[j]);
                String text = ct.dec(data);
                double value = evaluteText(text);
                if (value <= bestValue) {
                    bestMatch = text;
                    bestValue = (int)value;
                }
            }

        }

        //

        return bestMatch;
    }

}
