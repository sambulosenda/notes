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
import com.example.mohamed.mynotes.activities.ListNotesActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class SaveNoteTask extends AsyncTask<Note, String, Boolean>
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public SaveNoteTask(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Note... params) {
        try{
            // add to alarm
            if(params[0].getAlarmYear()!=0){
                Calendar calendar = new GregorianCalendar();
                calendar.set(params[0].getAlarmYear(),((params[0].getAlarmMonth()-1)), params[0].getAlarmDay(),
                        params[0].getAlarmHour(), params[0].getAlarmMinute(),0);
                Calendar cal = Calendar.getInstance();

                if(calendar.getTime().after(cal.getTime())){
                    Intent intent = new Intent(context, AlarmReceiverActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            params[0].getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            pendingIntent);
                }
                else{
                    params[0].setAlarm(0,0,0,0,0);
                }
            }

            //save note
            return (noteDataBaseOperations.addNote(params[0]) ? true : false);
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while save note task " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try{
            if(result)Toast.makeText
                    (context, "note saved", Toast.LENGTH_SHORT).show();

            context.startActivity(new Intent(context, ListNotesActivity.class));
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while save note task " + e);
        }
    }
}
