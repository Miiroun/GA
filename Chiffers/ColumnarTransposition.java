package Chiffers;

import Other.Statistics;
import Other.Utility;
import Other.Interfaces.StringEncrytionInterface;

public class ColumnarTransposition implements StringEncrytionInterface {
    public int[] key;

    public char[][] createMatrixEnc(String data) {
        int width = key.length;
        int hight = (int) Math.ceil(data.length() / width);
        char[][] matrix = new char[width][hight];

        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                char c;
                if (i * width + j < data.length()) {
                    c = data.charAt(i * width + j);
                } else {
                    c = 'q';
                }
                matrix[j][i] = c;
            }
        }
        return matrix;
    }

    public char[][] createMatrixDec(String data) {
        int width = key.length;
        int hight = (int) Math.ceil(data.length() / width);
        char[][] matrix = new char[width][hight];

        for (int i = 0; i < width; i++) {
            int l = indexOfvalue(key, i);
            // l = i;

            for (int j = 0; j < hight; j++) {
                char c = data.charAt((i * hight) + j);
                // char c = data.charAt((i) + j*(width));
                matrix[l][j] = c;
            }
        }

        return matrix;
    }

    public int indexOfvalue(int[] data, int i) {
        Integer l = null;
        for (int k = 0; k < data.length; k++) {
            if (i == data[k]) {
                l = k;
            }
        } // translates i till l
        return l;
    }

    @Override // encryption works, not decrypt
    public String enc(String data) {
        char[][] matrix = createMatrixEnc(data);
        String message = "";

        for (int i = 0; i < key.length; i++) {
            int l = indexOfvalue(key, i);

            char[] cs = matrix[l];
            for (int j = 0; j < cs.length; j++) {
                char c = cs[j];
                message += c;
            }
        }

        return message;
    }

    @Override
    public String dec(String data) {
        Statistics.recordStat("CallDecMethod");

        char[][] matrix = createMatrixDec(data);
        String message = "";

        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                message += matrix[i][j];
            }
        }

        return message;
    }

    @Override
    public void setKey(String data) {
        key = new int[data.length()];
        char[] localAlphabet = Utility.alphabet;
        char[] dataArray = data.toCharArray();

        int i = 0;
        while (i < data.length()) {
            int k = 0;
            while (k < localAlphabet.length) {
                char alf = localAlphabet[k];
                for (int j = 0; j < data.length(); j++) { 
                    char da = dataArray[j];
                    if (alf == Character.toLowerCase(da)) {
                        // i ++;
                        key[j] = i;
                        i++;
                    }
                }
                k += 1;

            }
        }

    }

    public void setKey(int[] data) {
        key = data;
    }

}
