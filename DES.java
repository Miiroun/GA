
import java.util.BitSet;
public class DES implements ByteEncrytionInterface {
    
    // functions
    public static BitSet shiftLeft(BitSet workSet, int size) {
        BitSet localBitSet = new BitSet(size);

        for (int i = 0; i <= size; i++) {
            localBitSet.set(i, workSet.get(i + 1));
        }
        localBitSet.set(size - 1, workSet.get(0));

        // printBitSet(28, localBitSet, 32);

        return localBitSet;
    }

    public static BitSet fuseBitSet(BitSet bitSet1, int size1, BitSet bitSet2, int size2) {
        BitSet tempBitSet = new BitSet(size1 + size2);
        for (int i = 0; i < size1 + size2; i++) {
            boolean n;
            if (i < size1) {
                n = bitSet1.get(i);
            } else {
                n = bitSet2.get(i - size1);
            }
            tempBitSet.set(i, n);
        }
        return tempBitSet;
    }

    public static void printBitSet(int bitDis, byte[] byteArray) {
        String decimal = "";
        String binary = "";
        String hex = "";
        for (byte b : byteArray) {
            int i = Byte.toUnsignedInt(b);
            decimal += Integer.toString(i) + " ";
            String tempBinary = Integer.toBinaryString(i);
            while (tempBinary.length() < Math.min(8, bitDis)) {
                tempBinary = "0" + tempBinary;
            }
            binary = binary + tempBinary;
            hex += Integer.toHexString(i) + "       ";
        }
        int times = binary.length() / bitDis;
        for (int i = 0; i < times; i++) {
            binary = binary.substring(0, i * (bitDis + 1) + bitDis)
                    + " "
                    + binary.substring(i * (bitDis + 1) + bitDis, binary.length());
            // hex = hex.substring(0, i * 5 + 1) + " " + hex.substring(i * 5 + 2,
            // hex.length());
        }
        System.out.println("decimal:" + decimal);
        System.out.println("binary :" + binary);
        System.out.println("hex :" + hex);
    }

    public static void printBitSet(int bitDis, BitSet bits, int length) {
        byte[] byteArray = reverseByteArray(reverseBitSet(bits, length).toByteArray());
        printBitSet(bitDis, byteArray);
    }

    public static BitSet reverseBitSet(BitSet bitSet, int tempSize) {
        int size = tempSize;
        BitSet temp = new BitSet(size);

        for (int i = 0; i < size; i++) {
            temp.set(i, bitSet.get(size - 1 - i));
        }

        return temp;
    }

    public static byte[] reverseByteArray(byte[] array) {
        int size = array.length;
        byte[] temp = new byte[size];

        for (int i = 0; i < size; i++) {
            temp[i] = array[size - 1 - i];
        }

        return temp;
    }

    //

    //

    // tables
    // for keys
    static final int _PC1[] = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
    // for keys
    static final int _PC2[] = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };
    // for message
    static final int _IP[] = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    static final int _E[] = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    static final int _P[] = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };

    static final int _IP_1[] = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    static final int tableS[][][] = {
            {
                    { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
            },
            {
                    { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
            },
            {
                    { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
            },
            {
                    { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
            },
            {
                    { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
            },
            {
                    { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }
            },
            {
                    { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }
            },
            {
                    { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }
            },
    };

    static final int shifts[] = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

    static BitSet[] _L;
    static BitSet[] _R;

    static BitSet keyKN[];

    //

    //

    //

    //

    //

    // algorithms

    static BitSet permutation(BitSet workSet, int[] table) {
        BitSet localBitSet = new BitSet(table.length);
        for (int i = 0; i < table.length; i++) {
            // System.out.print(Integer.toString(i) + " "
            // + Boolean.toString(workSet.get(table[i] - 1)) + " "
            // + Integer.toString(table[i]) + " \n");
            localBitSet.set(i, workSet.get(table[i] - 1));
        }
        return localBitSet;
    }

    static BitSet[] keyTransformation(BitSet workSet) {
        BitSet localKeyArray[] = new BitSet[16];

        BitSet[] C = new BitSet[17];
        BitSet[] D = new BitSet[17];
        C[0] = workSet.get(0, 28);
        D[0] = workSet.get(28, 56);
        // printBitSet(4, D[0], 32);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < shifts[i]; j++) {
                C[i + 1] = shiftLeft(C[i + j], 28);
                D[i + 1] = shiftLeft(D[i + j], 28);
            }
            // System.out.print(i);// + "\n");
            // printBitSet(28, C[i], 32);

        }

        for (int i = 0; i < 16; i++) {
            localKeyArray[i] = permutation(fuseBitSet(C[i + 1], 28, D[i + 1], 28), _PC2);
            // printBitSet(6, D[i + 1], 28);
            // printBitSet(6, fuseBitSet(C[i + 1], 28, D[i + 1], 28), 56);
            // printBitSet(6, localKeyArray[i], 48);
        }

        return localKeyArray;

    }

    static BitSet funSi(BitSet _Bi, int n) {
        BitSet selected;
        int a = n;
        int i = 1 * (_Bi.get(5) ? 1 : 0) + 2 * (_Bi.get(0) ? 1 : 0);
        int j = 1 * (_Bi.get(4) ? 1 : 0)
                + 2 * (_Bi.get(3) ? 1 : 0)
                + 4 * (_Bi.get(2) ? 1 : 0)
                + 8 * (_Bi.get(1) ? 1 : 0);
        int integer = tableS[a][i][j];
        // System.out.println(integer);
        // long[] longs = new long[1];
        // longs[0] = (long) integer;
        // selected = BitSet.valueOf(longs);

        byte[] bytes = new byte[1];
        bytes[0] = (byte) integer;
        // selected = reverseBitSet(BitSet.valueOf(reverseByteArray(bytes)), 6);
        selected = reverseBitSet(BitSet.valueOf(reverseByteArray(bytes)), 4);

        // System.out.println(selected);
        return selected;
    }

    static BitSet funLn(int n) {

        if (_L[n] != null) {
            return _L[n];
        } else {
            BitSet temBitSet;
            temBitSet = funRn(n - 1);
            _L[n] = temBitSet;
            // System.out.print("L:" + n + " ");
            // printBitSet(8, temBitSet, 32);
            return temBitSet;
        }

    }

    static BitSet funRn(int n) {
        if (_R[n] != null) {
            return _R[n];
        } else {
            BitSet temBitSet;

            temBitSet = funLn(n - 1);
            temBitSet.xor(funFn(n));
            _R[n] = temBitSet;
            // System.out.print("R:" + n + " ");
            // printBitSet(8, temBitSet, 32);
            return temBitSet;

        }

    }

    static BitSet funFn(int n) {
        // original
        BitSet rN_1 = funRn(n - 1);
        BitSet kN = keyKN[n - 1]; // my Kn hase it index moved one nummber lower

        BitSet _Ern_1 = permutation(rN_1, _E);
        kN.xor(_Ern_1);

        // System.out.print("K" + n + " + E(R" + (n - 1) + "): ");
        // printBitSet(8, kN, 48); //this looks correct, atelast for n=1

        BitSet _Si[] = new BitSet[8];

        BitSet sumS = new BitSet(0);
        for (int i = 0; i < 8; i++) {
            // this part seperates it into 8 Bi boxes
            BitSet _Bi = kN.get(i * 6, (i + 1) * 6);

            // this part aplies the Si box to it's coresponding Bi box
            _Si[i] = funSi(_Bi, i);

            sumS = fuseBitSet(sumS, i * 4, _Si[i], 4);
        }

        BitSet temp = permutation(sumS, _P);

        return temp;
    }

    //

    //

    //

    // main functions
    public byte[] enc(byte[] workArray) {

        // generate keys
        BitSet keyK = reverseBitSet(BitSet.valueOf(reverseByteArray(Main.keyArray)), 64);
        BitSet keyKplus = permutation(keyK, _PC1);
        keyKN = keyTransformation(keyKplus);

        // prep message
        BitSet message = reverseBitSet(BitSet.valueOf(reverseByteArray(workArray)), 64);
        BitSet ipMessage = permutation(message, _IP);
        _L = new BitSet[17];
        _R = new BitSet[17];
        _L[0] = ipMessage.get(0, 32);
        _R[0] = ipMessage.get(32, 64);

        // here do rounds
        BitSet _R16L16 = fuseBitSet(funRn(16), 32, funLn(16), 32);
        BitSet c = permutation(_R16L16, _IP_1);

        // make sure the array that sends back is correct size
        byte[] sendBytes = new byte[Main.byteSize];
        byte[] tempBytes = reverseByteArray(reverseBitSet(c, 64).toByteArray());
        System.arraycopy(tempBytes, 0, sendBytes, 0, tempBytes.length);
        return sendBytes;
    }

    //

    public byte[] dec(byte[] workArray) {
        // generate keys
        BitSet keyK = reverseBitSet(BitSet.valueOf(reverseByteArray(Main.keyArray)), 64);
        BitSet keyKplus = permutation(keyK, _PC1);
        keyKN = keyTransformation(keyKplus);

        BitSet tempKeyKN[] = new BitSet[keyKN.length];
        for (int i = 0; i < keyKN.length; i++) {
            tempKeyKN[i] = keyKN[keyKN.length - i - 1];
        }
        keyKN = tempKeyKN;

        // prep message
        BitSet message = reverseBitSet(BitSet.valueOf(reverseByteArray(workArray)), 64);
        BitSet ipMessage = permutation(message, _IP);
        _L = new BitSet[17];
        _R = new BitSet[17];
        _L[0] = ipMessage.get(0, 32);
        _R[0] = ipMessage.get(32, 64);

        // here do rounds
        BitSet _R16L16 = fuseBitSet(funRn(16), 32, funLn(16), 32);
        BitSet c = permutation(_R16L16, _IP_1);

        // make sure the array that sends back is correct size
        byte[] sendBytes = new byte[Main.byteSize];
        byte[] tempBytes = reverseByteArray(reverseBitSet(c, 64).toByteArray());
        System.arraycopy(tempBytes, 0, sendBytes, 0, tempBytes.length);
        return sendBytes;
    }

    public void setKey(byte[] data) {
        throw new UnsupportedOperationException("Unimplemented method 'setKey'");
    }
}
