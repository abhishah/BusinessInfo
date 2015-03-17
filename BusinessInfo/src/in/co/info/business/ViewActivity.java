package in.co.info.business;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by marauder on 3/17/15.
 */
public class ViewActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<String> orderList;
    ArrayAdapter adpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        orderList = new ArrayList<String>();
        adpater = new ArrayAdapter(this,android.R.layout.simple_list_item_1,orderList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orderList = updateData();

    }

    private ArrayList<String> updateData() {
        return null;
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
