package com.watch.communitywatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends Activity {
	Button register;
	EditText nameEdit;
	EditText emailEdit;
	
	String email;
	String name;
	
	SharedPreferences prefs = this.getSharedPreferences(
		      "com.watch.communitywatch", Context.MODE_PRIVATE);
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		
		
		name = prefs.getString("NAME", "");
		email = prefs.getString("EMAIL", "");
		
		nameEdit = (EditText) findViewById(R.id.registerName);
		emailEdit = (EditText) findViewById(R.id.registerEmail);
		
		nameEdit.setText(name);
		emailEdit.setText(email);
		
		register = (Button) findViewById(R.id.btnSend);
		
		register.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				// Save info
				String newName = nameEdit.getText().toString();
				String newEmail = emailEdit.getText().toString();
				
				prefs.edit().putString("NAME", newName);
				prefs.edit().putString("EMAIL", newEmail);
				
				Intent i = new Intent(getApplicationContext(),
						DashboardActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	
}
