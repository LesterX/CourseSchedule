package xymtz.CustomDataTypes;

public class Session {
    private String day;
    private String start_hour;
    private String end_hour;

    public Session(String d, String sh, String eh){
        day = d;
        start_hour = sh;
        end_hour = eh;
    }

    public String get_day(){
        return day;
    }

    public String get_start_hour(){
        return start_hour;
    }

    public String get_end_hour(){
        return end_hour;
    }

    public String toString(){
        return day + " " + start_hour + " : " + end_hour;
    }
}
