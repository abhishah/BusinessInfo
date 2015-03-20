package in.co.info.business;

import java.sql.SQLException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCompleteInfo extends Activity implements OnClickListener {

	DetailsDatabase detailsDb;
	int uid;
	TextView username, address, village, city, district, state, pincode,
			item_name, item_id, order_date, due_date, balance, amount;
	String mobileno, telephone, emailid, callno, sendto;
	Button view_payment, datechange;
	boolean calling, emailing;
	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_IDA = 0000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_single_order);
		try {
			detailsDb = new DetailsDatabase(this).open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeViews();
		int x = getIntent().getExtras().getInt("id");
		Log.i("received id ",x +"");
		uid = getIntent().getIntExtra("id", 0);
		calling = false;
		emailing = false;

	}

	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		uid = intent.getIntExtra("id", 0);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Order order = detailsDb.showOrder(uid);

		if(order == null){
			Toast.makeText(this, "Invalid Record " + uid, Toast.LENGTH_LONG).show();
			finish();
		} else {
			setValues(order);
			if (mobileno != null && mobileno.length() > 1) {
				callno = mobileno;
				// call.setVisibility(View.VISIBLE);
				calling = true;
			} else if (telephone != null && telephone.length() > 1) {
				callno = telephone;
				// call.setVisibility(View.VISIBLE);
				calling = true;
			} else {// call.setVisibility(View.INVISIBLE);
				callno = null;
				calling = false;
			}
			if (emailid != null && emailid.length() > 1) {
				// email.setVisibility(View.VISIBLE);
				emailing = true;
				sendto = emailid;
			} else {
				sendto = null;
				emailing = false;
			}
			invalidateOptionsMenu();
		}
	}

	private void setValues(Order order) {
		// TODO Auto-generated method stub
		username.setText(order.getUsername());
		address.setText(order.getAddress());
		village.setText(order.getVillage());
		city.setText(order.getCity());
		district.setText(order.getDistrict());
		state.setText(order.getState());
		pincode.setText(order.getPincode());
		item_name.setText(order.getItem_name());
		item_id.setText(order.getItem_id());
		order_date.setText(order.getOrder_date());
		due_date.setText(order.getDue_date());
		amount.setText(order.getAmount() + "");
		balance.setText(order.getBalance() + "");
		mobileno = order.getMobile();
		telephone = order.getLandline();
		emailid = order.getEmail();

	}

	private void initializeViews() {
		// TODO Auto-generated method stub
		username = (TextView) findViewById(R.id.view_username);
		address = (TextView) findViewById(R.id.view_address);
		village = (TextView) findViewById(R.id.view_village);
		city = (TextView) findViewById(R.id.view_city);
		district = (TextView) findViewById(R.id.view_district);
		state = (TextView) findViewById(R.id.view_state);
		pincode = (TextView) findViewById(R.id.view_pincode);
		item_name = (TextView) findViewById(R.id.view_item_name);
		item_id = (TextView) findViewById(R.id.view_item_id);
		order_date = (TextView) findViewById(R.id.view_order_date);
		balance = (TextView) findViewById(R.id.view_balance);
		due_date = (TextView) findViewById(R.id.view_due_date);
		amount = (TextView) findViewById(R.id.view_amount);
		view_payment = (Button) findViewById(R.id.payments);
		view_payment.setOnClickListener(this);
		datechange = (Button) findViewById(R.id.bDateChange);
		datechange.setOnClickListener(this);
		// ******************* left for date picker

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_menu, menu);
		menu.getItem(0).setVisible(false);
		menu.getItem(1).setVisible(false);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (calling) {
			menu.getItem(0).setVisible(true);
			menu.getItem(0).setEnabled(true);
		} else {
			menu.getItem(0).setVisible(true);
			menu.getItem(0).setEnabled(false);
		}
		if (emailing) {
			menu.getItem(1).setVisible(true);
			menu.getItem(1).setEnabled(true);
		} else {
			menu.getItem(1).setVisible(true);
			menu.getItem(1).setEnabled(false);
		}
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.call:
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + callno));
			startActivity(callIntent);
			break;
		case R.id.email:
			Intent sendIntent;
			// Since we want to send ,we are using ACTION_SEND
			sendIntent = new Intent(Intent.ACTION_SEND);
			sendIntent.setType("application/octet-stream");
			sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { sendto });
			startActivity(Intent.createChooser(sendIntent, "Send Mail"));
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.payments) {
			Intent intent = new Intent(this, Payments.class);
			intent.putExtra("id", uid);
			startActivity(intent);
			finish();
		} else if (v.getId() == R.id.bDateChange) {
			//Toast.makeText(getApplicationContext(), "called", Toast.LENGTH_LONG).show();
			final Calendar c = Calendar.getInstance();
			String []parts=due_date.getText().toString().split("-");
			String years[]=parts[2].split(" ");
			year=Integer.parseInt(years[0]);
			month=Integer.parseInt(parts[0]);
			day=Integer.parseInt(parts[1]);
			showDialog(DATE_PICKER_IDA);
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_IDA:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListenerA, year, month, day);
		}return null;
	}

	private DatePickerDialog.OnDateSetListener pickerListenerA = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			due_date.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));
			
			 detailsDb.showOrder(uid).setDue_date(due_date.getText().toString());

		}
	};
}
