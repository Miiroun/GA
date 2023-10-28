package attacks;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import Chiffers.X_OR;
import Other.Utility;
import Other.Interfaces.AttackInterface;

import static java.util.Map.entry;    

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
            if ( currentDis < lowestDisplasment) lowestDisplasment = currentDis;bestMatch = i;
        }

        return bestMatch;
    }

    public String attackCC(String data) {
        return Integer.toString(analysCharArray(data.toCharArray()));
    }


    //


    @Override
    public String attackST(String data) {

        
        char[] charArray = data.toCharArray();
        Map<Character, Integer> frequency = analysLetters(charArray);
        Set<Character> keys[] = new TreeSet[29];

        float percent = 0.15f;
        for (char letter  : Utility.alphabet) {
            int value = frequency.get(letter);

            for (char answerLetter  : Utility.alphabet) {
                int answerValue = sweFrequency.get(answerLetter);
                if(value * (1- percent) < answerValue && value * (1- percent) > answerValue);
                {
                    keys[Utility.indexOf(letter)].add(answerLetter);
                }
            }

        }

        //should go throw all key combinations ive generated
        

        String messString = new String(charArray);
        return messString;

        
    }

    @Override
    public String attackXO(String data) {
        char[] charArray = data.toCharArray();
        char[][] subArray = new char[4][];

        BruitForce bf = new BruitForce();
        X_OR xo = new X_OR();

        //int[] dividers = Utility.dividers(charArray.length);
        String str;

        aa:{//chould make it generic for length > 4
            int lgh = 4;//length

            for (int i = 0; i < charArray.length; i++) 
            {subArray[i % lgh][Math.floorDiv(i, lgh) +(i % lgh)] = charArray[i];} //create strings than inclued every 4th character

            int[] values = new int[lgh];
            for (int i = 0; i < subArray.length; i++) {
                values[i] = analysCharArray(subArray[i]);
            }
            str = "";
            for (int i = 0; i < lgh; i++) str += Utility.alphabet[values[i]];
            xo.setKey(str);
            if(bf.evaluteText(xo.dec(data)) > 7) {break aa;} //this check would be helpful if wanted to do for more than length 4
        }


        return str; // not completly implemented
    }
    
    @Override 
    public String attackCT(String data) {
        throw new UnsupportedOperationException("CT is not susepteble to FA");

    }
}
