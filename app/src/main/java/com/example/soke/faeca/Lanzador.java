package com.example.soke.faeca;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;

public class Lanzador extends ParsePushBroadcastReceiver {

    public void onPushOpen(Context context, Intent intent) {

        ParseAnalytics.trackAppOpenedInBackground(intent);
        String extra = null;
        if (intent.hasExtra("tipo"))
            extra = intent.getStringExtra("tipo");

        Class<? extends Activity> cls = getActivity(context, intent);
        Intent i = new Intent(context, Consulta.class);
        i.putExtra("extra", extra);

        if (Build.VERSION.SDK_INT >= 16) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(cls);
            stackBuilder.addNextIntent(i);
            stackBuilder.startActivities();
        } else {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("extra", extra);
            context.startActivity(i);
        }
    }

}
