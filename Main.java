import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Main {

    public enum EncStandard {
        encDES,
        decDES
    }

    // curently working in bits not bytes

    public static final int blockSize = 64;
    public static final int byteSize = blockSize / 8;
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
                for (int i = 0; i < byteArray.length; i++) {
                    if (byteArray[i] == 0) {
                        byte[] temp = new byte[i];
                        System.arraycopy(byteArray, 0, temp, 0, i);
                        byteArray = temp;
                        i = i - 1;
                    }
                }
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
        int blockCount = Math.floorDiv((inputArray.length - 1), byteSize) + 1;
        outputArray = new byte[blockCount * byteSize];
        int i = 0;
        int j = byteSize;
        while (i < blockCount) {
            byte[] data = new byte[byteSize];
            if (i == blockCount - 1) {
                j = inputArray.length + byteSize - outputArray.length;
            }
            System.arraycopy(inputArray, i * byteSize, data, 0, j);
            if (encStandard == EncStandard.encDES) {
                data = DES.encDES(data);
            } else if (encStandard == EncStandard.decDES) {
                data = DES.decDES(data);
            }

            System.arraycopy(data, 0, outputArray, i * byteSize, byteSize);
            i = i + 1;
        }
    }

    // algorithems

    public static void encryptData() {
        System.out.println("encrypting data");

        inputArray = readData("data/startText.txt", false);
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

        writeData(outputArray, "data/endText.txt", true);
        writeStats();
    }

    public static void main(String[] args) {
        System.out.println("Staring...");
        encryptData();
        // decryptData();
        System.out.println("Done!");
    }

}