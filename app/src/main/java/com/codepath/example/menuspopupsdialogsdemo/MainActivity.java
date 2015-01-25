package com.codepath.example.menuspopupsdialogsdemo;

import java.util.ArrayList;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.codepath.example.menuspopupsdialogsdemo.CustomAlertDialogFragment.CustomAlertListener;
import com.codepath.example.menuspopupsdialogsdemo.EditNameDialog.EditNameDialogListener;

public class MainActivity extends FragmentActivity implements EditNameDialogListener,
		CustomAlertListener {
	
	private ArrayList<String> items;
	private ArrayAdapter<String> adapterItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		populateListView();
	}

	public void populateListView() {
		items = new ArrayList<String>();
		items.add("First");
		items.add("Second");
		items.add("Third");
		items.add("Fourth");
		items.add("Fifth");
		adapterItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		ListView lvItems = (ListView) findViewById(R.id.lvItems);
		lvItems.setAdapter(adapterItems); 
		// Setup contextual action mode when item is clicked
	    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	if (currentActionMode != null) { return; }
	        currentListItemIndex = position;
	        currentActionMode = startActionMode(modeCallBack);
	        view.setSelected(true);
	      }
	    });
	}

	public void onShowDialogWindow(View v) {
		FragmentManager fm = getSupportFragmentManager();
		EditNameDialog editNameDialog = EditNameDialog.newInstance("Type your name");
		editNameDialog.show(fm, "fragment_edit_name");
	}

	public void onShowAlertDialog(View v) {
		FragmentManager fm = getSupportFragmentManager();
		CustomAlertDialogFragment alertDialog = CustomAlertDialogFragment.newInstance("Some title");
		alertDialog.show(fm, "fragment_alert");
	}

	public void onShowPopupWindow(View v) {
		PopupWindow popup = new PopupWindow(MainActivity.this);
		View layout = getLayoutInflater().inflate(R.layout.popup_window, null);
		popup.setContentView(layout);
		// Set content width and height
		popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		// Closes the popup window when touch outside of it - when looses focus
		popup.setOutsideTouchable(true);
		popup.setFocusable(true);
		// Show anchored to button
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.showAsDropDown(v);
	}

	public void onShowPopupMenu(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		// Inflate the menu from xml
		popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
		// Setup menu item selection
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.menu_more:
					Toast.makeText(MainActivity.this, "More!", Toast.LENGTH_SHORT).show();
					return true;
				case R.id.menu_hide:
					Toast.makeText(MainActivity.this, "Hide!", Toast.LENGTH_SHORT).show();
					return true;
				default:
					return false;
				}
			}
		});
		// Show the menu
		popup.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFinishEditDialog(String inputText) {
		Toast.makeText(this, "Your name is: " + inputText, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onOKButton() {
		Toast.makeText(this, "Pressed OK!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCancelButton() {
		Toast.makeText(this, "Pressed Cancel!", Toast.LENGTH_SHORT).show();
	}

	// Tracks current contextual action mode
	private ActionMode currentActionMode;
	// Tracks current menu item
	private int currentListItemIndex;
	// Define the callback when ActionMode is activated
	private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle("Remove Item");
			mode.getMenuInflater().inflate(R.menu.menu_item, menu);
			return true;
		}

		// Called each time the action mode is shown.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_delete:
				items.remove(currentListItemIndex);
				adapterItems.notifyDataSetChanged();
				mode.finish(); // Action picked, so close the contextual menu
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			currentActionMode = null; // Clear current action mode
		}
	};

}
