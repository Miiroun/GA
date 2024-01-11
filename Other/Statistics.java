package Other;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class Statistics {
    
    //global var for entire program
    public static long timeStart;
    public static String dataTime;
    public static String dataText;

    //local var for each encryption attack pair
    public static String[] dataEncAttPair; // stores the data collected in all messges

    //local var for each message
    public static int funIndex;

    //local var for each funtion type
    public static int[] useOfMessEva; //counts how many times this used the evalute of message function
    public static int[] useOfDecMeth;
    public static long[] lastTime;

    public static void startCollecting() {
        timeStart = System.nanoTime();
        dataTime = "\"TimeStamp\":[\n";
        dataText = "\"AttackData\":[";

    }

    public static void timeStamp() {timeStamp("");}
    
    public static void timeStamp(String message) {
        long now = System.nanoTime();
        
        String mess = "\t{\"Time\":" + (now - timeStart) + ", \"message\":\"" + message + "\"},";
        dataTime += mess + "\n";
        //System.out.println(mess);

    }


    public static void openAttEncPair() { //starts collection for; ex CC and BF
        dataEncAttPair = new String[20];
    }

    public static void openTextWork() {
        //declare varbles for pair
        funIndex = 0;        
        useOfMessEva = new int[3];
        useOfDecMeth = new int[3];

        lastTime = new long[4];
        lastTime[3] = System.nanoTime();

    }

    public static void closeSegmentInPair() { // closes the collection for; ex encryption of ceasar chifer
        lastTime[funIndex] = System.nanoTime();
        funIndex ++;

    }

    public static void closeTextWork(Boolean correct, int j) {
        //sumerice text
        String str = "";
        str += "{";

        
        str += "\"Name\":";
        str += "\"Text"+ j + "\",";

        //encryotion
        str += "\"enc\":{"; 
        str += "\"Type\":\"" + (Main.encStandard)+ "\",";
        str += "\"Time\":" + (lastTime[0] - lastTime[3])+ "";
        str += "},";
        
        //decryption
        str += "\"dec\":{"; 
        str += "\"Time\":" + (lastTime[1] - lastTime[0])+ "";
        str += "},";

        //attack
        str += "\"att\":{"; 
        str += "\"Type\":\"" + (Main.attStandard)+ "\",";
        str += "\"mesEva\": " + useOfMessEva[2] + ",";
        str += "\"decMet\": " + useOfDecMeth[2] + ",";
        str += "\"Succes\":" + Boolean.toString(correct) + ","; //kanske spara tiden här också??
        str += "\"Time\":" + (lastTime[2] - lastTime[1])+ "";
        str += "},";

        //summery
        str += "\"sum\":{"; 
        str += "\"mesEva\": " + (useOfMessEva[0] + useOfMessEva[1] + useOfMessEva[2]) + ",";
        str += "\"decMet\": " + (useOfDecMeth[0] + useOfDecMeth[1] + useOfDecMeth[2]) + ",";
        str += "\"textLength\":" + Main.inputString.length(); 
        str += "}";
        
        str += "}";
        dataEncAttPair[j] = str;
    }

    public static void closeAttEncPair(int i) { // close collection, preper for new pair: ex CC and BF

        //summerice pair
        String str = "";

        str += "\n\t" + "[";
        //str += "\"Typ" + i + "\"," 
        str += "\n";
        for (String string : dataEncAttPair) {
            str += "\t\t" + string + ",\n";
        }
        str = str.substring(0, str.length()-2);
        str += "],";

        dataText += str;
        //record key used

    }


    public static void recordStat(String mess) {
        if( mess == "UseMesEva") {
            useOfMessEva[funIndex] += 1;
        } else if( mess == "CallDecMethod") {
            useOfDecMeth[funIndex] += 1;
        } else if( mess == "") {

        } else {
            System.out.println("recorded stat not found:" + mess);
        } 
    }

    public static void addDataCount(String string, int i) {
        //increase a datafield with name "string" i times, or create it if not exist
    }

    public static String compileData() {
        String data = "{";


        data += dataTime.substring(0, dataTime.length()-2) + "]" + ",\n";

        data += dataText.substring(0, dataText.length()-1) + "]" +  "\n";

        data += "}";
        return data;
    }

    
    public static void endCollecting(Boolean saveData) {
        String data = compileData();
        if(saveData){
            saveData(data);
        } else {
            System.out.println(data);
        }
    }

    public static void saveData(String data) {
        try {
            Path path = Paths.get("data/statistics/" + "stats" + LocalDate.now().toString() + "_" + LocalTime.now().getHour() + "-"+ LocalTime.now().getMinute() + /* "-" + LocalTime.now().getSecond() +  "" +*/  ".json");
            Files.write(path, data.getBytes(Main.charset));
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    

}
