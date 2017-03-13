package com.mad.notekeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Note> notes;
    //AppAdapter adapter;
    static DatabaseDataManager dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dm = new DatabaseDataManager(this);
        notes = new ArrayList<Note>();
        notes = dm.getAllNote();
        listView = (ListView) findViewById(R.id.listview1);
        listView.setClickable(true);
        listView.setLongClickable(true);
        NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,notes);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        final Spinner sp = (Spinner) findViewById(R.id.spinner);

        findViewById(R.id.btn_ADD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                EditText et_in = (EditText) findViewById(R.id.et_input);
                note.setText(String.valueOf(et_in.getText()));
                note.setPriority((String) sp.getSelectedItem());
                et_in.setText("");
                String status = "PENDING";
                note.setStatus("PENDING");
                //Date date =new Date();

                note.setUpdate_time(System.currentTimeMillis()+"");

                //String updated_time = new PrettyTime(new Locale("")).format(date);
                Log.d("demo", note.toString());
                //notes.add(note);
                //Note note = new Note(text,priority,updated_time,status);
                dm.saveNote(note);
                notes = dm.getAllNote();
                NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,notes);
                adapter.setNotifyOnChange(true);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                Toast.makeText(MainActivity.this, "Long Clicked", Toast.LENGTH_SHORT).show();
                builder.setMessage("Do you really want to delete the task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Note note = notes.get(position);
                                dm.deleteNote(note);
                                notes = dm.getAllNote();
                                NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,notes);
                                adapter.setNotifyOnChange(true);
                                listView.setAdapter(adapter);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_show_all) {
            Toast.makeText(this, "Show all clicked", Toast.LENGTH_SHORT).show();
            notes = dm.getAllNote();
            Log.d("DEMO",notes.toString());
            NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,notes);
            adapter.setNotifyOnChange(true);
            listView.setAdapter(adapter);

        }
        if(item.getItemId()==R.id.action_complete) {
            Toast.makeText(this, "Completed clicked", Toast.LENGTH_SHORT).show();
            notes = dm.getAllNote();
            ArrayList<Note> arrayList = new ArrayList<Note>();
            for(int i=0;i<notes.size();i++){
                if(notes.get(i).getStatus().equals("COMPLETED")){
                    arrayList.add(notes.get(i));
                }
            }
            Log.d("DEMO",arrayList.toString());
            NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,arrayList);
            adapter.setNotifyOnChange(true);
            listView.setAdapter(adapter);


        }
        if(item.getItemId()==R.id.action_pending) {
            Toast.makeText(this, "Pending clicked", Toast.LENGTH_SHORT).show();
            notes = dm.getAllNote();
            ArrayList<Note> arrayList = new ArrayList<Note>();
            for(int i=0;i<notes.size();i++){
                if(notes.get(i).getStatus().equals("PENDING")){
                    arrayList.add(notes.get(i));
                }
            }
            Log.d("DEMO",arrayList.toString());
            NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,arrayList);
            adapter.setNotifyOnChange(true);
            listView.setAdapter(adapter);
        }
        if(item.getItemId()==R.id.action_sort_by_time) {
            Toast.makeText(this, "Sort Time clicked", Toast.LENGTH_SHORT).show();
            //Sort for time
            notes = dm.getAllNote();
            Collections.sort(notes,Note.dateComparator);

            NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,notes);
            adapter.setNotifyOnChange(true);
            listView.setAdapter(adapter);

        }
        if(item.getItemId()==R.id.action_sort_by_priority) {
            Toast.makeText(this, "Sort Priority clicked", Toast.LENGTH_SHORT).show();
            notes = dm.getAllNote();
            Collections.sort(notes,Note.priorityComparator);

            NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.row_layout,notes);
            adapter.setNotifyOnChange(true);
            listView.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }

}
