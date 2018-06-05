package com.example.xymtz.courseschedule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

@TargetApi(23)
public class PickTime extends AppCompatActivity {
    TimePicker tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_time);
        tp = findViewById(R.id.tp_picker);
    }

    public void back(View v){
        finish();
    }

    public void enter(View v){
        String hour = String.valueOf(tp.getHour());
        String minute;
        if (tp.getMinute() >= 10)
            minute = String.valueOf(tp.getMinute());
        else
            minute = "0" + String.valueOf(tp.getMinute());

        Intent return_i = new Intent();
        return_i.putExtra("hour",hour);
        return_i.putExtra("minute",minute);
        setResult(Activity.RESULT_OK,return_i);
        finish();
    }
}
