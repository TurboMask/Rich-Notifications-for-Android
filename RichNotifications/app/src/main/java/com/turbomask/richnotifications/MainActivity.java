package com.turbomask.richnotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnStartClicked(View view)
    {
        TextView info_text = (TextView) findViewById(R.id.info_text);
        Calendar _calendar = Calendar.getInstance();
        int _hour = _calendar.get(Calendar.HOUR_OF_DAY);
        int _min = _calendar.get(Calendar.MINUTE);
        int _sec = _calendar.get(Calendar.SECOND);
        long _timeout;
        int NOTIFICATION_HOUR = 9;
        int NOTIFICATION_MINUTE = 15;
        if(_hour > NOTIFICATION_HOUR || (_hour == NOTIFICATION_HOUR && _min >= NOTIFICATION_MINUTE))
        {
            _hour -= 24;
        }
        _timeout = ((NOTIFICATION_HOUR - _hour) * 60 + NOTIFICATION_MINUTE - _min) * 60 - _sec;
        _timeout *= 1000;
        long first_event_time = System.currentTimeMillis() + _timeout;
        long interval = 24 * 60 * 60 * 1000;

        //This is used for testing
        //first_event_time = System.currentTimeMillis() + 5000;
        //interval = 60 * 60 * 1000;

        PendingIntent _pending_intent = CreatePendingIntent();
        AlarmManager _alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        _alarm.setRepeating(AlarmManager.RTC_WAKEUP, first_event_time, interval, _pending_intent);
    }

    public void BtnStopClicked(View view)
    {
        PendingIntent _pending_intent = CreatePendingIntent();
        AlarmManager _alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        _alarm.cancel(_pending_intent);
    }

    public void BtnCheckClicked(View view)
    {
        AlarmManager _alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        AlarmManager.AlarmClockInfo _next_alarm = _alarm.getNextAlarmClock();
        TextView info_text = (TextView) findViewById(R.id.info_text);
        if(_next_alarm == null)
        {
            info_text.setText("Next notification: null");
        }
        else
        {
            long _time = (_next_alarm.getTriggerTime() - System.currentTimeMillis()) / 1000;
            info_text.setText("Next notification: " + _time);
        }
    }

    public void BtnShowClicked(View view)
    {
        PendingIntent _pending_intent = CreatePendingIntent();
        AlarmManager _alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        long _time = System.currentTimeMillis() + 3000;
        _alarm.set(AlarmManager.RTC_WAKEUP, _time, _pending_intent);
        TextView info_text = (TextView) findViewById(R.id.info_text);
        info_text.setText("Will show notification after 3 seconds");
    }

    private PendingIntent CreatePendingIntent()
    {
        Intent _intent = new Intent(this, NotificationPublisher.class);
        PendingIntent _pending_intent = PendingIntent.getBroadcast(this, 17, _intent, 0);
        return _pending_intent;
    }
}
