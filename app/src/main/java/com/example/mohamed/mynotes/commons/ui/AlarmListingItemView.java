package com.example.mohamed.mynotes.commons.ui;


import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.models.Note;

public class AlarmListingItemView extends RelativeLayout {

    private View itemView;
    private Context context;
    private TextView header, reminder;

    public AlarmListingItemView(Context context, View itemView) {
        super(context);
        this.context=context;
        this.itemView=itemView;
    }

    public void bind(Note note) {
        header = (TextView) itemView.findViewById(R.id.single_reminder_view_title);
        reminder = (TextView) itemView.findViewById(R.id.single_reminder_view_alarm);
        header.setText(note.getNoteHeader());
        String date = String.valueOf(note.getAlarmDay()) + "/" + String.valueOf(note.getAlarmMonth()) +
                "/" +String.valueOf(note.getAlarmYear()) + "    " + String.valueOf(note.getAlarmHour()) + ":" +
                String.valueOf(note.getAlarmMinute());
        reminder.setText(date);
    }
}
