package com.watch.communitywatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DashboardActivity extends ListActivity{
	Button newMessageBtn;
	Button profileBtn;
	ListView feedView;
	
	List g;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
        newMessageBtn = (Button) findViewById(R.id.newMessageBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        
        newMessageBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						MessageActivity.class);
				startActivity(i);
				finish();
			}
		});
        
        profileBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						ProfileActivity.class);
				startActivity(i);
				finish();
			}
		});
        
     // Populate from database
        DatabaseHandler x = new DatabaseHandler(this);
		x.recreate_table();
        Log.d("Insert: ", "Inserting .."); 
        x.addMember(new Members("Ravi", "Shady looking dude standing by the corner", "hi@me.com"));        
        x.addMember(new Members("Srinivas", "Hey Ravi, Thanks. Will be on the look out :)","hi@me.com"));
        x.addMember(new Members("Tommy", "Anyone up for a BBQ this weekend?","hi@me.com"));
        x.addMember(new Members("Karthik", "Yep :)","hi@you.com"));
        
        List msgs = x.getAllMembers();
        List g = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> item;
        
        for(int i=0; i < msgs.size(); i++)
        {
           
           HashMap<String, String> map = new HashMap<String, String>();
           Members x1 = (Members) msgs.get(i);
		    map.put("name", x1.getName());
		    map.put("message", x1.getMessage());
		    
		    g.add(map);
		    Log.i("LIST-ITEMS", g.get(i).toString());
        }
        
        // get lv
        // ListView lv = getListView();
        
        ListAdapter adapter = new SimpleAdapter(
                DashboardActivity.this, g,
                R.layout.new_blast_view, new String[] { "name",
                        "message", }, new int[] {
                        R.id.blast_name, R.id.blast_message });
         
        // updating listview
        setListAdapter(adapter);
        
        // lv.setAdapter((ListAdapter) new ArrayAdapter<String>(this,android.R.layout.activity_list_item ));
	}
	
}
