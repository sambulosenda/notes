package com.example.mohamed.mynotes.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class RetrieveSortedReminders extends AsyncTask < ArrayList<Note>, String, ArrayList<Note> >
{
    private NoteDatabaseOperations noteDataBaseOperations;
    private Context context;

    public RetrieveSortedReminders(NoteDatabaseOperations noteDatabaseOperations, Context context)
    {
        this.noteDataBaseOperations = noteDatabaseOperations;
        this.context = context;
    }

    @Override
    protected ArrayList<Note> doInBackground(ArrayList<Note>... params) {
        ArrayList<Note>res = new ArrayList<>();
        ArrayList<Note> temp = new ArrayList<>();

        try{

            temp=noteDataBaseOperations.retrieveNotesList();

            /*int size = params[0].size();

            for(int j=0;j<size;j++){

                Calendar calendar = new GregorianCalendar();
                calendar.set(params[0].get(0).getAlarmYear(),((params[0].get(0).getAlarmMonth())-1),
                        params[0].get(0).getAlarmDay(),params[0].get(0).getAlarmHour(), params[0].get(0).getAlarmMinute(),0);
                long min = calendar.getTimeInMillis();
                int minIndex = 0;

                for(int i=0;i<params[0].size();i++){
                    calendar = new GregorianCalendar();
                    calendar.set(params[0].get(i).getAlarmYear(),((params[0].get(i).getAlarmMonth())-1),
                            params[0].get(i).getAlarmDay(),params[0].get(i).getAlarmHour(), params[0].get(i).getAlarmMinute(),0);
                    long cal = calendar.getTimeInMillis();
                    if(cal<min){
                        min=cal;
                        minIndex=i;
                    }
                    res.add(params[0].get(minIndex));
                    params[0].remove(minIndex);
                }
            }
            return res;*/
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while retrieving sorted reminders task " + e);
        }
        params[0]=new ArrayList<>();

        for(int i=0;i<temp.size();i++) if(temp.get(i).getAlarmYear()!=0) params[0].add(temp.get(i));
        int size=params[0].size();

        for(int i=0;i<size;i++){
            int minIndex=0;
            Note min = params[0].get(0);
            Calendar minCalendar = new GregorianCalendar();
            minCalendar.set(min.getAlarmYear(),min.getAlarmMonth(),
                    min.getAlarmDay(),min.getAlarmHour(),min.getAlarmMinute(),0);
            for(int j=0;j<params[0].size();j++){
                Calendar calendar = new GregorianCalendar();
                calendar.set(params[0].get(j).getAlarmYear(),params[0].get(j).getAlarmMonth(),
                        params[0].get(j).getAlarmDay(),params[0].get(j).getAlarmHour(),params[0].get(j).getAlarmMinute(),0);
                if(calendar.getTime().before(minCalendar.getTime())){
                    min=params[0].get(j);
                    minIndex=j;
                }
            }
            res.add(min);
            params[0].remove(minIndex);
        }
        params[0].clear();
        params[0] = new ArrayList<>();
        for(int j=0;j<res.size();j++){
            Calendar calendar = new GregorianCalendar();
            calendar.set(res.get(j).getAlarmYear(),res.get(j).getAlarmMonth()-1,
                    res.get(j).getAlarmDay(),res.get(j).getAlarmHour(),res.get(j).getAlarmMinute()+3,0);
            Calendar cal = Calendar.getInstance();
            if(calendar.getTime().after(cal.getTime())) params[0].add(res.get(j));
            else res.get(j).setAlarm(0, 0, 0, 0, 0);
        }
        return params[0];
    }
}
