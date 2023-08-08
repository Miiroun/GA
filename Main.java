
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

class Main {

    public enum EncStandard {
        DES,
        TriDES,
        D_H
    }

    // curently working in bits not bytes

    public static int blockSize = 64;
    public static int byteSize = blockSize / 8;
    public static int blockCount;
    static Charset charset = Charset.forName("UTF-8");

    // setup
    public static byte[] inputArray;
    public static byte[] outputArray;
    public static byte[] keyArray;

    public static byte[] readData(String path, boolean isText) {

        byte[] inputArray = new byte[0];
        // read file
        try {
            File file = new File(path);
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(bytes);
            }
            inputArray = bytes;
            if (isText) {
                String input = new String(inputArray, charset);
                System.out.println("Input" + ":" + input);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
        return inputArray;
    }

    public static void writeData(byte[] byteArray, String path, boolean isText) {

        try {
            Files.write(Paths.get(path), byteArray);

            if (isText) {
                String output = new String(byteArray, charset);
                System.out.println("Output:" + output);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void writeStats() {

    }

    public static byte[][] seperateIntoBlocks() {

        byte[][] blocks = new byte[blockCount][];

        int i = 0;
        int j = byteSize;
        while (i < blockCount) {
            byte[] data = new byte[byteSize];
            if (i == blockCount - 1) {
                j = inputArray.length + byteSize - outputArray.length;
            }
            System.arraycopy(inputArray, i * byteSize, data, 0, j);

            blocks[i] = data;

            i = i + 1;
        }
        return blocks;
    }

    public static void computeBlock(byte[] block, int i, EncrytionInterface encClass, boolean encrypt) {
        byte[] data = block;

        /*
         * if (encStandard == EncStandard.DES) {
         * if (encrypt) {
         * data = DES.encDES(data);
         * } else {
         * data = DES.decDES(data);
         * }
         * } else if (encStandard == EncStandard.TriDES) {
         * if (encrypt) {
         * data = DES.encDES(data);
         * data = DES.encDES(data);
         * data = DES.encDES(data);
         * } else {
         * data = DES.decDES(data);
         * data = DES.decDES(data);
         * data = DES.decDES(data);
         * }
         * } else if (encStandard == EncStandard.D_H) {
         * if (encrypt) {
         * data = DiffieHellman.encDH(data);
         * } else {
         * data = DiffieHellman.decDH(data);
         * }
         * }
         */

        if (encrypt) {
            data = encClass.enc(data);
        } else {
            data = encClass.dec(data);
        }

        System.arraycopy(data, 0, outputArray, i * byteSize, byteSize);

    }

    public static void doCryotionBlocks(EncStandard encStandard, boolean encrypt) {
        blockCount = Math.floorDiv((inputArray.length - 1), byteSize) + 1;
        outputArray = new byte[blockCount * byteSize];
        byte[][] blocks = seperateIntoBlocks();
        EncrytionInterface encClass;

        if (encStandard == EncStandard.DES) {
            encClass = new DES();
        } else if (encStandard == EncStandard.TriDES) {
            encClass = new TriDES();
        } else if (encStandard == EncStandard.D_H) {
            encClass = new DiffieHellman();
        } else {
            // error, should never be here
            encClass = new DES();
        }

        for (int i = 0; i < blocks.length; i++) {
            computeBlock(blocks[i], i, encClass, encrypt);
        }
    }

    // algorithems

    public static void encryptData(EncStandard encStandard) {
        System.out.println("encrypting data");

        inputArray = readData("data/startText.txt", true);
        keyArray = readData("data/encryptKey.txt", false);

        doCryotionBlocks(encStandard, true);

        writeData(outputArray, "data/encryptedText.txt", false);
        writeStats();
    }

    public static void decryptData(EncStandard encStandard) {
        System.out.println("decrypting data");

        inputArray = readData("data/encryptedText.txt", false);
        keyArray = readData("data/encryptKey.txt", false);

        //
        doCryotionBlocks(encStandard, false);
        //

        writeData(outputArray, "data/endText.txt", true);
        writeStats();
    }

    public static void main(String[] args) {
        System.out.println("Staring...");
        encryptData(EncStandard.D_H);
        // decryptData(EncStandard.D_H);
        // Hacking.hack(EncStandard.DES);
        System.out.println("Done!");
    }

}