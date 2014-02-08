/**
 * 
 */
package com.watch.communitywatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MessageActivity extends Activity {
	SharedPreferences prefs = this.getSharedPreferences(
		      "com.watch.communitywatch", Context.MODE_PRIVATE);
	
	EditText messageEdit;
	Button sendBtn;
	TextView heading;
	
	String name;
	String message;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newmessage);
		
		heading = (TextView) findViewById(R.id.newMessageName);
		sendBtn = (Button) findViewById(R.id.btnSend);
		messageEdit = (EditText) findViewById(R.id.maessageText);
		
		name = prefs.getString("NAME", "");
		heading.setText(name);
		
		sendBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				// Save info
				message = messageEdit.getText().toString();
				
				// Send the message
				
				
				Intent i = new Intent(getApplicationContext(),
						DashboardActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	
}
