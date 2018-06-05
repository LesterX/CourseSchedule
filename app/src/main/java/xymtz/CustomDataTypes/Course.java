package xymtz.CustomDataTypes;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String location;
    private List<Session> sessions;

    public Course(String n, String l, List<Session> s){
        name = n;
        location = l;
        sessions = s;
    }

    public void add_session(Session s){
        sessions.add(s);
    }

    public void delete_session(Session s){
        sessions.remove(s);
    }

    public String get_name(){
        return name;
    }

    public String get_location(){
        return location;
    }

    public List<Session> get_sessions(){
        return sessions;
    }

    public String toString(){
        String s = "Course Name: " + name + "\n"
                + "Location: " + location + "\n";

        for (Session session : sessions){
            s = s + session.toString() + "\n";
        }

        return s;
    }
}
