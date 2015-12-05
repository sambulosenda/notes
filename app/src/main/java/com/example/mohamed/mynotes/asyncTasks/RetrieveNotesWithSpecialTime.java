package com.example.mohamed.mynotes.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RetrieveNotesWithSpecialTime extends AsyncTask < ArrayList<Integer>, String, ArrayList<Note> >
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public RetrieveNotesWithSpecialTime(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected ArrayList<Note> doInBackground(ArrayList<Integer>... params) {
        ArrayList<Note> res = new ArrayList<>();
        try{
            /*
            /*
date.add(Integer.valueOf(new Date().getYear()));
        date.add(Integer.valueOf(new Date().getMonth()));
        date.add(Integer.valueOf(new Date().getDay()));
        date.add(Integer.valueOf(new Date().getHours()));
        date.add(Integer.valueOf(new Date().getMinutes()));
 */
            Calendar cal = Calendar.getInstance();
           res = noteDataBaseOperations.retrieveNotesByAlarm(new Date().getYear()+1900,
                   (new Date().getMonth()) + 1, cal.get(Calendar.DAY_OF_MONTH), new Date().getHours(),
                   new Date().getMinutes());


        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while retrieve notes by special time task " + e);
        }
        return res;
    }
}
