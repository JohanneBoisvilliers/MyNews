package com.example.jbois.mynews.Utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.jbois.mynews.Controllers.Activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ALARMTEST","OK");
        //Intent to invoke app when click on notification.
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build notification
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.drawable.arrow_up_float)
                        .setContentTitle("My News : new articles !")
                        .setContentText("There are new articles for you ! click to read")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }


}