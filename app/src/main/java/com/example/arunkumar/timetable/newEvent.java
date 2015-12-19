package com.example.arunkumar.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class newEvent extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        String Mode=getIntent().getExtras().getString("mode");



        Button event=(Button)findViewById(R.id.added);
        final TimePicker t=(TimePicker) findViewById(R.id.time);
        final EditText et=(EditText) findViewById(R.id.describe);

        if(Mode.equals("update")){

            String update=getIntent().getExtras().getString("uptime");
            String updesc=getIntent().getExtras().getString("updesc");

            String[] parts = update.split(":");

            t.setCurrentHour(Integer.parseInt(parts[0]));
            t.setCurrentMinute(Integer.parseInt(parts[1]));

            et.setText(updesc);


        }





        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int hour,minutes;
                String desc,notifyTime;

                hour=t.getCurrentHour();
                minutes=t.getCurrentMinute();
                desc=et.getText().toString();


                setAlarm(hour,minutes,desc);

                notifyTime=hour+":"+minutes;

                Intent parent=new Intent(newEvent.this,MainActivity.class);

                parent.putExtra("notifyTime",notifyTime);
                parent.putExtra("desc",desc);



                MainActivity.count++;
                startActivity(parent);


            }
        });
    }

    private void setAlarm(int hourOfDay,int minute,String describe) {

        final String[] dayarray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


        Calendar calNow = Calendar.getInstance();
        Calendar targetCal = (Calendar) calNow.clone();

        targetCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        targetCal.set(Calendar.MINUTE, minute);
        targetCal.set(Calendar.SECOND, 0);
        targetCal.set(Calendar.MILLISECOND, 0);
        String today= (String) android.text.format.DateFormat.format("EEEE", calNow);

        int selectedday=0,currentday=0;
        for(int i=0;i<7;i++){

            if(dayarray[i].equals(Day.day))
                selectedday=i;
            if(dayarray[i].equals(today))
                 currentday=i;

        }



        int repeat=  selectedday-currentday;
        if(repeat<0)
            repeat+=7;





        if (targetCal.compareTo(calNow) <= 0) {
            // Today Set time passed, count to tomorrow
            targetCal.add(Calendar.DATE, repeat);
        }




        Toast.makeText(this, "Alarm is set "
                + targetCal.getTime() + "\n", Toast.LENGTH_LONG).show();




              Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
              intent.putExtra("describe",describe);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(),1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), 7*24 * 60 * 60 * 100 , pendingIntent);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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
}
