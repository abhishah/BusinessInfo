package in.co.info.business;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by marauder on 3/17/15.
 */
public class AddOrder extends Activity implements OnItemSelectedListener,
		OnClickListener {

	public DetailsDatabase details;
	public EditText name, address, village, city, district, pin, mobile,
			landline, email, itemname, itemid, totalpayment, advance, balance;
	public Spinner state;
	public TextView order_date, paymentdate;
	public Button datepayment, dateorder, submit;
	String sname, saddress, svillage, scity, sdistrict, spin, sstate, smobile,
			slandline, semail, sitemname, sitemid, stotalpayment, sadvance,
			sbalance, spaymentdate, sadvancedate = null;
	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_IDP = 1111;
	static final int DATE_PICKER_IDA = 0000;
	private ScheduleClient scheduleClient;
	private PendingIntent alarmIntent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_order);
		setVariable();
		initstring();
		try {
			details = new DetailsDatabase(this).open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.state_array,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		state.setAdapter(adapter);
		state.setOnItemSelectedListener(this);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		order_date.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));
		paymentdate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));
		datepayment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog
				showDialog(DATE_PICKER_IDP);

			}

		});
		dateorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog
				showDialog(DATE_PICKER_IDA);

			}

		});
		submit.setOnClickListener(this);
		// Create a new service client and bind our activity to this service
		scheduleClient = new ScheduleClient(this);
		scheduleClient.doBindService();

	}

	private void initstring() {
		sname = null;
		saddress = null;
		svillage = null;
		scity = null;
		sdistrict = null;
		spin = null;
		smobile = null;
		slandline = null;
		semail = null;
		sitemname = null;
		sitemid = null;
		stotalpayment = null;
		sadvance = null;
		sbalance = null;
	}

	private void setVariable() {
		// TODO Auto-generated method stub
		name = (EditText) findViewById(R.id.eName);
		address = (EditText) findViewById(R.id.eAddress);
		village = (EditText) findViewById(R.id.eVillage);
		city = (EditText) findViewById(R.id.eCity);
		district = (EditText) findViewById(R.id.eDistrict);
		pin = (EditText) findViewById(R.id.ePin);
		mobile = (EditText) findViewById(R.id.eMobile);
		landline = (EditText) findViewById(R.id.eLandline);
		email = (EditText) findViewById(R.id.eEmail);
		itemname = (EditText) findViewById(R.id.eItemName);
		itemid = (EditText) findViewById(R.id.eItemId);
		totalpayment = (EditText) findViewById(R.id.eTotalPayment);
		paymentdate = (TextView) findViewById(R.id.tPaymentDate);
		order_date = (TextView) findViewById(R.id.tAdvanceDate);
		datepayment = (Button) findViewById(R.id.myDatePickerButton1);
		dateorder = (Button) findViewById(R.id.myDatePickerButton2);
		submit = (Button) findViewById(R.id.Submit);
		state = (Spinner) findViewById(R.id.spinner1);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		sstate = parent.getItemAtPosition(pos).toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		sstate = null;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_IDA:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListenerA, year, month, day);
		case DATE_PICKER_IDP:
			return new DatePickerDialog(this, pickerListenerP, year, month, day);
		}
		return null;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent viewIntent = new Intent(this, ViewActivity.class);
		startActivity(viewIntent);
	}

	private DatePickerDialog.OnDateSetListener pickerListenerP = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			paymentdate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};
	private DatePickerDialog.OnDateSetListener pickerListenerA = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			order_date.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		collectdata();
		if (sname != null && saddress != "" && svillage != "" && scity != ""
				&& sdistrict != "" && spin != "") {
			Order givenorder = createObject();
			details.addOrder(givenorder);
			startAlarm(givenorder.due_date);
			Toast.makeText(this, "Order added", Toast.LENGTH_LONG).show();
		//	Intent intent = new Intent(this, ViewActivity.class);
			//startActivity(intent);
		//	finish();

		} else
			Toast.makeText(this, "Enter Empty Fields", Toast.LENGTH_LONG)
					.show();
	}

	private void startAlarm(String date) {
		String[] days = date.split("-");
		String years[]=days[2].split(" ");
		int year = Integer.parseInt(years[0]);
		int day = Integer.parseInt(days[1]);
		int month = Integer.parseInt(days[0]);
		Log.e("year", " "+year+" "+day+" "+month);
		//AlarmManager alarmManager = (AlarmManager) this
			//	.getSystemService(this.ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 18,52, 0);
		Toast.makeText(getApplicationContext(), "Time:"+6, Toast.LENGTH_LONG).show();
		// Ask our service to set an alarm for that date, this activity talks to
		// the client that talks to the service
		 Long time = new GregorianCalendar().getTimeInMillis()+60*06*24*1000;
		 
		    Intent intentAlarm = new Intent(this, AlarmReceiver.class);
		 
		    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		    alarmIntent=PendingIntent.getBroadcast(this, 1, intentAlarm, 0);
		 
		    alarmManager.set(AlarmManager.RTC_WAKEUP,time, alarmIntent);
		 
		    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60*60*24, alarmIntent);
		// Notify the user what they just did
		Toast.makeText(
				this,
				"Notification set for: " + day + "/" + (month + 1) + "/" + year,
				Toast.LENGTH_SHORT).show();
	}

	private Order createObject() {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "Order creation called",
		// Toast.LENGTH_LONG).show();
		Order a = new Order();
		try {
			a.setUsername(sname);
			a.setAddress(saddress);
			a.setVillage(svillage);
			a.setCity(scity);
			a.setDistrict(sdistrict);
			a.setPincode(spin);
			a.setState(sstate);
			a.setMobile(smobile);
			a.setLandline(slandline);
			a.setEmail(semail);
			a.setItem_name(sitemname);
			a.setItem_id(sitemid);
			float amount = Float.parseFloat(stotalpayment);
			float balancerem = Float.parseFloat(sbalance);
			a.setAmount(amount);
			a.setBalance(balancerem);
			a.setOrder_date(spaymentdate);
			a.setDue_date(sadvancedate);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Invalid Input",
					Toast.LENGTH_LONG).show();
		}
		return a;
	}

	public void collectdata() {
		sname = name.getText().toString();
		saddress = address.getText().toString();
		svillage = village.getText().toString();
		scity = city.getText().toString();
		sdistrict = district.getText().toString();
		spin = pin.getText().toString();
		smobile = mobile.getText().toString();
		slandline = landline.getText().toString();
		semail = email.getText().toString();
		sitemname = itemname.getText().toString();
		sitemid = itemid.getText().toString();
		stotalpayment = totalpayment.getText().toString();
		spaymentdate = paymentdate.getText().toString();
		sadvancedate = order_date.getText().toString();
		sbalance = stotalpayment;
	}

	@Override
	protected void onStop() {
		// When our activity is stopped ensure we also stop the connection to
		// the service
		// this stops us leaking our activity into the system *bad*
		if (scheduleClient != null)
			scheduleClient.doUnbindService();
		super.onStop();
	}
}
