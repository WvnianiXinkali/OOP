package Quiz.src.main.java.HelperMethods;

import java.sql.Time;
import java.util.ArrayList;

public class TimeInStrings {
    private String time;
    public TimeInStrings(String time){
        this.time = time;
    }

    public int getSeconds(){
        return Integer.parseInt(time.split(" ")[2]);
    }
    public int getMinutes(){
        return Integer.parseInt(time.split(" ")[1]);
    }
    public int getHours(){
        return Integer.parseInt(time.split(" ")[0]);
    }
    public static String timeToStrings(int seconds){
        String time = "";
        int hours = seconds / 3600;
        int minutes= (seconds - hours * 3600) / 60;
        int secondss = (seconds - hours * 3600 - minutes * 60);

        time += hours == 0 ? "":""+hours + " Hours ";
        time += minutes == 0 && seconds == 0 ? "":""+ minutes +" Minutes ";
        time += secondss == 0 && minutes == 0? "":""+ secondss +" Seconds";

        return time;
    }

}
