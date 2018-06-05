package com.example.xymtz.courseschedule;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);


        String[] days = new String[]{
                "Monday","Tuesday","Wednesday","Thursday",
                "Friday","Saturday","Sunday"
        };

        Spinner day = findViewById(R.id.as_spinner_day);
        ArrayAdapter<String> adapter_days = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, days);
        adapter_days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter_days);
    }

    public void back(View v){
        finish();
    }

    public void enter(View v){

        Intent i = new Intent();
        Spinner spinner_day = findViewById(R.id.as_spinner_day);
        String day = spinner_day.getSelectedItem().toString();
        TextView text_sh = findViewById(R.id.as_text_sh_value);
        String sh = text_sh.getText().toString();
        TextView text_eh = findViewById(R.id.as_text_eh_value);
        String eh = text_eh.getText().toString();

        if (sh == null || eh == null || sh.length() < 1 || eh.length() < 1) {
            Toast.makeText(getApplicationContext(),
                    "Time cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }else {

            i.putExtra("day",day);
            i.putExtra("sh",sh);
            i.putExtra("eh",eh);
            setResult(Activity.RESULT_OK,i);
            finish();
        }

    }

    public void pick_sh(View v){
        Intent i = new Intent(this,PickTime.class);
        startActivityForResult(i,1);
    }

    public void pick_eh(View v){
        Intent i = new Intent(this,PickTime.class);
        startActivityForResult(i,2);
    }

    protected void onActivityResult(int request_code, int result_code, Intent data){
        if (request_code == 1) {
            if (result_code == Activity.RESULT_OK){
                String hour = data.getStringExtra("hour");
                String minute = data.getStringExtra("minute");
                String time = hour + ":" + minute;
                TextView sh_value = findViewById(R.id.as_text_sh_value);
                sh_value.setText(time);
                sh_value.setVisibility(View.VISIBLE);
                Button change = findViewById(R.id.as_button_change_sh);
                change.setText("CHANGE");
            }
        }else if (request_code == 2) {
            if (result_code == Activity.RESULT_OK){
                String hour = data.getStringExtra("hour");
                String minute = data.getStringExtra("minute");
                String time = hour + ":" + minute;
                TextView eh_value = findViewById(R.id.as_text_eh_value);
                eh_value.setText(time);
                eh_value.setVisibility(View.VISIBLE);
                Button change = findViewById(R.id.as_button_change_eh);
                change.setText("CHANGE");
            }
        }
    }
}
