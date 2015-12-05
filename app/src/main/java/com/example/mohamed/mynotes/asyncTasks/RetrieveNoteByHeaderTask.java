package com.example.mohamed.mynotes.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;


public class RetrieveNoteByHeaderTask extends AsyncTask <String , String, Note>
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public RetrieveNoteByHeaderTask(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected Note doInBackground(String... params) {
        try{
            Note note = noteDataBaseOperations.retrieveNoteByHeader(params[0]);
            return note;
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while retrieve note by header task " + e);
        }
        return null;
    }
}
