package in.co.info.business;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver{
private static final int MY_NOTIFICATION_ID=1;
Intent in;
PendingIntent pendingIntent;
Notification mBuilder;
 
@Override 
public void onReceive(Context context, Intent intent)
{ Bundle id=new Bundle();
  id.putInt("id", 1);
    in=new Intent(context,ViewCompleteInfo.class);
    in.putExtras(id);
    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    pendingIntent=PendingIntent.getActivity(context, 0, in, 0);
 
    mBuilder=new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("Your Title") 
        .setContentText("Your Text") 
        .setContentIntent(pendingIntent)
        .setAutoCancel(true) 
        .build(); 
 
    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
 
    mNotificationManager.notify(1, mBuilder);
    mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder);
} 
} 