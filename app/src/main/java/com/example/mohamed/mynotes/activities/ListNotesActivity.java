package com.example.mohamed.mynotes.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.adapters.ListingAdapter;
import com.example.mohamed.mynotes.asyncTasks.RetrieveAllNotesTask;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.ArrayList;


public class ListNotesActivity extends Activity {

    private Button reminders, newNote;
    private Context context;
    private NoteDatabaseOperations noteDatabaseOperations;
    private ArrayList<Note>notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        init();
        populateListView();
        itemClickCallback();
        addListenerOnButtons();
    }

    private void init(){
        reminders = (Button) findViewById(R.id.list_reminder_button);
        newNote = (Button) findViewById(R.id.Create_new_note1);
        context = ListNotesActivity.this;
        noteDatabaseOperations = new NoteDatabaseOperations(context);
        RetrieveAllNotesTask retrieveAllNotesTask = new RetrieveAllNotesTask(noteDatabaseOperations, context);
        try{
            notes = retrieveAllNotesTask.execute(notes).get();
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while listing notes : " + e);
        }
    }

    private void populateListView() {
        ArrayAdapter<Note> adapter = new ListingAdapter(context, notes, R.layout.single_note_view);
        GridView list = (GridView) findViewById(R.id.list_notes_grid_view);
        list.setAdapter(adapter);
    }
    private void itemClickCallback() {
        GridView list = (GridView) findViewById(R.id.list_notes_grid_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                Note clickedNote = notes.get(position);
                Intent intent = new Intent(context, ViewNoteActivity.class);
                intent.putExtra("note", clickedNote);
                startActivity(intent);
            }
        });
    }
    public void addListenerOnButtons(){
        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ListRemindersActivity.class));
            }

        });
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,CreateNoteActivity.class));
            }

        });
    }
}