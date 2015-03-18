package in.co.info.business;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCompleteInfo extends Activity{

	DetailsDatabase detailsDb;
	int uid;
	TextView username, address, village, city, district,
    state, pincode, item_name, item_id, order_date, due_date, balance, amount;
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
		uid = getIntent().getIntExtra("id", 0);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Order order = detailsDb.showOrder(uid);
		if(order == null){
			Toast.makeText(this, "Invalid Record", Toast.LENGTH_LONG).show();
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
		
		//******************* left for date picker
		
	}

}
