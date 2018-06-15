package com.example.xymtz.courseschedule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import xymtz.CustomDataTypes.CourseList;

public class DeleteCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        ViewGroup layout = findViewById(R.id.delete_linear);

        List<LinearLayout> views =
                CourseList.get_instance().create_textviews(getApplicationContext());

        for (LinearLayout l : views){
            TextView t = (TextView)l.getChildAt(0);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View course = v;
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCourse.this);
                    builder.setTitle(R.string.delete_course)
                            .setMessage(R.string.wantDeleteCourse)
                            .setPositiveButton(getString(R.string.positive), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    String course_info = ((TextView) course).getText().toString();
                                    String[] course_name0 = course_info.split("\n")[0].split(" ");
                                    String course_name = "";
                                    for (int i = 2; i < course_name0.length; i ++){
                                        course_name = course_name + " " + course_name0[i];
                                    }

                                    File file = new File(getApplicationContext().getFilesDir(),"Courses.txt");
                                    String updated = update_file(file,course_name);

                                    try{
                                        FileOutputStream stream = new FileOutputStream(file,false);

                                        try {
                                            stream.write(updated.getBytes());
                                        } finally {
                                            stream.close();
                                        }
                                    }catch (IOException e){
                                        System.out.println("***Error Message*** " + e.getMessage());
                                    }

                                    CourseList.get_instance().remove_course(course_name);

                                    ((ViewGroup) course.getParent()).removeView(course);

                                    Toast.makeText(getApplicationContext(),
                                            R.string.course_deleted,
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            })
                            .show();



                }
            });
            layout.addView(l);
        }
    }

    private String update_file(File file, String course) {
        if (file.exists()) {
            int length = (int) file.length();

            byte[] bytes = new byte[length];

            try {
                FileInputStream in = new FileInputStream(file);
                try {
                    in.read(bytes);
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("***Error Message*** " + e.getMessage());
            }

            String contents = new String(bytes);

            String[] saved = contents.split("\n");
            String updated = "";

            for(int i = 0; i < saved.length; i ++){
                String a = saved[i];
                String b = course;
                if (a.replaceAll("\\s+","").equalsIgnoreCase(b.replaceAll("\\s+",""))){
                    while (!saved[i].equals("---")){i++;}
                }else{
                    int k = 0;
                    for (int j = i; !saved[j].equals("---") && j < saved.length; j ++){
                        updated = updated + saved[j] + "\n";
                        k = j;
                    }
                    updated = updated + "---" + "\n";
                    i = k + 1;
                }
            }
            return updated;
        }else{
            return "";
        }
    }
}
