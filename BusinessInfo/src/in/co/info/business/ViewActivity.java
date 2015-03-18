package in.co.info.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by marauder on 3/17/15.
 */
public class ViewActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<HashMap<String,String>> orderList;
    ViewOrderAdapter adapter;
    DetailsDatabase detailsDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);
        detailsDb = new DetailsDatabase(this);
        try {
			detailsDb.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        orderList = updateData();
        if(orderList != null){
        	adapter = new ViewOrderAdapter(this,orderList);
        	//Log.i("view item ", orderList.get(0).get("username"));
        }
        	
        else{
        	adapter = null;
        }
        lv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orderList = updateData();
        if(orderList == null){
        	adapter = null;
        	Toast.makeText(this, "No records to show", Toast.LENGTH_LONG).show();
        }else{
        	if(adapter == null)
        		adapter = new ViewOrderAdapter(this, orderList);
        	adapter.notifyDataSetChanged();
        }
        
    }

    private ArrayList<HashMap<String,String>> updateData() {
        return detailsDb.fetchUserNames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
            	Intent i=new Intent(this,AddOrder.class);
            	startActivity(i);
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
