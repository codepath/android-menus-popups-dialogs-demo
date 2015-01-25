package com.codepath.example.menuspopupsdialogsdemo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditNameDialog extends DialogFragment {
	private Button btnSubmit;
    private EditText mEditText;
    
    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditNameDialog newInstance(String title) {
        EditNameDialog frag = new EditNameDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
	            listener.onFinishEditDialog(mEditText.getText().toString());
	            dismiss();
			}
		});
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }
}
