/**
 * Created by rnewton on 7/16/16.
 */


package com.fadetoproductions.rvkn.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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

        TextView tvTodoName = (TextView) convertView.findViewById(R.id.tvTodoName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView dueDate = (TextView) convertView.findViewById(R.id.tvDueDate);

        tvTodoName.setText(todo.name);
        tvPriority.setText(todo.priority);

        String dueDateString = new SimpleDateFormat("EEE, MMM d" ).format(todo.dueDate);
        dueDate.setText(dueDateString);

        setOnTouchFadeOutListenerOnView(convertView);
        return convertView;
    }

    private void setOnTouchFadeOutListenerOnView(View view) {
        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(1000);  // TODO set this to the default longpress time

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    view.startAnimation(fadeOut);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    view.setAlpha(1);
                    view.clearAnimation();
                }
                return false;
            }
        });
    }
}