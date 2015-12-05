package com.example.mohamed.mynotes.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.ArrayList;


public class RetrieveAllNotesTask extends AsyncTask < ArrayList<Note>, String, ArrayList<Note> >
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public RetrieveAllNotesTask(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected ArrayList<Note> doInBackground(ArrayList<Note>... params) {
        try{
            params[0]=noteDataBaseOperations.retrieveNotesList();
            return params[0];
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while retrieving all notes task" + e);
        }
        return null;
    }
}
