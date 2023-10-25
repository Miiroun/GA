package Other;

import java.util.ArrayList;

public class QuadGram {

    private long[][][][] quadGram;
    private long length;

    public QuadGram() {
        quadGram = new long[29][29][29][29];
        length = 0;
    }

    public long getLength() {
        return length;
    }

    public long[][][][] getQuadGram() {
        return quadGram;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setQuadGram(long[][][][] quadGram) {
        this.quadGram = quadGram;
    }

    public void changeLength(long change) {
            this.length += change;

    }

    public static double compareNGrams(QuadGram qGrams1, QuadGram qGrams2, int nG2Count) {
        long[][][][] nGrams1 = qGrams1.getQuadGram();
        long[][][][] nGrams2 = qGrams2.getQuadGram();

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
    
    public void readNGrams(String path) {//shoould maybe redo method
        // reads the ngram in files
        QuadGram grams = new QuadGram();
        // int count = 0;

        String[] splits = Main.readData(path).split("\n", 0);

        for (String split : splits) {
            String gram = split.substring(0, 4);
            long num = Long.valueOf(split.substring(5));
            grams.setNgramValue(gram, num);
            // count += Integer.valueOf(split.substring(5));
        }

        // 79367664
        // System.out.println(count); //sould divide by this and then multiply by 1000
        quadGram = grams.getQuadGram();
        length = grams.getLength();
    }

    public long getNgramValue(String nGramS) {
        char[] nGram = nGramS.toCharArray();
        for (int i = 0; i < nGram.length; i++) nGram[i] = Character.toLowerCase(nGram[i]);
        
        //this for loop checks so that it is letters and not signs
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


        long value = quadGram[Utility.indexOf(nGram[0])][Utility.indexOf(nGram[1])][Utility.indexOf(nGram[2])][Utility
                .indexOf(nGram[3])];

        return value;

    }

    public void setNgramValue(String nGramS, long value) {
        char[] nGram = nGramS.toCharArray();

        for (char c : nGram) {
            if(Utility.contains(Utility.alphabet, c) != false)
            throw new java.lang.NullPointerException("something wrong with ngram trying to set:" + nGramS) ;
        }

        length += value - quadGram[Utility.indexOf(nGram[0])][Utility.indexOf(nGram[1])][Utility.indexOf(nGram[2])][Utility
                .indexOf(nGram[3])];
        quadGram[Utility.indexOf(nGram[0])][Utility.indexOf(nGram[1])][Utility.indexOf(nGram[2])][Utility
                .indexOf(nGram[3])] = value;
    }

    public static String[] genNGram(String data) {
        String str = Utility.removeSigns(data);

        ArrayList<String> grams = new ArrayList<String>() {};

        for (int i = 0; i <= str.length() - 4; i++) {
            char[] temp = { str.charAt(i), str.charAt(i + 1), str.charAt(i + 2), str.charAt(i + 3) };
            grams.add(new String(temp));
        }

        String[] gramArray = new String[grams.size()];
        gramArray = grams.toArray(gramArray);

        return gramArray;
    }

    public void countNGrams(String[] grams) {
        QuadGram nGramCount = new QuadGram();

        for (String gramS : grams) {
            nGramCount.setNgramValue(gramS, nGramCount.getNgramValue(gramS) + 1);
        }

        quadGram = nGramCount.getQuadGram();
        length = nGramCount.getLength();

    }
}
