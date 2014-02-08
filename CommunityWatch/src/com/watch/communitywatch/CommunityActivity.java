package com.watch.communitywatch;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class CommunityActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community);
		DatabaseHandler db = new DatabaseHandler(this);
        
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting .."); 
        db.addMember(new Members("Ravi", "9100000000"));        
        db.addMember(new Members("Srinivas", "9199999999"));
        db.addMember(new Members("Tommy", "9522222222"));
        db.addMember(new Members("Karthik", "9533333333"));
         
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts.."); 
        List<Members> member = db.getAllMembers();       
         
        for (Members mb : member) {
            String log = "Id: "+mb.getID()+" ,Name: " + mb.getName() + " ,Message: " + mb.getMessage();
                // Writing Contacts to log
        Log.d("Name: ", log);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.community, menu);
		return true;
	}

}
