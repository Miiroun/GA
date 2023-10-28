package Other;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class Statistics {
    
    //global var for entire program
    public static LocalTime timeStart;
    public static String dataTime;
    public static String textData;

    //local var for each encryption attack pair
    public static int mesIndex;
    public static String[] dataEncAttPair; // stores the data collected in all messges

    //local var for each message
    public static int funIndex;

    //local var for each funtion type
    public static int[] useOfMessEva; //counts how many times this used the evalute of message function
    public static int[] useOfDecMeth;

    public Statistics(){
        timeStart = java.time.LocalTime.now();
        dataTime = "'TimeStamp':[";
    }

    public static void startCollecting() {
        timeStart = java.time.LocalTime.now();
    }

    public static void timeStamp() {timeStamp("");}
    
    public static void timeStamp(String message) {
        LocalTime now = java.time.LocalTime.now();
        String mess = "{'Time':" + now.compareTo(timeStart) + ", 'message':" + message + "},";
        dataTime += mess + "\n";
        System.out.println(mess);

    }

    public static void endCollecting(Boolean saveData) {
        String data = compileData();
        if(saveData){
            saveData(data);
        }
        System.out.println(data);
    }

    public static void saveData(String data) {
        try {
            Path path = Paths.get("data/statistics/" + "stats:" + LocalDate.now().toString() + "_" + LocalTime.now().getHour() + "-"+ LocalTime.now().getMinute() + /* "-" + LocalTime.now().getSecond() +  "" +*/  ".json");
            Files.write(path, data.getBytes(Main.charset));
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static String compileData() {
        String data = "{";

        dataTime = dataTime.substring(0, dataTime.length()-1) + "]";
        data += dataTime;

        data += "}";
        return data;
    }

    public static void openAttEncPair() { //starts collection for; ex CC and BF
        mesIndex = 0;
        dataEncAttPair = new String[20];
    }

    public static void openTextWork() {
        //declare varbles for pair
        funIndex = 0;        
        useOfMessEva = new int[3];
        useOfDecMeth = new int[3];

    }

    public static void closeSegmentInPair() { // closes the collection for; ex encryption of ceasar chifer
        
        funIndex ++;
    }

    public static void closeTextWork(Boolean correct) {
        //sumerice text
        String str = "";

        //encryotion
        str += "'enc':{"; 
        str += "}";
        
        //decryption
        str += "'dec':{"; 
        str += "'mesEva':" + useOfMessEva[1];
        str += "}";

        //attack
        str += "'att':{"; 
        str += "'mesEva': " + useOfMessEva[2] + ",\n";
        str += "'decMet': " + useOfDecMeth[2] + ",\n";
        str += "'Succes':" + Boolean.toString(correct); //is this correct method?
        str += "}";

        //summery
        str += "'sum':{"; 
                str += "'mesEva': " + useOfMessEva[0] + useOfMessEva[1] + useOfMessEva[2] + ",\n";
        str += "'decMet': " + useOfDecMeth[0] + useOfDecMeth[1] + useOfDecMeth[2] + ",\n";
        str += "textLength:" + Main.inputString.length();
        str += "}";
        
        dataEncAttPair[mesIndex] = str;

        mesIndex ++;
    }

    public static void closeAttEncPair() { // close collection, preper for new pair: ex CC and BF

        //summerice pair
        textData += "{";
        for (String string : dataEncAttPair) {
            textData += string + ", ";
        }
        textData += "}";

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

}
