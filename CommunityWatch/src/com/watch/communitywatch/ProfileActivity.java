package com.watch.communitywatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends Activity {
	Button register;
	EditText nameEdit;
	EditText emailEdit;
	
	String email;
	String name;
	
	SharedPreferences prefs;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		prefs = this.getSharedPreferences("com.watch.communitywatch", 0);
		
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
				
				Log.d("PROFILE", "Name is: "+newName);
				Log.d("PROFILE", "Email is: "+newEmail);
				
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("NAME", newName);
				editor.putString("EMAIL", newEmail);
				editor.commit();
				
				Log.d("PROFILE-CHECK", "Current pref name: "+ prefs.getString("NAME", ""));
				Log.d("PROFILE-CHECK", "Current pref email: "+ prefs.getString("EMAIL", ""));
				
				Intent i = new Intent(getApplicationContext(),
						DashboardActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	
}
