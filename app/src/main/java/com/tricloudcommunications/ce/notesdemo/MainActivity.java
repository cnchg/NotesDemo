package com.tricloudcommunications.ce.notesdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView notesLV;
    static ArrayList<String> myNotesList;
    static ArrayAdapter arrayAdapter;
    static Set<String> set;
    static SharedPreferences sharedPreferences;

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

        sharedPreferences = this.getSharedPreferences("com.tricloudcommunications.ce.notesdemo", Context.MODE_PRIVATE);
        set = sharedPreferences.getStringSet("notes", null);

        myNotesList.clear();

        if (set != null){

            myNotesList.addAll(set);

        }else {

            myNotesList.add("Example Note");
            set = new HashSet<String>();
            set.addAll(myNotesList);
            sharedPreferences.edit().putStringSet("notes", set).apply();

        }

        notesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), EditNote.class);
                intent.putExtra("editAddNote", position);
                intent.putExtra("noteType", "Edit Note");
                startActivity(intent);

                Log.i("List Item", myNotesList.get(position));

            }
        });

        notesLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                myNotesList.remove(position);

                                sharedPreferences = MainActivity.this.getSharedPreferences("com.tricloudcommunications.ce.notesdemo", Context.MODE_PRIVATE);

                                if (set == null){

                                    set = new HashSet<String>();

                                }else {

                                    set.clear();
                                }

                                set.addAll(myNotesList);

                                sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
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

            myNotesList.add("");

            sharedPreferences = this.getSharedPreferences("com.tricloudcommunications.ce.notesdemo", Context.MODE_PRIVATE);

            if (set == null){

                set = new HashSet<String>();

            }else {

                set.clear();
            }

            set.addAll(myNotesList);
            arrayAdapter.notifyDataSetChanged();

            sharedPreferences.edit().remove("notes").apply();
            sharedPreferences.edit().putStringSet("notes", set).apply();


            Intent intent = new Intent(getApplicationContext(), EditNote.class);
            intent.putExtra("editAddNote", myNotesList.size() - 1);
            intent.putExtra("noteType", "Add Note");
            startActivity(intent);

            Log.i("Menu Option", "Add Note");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
