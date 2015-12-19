package com.example.arunkumar.timetable;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    static int count=0;
    SwipeMenuListView lay;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String day = Day.day;


        Button add = (Button) findViewById(R.id.add);
        lay = (SwipeMenuListView) findViewById(R.id.list);
        db = new database(this, null, null, 1);
        refresh();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, newEvent.class);
                i.putExtra("mode", "create");
                startActivity(i);

            }
        });

        if (count != 0) {
            table_prop tableprop = new table_prop(getIntent().getExtras().getString("notifyTime"), getIntent().getExtras().getString("desc"), Day.day);

            db.addrow(tableprop);
            refresh();

        }

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Update");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_cancel_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lay.setMenuCreator(creator);

        lay.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                final int deleteid = db.returnid[position];
                final String updatetime = db.returntime[position];
                final String updatedesc = db.returndesc[position];

                switch (index) {
                    case 0:
                        // open
                        db.deleterow(deleteid);
                        Intent up = new Intent(MainActivity.this, newEvent.class);
                        up.putExtra("mode", "update");
                        up.putExtra("uptime", updatetime);
                        up.putExtra("updesc", updatedesc);
                        startActivity(up);
                        break;
                    case 1:
                        // delete
//					delete(item);
                        db.deleterow(deleteid);
                        refresh();
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();

                        break;
                }
                return false;
            }
        });
    }







    private void setAlarm(int hourOfDay,int minute) {


        Calendar calNow = Calendar.getInstance();
        Calendar targetCal = (Calendar) calNow.clone();

        targetCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        targetCal.set(Calendar.MINUTE, minute);
        targetCal.set(Calendar.SECOND, 0);
        targetCal.set(Calendar.MILLISECOND, 0);

        if (targetCal.compareTo(calNow) <= 0) {
            // Today Set time passed, count to tomorrow
            targetCal.add(Calendar.DATE, 1);
        }




        Toast.makeText(this, "Alarm is set "
                + targetCal.getTime() + "\n", Toast.LENGTH_LONG).show();




        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(),1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

    }


    public void refresh(){

        db.setarray();

        ListAdapter EventList= new customlist(this,db.returndesc,db.returntime,db.returnid);

        lay.setAdapter(EventList);


    }

    @Override
    protected void onPause() {
        super.onPause();
        count=0;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}


