package com.turbomask.richnotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import java.net.URL;

public class NotificationPublisher extends BroadcastReceiver
{
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String img_path = "https://picsum.photos/204/204/?random";

    public void onReceive(Context context, Intent intent)
    {
        Log.d("App","Received intent");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                //.detectNetwork()
                .penaltyLog()
                .build());

        NotificationChannel channel = new NotificationChannel("test_channel", "Rich Notifications", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Showing rich notifications");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Bitmap image = null;
        try {
            URL img_url = new URL(img_path);
            image = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        Notification.Style picture_style = new Notification.BigPictureStyle()
                .bigPicture(image);

        Notification.Builder b = new Notification.Builder(context, "test_channel")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Rich Notifications")
                .setContentText("Notification text")
                .setStyle(picture_style)
                .setAutoCancel(true);

        Notification notif = b.build();

        notificationManager.notify(1, notif);
    }
}