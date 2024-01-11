package attacks;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import Chiffers.CaesarCipher;
import Chiffers.ColumnarTransposition;
import Chiffers.Substitution;
import Chiffers.X_OR;
import Other.Utility;
import Other.Interfaces.AttackInterface;

import static java.util.Map.entry;

import java.util.ArrayList;

public class FrequencyAnalysis implements AttackInterface{

    public Map<Character, Integer> sweFrequency = Map.ofEntries(
        entry('a', 720),
        entry('b', 120),
        entry('c', 180),
        entry('d', 360),
        entry('e', 1120),
        entry('f', 180),
        entry('g', 170),
        entry('h', 610),
        entry('i', 620),
        entry('j', 10),
        entry('k', 90),
        entry('l', 440),
        entry('m', 290),
        entry('n', 590),
        entry('o', 800),
        entry('p', 140),
        entry('q', 10),
        entry('r', 560),
        entry('s', 600),
        entry('t', 870),
        entry('u', 300),
        entry('v', 80),
        entry('w', 210),
        entry('x', 10),
        entry('y', 220),
        entry('z', 10),
        entry('å', 10),
        entry('ä', 10),
        entry('ö', 10)
    );

    public Map<Character, Integer> analysLetters(char[] wordlist) {
        Map<Character, Integer> frequency = new TreeMap<>();
        //for (char c : Utility.alphabet) {frequency.put(Character.toLowerCase(c), 0);} // sets each letter to zero
        //for (char c : Utility.nummbers) {frequency.put(Character.toLowerCase(c), 0);} // sets each nummber to zero
        //for (char c : Utility.signs) {frequency.put(Character.toLowerCase(c), 0);} // sets each sign to zero

        //

        for (char cha: wordlist) {
            char chaL = Character.toLowerCase(cha);
            Integer num = frequency.get(chaL); if(num == null) {num = 0;}

            frequency.put(chaL, num + 1);
        }

        //refaktor code to be in promille
        int size = 0; //wordlist.length ;//- 6079 - 28050 -742 -3193 - 442 - 2098;//- frequency.get(' ') - frequency.get('\n');
        for (char c : Utility.alphabet) {Integer frec = frequency.get(Character.toLowerCase(c)); if (frec != null){size += frec;}} //counts total amout of letters

        for ( int i = 0; i < Utility.alphabet.length; i++) {
            Character cha = Utility.alphabet[i];
            Integer in = frequency.get(cha); if (in == null){in = 0;}
            int x = Math.round(((in * 10000 / size)));
            //x = in;
            frequency.put(cha, x);
        }

        return frequency;
    }

    public int analysCharArray(char[] charArray) {
        Map<Character, Integer> frequency = analysLetters(charArray);

        int lowestDisplasment = Integer.MAX_VALUE;
        int bestMatch = -1;
        for(int i = 0; i < 29; i++) //i = diffrent keys
        {
            int currentDis = 0;
            for(int j = 0; j < 29; j++)
            {
            char absChar = Utility.alphabet[(j) % 29];
            char locChar = Utility.alphabet[(j + i) % 29];
            int dis = Math.abs(sweFrequency.get(absChar) - frequency.get(locChar));
            currentDis += dis;
            //System.out.println(dis);

            }
            if ( currentDis < lowestDisplasment) {lowestDisplasment = currentDis;bestMatch = i;}
        }

        return bestMatch;
    }

    public String[] genArrayListFromCharComb(ArrayList<Character>[] charComb, int i, String keys[]) {
        String[] tempList = keys;

    

        
            
            String[] prior = new String[1];
            if(i != 0){
                prior = genArrayListFromCharComb(charComb, i - 1, keys); //not entierly correct, needs ti fix somthing
            }
            if(i == 29) {
                return prior;
            }
            tempList = new String[prior.length * charComb[i].size()];
            int j = 0;
            for (char c: charComb[i]) {
                for(int k = 0; k < prior.length; k++) {
                    tempList[j] = prior[j % prior.length] + c;
                    j++;
                }
            }

            //how should this work??
        

        return tempList;
    }

    public String attackCC(String data) {
        CaesarCipher cc = new CaesarCipher();
        cc.setKey(Integer.toString(analysCharArray(data.toCharArray())));
        return cc.dec(data);
    }


    @Override
    public String attackST(String data) { 
        char[] charArray = data.toCharArray();
        Map<Character, Integer> frequency = analysLetters(charArray);
        ArrayList<Character>[] charComb = new ArrayList[29];

        float percent = 20f;
        for (int i = 0; i < 29; i++) {
            char letter = Utility.alphabet[i];
            int value = frequency.get(letter); //problem här att blir 0, kanske fel från datan
            charComb[i] = new ArrayList<Character>();
            
            for (char answerLetter  : Utility.alphabet) {
                int answerValue = sweFrequency.get(answerLetter);
                if (value * (1f - percent) <= answerValue )   
                    if(answerValue <= value * (1f + percent)){
                    {
                        charComb[i].add(answerLetter);
                    }
                }
            }

        }

        String[] keys = genArrayListFromCharComb(charComb, 29, new String[1]); //problem med skapandet av nycklar
        
        
        Substitution st = new Substitution();
        String message[] = new String[keys.length];
        int j = 0;
        for (String key : keys) {
            st.setKey(key);
            message[j] = st.dec(data);
            j++;
        }

        return BruitForce.evaluteMessageArray(message);

        
    }

    @Override
    public String attackXO(String data) {
        char[] charArray = data.toCharArray();
        char[][] subArray = new char[0][];

        X_OR xo = new X_OR();

        char[] keyStr;

        aa:{//chould make it generic for length > 4
            int keyLen = 4;//length
            subArray = new char[keyLen][];
            for (int i = 0; i < subArray.length; i++) {
                int rounded = Math.floorDiv(charArray.length, keyLen)+ 1;
                subArray[i] = new char[rounded];
            }

            for (int i = 0; i < subArray[0].length * keyLen; i++) 
            {
                char c = charArray[Math.min(i, charArray.length-1)];
                subArray[i % keyLen][Math.floorDiv(i, keyLen)] = c;
            } //create strings than inclued every keyLenth character

            int[] values = new int[keyLen];
            for (int i = 0; i < subArray.length; i++) {
                values[i] = analysCharArray(subArray[i]);
            }

            keyStr = new char[keyLen];
            for (int i = 0; i < keyLen; i++){keyStr[i] = Utility.alphabet[values[i]];}
            xo.setKey(new String(keyStr));
            if(BruitForce.evaluteText(xo.dec(data)) > 7) {break aa;} //this check would be helpful if wanted to do for more than length 4
        }


        return xo.dec(data); 
    }


    @Override 
    public String attackCT(String data) {
        //do an (not anagram) attack here
        int[] div = Utility.dividers(data.length());
        ColumnarTransposition ct = new ColumnarTransposition();

        int[] values = new int[div.length];
        for (int i = 0; i < div.length; i++) {
            int[] key = new int[div[i]];
            for (int j = 0; j < key.length; j++) key[j] = j;
            ct.setKey(key);

            char[][] matrix = ct.createMatrixDec(data);
            
            values[i] = 0;
            for (int j = 0; j < matrix.length; j++) {
                String subMessage = new String(matrix[j]);
                values[i] += BruitForce.evaluteText(subMessage);
            } //maybe not evalute inside forloop but as a whole


        }
        int k = -1;
        int bestMatch = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            if(values[i] > bestMatch) {
                k = i;
                bestMatch = values[i];
            }
        }

        //loop throw to check which key permutation is best
        int keyLen = div[k];

        //cheats and tells system current length, since above algorithm not acuret enouth and throws errors
        keyLen = 2;
        int[] key = new int[keyLen];
        for (int j = 0; j < keyLen; j++) { key[j] = j;} //om blir för bred kolumn blir det myckeyt eftersom n! långl, problemet här är att överstrider integer bit limit

        if(Utility.factorial(keyLen) < 1000) {
            int[][] permutations = Utility.permutations(key); //alldeles för långsamt att testa all möjliga permutationer, 

            String[] message = new String[permutations.length];
            for (int i = 0; i < message.length; i++) {
                ct.setKey(permutations[i]);
                char[][] matrix = ct.createMatrixDec(data);
                message[i] = ct.decWithMatrix(matrix);
            }

            return BruitForce.evaluteMessageArray(message);
        } else {
            int[] lastKey = key.clone();
            double lastValue = Integer.MIN_VALUE;
            
            Random r = new Random();

            for (int i = 0; i < 100000; i++) {
                int[] nowKey = lastKey.clone();
                int rand1 = r.nextInt(keyLen-1);
                int rand2 = r.nextInt(keyLen-1);
                nowKey[rand1] = lastKey[rand2];
                nowKey[rand2] = lastKey[rand1];
                ct.setKey(nowKey);
                double nowValue = BruitForce.evaluteText(ct.dec(data));
                if(nowValue > lastValue) {
                    lastValue = nowValue;
                    nowKey = lastKey;
                }
            }
            
            ct.setKey(lastKey);
            return ct.dec(data);
        } 
    }
}
