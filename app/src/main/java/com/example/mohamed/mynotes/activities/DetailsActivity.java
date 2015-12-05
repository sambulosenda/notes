package com.example.mohamed.mynotes.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.models.Note;

public class DetailsActivity extends Activity {

    private Note note;
    private TextView creation, modification, alarm;
    private ImageButton back;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        setData();
    }
    private void init(){
        note = (Note) getIntent().getExtras().getSerializable("note");
        creation = (TextView) findViewById(R.id.fragment_details_note_creation);
        modification = (TextView) findViewById(R.id.fragment_details_note_modification);
        alarm = (TextView) findViewById(R.id.fragment_details_note_reminder);
        back = (ImageButton) findViewById(R.id.fragment_back);
        context = DetailsActivity.this;
    }
    private void setData (){
        String alarmStr;
        if(note.getAlarmYear()==0) alarmStr="you didn't set a reminder";
        else alarmStr = String.valueOf(note.getAlarmDay()) + "/" + String.valueOf(note.getAlarmMonth()) +
                "/" +String.valueOf(note.getAlarmYear()) + "    " + String.valueOf(note.getAlarmHour()) + ":" +
                String.valueOf(note.getAlarmMinute());

        String creationStr = String.valueOf(note.getCreationDay()) + "/" + String.valueOf(note.getCreationMonth()) +
                "/" +String.valueOf(note.getCreationYear()) + "    " + String.valueOf(note.getCreationHour()) + ":" +
                String.valueOf(note.getCreationMinute());

        String modificationStr;
        if(note.getModificationYear()==0) modificationStr="you didn't modify this note before";
        else modificationStr = String.valueOf(note.getModificationDay()) + "/" + String.valueOf(note.getModificationMonth()) +
                "/" +String.valueOf(note.getModificationYear()) + "    " + String.valueOf(note.getModificationHour()) + ":" +
                String.valueOf(note.getModificationMinute());

        alarm.setText(alarmStr);
        modification.setText(modificationStr);
        creation.setText(creationStr);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
//                Intent intent = new Intent(context, ViewNoteActivity.class);
//                intent.putExtra("note", note);
//                startActivity(intent);
            }
        });
    }
}