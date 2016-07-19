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

import java.util.List;

public class TodoAdapter extends ArrayAdapter<Todo> {

    public TodoAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvTodoName = (TextView) convertView.findViewById(R.id.tvTodoName);
        // Populate the data into the template view using the data object
        tvTodoName.setText(todo.name);

        return convertView;
    }
}