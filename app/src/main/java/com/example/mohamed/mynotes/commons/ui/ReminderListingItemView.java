package com.example.mohamed.mynotes.commons.ui;


import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.models.Note;

public class ReminderListingItemView extends RelativeLayout {

    private View itemView;
    private Context context;
    private TextView header;
    private LinedTextView body;

    public ReminderListingItemView(Context context, View itemView) {
        super(context);
        this.context=context;
        this.itemView=itemView;
    }

    public void bind(Note note) {
        header = (TextView) itemView.findViewById(R.id.single_note_view_title);
        body = (LinedTextView) itemView.findViewById(R.id.single_note_view_body);
        header.setText(note.getNoteHeader());
        body.setText(note.getNoteBody());
    }
}
