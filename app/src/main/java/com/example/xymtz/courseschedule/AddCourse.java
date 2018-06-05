package com.example.xymtz.courseschedule;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xymtz.CustomDataTypes.Course;
import xymtz.CustomDataTypes.CourseList;
import xymtz.CustomDataTypes.Session;

public class AddCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
    }

    public void add_session(View v){
        Intent i = new Intent(this,AddSession.class);
        startActivityForResult(i,1);
    }

    public void back(View v){
        finish();
    }

    public void enter_course(View v){
        EditText text_name = findViewById(R.id.et_add_course_name);
        EditText text_location = findViewById(R.id.et_add_course_location);
        if (text_name.getText().toString().equals("") ||
                text_location.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),
                    "Please enter name and location",
                    Toast.LENGTH_SHORT).show();
        }

        String name = text_name.getText().toString();
        String location = text_location.getText().toString();

        String course_data = name + "\n" + location + "\n";

        ViewGroup layout = findViewById(R.id.ac_linear_session);
        int num_session = layout.getChildCount();

        if (num_session == 0){
            Toast.makeText(getApplicationContext(),
                    "Please enter at least one session",
                    Toast.LENGTH_SHORT).show();
        }

        List<Session> sessions = new ArrayList<Session>();

        for (int i = 0; i < num_session; i ++){
            TextView text_session = (TextView) layout.getChildAt(i);
            String[] texts = text_session.getText().toString().split("\n");
            String day = texts[0];
            String[] time = texts[1].split(" to ");
            String sh = time[0];
            String eh = time[1];
            Session s = new Session(day,sh,eh);
            sessions.add(s);
            course_data = course_data + day + " " + sh + " " + eh + "\n";
        }

        CourseList.get_instance().add_course(new Course(name,location,sessions));

        course_data = course_data + "---\n";

        try{
            FileOutputStream stream = openFileOutput("Courses.txt",MODE_APPEND);

            try {
                stream.write(course_data.getBytes());
            } finally {
                stream.close();
            }
        }catch (IOException e){
            System.out.println("***Error Message*** " + e.getMessage());
        }

        Toast.makeText(getApplicationContext(),
                "Course Added", Toast.LENGTH_SHORT).show();

        finish();
    }

    protected void onActivityResult(int request_code, int result_code, Intent data) {
        if (request_code == 1) {
            if (result_code == Activity.RESULT_OK) {
                String day = data.getStringExtra("day");
                String sh = data.getStringExtra("sh");
                String eh = data.getStringExtra("eh");
                String string_session = day + "\n" + sh + " to " + eh + "\n";


                ViewGroup layout = findViewById(R.id.ac_linear_session);
                TextView text = new TextView(getApplicationContext());
                text.setText(string_session);
                text.setTextColor(Color.BLACK);
                text.setTextSize(18);
                layout.addView(text);
            }
        }
    }
}
