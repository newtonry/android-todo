/**
 * Created by rnewton on 7/16/16.
 */


package com.fadetoproductions.rvkn.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    public ToDoItemAdapter(Context context, ArrayList<ToDoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem todoItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvTodoItemName = (TextView) convertView.findViewById(R.id.tvTodoItemName);
        // Populate the data into the template view using the data object
        tvTodoItemName.setText(todoItem.getName());

        return convertView;
    }
}