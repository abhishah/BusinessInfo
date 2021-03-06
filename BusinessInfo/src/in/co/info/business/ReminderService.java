package in.co.info.business;

import java.util.Calendar;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

class ReminderService extends IntentService {
	private static final int NOTIF_ID = 1;

	public ReminderService() {
		super("ReminderService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//long when = System.currentTimeMillis(); // notification time
		Calendar calendar = Calendar.getInstance(); 
		//calendar.set(int year, int month, int date, int hour, int minute, int second); 
		long when = calendar.getTimeInMillis();
		Notification notification = new Notification(R.drawable.ic_launcher,
				"reminder", when);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= notification.FLAG_AUTO_CANCEL;
		Intent notificationIntent = new Intent(this, ViewActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification
				.setLatestEventInfo(getApplicationContext(), "It's about time",
						"You should open the app now", contentIntent);
		nm.notify(NOTIF_ID, notification);

	}
}
