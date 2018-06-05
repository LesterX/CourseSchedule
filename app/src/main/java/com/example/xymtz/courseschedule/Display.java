package com.example.xymtz.courseschedule;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import xymtz.CustomDataTypes.Course;
import xymtz.CustomDataTypes.CourseList;

public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ViewGroup layout = findViewById(R.id.display_linear);
        List<LinearLayout> views =
                CourseList.get_instance().create_textviews(getApplicationContext());

        for (LinearLayout l : views){
            TextView t = (TextView)l.getChildAt(0);
            //System.out.println("***Debug***\n" + t.getText().toString());
            layout.addView(l);
        }
    }
}
