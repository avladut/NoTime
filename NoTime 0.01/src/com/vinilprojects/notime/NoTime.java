package com.vinilprojects.notime;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.vinilprojects.databasecontrol.TasksContentProvider;
import com.vinilprojects.databasecontrol.TasksDb;

public class NoTime extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final int INSERT_ID = Menu.FIRST;
	 private SimpleCursorAdapter dataAdapter;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_time);
        displayListView(); 
    
   /* Button add = (Button) findViewById(R.id.add);
    add.setOnClickListener (new OnClickListener() {
   
     public void onClick(View v) {
      // starts a new Intent to add a Country
      Intent countryEdit = new Intent(getBaseContext(), TaskEdit.class);
      Bundle bundle = new Bundle();
      bundle.putString("mode", "add");
      countryEdit.putExtras(bundle);
      startActivity(countryEdit);
     }
    });*/
}
   
   
   
  
   protected void onResume() {
    super.onResume();
    //Starts a new or restarts an existing Loader in this manager
    getLoaderManager().restartLoader(0, null, this);
   }
   
   private void displayListView () {
   
   
    // The desired columns to be bound
    String[] columns = new String[] {
      TasksDb.KEY_TITLE,
      TasksDb.KEY_DETAILS,
      TasksDb.KEY_PATH,
      TasksDb.KEY_STATE,
      TasksDb.KEY_IMPORTANCE,
      TasksDb.KEY_TAGS,
      
    };
   
    // the XML defined views which the data will be bound to
    int[] to = new int[] { 
      R.id.task_title,
      R.id.task_details,
      R.id.task_path,
      R.id.task_state,
      R.id.task_importance,
      R.id.task_tags,
      
      
    };
   
    // create an adapter from the SimpleCursorAdapter
    dataAdapter = new SimpleCursorAdapter(
      this, 
      R.layout.row, 
      null, 
      columns, 
      to,
      0);
   
    // get reference to the ListView
    ListView listView = (ListView) findViewById(android.R.id.list);
    // Assign adapter to ListView
    listView.setAdapter(dataAdapter);
    //Ensures a loader is initialized and active.
    getLoaderManager().initLoader(0, null, this);
   
   
    listView.setOnItemClickListener(new OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> listView, View view, 
       int position, long id) {
      // Get the cursor, positioned to the corresponding row in the result set
      Cursor cursor = (Cursor) listView.getItemAtPosition(position);
   
      // display the selected task
      String taskCode = 
       cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_TITLE));
      Toast.makeText(getApplicationContext(),
        taskCode, Toast.LENGTH_SHORT).show();
   
      String rowId = 
       cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_ROWID));
       
      // starts a new Intent to update/delete a Task
      // pass in row Id to create the Content URI for a single row
      Intent taskEdit = new Intent(getBaseContext(), TaskEdit.class);
      Bundle bundle = new Bundle();
      bundle.putString("mode", "update");
      bundle.putString("rowId", rowId);
      taskEdit.putExtras(bundle);
      startActivity(taskEdit);
   
     }
    });
   
   }
   
   // This is called when a new Loader needs to be created.
   @Override
   public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String[] projection = { 
      TasksDb.KEY_ROWID,
      TasksDb.KEY_TITLE, 
      TasksDb.KEY_PATH, 
      TasksDb.KEY_DETAILS,
      TasksDb.KEY_STATE,
      TasksDb.KEY_IMPORTANCE,
      TasksDb.KEY_TAGS,
      };
    CursorLoader cursorLoader = new CursorLoader(this,
      TasksContentProvider.CONTENT_URI, projection, null, null, null);
    return cursorLoader;
   }
   
   @Override
   public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    // Swap the new cursor in.  (The framework will take care of closing the
          // old cursor once we return.)
          dataAdapter.swapCursor(data);
   }
   
   @Override
   public void onLoaderReset(Loader<Cursor> loader) {
    // This is called when the last Cursor provided to onLoadFinished()
    // above is about to be closed.  We need to make sure we are no
    // longer using it.
    dataAdapter.swapCursor(null);
   }
   
  
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       boolean result = super.onCreateOptionsMenu(menu);
       menu.add(0, INSERT_ID, 0, R.string.menu_insert);
       return result;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
       case INSERT_ID:
           createTask();
           return true;
       }
      
       return super.onOptionsItemSelected(item);
   }




private void createTask() {
	// TODO Auto-generated method stub
	Intent taskEdit = new Intent(getBaseContext(), TaskEdit.class);
    Bundle bundle = new Bundle();
    bundle.putString("mode", "add");
    taskEdit.putExtras(bundle);
    startActivity(taskEdit);
}
   
   
  }
