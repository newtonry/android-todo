package com.fadetoproductions.rvkn.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditTodoFragment.EditTodoDialogListener {

    private final int EDIT_REQUEST_CODE = 20;
    private final int EDIT_SUCCESS_CODE = 200;

    ArrayList<String> items;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        setupListeners();


        Log.d("db", "setting up db");

        setupDatabase();

        Log.d("db", "creating todo");

        Todo newTodo = new Todo();
        newTodo.name = "some neweeeeeer name";
        newTodo.dueDate = new Date();
        newTodo.priority = 1;
        newTodo.save();

        List<Todo> todos = Todo.getAll();

        for (int i = 0; i < todos.size(); i++) {
            Log.d("db", todos.get(i).name);
        }
    }


    private void setupDatabase() {
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("todos_table.db").create();
        ActiveAndroid.initialize(dbConfiguration);
    }

    private void setupListeners() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        String item = items.get(pos);
                        showEditDialog();
//                        launchEditActivity(item, pos);
                    }
                }
        );
    }

    // Launches the edit activity
    public void launchEditActivity(String item, int pos) {
        Intent launchEditActivity = new Intent(MainActivity.this, EditItemActivity.class);
        launchEditActivity.putExtra("item", item);
        launchEditActivity.putExtra("itemPosition", pos);
        startActivityForResult(launchEditActivity, EDIT_REQUEST_CODE);
    }

    // This is called when another activity finishes.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_REQUEST_CODE && resultCode == EDIT_SUCCESS_CODE) {
            String newItem = data.getExtras().getString("item");
            int itemPosition = data.getExtras().getInt("itemPosition");
            items.set(itemPosition, newItem);
            writeItems();
            aToDoAdapter.notifyDataSetChanged();
        } else {
            // There really shouldn't be anything else in the app
        }
    }

    // This is the delegate method called when the dialogue is done
    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }







    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoFragment editNameDialogFragment = EditTodoFragment.newInstance("Edit Todo Item");
        editNameDialogFragment.show(fm, "fragment_edit_todo");
    }



    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        aToDoAdapter = new ArrayAdapter<String>(this, R.layout.todo_list_item, items);
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(file, items);

        } catch (IOException e) {

        }
    }
}
