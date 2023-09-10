
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

class Main {

    public enum EncStandard {
        DES,
        TriDES,
        D_H,
        CC,
        XOR,
        Sub
    }

    public enum AttStandard {
        FA,
        BF
    }

    public static EncStandard encStandard;
    public static AttStandard attStandard;
    public static boolean doBytes; // else work with strings
    public static int blockSize = 64;
    public static int byteSize = blockSize / 8;
    public static int blockCount;
    public static Charset charset = Charset.forName("UTF-8");

    public static void varibleSetUp(EncStandard encStand, AttStandard attStand) {
        encStandard = encStand;
        attStandard = attStand;

        if (encStandard == EncStandard.DES || encStandard == EncStandard.TriDES || encStandard == EncStandard.D_H) {
            doBytes = true;
            blockSize = 64;
            byteSize = blockSize / 8;
            charset = Charset.forName("UTF-8");
        } else if (encStandard == EncStandard.CC) {
            doBytes = false;
        }
    }

    // setup
    public static byte[] inputArray;
    public static byte[] outputArray;
    public static byte[] keyArray;

    public static String inputString;
    public static String outputString;
    public static String keyString;

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

    public static String readData(String path) {
        return "4";
    }

    public static void writeData(String string, String path) {

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

    public static void computeBlock(byte[] block, int i, ByteEncrytionInterface encClass, boolean encrypt) {
        byte[] data = block;

        if (encrypt) {
            data = encClass.enc(data);
        } else {
            data = encClass.dec(data);
        }

        System.arraycopy(data, 0, outputArray, i * byteSize, byteSize);

    }

    public static void doCryotionBlocks(boolean encrypt) {
        blockCount = Math.floorDiv((inputArray.length - 1), byteSize) + 1;
        outputArray = new byte[blockCount * byteSize];
        byte[][] blocks = seperateIntoBlocks();
        ByteEncrytionInterface encClass;

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

    public static void doCryption(boolean encrypt) {
        StringEncrytionInterface encClass;

        if (encStandard == EncStandard.CC) {
            encClass = new CaesarCipher();
        } else if (encStandard == EncStandard.XOR) {
            encClass = new X_OR();
        } else if (encStandard == EncStandard.Sub) {
            encClass = new Substitution();
        } else {
            // error, should never be here
            encClass = new CaesarCipher();
        }

        encClass.setKey(keyString);

        if (encrypt) {
            encClass.enc(inputString);
        } else {
            encClass.dec(inputString);
        }

    }

    public static void doAttacking() {
        AttackInterface attClass;

        if (attStandard == AttStandard.BF) {
            attClass = new BruitForce();
        } else if (attStandard == AttStandard.FA) {
            attClass = new FrequencyAnalysis();
        } else {
            // error, should never be here
            System.out.println("something wrong with attact standard selection");
            attClass = new BruitForce();
        }

        //

        if (encStandard == EncStandard.CC) {
            outputString = attClass.attackCC(inputString);
        } else if (encStandard == EncStandard.XOR) {
            outputString = attClass.attackXO(inputString);
        } else if (encStandard == EncStandard.Sub) {
            outputString = attClass.attackST(inputString);
        } else {
            // error, should never be here
            System.out.println("something wrong with attact standard selection");
        }

    }
    // algorithems

    public static void encryptData() {
        System.out.println("encrypting data");

        if (doBytes) {
            inputArray = readData("data/startText.txt", true);
            keyArray = readData("data/encryptKey.txt", false);

            doCryotionBlocks(true);

            writeData(outputArray, "data/encryptedText.txt", false);
        } else {
            inputString = readData("data/startText.txt");
            keyString = readData("data/encryptKey.txt");

            doCryption(true);

            writeData(outputString, "data/encryptedText.txt");
        }

        Statistics.recordeStats();
    }

    public static void decryptData() {
        System.out.println("decrypting data");

        if (doBytes) {
            inputArray = readData("data/encryptedText.txt", false);
            keyArray = readData("data/encryptKey.txt", false);

            //
            doCryotionBlocks(false);
            //

            writeData(outputArray, "data/endText.txt", true);
        } //
        else //
        { //
            inputString = readData("data/encryptedText.txt");
            keyString = readData("data/encryptKey.txt");

            //
            doCryption(false);
            //

            writeData(outputString, "data/endText.txt");
        }
        Statistics.recordeStats();
    }

    public static void attackData() {
        if (doBytes) {

        } else {
            System.out.println("attacking data");

            inputString = readData("data/encryptedText");

            doAttacking();

            writeData(outputString, "data/attackedText");
        }
        Statistics.recordeStats();

    }

    public static void main(String[] args) {
        varibleSetUp(EncStandard.CC, AttStandard.FA);
        Statistics.startCollecting();

        System.out.println("Staring...");

        encryptData();

        decryptData();

        //attackData();

        System.out.println("Done!");

        Statistics.endCollecting(false);
    }

}