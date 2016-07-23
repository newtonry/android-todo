package com.fadetoproductions.rvkn.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private final int EDIT_REQUEST_CODE = 20;  // Not going to use this here
    private final int EDIT_SUCCESS_CODE = 200;

    String item;
    EditText etEditText;
    int itemPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = getIntent().getStringExtra("item");
        itemPosition = getIntent().getIntExtra("itemPosition", -1);
        etEditText = (EditText) findViewById(R.id.etEditText);
        etEditText.setText(item);
    }

    public void onSaveEdit(View view) {
        String newItem = etEditText.getText().toString();
        Intent data = new Intent();
        data.putExtra("item", newItem);
        data.putExtra("itemPosition", itemPosition);
        setResult(EDIT_SUCCESS_CODE, data);
        this.finish();
    }
}
