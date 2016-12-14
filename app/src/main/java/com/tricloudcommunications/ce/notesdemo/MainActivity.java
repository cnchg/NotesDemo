package com.tricloudcommunications.ce.notesdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView notesLV;
    static ArrayList<String> myNotesList;
    static ArrayAdapter arrayAdapter;
    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesLV = (ListView) findViewById(R.id.notesListView);

        myNotesList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myNotesList);
        notesLV.setAdapter(arrayAdapter);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.tricloudcommunications.ce.notesdemo", Context.MODE_PRIVATE);
        set = sharedPreferences.getStringSet("notes", null);



        myNotesList.add("Get New Lecture");

        notesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), EditNote.class);
                intent.putExtra("editAddNote", position);
                startActivity(intent);

                Log.i("List Item", myNotesList.get(position));

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_note) {

            Intent intent = new Intent(getApplicationContext(), EditNote.class);
            startActivity(intent);

            Log.i("Menu Option", "Add Note");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
