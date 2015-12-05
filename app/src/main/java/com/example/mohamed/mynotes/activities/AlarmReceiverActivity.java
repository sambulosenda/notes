package com.example.mohamed.mynotes.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.adapters.ListingAdapter;
import com.example.mohamed.mynotes.asyncTasks.RetrieveNotesWithSpecialTime;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.io.IOException;
import java.util.ArrayList;

public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer;
    private Button stopAlarm;
    private Context context;
    private ArrayList<Note>notes;
    private NoteDatabaseOperations noteDatabaseOperations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alarm);
        init();
        populateListView();
        itemClickCallback();
        stopAlarm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                mMediaPlayer.stop();
                startActivity(new Intent(context,ListRemindersActivity.class));
                finish();
                return false;
            }
        });

        playSound(this, getAlarmUri());
    }

    private void init(){
        stopAlarm = (Button) findViewById(R.id.stopAlarm);
        context = AlarmReceiverActivity.this;
        noteDatabaseOperations = new NoteDatabaseOperations(context);
        ArrayList<Integer>arr=new ArrayList<>();
        RetrieveNotesWithSpecialTime retrieveNotesWithSpecialTime = new RetrieveNotesWithSpecialTime(noteDatabaseOperations,context);
        try{
            notes=retrieveNotesWithSpecialTime.execute(arr).get();
        }
        catch(Exception e){

        }

    }

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }


    private void populateListView() {
        ArrayAdapter<Note> adapter = new ListingAdapter(context, notes, R.layout.single_reminder_view);
        ListView list = (ListView) findViewById(R.id.list_alarmss_list_view);
        list.setAdapter(adapter);
    }
    private void itemClickCallback() {
        ListView list = (ListView) findViewById(R.id.list_alarmss_list_view);
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
}
