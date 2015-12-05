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
import com.example.mohamed.mynotes.activities.ViewNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class UpdateNoteTask extends AsyncTask<Note, String, Note> {
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public UpdateNoteTask(NoteDatabaseOperations noteDatabaseOperations, Context context) {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected Note doInBackground(Note... params) {

        // update into alarm
        if (params[0].getAlarmYear() != 0) {
            Calendar calendar = new GregorianCalendar();
            calendar.set(params[0].getAlarmYear(), params[0].getAlarmMonth()-1, params[0].getAlarmDay(),
                    params[0].getAlarmHour(), params[0].getAlarmMinute(), 0);
            Calendar cal = Calendar.getInstance();
            if (calendar.getTime().after(cal.getTime())) {
                Intent intent = new Intent(context, AlarmReceiverActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        params[0].getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);
            }
            else {
                Intent intent = new Intent(context, AlarmReceiverActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        params[0].getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
                am.cancel(pendingIntent);
                params[0].setAlarm(0, 0, 0, 0, 0);
            }
        }

        try {
            noteDataBaseOperations.updateNote(params[0]);
        } catch (Exception e) {
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while update note task " + e);
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(Note result) {
        try {
            Toast.makeText
                    (context, "note updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ViewNoteActivity.class);
            intent.putExtra("note", result);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while update note task " + e);
        }
    }
}
