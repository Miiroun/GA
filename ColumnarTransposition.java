import Interfaces.StringEncrytionInterface;

public class ColumnarTransposition implements StringEncrytionInterface {
    public int[] key;

    public char[][] createMatrix(String data) {
        char[][] matrix = new char[key.length][];
        int width = key.length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < Math.ceil(data.length() / width); j++) {
                char c;
                if (i * width + j < data.length()) {
                    c = data.charAt(i * width + j);
                } else {
                    c = 'q';
                }
                matrix[i][j] = c;
            }
        }
        return matrix;
    }

    @Override
    public String enc(String data) {
        char[][] matrix = createMatrix(data);
        String message = "";

        for (int i = 0; i < matrix[0].length; i++) {
            int l = 0;
            for (int k = 0; k < key.length; k++) {
                if (i == key[i]) {
                    l = k;
                }
            } // translates i inte l

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
        throw new UnsupportedOperationException("Unimplemented method 'dec'");
    }

    @Override
    public void setKey(String data) {
        key = new int[data.length()];
        char[] localAlphabet = Utility.alphabet;

        for (int i = 0; i < data.length(); i++) {
            int k = 0;

            breakPoint: {
                while (k < localAlphabet.length) {
                    char c = localAlphabet[k];
                    for (int j = 0; j < data.length(); j++) {
                        char d = data.toCharArray()[j];
                        if (c == d) {
                            key[i] = d;
                            break breakPoint;
                        }
                    }
                    k += 1;
                }
            }
        }
    }

}
