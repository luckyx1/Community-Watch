package com.watch.communitywatch;

public class Members {
	//private variables
    int _id;
    String _name;
    String _message;
    String _email;
     
    // Empty constructor
    public Members(){
         
    }
    // constructor
    public Members(int id, String name, String _message, String _email){
        this._id = id;
        this._name = name;
        this._message = _message;
        this._email = _email;
    }
     
    // constructor
    public Members(String name, String _message, String _email){
        this._name = name;
        this._message = _message;
        this._email = _email;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String getName(){
        return this._name;
    }
     
    // setting name
    public void setName(String name){
        this._name = name;
    }
     
    // getting message
    public String getMessage(){
        return this._message;
    }
     
    // setting message
    public void setMessage(String _message){
        this._message = _message;
    }
    //getting email
    public String getEmail(){
    	return this._email;
    }
    //setting email
    public void setEmail(String email){
    	this._email = email;
    }

}
