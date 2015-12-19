package com.example.arunkumar.timetable;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class customlist extends ArrayAdapter<String> {


    private final Context context;
    private final String[] descs;
    private final String[] times;
    private final int[] hiddenid;


    public customlist(Context context, String descs[], String times[],int hiddenid[]) {

        super(context, R.layout.activity_customlist,times );

        this.hiddenid=hiddenid;
        this.context = context;
        this.descs = descs;
        this.times = times;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View custom_list = inflater.inflate(R.layout.activity_customlist, parent, false);

        TextView dt = (TextView) custom_list.findViewById(R.id.displayTime);
        TextView dd = (TextView) custom_list.findViewById(R.id.displayDesc);
        TextView hidid=(TextView) custom_list.findViewById(R.id.hid);

        dt.setText(times[position]);
        dd.setText(descs[position]);
        hidid.setText(Integer.toString(hiddenid[position]));

        return custom_list;

    }


}


