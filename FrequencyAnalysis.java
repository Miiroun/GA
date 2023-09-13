import java.util.Map;
import java.util.TreeMap;

import Interfaces.AttackInterface;

import static java.util.Map.entry;    

public class FrequencyAnalysis implements AttackInterface{

    public Map<Character, Integer> englishFrequency = Map.ofEntries(
        entry('a', 0),
        entry('b', 0),
        entry('c', 0),
        entry('d', 0),
        entry('e', 0),
        entry('f', 0),
        entry('g', 0),
        entry('h', 0),
        entry('i', 0),
        entry('j', 0),
        entry('k', 0),
        entry('l', 0),
        entry('m', 0),
        entry('n', 0),
        entry('o', 0),
        entry('p', 0),
        entry('q', 0),
        entry('r', 0),
        entry('s', 0),
        entry('t', 0),
        entry('u', 0),
        entry('v', 0),
        entry('w', 0),
        entry('x', 0),
        entry('y', 0),
        entry('z', 0),
        entry('å', 0),
        entry('ä', 0),
        entry('ö', 0),
        entry('0', 0),
        entry('1', 0),
        entry('2', 0),
        entry('3', 0),
        entry('4', 0),
        entry('5', 0),
        entry('6', 0),
        entry('7', 0),
        entry('8', 0),
        entry('9', 0)
    );

    public Map<Character, Integer> analysLetters(char[] wordlist) {
        Map<Character, Integer> frequency = new TreeMap<>();
        for (char c : Utility.alphabet) {frequency.put(Character.toLowerCase(c), 0);} // sets each letter to zero
        for (char c : Utility.nummbers) {frequency.put(Character.toLowerCase(c), 0);} // sets each letter to zero
//

        for (char cha: wordlist) {
            char charL = Character.toLowerCase(cha);
            int num = frequency.get(cha);
            frequency.put(charL, num);
        }

        int size = wordlist.length;

        for ( int i = 0; i < 29; i++) {
            frequency.put(Utility.alphabet[i], Math.round(frequency.get(Utility.alphabet[i]) / size * 1000 ));
        }

        return frequency;
    }

    public String attackCC(String data) {
        String message = "";
        char[] charArray = data.toCharArray();

        Map<Character, Integer> frequency = analysLetters(charArray);

        int lowestDisplasment = Integer.MAX_VALUE;
        int bestMatch = -1;
        for(int i = 0; i < 29; i++) 
        {
            int currentDis = 0;
            for(int j = 0; j < 29; j++)
            {
            char locChar = Utility.alphabet[(j + i) % 29];
            currentDis = currentDis + Math.abs(englishFrequency.get(locChar) - frequency.get(locChar));
            }
            if ( currentDis < lowestDisplasment) {
                lowestDisplasment = currentDis;
                bestMatch = i;
            }
        }

        CaesarCipher caesarCipher = new CaesarCipher();
        caesarCipher.setKey(Integer.toString(bestMatch));

        message = caesarCipher.dec(data);

        return message;
    }


    //


    @Override
    public String attackST(String data) {
        throw new UnsupportedOperationException("Unimplemented method 'attackST'");
    }

    @Override
    public String attackXO(String Data) {
        throw new UnsupportedOperationException("Unimplemented method 'attackXO'");
    }
    
}
