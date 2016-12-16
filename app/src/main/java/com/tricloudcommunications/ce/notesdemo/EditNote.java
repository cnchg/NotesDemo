package com.tricloudcommunications.ce.notesdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;

public class EditNote extends AppCompatActivity {

    int selectedNote = -1;
    String noteType = "";
    EditText editNoteText;
    TextView actionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editNoteText = (EditText) findViewById(R.id.editNoteText);
        actionTextView = (TextView) findViewById(R.id.actionTextView);

        Intent i = getIntent();
        selectedNote = i.getIntExtra("editAddNote", -1);
        noteType = i.getStringExtra("noteType");
        actionTextView.setText(noteType);

        editNoteText.setText(MainActivity.myNotesList.get(selectedNote));
        Log.i("List Item Text", MainActivity.myNotesList.get(selectedNote));

        editNoteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.i("On Text Changed", s.toString());
                MainActivity.myNotesList.set(selectedNote, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getSharedPreferences("com.tricloudcommunications.ce.notesdemo", Context.MODE_PRIVATE);

                if (MainActivity.set == null){

                    MainActivity.set = new HashSet<String>();

                }else {

                    MainActivity.set.clear();
                }

                MainActivity.set.addAll(MainActivity.myNotesList);
                sharedPreferences.edit().remove("notes").apply();
                sharedPreferences.edit().putStringSet("notes", MainActivity.set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Log.i("Selected Note Value", Integer.toString(selectedNote));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // When app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
