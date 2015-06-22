package com.example.finalproject;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddOrder extends ActionBarActivity {
	
	public static final int ADD_PRESSED = 0;
	public static final int GET_CLIENT = 1;
	public static final int GET_ITEM = 2;
	private double cost = 0;
	private int stock = 0;
	private double qtyDouble = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_order);
		
		Intent data = getIntent();
		EditText et1 = (EditText) findViewById(R.id.itemName);
		EditText et2 = (EditText) findViewById(R.id.quantityText);
		EditText et3 = (EditText) findViewById(R.id.clientName);
		EditText et4 = (EditText) findViewById(R.id.commentText);
		
		if (data != null)
		{
			et1.setText(data.getStringExtra("itemName"));
			et2.setText(data.getStringExtra("quantity"));
			et3.setText(data.getStringExtra("clientName"));
			et4.setText(data.getStringExtra("comment"));
		}
	}
	
	public void getClient(View v)
	{
		Intent intent1 = new Intent(this, PickClient.class);
		
		startActivityForResult(intent1, GET_CLIENT);
	}
	
	public void getItem(View v)
	{
		Intent intent2 = new Intent(this, PickItem.class);
		startActivityForResult(intent2, GET_ITEM);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (intent == null)
		{
			return;
		}
        if (requestCode == GET_CLIENT)
        {
        	if (resultCode == ADD_PRESSED) {
            	//oldIntent = intent;
            	
            	String s1 = intent.getStringExtra("name");
            	
            	if (s1.equals(""))
				{
					return;
				}
            	
            	EditText et1 = (EditText) findViewById(R.id.clientName);
            	et1.setText(s1);
				
				Toast.makeText(this, "Client: " + s1, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == GET_ITEM)
        {
        	if (resultCode == ADD_PRESSED) {
            	//oldIntent = intent;
            	
            	String s1 = intent.getStringExtra("name");
            	String s2 = intent.getStringExtra("cost");
            	String s3 = intent.getStringExtra("stock");
            	Toast.makeText(this, s3, Toast.LENGTH_SHORT).show();
            	cost = Double.parseDouble(s2);
            	stock = Integer.parseInt(s3);
            	
            	if (s1.equals(""))
				{
					return;
				}
            	
            	EditText et1 = (EditText) findViewById(R.id.itemName);
            	et1.setText(s1);
				
				Toast.makeText(this, "Item: " + s1, Toast.LENGTH_SHORT).show();
            }
        }
    }
	
	public void addPressed(View v)
	{
		Intent data = getIntent();
		
		EditText et1 = (EditText) findViewById(R.id.itemName);
		EditText et2 = (EditText) findViewById(R.id.quantityText);
		EditText et3 = (EditText) findViewById(R.id.clientName);
		EditText et4 = (EditText) findViewById(R.id.commentText);
		String s1 = et1.getText().toString();
		String s2 = et2.getText().toString();
		String s3 = et3.getText().toString();
		String s4 = et4.getText().toString();
		
		
		ArrayList<String> itemsStocks;
		ArrayList<String> itemsNames;
		itemsStocks=data.getStringArrayListExtra("itemsStock");
		itemsNames= data.getStringArrayListExtra("itemsName");;
		String ammount="";
		
		if((data.getStringArrayListExtra("itemsStock")!=null) && (data.getStringArrayListExtra("itemsName")!=null))
		{	
			int x= data.getStringArrayListExtra("itemsName").indexOf(s1);
			stock=Integer.parseInt(data.getStringArrayListExtra("itemsStock").get(x));
		}
		else
		{
			Toast.makeText(this, "they are null", Toast.LENGTH_SHORT).show();
		}
		
		
		
		
		if ((s1.equals("")) || (s2.equals("")) || (s3.equals("")))
		{
			if (s1.equals(""))
			{
				Toast.makeText(this, "Item Name must have a value", Toast.LENGTH_SHORT).show();
			}
			
			if (s2.equals(""))
			{
				Toast.makeText(this, "Quantity must have a value", Toast.LENGTH_SHORT).show();
			}
			
			else if (s3.equals(""))
			{
				Toast.makeText(this, "Name must have a value", Toast.LENGTH_SHORT).show();
			}
			
			else if ((s1.equals("")) && (s2.equals("")) && (s3.equals("")))
			{
			Toast.makeText(this, "Item Name must have a value", Toast.LENGTH_SHORT).show();
			}
		}
		
		else if (!s2.equals(""))
		{
			int qty = Integer.parseInt(s2);
			qtyDouble = (double) qty;
			
			if (qty <= 0)
			{
				Toast.makeText(this, "Quantity must have a value", Toast.LENGTH_SHORT).show();
			}
			
			else if (qty > stock)
			{
				Toast.makeText(this, "Insufficient stock available", Toast.LENGTH_SHORT).show();
			}

			else
			{
				double totalCost = cost * qtyDouble;
				String totalCostString = String.format("%1$,.2f", totalCost);
				data.putExtra("totalCost", totalCostString);
				data.putExtra("itemName", s1);
				data.putExtra("quantity", s2);	
				data.putExtra("clientName", s3);
				data.putExtra("comment", s4);	
				setResult(ADD_PRESSED, data);
				finish();
			}
		}
	}
	
	public void cancelPressed(View v)
	{
		Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
		finish();
	}

	
	@Override
    public void onBackPressed()
    {
        Toast.makeText(this, "Back to Order List", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        finish();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_order, menu);
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
