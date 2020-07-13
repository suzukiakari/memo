package com.example.memo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String id = "notification_alarm_channel";
        String name = "アラームチャンネル";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(
                context, "notification_alarm_channel");
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle(context.getString(R.string.notification_alert_text));
        builder.setContentText(context.getString(R.string.notification_alert_text));
        Notification notification = builder.build();
        manager.notify(0, notification);

    }
}
