package com.example.finalproject;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LastSummaryActivity extends ActionBarActivity {

	
	private OrderDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last_summary);
		
		// INSTANCE DB ADAPTER
		dbHelper = new OrderDbAdapter(this);
		
		// OPEN
		dbHelper.open();

		
		// DO ACTIONS
		// Clean all data
		//dbHelper.deleteAllOrders();

		//System.out.println("SEED DB");
		// Add some data
		//dbHelper.seed();
		
		// QUERY
		// print contents of DB
		Intent data = getIntent();
		String name = data.getStringExtra("name");
		Cursor cursor = dbHelper.fetchOrderByName(name);
		
		dataAdapter = new SimpleCursorAdapter (this, R.layout.order_info, cursor, 
				new String[] { OrderDbAdapter.KEY_ITEMNAME, OrderDbAdapter.KEY_QUANTITY, OrderDbAdapter.KEY_TOTALCOST, OrderDbAdapter.KEY_NAME, OrderDbAdapter.KEY_COMMENT },
				new int[] { R.id.itemName, R.id.quantityText, R.id.totalCost, R.id.clientName, R.id.commentText }, 
				0);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(dataAdapter);
		
		// NOTE: REGISTER listview for context menu
        /////registerForContextMenu(listView);
        
     // clicking an item in the listview
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				// Get the cursor, positioned to the corresponding row in the
				// result set
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);

				// Get the client's name from this row in the database.
				String name = cursor.getString(cursor.getColumnIndexOrThrow(OrderDbAdapter.KEY_ITEMNAME));
				
				// display in toast
				Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
			}
		});
		
		/*
		// this detects changes in the EditText as you type
		EditText myFilter = (EditText) findViewById(R.id.myFilter);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// this tells the adapter to trigger the filter
				dataAdapter.getFilter().filter(s.toString());
			}
		});*/
		
		// indicates what query will be run when filtering
		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbHelper.fetchOrderByName(constraint.toString());
			}
		});		
	}

	
	
	
	@Override
    public void onBackPressed()
    {
        Toast.makeText(this, "Back to Summary List", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        finish();       
    }
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.last_summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
