package com.example.arunkumar.timetable;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 11/24/2015.
 */


    public class Alarm extends Activity {
    private MediaPlayer player;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.alarm_layout);

        final String loginurl="http://52.10.251.227:3000/login";

        final ConnectionDetector cd = new ConnectionDetector(Alarm.this);
        if(cd.isConnectingToInternet()){


            JSONObject jsonobject;
            final JSONParser jParser1 = new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("password","arun"));
            params1.add(new BasicNameValuePair("username","arun"));


            jsonobject = jParser1.makeHttpRequest(loginurl, "POST", params1);

            try{
                if(jsonobject!=null){

                    String result=jsonobject.getString("success");

                    if(result.equals("true"))
                    {
                        Toast.makeText(Alarm.this, "Goyala op", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(Alarm.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();

                    }

                }
                else{
                    Toast.makeText(Alarm.this, "No Response From Server", Toast.LENGTH_LONG).show();


                }



            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }






        }else{

            Toast.makeText(Alarm.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }




        TextView tv=(TextView) findViewById(R.id.d);
        tv.setText(getIntent().getExtras().getString("describe"));

        Button stop = (Button) findViewById(R.id.alarm);
        stop.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                player.stop();
                finish();
                return false;
            }
        });

        play(this, getAlarmSound());
    }

    private void play(Context context, Uri alert) {
        player = new MediaPlayer();
        try {
            player.setDataSource(context, alert);
            final AudioManager audio = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audio.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                player.setAudioStreamType(AudioManager.STREAM_ALARM);
                player.prepare();
                player.start();
            }
        } catch (IOException e) {
            Log.e("Error....","Check code...");
        }
    }

    private Uri getAlarmSound() {
        Uri alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alertSound == null) {
            alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alertSound == null) {
                alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alertSound;
    }
}

