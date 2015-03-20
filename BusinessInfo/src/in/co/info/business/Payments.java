package in.co.info.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Payments extends Activity implements OnClickListener {

	ListView lv;
	Button add_payment;
	DetailsDatabase detailsDb;
	ArrayList<HashMap<String, String>> paymentList;
	PaymentAdapter adapter;
	int uid;
	int year, month, day;
	TextView view_date;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_payments);
		uid = getIntent().getIntExtra("id", 0);
		context = this;
		try {
			detailsDb = new DetailsDatabase(this).open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeViews();
		paymentList = updateData();
		if (paymentList != null) {
			Toast.makeText(this, "Not NUll",Toast.LENGTH_LONG);
			adapter = new PaymentAdapter(this, paymentList);
			// Log.i("view item ", orderList.get(0).get("username"));
		}

		else {
			adapter = null;
		}
		lv.setAdapter(adapter);

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}

	private ArrayList<HashMap<String, String>> updateData() {
		// TODO Auto-generated method stub
		Log.i("user",uid +"");
		return detailsDb.getPaymentsForUser(uid);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(this, ViewCompleteInfo.class);
		i.putExtra("id", uid);
		startActivity(i);
		finish();
	}

	private void initializeViews() {
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.list_payments);
		add_payment = (Button) findViewById(R.id.add_payment);
		add_payment.setOnClickListener(this);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new DatePickerDialog(this, payment_date_picker, year, month, day);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.add_payment) {
			openDialog();
		}
	}

	private DatePickerDialog.OnDateSetListener payment_date_picker = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			view_date.setText(new StringBuilder().append(month + 1).append("-")
					.append(day).append("-").append(year).append(" "));
		}
	};

	private void openDialog() {
		// TODO Auto-generated method stub

		LayoutInflater li = LayoutInflater.from(this);
		View dialogview = li.inflate(R.layout.payments, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(dialogview);
		final EditText pay_amount = (EditText) dialogview
				.findViewById(R.id.get_payment);

		Button set_date = (Button) dialogview.findViewById(R.id.setdatePicker);
		view_date = (TextView) dialogview.findViewById(R.id.view_payment_date);

		// Show selected date
		view_date.setText(new StringBuilder().append(month + 1).append("-")
				.append(day).append("-").append(year).append(" "));

		set_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(1);
			}
		});

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Add",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								if ((pay_amount.getText().toString().trim()
										.length()) != 0) {
									addPayment();
									dialog.dismiss();
									
								}else{
									Toast.makeText(getApplicationContext(),
											"Empty Fields", Toast.LENGTH_LONG)
											.show();
									openDialog();
								}
							}

							private void addPayment() {
								// TODO Auto-generated method stub
								PaymentClass pcl = new PaymentClass();
								pcl.setUser_id(uid);
								pcl.setPayment(Float.parseFloat(pay_amount.getText().toString()));
								pcl.setPayment_date(view_date.getText().toString());
								if(detailsDb.addPayment(pcl)){
									Intent i = new Intent(context, ViewActivity.class);
									startActivity(i);
									finish();
								}else{
									Intent i = new Intent(context, ViewCompleteInfo.class);
									i.putExtra("id",uid);
									startActivity(i);
									finish();
								}
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
