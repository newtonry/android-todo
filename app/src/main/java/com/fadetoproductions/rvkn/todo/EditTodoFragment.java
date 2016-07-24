package com.fadetoproductions.rvkn.todo;

/**
 * Created by rnewton on 7/19/16.
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class EditTodoFragment extends DialogFragment {

    private Button btnSave;
    private EditText etTodoName;
    private Spinner spnnrTaskPriority;
    private DatePicker dpDueDate;
    public Todo todo;


    public EditTodoFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditTodoDialogListener {
        void onFinishEditDialog(Boolean didSave);
    }

    public static EditTodoFragment newInstance(Todo todo) {
        EditTodoFragment frag = new EditTodoFragment();
        // Is there any reason why we shouldn't do this over creating a new bundle and putting args in there?
        frag.todo = todo;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_todo, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnSave = (Button) view.findViewById(R.id.btnSave);
        etTodoName = (EditText) view.findViewById(R.id.etTodoName);
        spnnrTaskPriority = (Spinner) view.findViewById(R.id.spnnrTaskPriority);
        dpDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);

        populateFields();

        etTodoName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
                getCurrentValuesAndSaveTodo();
                listener.onFinishEditDialog(true);
                dismiss();
            }
        });
    }

    private void getCurrentValuesAndSaveTodo() {
        todo.name = etTodoName.getText().toString();
        todo.priority = spnnrTaskPriority.getSelectedItem().toString();

        int year = dpDueDate.getYear();
        int month = dpDueDate.getMonth();
        int day = dpDueDate.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        todo.dueDate = calendar.getTime();

        todo.save();
    }

    private void populateFields() {
        // Setup the task name
        etTodoName.setText(todo.name);

        // Setup the due date
        // Is there a better way to do all this date stuff? Seems overly complex to pass data between a Date, Calendar, and DatePicker
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todo.dueDate);
        dpDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Setup the dropdown spinner
        // Is there a better way to do this? Seems kind of fragile
        List<String> prioritiesArray = Arrays.asList(getResources().getStringArray(R.array.priorities_array));
        int indexOfOption = prioritiesArray.indexOf(todo.priority);
        spnnrTaskPriority.setSelection(indexOfOption);


    }

}
