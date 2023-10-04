import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class Statistics {
    
    static LocalTime timeStart;

    static String data;

    public static void startCollecting() {
        timeStart = java.time.LocalTime.now();
    }

    public static void timeStamp() {timeStamp("");}
    
    public static void timeStamp(String message) {
        LocalTime now = java.time.LocalTime.now();
        String mess = "TimeStamp:" + now.compareTo(timeStart) + "." + message;
        data += mess + "\n";
        System.out.println(mess);

    }

    public static void endCollecting(Boolean saveData) {
        data = "";
        if(saveData){saveData();}
    }

    public static void saveData() {
        try {
            Path path = Paths.get("data/statistics/" + LocalDate.now().toString() + "_" + LocalTime.now().getHour() + "-"+ LocalTime.now().getMinute() + /* "-" + LocalTime.now().getSecond() +  "" +*/  ".json");
            Files.write(path, data.getBytes(Main.charset));
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void compileData() {

    }

    public static void recordeStats() {
    }

    public static void addDataCount(String string, int i) {
        //increase a datafield with name "string" i times, or create it if not exist
    }

}
