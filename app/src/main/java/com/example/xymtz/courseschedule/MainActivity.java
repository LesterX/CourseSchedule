package com.example.xymtz.courseschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xymtz.CustomDataTypes.Course;
import xymtz.CustomDataTypes.CourseList;
import xymtz.CustomDataTypes.Session;

public class MainActivity extends AppCompatActivity {
    File course_file;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        course_file = new File(getApplicationContext().getFilesDir(),"Courses.txt");
        System.out.println("****" + course_file.exists());

        if (course_file.exists()){
            int length = (int) course_file.length();

            byte[] bytes = new byte[length];

            try{
                FileInputStream in = new FileInputStream(course_file);
                try{
                    in.read(bytes);
                }finally {
                    in.close();
                }
            } catch (IOException e){
                System.out.println("***Error Message*** " + e.getMessage());
            }

            String contents = new String(bytes);

            //System.out.println(contents);

            String[] saved = contents.split("\n");

            for(int i = 0; i < saved.length; i ++){
                String name = saved[i++];
                String location = saved[i++];
                List<Session> sessions = new ArrayList<Session>();
                while (!saved[i].equals("---")){
                    String[] s = saved[i].split(" ");
                    if (s.length != 3){System.out.println("***Invalid session input: "
                    + saved[i]);}
                    else {
                        sessions.add(new Session(s[0],s[1],s[2]));
                    }
                    i++;
                }
                CourseList.get_instance().add_course(new Course(name,location,sessions));
            }

            CourseList.get_instance().print_all();
        }else{
            try{
                course_file.createNewFile();
            }catch (IOException e){
                System.out.println("***Error Message*** " + e.getMessage());
            }
        }
    }

    public void on_click(View v){
        switch (v.getId()){
            case (R.id.button_main_add):{
                Intent i = new Intent(this,AddCourse.class);
                startActivity(i);
                break;
            }
            case (R.id.button_main_delete): {
                break;
            }
            case (R.id.button_main_display):{
                Intent i = new Intent(this,Display.class);
                startActivity(i);
                break;
            }
        }
    }
}
