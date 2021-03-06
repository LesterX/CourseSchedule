package com.example.xymtz.courseschedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_ac_cancel:{
                finish();
                break;
            }
            case R.id.button_ac_add_session:{
                Intent i = new Intent(this,AddSession.class);
                startActivityForResult(i,1);
                break;
            }
            case R.id.button_ac_enter:{
                EditText text_name = findViewById(R.id.et_add_course_name);
                EditText text_location = findViewById(R.id.et_add_course_location);
                if (text_name.getText().toString().equals("") ||
                        text_location.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            R.string.enter_name_location,
                            Toast.LENGTH_SHORT).show();
                    break;
                }

                String name = text_name.getText().toString();
                String location = text_location.getText().toString();

                String course_data = name + "\n" + location + "\n";

                ViewGroup layout = findViewById(R.id.ac_linear_session);
                int num_session = layout.getChildCount();

                if (num_session == 0){
                    Toast.makeText(getApplicationContext(),
                            R.string.enter_session,
                            Toast.LENGTH_SHORT).show();
                    break;
                }

                List<Session> sessions = new ArrayList<Session>();

                for (int i = 0; i < num_session; i ++){
                    TextView text_session = (TextView) layout.getChildAt(i);
                    String[] texts = text_session.getText().toString().split("\n");
                    String day = texts[0];
                    String[] time = texts[1].split(getString(R.string.to));
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
                        R.string.course_added, Toast.LENGTH_SHORT).show();

                finish();
                break;
            }
        }
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
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View sess = v;
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddCourse.this);
                        builder.setTitle(R.string.delete_session)
                                .setMessage(R.string.want_deleteSession)
                                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        ((ViewGroup) sess.getParent()).removeView(sess);
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                })
                                .show();
                    }
                });

                layout.addView(text);
            }
        }
    }
}
