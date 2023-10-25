package Other;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import Chiffers.CaesarCipher;
import Chiffers.ColumnarTransposition;
import Chiffers.Substitution;
import Chiffers.X_OR;
import Other.Interfaces.AttackInterface;
import Other.Interfaces.ByteEncrytionInterface;
import Other.Interfaces.StringEncrytionInterface;
import attacks.BruitForce;
import attacks.FrequencyAnalysis;
import attacks.AdvancedEncryption.DES;
import attacks.AdvancedEncryption.DiffieHellman;
import attacks.AdvancedEncryption.TriDES;

public class Main {

    public enum EncStandard {
        DES,
        TriDES,
        DH,
        CC,
        XOR,
        Sub,
        CT
    }

    public enum AttStandard {
        FA,
        BF,
        not
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

        if (encStandard == EncStandard.DES || encStandard == EncStandard.TriDES || encStandard == EncStandard.DH) {
            doBytes = true;
            blockSize = 64;
            byteSize = blockSize / 8;
            charset = Charset.forName("UTF-8");
        } else if (encStandard == EncStandard.CC || encStandard == EncStandard.Sub ||  encStandard == EncStandard.XOR || encStandard == EncStandard.CT) {
            doBytes = false;
            charset = Charset.forName("UTF-8");

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
            System.out.println("An error '" + e + "' has occurred.");
        }
        return inputArray;
    }

    public static String readData(String path){
        String data = "";
        try{
            data = new String(Files.readAllBytes(Paths.get(path)), charset);
        } catch (Exception e) {
            System.out.println("An error '" + e + "' has occurred.");
        }
        return data;

    }

    public static void writeData(String data, String path) {
        try{
            Files.writeString(Paths.get(path), data);
        } catch (Exception e) {
            System.out.println("An error '" + e + "' has occurred.");
        }
    }

    public static void writeData(byte[] byteArray, String path, boolean isText) {

        try {
            Files.write(Paths.get(path), byteArray);

            if (isText) {
                String output = new String(byteArray, charset);
                System.out.println("Output:" + output);
            }
        } catch (Exception e) {
            System.out.println("An error '" + e + "' has occurred.");
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
        } else if (encStandard == EncStandard.DH) {
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
        }else if (encStandard == EncStandard.CT) {
            encClass = new ColumnarTransposition();
        } else {
            // error, should never be here
            encClass = null;
        }

        encClass.setKey(keyString);

        if (encrypt) {
            outputString = encClass.enc(inputString);
        } else {
            outputString = encClass.dec(inputString);
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
        } else if (encStandard == EncStandard.CT) {
            outputString = attClass.attackCT(inputString);
        } else {
            // error, should never be here
            throw new UnsupportedOperationException("something wrong with attact standard selection");
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

            inputString = readData("data/encryptedText.txt");

            doAttacking();

            System.out.println("guess of oridginal text:" + outputString);

            
            if (outputString.equals(readData("data/startText.txt") )){System.out.println("The attack got the correct answer");
            } else {System.out.println("The attack got the wrong answer");}
            
            writeData(outputString, "data/attackedText.txt");
        }
        Statistics.recordeStats();

    }

    

   

    public static void testKrypto() {
        Statistics.startCollecting();
        varibleSetUp(EncStandard.CC, AttStandard.BF);

        System.out.println("Staring...");

        encryptData();

        decryptData();

        if(attStandard != AttStandard.not) attackData();

        System.out.println("Done!");

        Statistics.endCollecting(false);
    }
    
    public static void anaCharFrec () {
        FrequencyAnalysis fa = new FrequencyAnalysis();
        String str = readData("data/texts/hamlet.txt");
        System.out.println(fa.analysLetters(str.toCharArray()));
    }


    public static void testEvaluate() {
        BruitForce bf = new BruitForce();
        String str = readData("data/texts/hamlet.txt");
        //str = "kzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxbkzuojahsnqxfqkevsfftsbpuioyehpxb";
        //str = "TobeornottobethatisthequestionWhethertisNoblerinthemindtosufferTheSlingsandArrowsofoutrageousFortuneOrtotakeArmsagainstaSeaoftroublesAndbyopposingendthemWilliamShakespeareHamlet";
        System.out.println(bf.evaluteText(str));
    }

    public static void collectData() {
        Statistics.startCollecting();
        varibleSetUp(EncStandard.Sub, AttStandard.BF);

        String[] texts = new String[20]; //5 shackspear 5 Stringberg 10 av olika kort längder 10--> 200 karaktärer
        texts[0] = readData("data/texts/hamlet.txt");

        for (int i = 0; i < 4 * 2; i++) {// looop throw encryption and attack pattarns
            if(i % 2 == 0) {
                attStandard = AttStandard.BF;
            } else {
                attStandard = AttStandard.FA;
            }
            if(i == 0 || i == 1) {
                encStandard = EncStandard.CC;
                keyString = "3";
            } else if(i == 2 || i == 3) {
                encStandard = EncStandard.Sub;
                keyString = "timeodansfrbcghjklpåäöquvwxyz";
            } else if(i == 4 || i == 5) {
                encStandard = EncStandard.XOR;
                keyString = "data";
            } else if(i == 6 || i == 7) {
                encStandard = EncStandard.CT;
                keyString = "swindon";
            } 

            for (int j = 0; j < texts.length; j++) { //loop throu all the texts
                inputString = texts[i];
                doCryption(false);

                inputString = outputString;
                attackData();
                //Statistics.dumpResults();
            }
        }
        Statistics.endCollecting(false);
    }



    public static void main(String[] args) {
        testKrypto();
        //anaCharFrec();
        //testEvaluate();
        //collectData();
    }

}