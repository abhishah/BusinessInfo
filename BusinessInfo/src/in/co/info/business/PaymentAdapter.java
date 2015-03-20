package in.co.info.business;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PaymentAdapter extends SimpleAdapter {

	ArrayList<HashMap<String, String>> orderList;
	PaymentAdapter adapter = this;
	Context mContext;

	public PaymentAdapter(Context context,
			ArrayList<HashMap<String, String>> data) {
		super(context, data, android.R.layout.simple_list_item_1,
				new String[] { "from" },
				new int[] { android.R.layout.simple_list_item_1 });
		this.orderList = data;
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View list_item = mInflater.inflate(R.layout.payment_list_item, parent,
				false);
		TextView pay = (TextView) list_item.findViewById(R.id.list_item1);
		TextView paydate = (TextView) list_item.findViewById(R.id.list_item2);
		pay.setText(orderList.get(position).get("payment"));
		paydate.setText(orderList.get(position).get("payment_date"));
		Log.i("adapter",orderList.get(position).get("payment"));
		return list_item;
	}

}
