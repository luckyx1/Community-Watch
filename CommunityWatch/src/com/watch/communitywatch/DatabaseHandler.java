package com.watch.communitywatch;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "COMMUNITY";
 
    // Contacts table name
    private static final String Feed = "data";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EMAIL = "email";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        Log.d("info:", "calling oncreate");
		callDB(db);
		
	}
	
	public void callDB(SQLiteDatabase db){
		
		db.execSQL("DROP TABLE IF EXISTS " + Feed);
		String CREATE_COMMUNITY_TABLE = "CREATE TABLE " + Feed + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_MESSAGE + " TEXT," + KEY_EMAIL + " TEXT"+")";
		 Log.d("current: ",CREATE_COMMUNITY_TABLE);
		
        db.execSQL(CREATE_COMMUNITY_TABLE);
		
	}
	
	public void recreate_table(){
	    SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + Feed);
		 
        // Create tables again
        onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		recreate_table();
		

        
		
	}
	
	
	
	// Adding new contact
	public void addMember(Members member) {
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put(KEY_NAME, member.getName()); // Member Name
		    values.put(KEY_MESSAGE, member.getMessage()); // Member Message
		    values.put(KEY_EMAIL, member.getEmail()); // Member Email
		 
		    // Inserting Row
		    db.insert(Feed, null, values);
		    db.close(); // Closing database connection
	}
	 
	// Getting single contact
	public Members getMember(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(Feed, new String[] { KEY_ID,
	            KEY_NAME, KEY_MESSAGE, KEY_EMAIL }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Members member = new Members(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2), cursor.getString(3) );
	    // return member
	    return member;
	}
	 
	// Getting All Contacts
	public List<Members> getAllMembers() {
		List<Members> memberList = new ArrayList<Members>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + Feed;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Members member = new Members();
	            member.setID(Integer.parseInt(cursor.getString(0)));
	            member.setName(cursor.getString(1));
	            member.setMessage(cursor.getString(2));
	            member.setEmail(cursor.getString(3));
	            // Adding contact to list
	            memberList.add(member);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return memberList;
		}
	 
	// Getting contacts Count
	public int getMembersCount() {
		String countQuery = "SELECT  * FROM " + Feed;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	// Updating single contact
	public int updateMembers(Members member) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, member.getName());
	    values.put(KEY_MESSAGE, member.getMessage());
	    values.put(KEY_EMAIL, member.getMessage());

	 
	    // updating row
	    return db.update(Feed, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(member.getID()) });
	}
	 
	// Deleting single contact
	public void deleteMembers(Members member) {
		 SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(Feed, KEY_ID + " = ?",
		            new String[] { String.valueOf(member.getID()) });
		    db.close();
	}


}
