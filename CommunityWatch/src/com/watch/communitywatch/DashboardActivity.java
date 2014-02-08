package com.watch.communitywatch;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
	}
	
}
