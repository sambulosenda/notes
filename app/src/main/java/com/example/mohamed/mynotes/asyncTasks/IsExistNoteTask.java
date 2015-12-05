package com.example.mohamed.mynotes.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;


public class IsExistNoteTask extends AsyncTask<Note, String, Boolean>
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public IsExistNoteTask(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Note... params) {
        try{
            String header = params[0].getNoteHeader();
            return (noteDataBaseOperations.isNoteHeaderExists(header) ? true : false);
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while check is exist " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try{
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while check is exist task" + e);
        }
    }
}
