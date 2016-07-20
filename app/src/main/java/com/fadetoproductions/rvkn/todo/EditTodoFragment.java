package com.fadetoproductions.rvkn.todo;

/**
 * Created by rnewton on 7/19/16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


public class EditTodoFragment extends DialogFragment {

    private Button btnSave;
    private EditText etTodoName;


    public EditTodoFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditTodoDialogListener {
        void onFinishEditDialog(String newName, Date newDueDate, int newPriority);
    }

    public static EditTodoFragment newInstance(int id, String name, Date dueDate, int priority) {
        EditTodoFragment frag = new EditTodoFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("id", id);
        args.putInt("priority", priority);
        // TODO putDate?
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




//        mEditText.setOnEditorActionListener(this);

        return inflater.inflate(R.layout.fragment_edit_todo, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        etTodoName = (EditText) view.findViewById(R.id.etTodoName);

        populateFields();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
                listener.onFinishEditDialog(
                        etTodoName.getText().toString(),
                        new Date(),
                        1
                );
                dismiss();
            }
        });

//        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
//        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void populateFields() {
        etTodoName.setText(getArguments().getString("name", ""));
    }

}
