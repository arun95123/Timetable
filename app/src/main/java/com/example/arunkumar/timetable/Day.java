package com.example.arunkumar.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Context;

/**
 * Created by Arunkumar on 11/22/2015.
 */
public class Day extends AppCompatActivity {

    static String day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days);

        ListView daylay = (ListView) findViewById(R.id.dayslist);

        final String[] dayarray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        ListAdapter dayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dayarray);

        daylay.setAdapter(dayadapter);

        daylay.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        day=dayarray[position];
                        Intent event=new Intent(Day.this,MainActivity.class);
                        startActivity(event);


                    }


                });


    }
}
