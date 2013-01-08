package com.vinilprojects.notime;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.vinilprojects.databasecontrol.TasksContentProvider;
import com.vinilprojects.databasecontrol.TasksDb;

public class NoTimeListFragment extends Fragment 
				implements LoaderManager.LoaderCallbacks<Cursor> {
	
	

	 private SimpleCursorAdapter dataAdapter;
	 public static final int INSERT_ID = Menu.FIRST;
	
	
  
	 
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	setHasOptionsMenu(true);
   
   	
    displayListView(); 
    
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_no_time, container, false);
    return view;
    }
   
    public void createTask () {
      // starts a new Intent to add a Task
      Intent taskEdit = new Intent(getActivity().getBaseContext(), TaskEdit.class);
      Bundle bundle = new Bundle();
      bundle.putString("mode", "add");
      taskEdit.putExtras(bundle);
      startActivity(taskEdit);
     }
    

   
   
  
   public void onResume() {
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
      TasksDb.KEY_DEADLINE
      
    };
   
    // the XML defined views which the data will be bound to
    int[] to = new int[] { 
      R.id.task_title,
      R.id.task_details,
      R.id.task_path,
      R.id.task_state,
      R.id.task_importance,
      R.id.task_tags,
      R.id.task_deadline
         
    };
   
    // create an adapter from the SimpleCursorAdapter
    dataAdapter = new SimpleCursorAdapter(
      getActivity().getApplicationContext(), 
      R.layout.row, 
      null, 
      columns, 
      to,
      0);
   
 // get reference to the ListView
    ListView listView = (ListView) getView().findViewById(R.id.list);
    // Assign adapter to ListView
    listView.setAdapter(dataAdapter);
    //Ensures a loader is initialized and active.
    getLoaderManager().initLoader(0, null, this);
   
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
      Toast.makeText(getActivity().getApplicationContext(),
        taskCode, Toast.LENGTH_SHORT).show();
   
      String rowId = 
       cursor.getString(cursor.getColumnIndexOrThrow(TasksDb.KEY_ROWID));
       
      // starts a new Intent to update/delete a Task
      // pass in row Id to create the Content URI for a single row
      Intent taskEdit = new Intent(getActivity().getBaseContext(), TaskEdit.class);
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
      TasksDb.KEY_DEADLINE
      };
    CursorLoader cursorLoader = new CursorLoader(getActivity(),
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
   public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {  
	   menu.add (0, INSERT_ID, 0, R.string.menu_insert);      
       super.onCreateOptionsMenu(menu, inflater);
   }
  
   
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item){
   	switch (item.getItemId()){
   	case INSERT_ID:
   		createTask();
   		return true;
   	}
   	return super.onOptionsItemSelected(item);
   }
	
	
	



	



/*
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	*/
	

}
