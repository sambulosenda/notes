package com.example.mohamed.mynotes.asyncTasks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mohamed.mynotes.activities.AlarmReceiverActivity;
import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;


public class DeleteNoteTask extends AsyncTask<Note, String, Boolean>
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public DeleteNoteTask(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Note... params) {
        try{
            // delete from alarm
            if(params[0].getAlarmYear()!=0){
                Intent intent = new Intent(context, AlarmReceiverActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        params[0].getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
                am.cancel(pendingIntent);
            }

            //delete note
            return (noteDataBaseOperations.deleteNote(params[0]) ? true : false);
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while deleting " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try{
            if(result)Toast.makeText
                    (context, "note deleted", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while deleting task" + e);
        }
    }
}
