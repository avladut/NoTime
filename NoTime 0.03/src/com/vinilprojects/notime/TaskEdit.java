package com.vinilprojects.notime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.vinilprojects.databasecontrol.TasksContentProvider;
import com.vinilprojects.databasecontrol.TasksDb;
 
public class TaskEdit extends Activity implements OnClickListener{
 
	private Spinner stateList, importanceList;
	 private Button save, delete, deadlineDate, deadlineTime;
	 private String mode;
	 private EditText title, details, tags, path;
	 private String id;
 //..............................Time selection related methods and fields.........
	 
	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar myCalendar = Calendar.getInstance();
		
		
		
		DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
		myCalendar.set(Calendar.YEAR, year);
		myCalendar.set(Calendar.MONTH, monthOfYear);
		myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		updateLabel();
		}
		};

		TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			myCalendar.set(Calendar.MINUTE, minute);
			updateLabel();
		}
		};
		private void updateLabel() {
			                 
			 dateFormat.format(myCalendar.getTime());
		}

//.............................end of time selection portion.......................	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_edit);
	 
	  // get the values passed to the activity from the calling activity
	  // determine the mode - add, update or delete
	  if (this.getIntent().getExtras() != null){
	   Bundle bundle = this.getIntent().getExtras();
	   mode = bundle.getString("mode");
	  }
	 
	  // get references to the buttons and attach listeners
	  save = (Button) findViewById(R.id.save);
	  save.setOnClickListener(this);
	  delete = (Button) findViewById(R.id.delete);
	  delete.setOnClickListener(this);
	  deadlineDate = (Button) findViewById(R.id.deadline_date_button);
	  deadlineDate.setOnClickListener(this);
	  deadlineTime = (Button) findViewById(R.id.deadline_time_button);
	  deadlineTime.setOnClickListener(this);
	 
	  title = (EditText) findViewById(R.id.title_fill);
	  path = (EditText) findViewById(R.id.path_fill);
	  details = (EditText) findViewById(R.id.details_fill);
	  tags = (EditText) findViewById(R.id.tags_fill);
	  
	// create a dropdown for users to select various options for state and importance
	  stateList = (Spinner) findViewById(R.id.state_list);
	  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	    R.array.state_array, android.R.layout.simple_spinner_item);
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  stateList.setAdapter(adapter);
	  
	  importanceList = (Spinner) findViewById(R.id.importance_list);
	  ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
	    R.array.importance_array, android.R.layout.simple_spinner_item);
	  adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  importanceList.setAdapter(adapter1);
	 
	  // if in add mode disable the delete option
	  if(mode.trim().equalsIgnoreCase("add")){
	   delete.setEnabled(false);
	  }
	  // get the rowId for the specific task
	  else{
	   Bundle bundle = this.getIntent().getExtras();
	   id = bundle.getString("rowId");
	   loadTaskInfo();
	  }
	 }
	  
	  public void onClick (View v) {
		  
		  // get values from the spinner and the input text fields
		  String myState = stateList.getSelectedItem().toString();
		  String myImportance = importanceList.getSelectedItem().toString();
		  String myTitle = title.getText().toString();
		  String myPath = path.getText().toString();
		  String myDetails = details.getText().toString();
		  String myTags = tags.getText().toString();
		 
		  /*
		  
		  // check for blanks
		  if(myTitle.trim().equalsIgnoreCase("")){
		   Toast.makeText(getBaseContext(), "Please enter a title", Toast.LENGTH_LONG).show();
		   return;
		  }
		 
		  // check for blanks
		  if(myDetails.trim().equalsIgnoreCase("")){
		   Toast.makeText(getBaseContext(), "Please enter a  description", Toast.LENGTH_LONG).show();
		   return;
		  }
		  
		  // check for blanks
		  if(myTags.trim().equalsIgnoreCase("")){
		   Toast.makeText(getBaseContext(), "Please enter the tags", Toast.LENGTH_LONG).show();
		   return;
		  } */
	 
		  switch (v.getId()) {
		  case R.id.save:
		   ContentValues values = new ContentValues();
		   values.put(TasksDb.KEY_TITLE, myTitle);
		   values.put(TasksDb.KEY_DETAILS, myDetails);
		   values.put(TasksDb.KEY_PATH, myPath);
		   values.put(TasksDb.KEY_TAGS, myTags);
		   values.put(TasksDb.KEY_IMPORTANCE, myImportance);		   
		   values.put(TasksDb.KEY_STATE, myState);
		   values.put(TasksDb.KEY_DEADLINE, dateFormat.format(myCalendar.getTime()));
		   
		// insert a record
		   if(mode.trim().equalsIgnoreCase("add")){
		    getContentResolver().insert(TasksContentProvider.CONTENT_URI, values);
		   }
		   // update a record
		   else {
		    Uri uri = Uri.parse(TasksContentProvider.CONTENT_URI + "/" + id);
		    getContentResolver().update(uri, values, null, null);
		   }
		   finish();
		   break;
		   
		  case R.id.delete:
			   // delete a record
			   Uri uri = Uri.parse(TasksContentProvider.CONTENT_URI + "/" + id);
			   getContentResolver().delete(uri, null, null);
			   finish();
			   break;
			 
			   // More buttons go here (if any) ...
	//.......................Date and time buttons...........................		   
		  case R.id.deadline_date_button:
			  new DatePickerDialog(TaskEdit.this, d, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			  break;
		  case R.id.deadline_time_button:
			  new TimePickerDialog(TaskEdit.this, t, myCalendar
						.get(Calendar.HOUR_OF_DAY), myCalendar
						.get(Calendar.MINUTE), true).show();
	//.....................................................................		  
			  }
		  updateLabel();
			 }
	  
	  
	  // based on the rowId get all information from the Content Provider 
	  // about that task
	  private void loadTaskInfo(){
	  
	   String[] projection = { 
			   TasksDb.KEY_TITLE,
			   TasksDb.KEY_DETAILS,
			   TasksDb.KEY_PATH,
			   TasksDb.KEY_STATE,
			   TasksDb.KEY_IMPORTANCE,
			   TasksDb.KEY_TAGS ,
			   TasksDb.KEY_DEADLINE
			   };
	   Uri uri = Uri.parse(TasksContentProvider.CONTENT_URI + "/" + id);
	   Cursor cursor = getContentResolver().query(uri, projection, null, null,
	     null);
	   if (cursor != null) {
	    cursor.moveToFirst();
	    String myTitle = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_TITLE));
	    String myDetails = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_DETAILS));
	    String myPath = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_PATH));
	    String myTags = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_TAGS));
	    String mySate = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_STATE));
	    String myImportance = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_IMPORTANCE));
	    String myDeadline = cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_DEADLINE));
	    title.setText(myTitle); 
	    path.setText(myPath);
	    details.setText(myDetails);
	    tags.setText(myTags);
	    
	    
	    stateList.setSelection(getIndex(stateList, mySate));
	    importanceList.setSelection(getIndex(importanceList, myImportance));
	   
	  
	   }
	  }
	  
	  // this sets the spinner selection based on the value 
	  private int getIndex(Spinner spinner, String myString ){
	  
	   int index = 0;
	  
	   for (int i=0;i<spinner.getCount();i++){
	    if (spinner.getItemAtPosition(i).equals(myString)){
	     index = i;
	    }
	   }
	   return index;
	  }
	  
	 
	 
	  
	 }
	  
	  

