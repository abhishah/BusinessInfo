package in.co.info.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	Intent in;
	PendingIntent pendingIntent;
	Notification mBuilder;

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		// Acquire the lock
		wl.acquire();

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		StringBuilder s = new StringBuilder().append(month + 1).append("-")
				.append(day).append("-").append(year).append(" ");

		DetailsDatabase db;
		try {
			db = new DetailsDatabase(context).open();

			ArrayList<HashMap<String, String>> data = db
					.fetchNotificationData(s.toString());

			if (data != null) {

				for (int i = 0; i < data.size(); i++) {
					/*Toast.makeText(context, i + "", Toast.LENGTH_LONG).show();*/
					in = new Intent(context, ViewCompleteInfo.class);
					Bundle bundle = new Bundle();
					bundle.putInt("id", Integer.parseInt(data.get(i).get("id")));
					
					Log.i("user_id in receiver",data.get(i).get("id"));
					
					in.putExtras(bundle);
					in.putExtra("id", Integer.parseInt(data.get(i).get("id")));
					in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					pendingIntent = PendingIntent
							.getActivity(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);

					mBuilder = new NotificationCompat.Builder(context)
							.setSmallIcon(R.drawable.ic_launcher)
							.setContentTitle(data.get(i).get("username"))
							.setContentText(data.get(i).get("balance"))
							.setContentIntent(pendingIntent)
							.setAutoCancel(true).build();

					NotificationManager mNotificationManager = (NotificationManager) context
							.getSystemService(Context.NOTIFICATION_SERVICE);

					mNotificationManager.notify(Integer.parseInt(data.get(i).get("id")), mBuilder);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Release the lock
		wl.release();
	}
}