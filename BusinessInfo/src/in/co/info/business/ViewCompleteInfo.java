package in.co.info.business;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCompleteInfo extends Activity implements OnClickListener{

	DetailsDatabase detailsDb;
	int uid;
	TextView username, address, village, city, district,
    state, pincode, item_name, item_id, order_date, due_date, balance, amount;
	
	Button view_payment;
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
		}else{
			setValues(order);
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
		
	}

	private void initializeViews() {
		// TODO Auto-generated method stub
		username = (TextView)findViewById(R.id.view_username);
		address = (TextView)findViewById(R.id.view_address);
		village = (TextView)findViewById(R.id.view_village);
		city = (TextView)findViewById(R.id.view_city);
		district = (TextView)findViewById(R.id.view_district);
		state = (TextView)findViewById(R.id.view_state);
		pincode  = (TextView)findViewById(R.id.view_pincode);
		item_name  = (TextView)findViewById(R.id.view_item_name);
		item_id = (TextView)findViewById(R.id.view_item_id);
		order_date  = (TextView)findViewById(R.id.view_order_date);
		balance = (TextView)findViewById(R.id.view_balance);
		due_date  = (TextView)findViewById(R.id.view_due_date);
		amount = (TextView)findViewById(R.id.view_amount);
		view_payment = (Button)findViewById(R.id.payments);
		view_payment.setOnClickListener(this);
		//******************* left for date picker
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.payments){
			Intent intent = new Intent(this, Payments.class);
			intent.putExtra("id",uid);
			startActivity(intent);
			finish();
		}
	}
}
