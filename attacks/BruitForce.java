package attacks;

import java.math.BigInteger;

import Chiffers.CaesarCipher;
import Chiffers.ColumnarTransposition;
import Chiffers.Substitution;
import Other.Main;
import Other.QuadGram;
import Other.Statistics;
import Other.Utility;
import Other.Interfaces.AttackInterface;

public class BruitForce implements AttackInterface {
    QuadGram swe4Grams;

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

    public double evaluateNGram(String data) {
        Statistics.addDataCount("evaText", 1);
        // not yet implemented

        if (swe4Grams == null){
            swe4Grams = new QuadGram();
             swe4Grams.readNGrams("data/nGrams/swedish_quadgrams.txt");}


        String[] Grams = QuadGram.genNGram(data); 
        QuadGram nGramValues = new QuadGram();
        nGramValues.countNGrams(Grams);

        double value = QuadGram.compareNGrams(swe4Grams, nGramValues, Grams.length);

        return value;
    }

    public double evaluteIOC(String data) { //index of coincidence
        //does not work for transposition chifers, can just be used to tell that been incrypted by substitution
        int alfLength = 29;

        double sum = 0d;
        long n = data.length();
        char[] letterarray = data.toCharArray();
        int[] letterCount = new int[29];

        for (int i = 0; i < letterarray.length; i++){ 
            aa:{for(char c : Utility.alphabet){if(letterarray[i] == c)break aa;}letterarray[i] = 'q';}} //if char:i not in letterArray then replace with q
        for (char c : letterarray) letterCount[Utility.indexOf(c)] += 1;

        for (int i = 0; i < letterCount.length; i++){ 
            long mult = Math.multiplyExact(letterCount[i], (letterCount[i] -1));
            sum += mult;
        }

        long div = Math.multiplyExact(n, n-1);
        return sum *(alfLength) / div ;
    }

    public double evaluateQGProb(String data) {//Quadgram but with proberbility
        if (swe4Grams == null){
            swe4Grams = new QuadGram();
             swe4Grams.readNGrams("data/nGrams/swedish_quadgrams.txt");}

        String[] grams = QuadGram.genNGram(data); 
        double value = 0d;


        long length = swe4Grams.getLength();
        double log2 = Math.log10(length);
        for(String gram : grams) {
            long standVal = swe4Grams.getNgramValue(gram);
            double prob;
            if(standVal != 0) {
                double log1 = Math.log10(standVal);
                prob = log1 - log2;
                //log ( val / (length) ); 
            } else {
                prob = 0;
            }
            value += Math.abs(prob);   
        }

        double normalValue = (value)/grams.length;
        double removedSigns = -50; int k = 5;
        if(grams.length != 0)removedSigns = (k * ((4 * grams.length ) - data.length()) )/ (grams.length); 
        return normalValue + removedSigns;
    }

    public double evaluteText(String data) {
        //return evaluateNGram(data);
        //return evaluteIOC(data);
        return evaluateQGProb(data);
    }
    

    public String attackCC(String data) {
        final int testLength = 29*2;
        String[] message = new String[testLength];
        CaesarCipher cc = new CaesarCipher();

        for (int i = 0; i < testLength; i++) {
            cc.setKey(Integer.toString(i- (testLength/2)));
            message[i] = cc.dec(data);
        }

        int bestMatch = -1;
        double bestValue = Double.MIN_VALUE; //change sign if focus on low
        double[] values = new double[testLength];
        for (int i = 0; i < testLength; i++) {
            values[i] = evaluteText(message[i]); 
            if(values[i] > bestValue) {// change sign if focus on low
                bestMatch = i;
                bestValue = values[i];
            }
        }

        return message[bestMatch];
    }

    @Override
    public String attackST(String data) {
        int key[] = new int[29];
        for (int i = 1; i <= key.length; i++) key[i] = i;
        int keys[][] = Utility.permutations(key);


        Substitution sub = new Substitution();
        String[] message = new String[Utility.factorial(29)];
        int k = 0;//im breacking integer limit, finns 8*10^30 möjligheter
        for (int[] is : keys) {
            String inChar = "";
            for (int i = 0; i < is.length; i++) inChar += Utility.alphabet[is[i]];
            sub.setKey(inChar);
            message[k] = sub.dec(data);
            k++;

        }


        int bestMatch = -1;
        double bestValue = Double.MAX_VALUE;
        double value;
        for (int i = 0; i < message.length; i++) {
            value = evaluteText(message[i]);
            if(value < bestValue) { //something wrong with value function, contiusly decrease
                bestMatch = i;
                bestValue = value;
            }
        }
        return message[bestMatch];
    }

    @Override
    public String attackXO(String Data) {
        throw new UnsupportedOperationException("Unimplemented method 'attackXO'");
    }

    @Override
    public String attackCT(String data) {
        int[] div = Utility.dividers(data.length());
        ColumnarTransposition ct = new ColumnarTransposition();

        String bestMatch = "";
        int bestValue = Integer.MAX_VALUE;

        int[][] keys = null;
        for (int num : div) { // div is all matrix dimentions
            int[] key = new int[num];
            for (int j = 0; j < num; j++) { // nummbers that appera in key
                key[j] = j;
            }

            keys = Utility.permutations(key);
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
