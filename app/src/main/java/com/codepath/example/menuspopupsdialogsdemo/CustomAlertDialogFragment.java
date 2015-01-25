package com.codepath.example.menuspopupsdialogsdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CustomAlertDialogFragment extends DialogFragment {
    public interface CustomAlertListener {
        void onOKButton();
        void onCancelButton();
    }
    
    private CustomAlertListener listener;
	
	public CustomAlertDialogFragment() {
		// Empty constructor required for DialogFragment
	}

	public static CustomAlertDialogFragment newInstance(String title) {
		CustomAlertDialogFragment frag = new CustomAlertDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage("Are you sure?");
		listener = (CustomAlertListener) getActivity();
		alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
		        listener.onOKButton();
				dialog.dismiss();
			}
		});
		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
		        listener.onCancelButton();
				dialog.dismiss();
			}
		});
		return alertDialogBuilder.create();
	}
}
