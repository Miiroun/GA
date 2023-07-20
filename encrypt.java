import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Encrypt {

    public enum EncStandard {
        encDES,
        decDES
    }

    // curently working in bits not bytes

    static final int blockSize = 64 / 8;
    static Charset charset = Charset.forName("UTF-8");

    // setup
    public static byte[] inputArray;
    public static byte[] outputArray;
    public static byte[] keyArray;

    public static byte[] readData(String path, boolean isText) {

        byte[] inputArray = new byte[0];
        // read file
        try {

            if (isText) {
                String input = "";
                File file = new File(path);
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    input = input + sc.nextLine();
                }
                sc.close();
                inputArray = input.getBytes(charset);
                System.out.println("Input" + ":" + input);
            } else {
                inputArray = Files.readAllBytes(Paths.get(path));
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return inputArray;
    }

    public static void writeData(byte[] byteArray, String path, boolean isText) {

        try {
            if (isText) {
                String output = new String(byteArray, charset);
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(output);
                fileWriter.close();
                System.out.println("Output:" + output);
            } else {
                Files.write(Paths.get(path), byteArray);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void writeStats() {

    }

    public static void seperateIntoBlocks(EncStandard encStandard) {
        int blockCount = Math.floorDiv((inputArray.length - 1), blockSize) + 1;
        outputArray = new byte[blockCount * blockSize];
        int i = 0;
        int j = blockSize;
        while (i < blockCount) {
            byte[] data = new byte[blockSize];
            if (i == blockCount - 1) {
                j = inputArray.length + blockSize - outputArray.length;
            }
            System.arraycopy(inputArray, i * blockSize, data, 0, j);
            if (encStandard == EncStandard.encDES) {
                data = encDES(data);
            } else if (encStandard == EncStandard.decDES) {
                data = decDES(data);
            }

            System.arraycopy(data, 0, outputArray, i * blockSize, blockSize);
            i = i + 1;
        }
    }

    // algorithems

    public static byte[] encDES(byte[] workArray) {
        byte[] localArray = workArray;
        for (int j = 0; j < blockSize; j++) {
            byte b = localArray[j];
            int i = (int) b;
            i = ~i;
            // System.out.print(i + ", " + ~i);
            b = (byte) i;
            localArray[j] = b;

        }

        return localArray;
    }

    public static byte[] decDES(byte[] workArray) {
        byte[] localArray = workArray;
        for (int j = 0; j < blockSize; j++) {
            byte b = localArray[j];
            int i = (int) b;
            i = ~i;
            // System.out.print(i + ", " + ~i);
            b = (byte) i;
            localArray[j] = b;

        }

        return localArray;
    }

    public static void encryptData() {
        System.out.println("encrypting data");

        inputArray = readData("data/decryptedText.txt", true);
        keyArray = readData("data/encryptKey.txt", false);

        seperateIntoBlocks(EncStandard.encDES);

        writeData(outputArray, "data/encryptedText.txt", false);
        writeStats();
    }

    public static void decryptData() {
        System.out.println("decrypting data");

        inputArray = readData("data/encryptedText.txt", false);
        keyArray = readData("data/encryptKey.txt", false);

        seperateIntoBlocks(EncStandard.decDES);

        writeData(outputArray, "data/decryptedText.txt", true);
        writeStats();
    }

    public static void main(String[] args) {
        System.out.println("Staring...");
        encryptData();
        decryptData();
        System.out.println("Done!");
    }

}