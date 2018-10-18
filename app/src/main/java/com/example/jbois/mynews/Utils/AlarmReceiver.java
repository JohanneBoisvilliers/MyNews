package com.example.jbois.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.jbois.mynews.Controllers.Activities.MainActivity;
import com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.CHECKBOX_CATEGORY;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.GET_BEGIN_DATE;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.GET_END_DATE;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.SEARCH_QUERY_TERMS;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.BEGIN_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.CATEGORY;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.END_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.PREFS_NAME;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.QUERY_TERMS;

public class AlarmReceiver extends BroadcastReceiver {

    private String mQueryTerms,mBeginDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ALARMTEST","alarme reçue");

        Gson gson = new Gson();

        mQueryTerms=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getString("queryToAlarm","");
        Log.e("ALARMTEST","Mots clés pour la recherche :"+mQueryTerms);

        String categoryFromJson = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getString("categoryToAlarm","");
        ArrayList<String> category = gson.fromJson(categoryFromJson,new TypeToken<ArrayList<String>>(){}.getType());
        Log.e("ALARMTEST","catégories :"+category);
        //Intent to invoke app when click on notification.
        Intent intentToRepeat = new Intent(context, ResultSearchActivity.class);

        DateTime jodatime = DateTime.parse(new DateTime().toString());
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yy");
        mBeginDate = dtfOut.print(jodatime);

        intentToRepeat.putExtra(QUERY_TERMS, mQueryTerms);
        intentToRepeat.putStringArrayListExtra(CATEGORY, category);
        intentToRepeat.putExtra(BEGIN_DATE,mBeginDate);
        intentToRepeat.putExtra(END_DATE,"Select a date");
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "CHANNEL_ID",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("NOTIFICATION_CHANNEL_ID");
            mNotificationManager.createNotificationChannel(channel);
        }
        //Build notification
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context,"default")
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