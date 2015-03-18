package in.co.info.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewOrderAdapter extends SimpleAdapter {

	ArrayList<HashMap<String, String>> orderList;
	ViewOrderAdapter adapter = this;
	Context mContext;

	public ViewOrderAdapter(Context context,
			ArrayList<HashMap<String, String>> data) {
		super(context, data, android.R.layout.simple_list_item_1,new String[]{"from"}, new int[]{android.R.layout.simple_list_item_1});
		this.orderList = data;
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View list_item = mInflater.inflate(R.layout.view_list_item, parent,
				false);
		TextView user_tv = (TextView) list_item.findViewById(R.id.username_tv);
		user_tv.setText(orderList.get(position).get("username"));
		return list_item;
	}

}
