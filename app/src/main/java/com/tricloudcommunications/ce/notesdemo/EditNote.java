package com.tricloudcommunications.ce.notesdemo;

import android.content.Intent;
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

public class EditNote extends AppCompatActivity {

    int selectedNote = -1;
    EditText editNoteText;
    TextView actionTextView;


    public void editNote(){

        if (selectedNote != -1){


            actionTextView.setText("Edit Note");
            editNoteText.setText(MainActivity.myNotesList.get(selectedNote));
            Log.i("List Item Text", MainActivity.myNotesList.get(selectedNote));

            editNoteText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    Log.i("On Text Changed", s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



            //MainActivity.myNotesList.set(selectedNote, "some Shit");
            //MainActivity.arrayAdapter.notifyDataSetChanged();



        }else {

            actionTextView.setText("Add Note");
            Log.i("List Item Text", "No Text To Edit");

            editNoteText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    Log.i("On Text Changed", s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            //MainActivity.myNotesList.add("some Shit");
            //MainActivity.arrayAdapter.notifyDataSetChanged();
        }

    }

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

        editNoteText.setVisibility(View.VISIBLE);

        editNote();

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
