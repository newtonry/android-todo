package com.fadetoproductions.rvkn.todo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditTodoFragment.EditTodoDialogListener {

    List<Todo> todos;
    TodoAdapter aTodoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        setupListeners();
        setupDatabase();
    }

    private void setupDatabase() {
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("todos_table.db").create();
        ActiveAndroid.initialize(dbConfiguration);
    }

    private void setupListeners() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTodoAtPosition(position);
                view.clearAnimation();
                return true;
            }
        });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Todo todo = todos.get(position);
                        showEditDialog(todo);
                    }
                }
        );
    }

    // This is the delegate method called when the dialogue is done
    @Override
    public void onFinishEditDialog(Boolean didSave) {
        if (didSave) {
            Toast.makeText(this, R.string.changes_were_saved, Toast.LENGTH_SHORT).show();
            aTodoAdapter.notifyDataSetChanged();
        }
    }

    public void deleteTodoAtPosition(int position) {
        Todo todo = todos.get(position);
        todo.delete();
        todos.remove(position);
        aTodoAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.completed_text, Toast.LENGTH_SHORT).show();
    }

    private void showEditDialog(Todo todo) {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoFragment editNameDialogFragment = EditTodoFragment.newInstance(todo);
        editNameDialogFragment.show(fm, "fragment_edit_todo");
    }

    public void populateArrayItems() {
        todos = Todo.getAll();
        aTodoAdapter = new TodoAdapter(this, todos);
    }

    public void onAddItem(View view) {
        String newTodoName = etEditText.getText().toString();

        if (newTodoName.equals("")) {
            Toast.makeText(this, R.string.need_task_name, Toast.LENGTH_SHORT).show();
            return;
        }

        Todo newTodo = new Todo(newTodoName, new Date(), "Normal");
        newTodo.name = newTodoName;
        newTodo.save();

        etEditText.setText("");
        aTodoAdapter.add(newTodo);
        aTodoAdapter.notifyDataSetChanged();
    }
}
