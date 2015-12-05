package com.example.mohamed.mynotes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.commons.ui.AlarmListingItemView;
import com.example.mohamed.mynotes.commons.ui.NoteListingItemView;
import com.example.mohamed.mynotes.models.Note;

import java.util.List;

public class ListingAdapter extends ArrayAdapter<Note> {

    private List<Note> notes;
    private Context context;
    private int ID;
    public ListingAdapter(Context context, List<Note> notes, int id) {
        super(context, id, notes);
        ID=id;
        this.notes = notes;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            View itemView = convertView;
            if (itemView == null) itemView = LayoutInflater.from(getContext()).inflate(ID, parent, false);
            Note currentNote = getItem(position);
            if(ID == R.layout.single_note_view){
                NoteListingItemView noteListingItemView = new NoteListingItemView(context, itemView);
                noteListingItemView.bind(currentNote);
                return itemView;
            }
            else{
                AlarmListingItemView alarmListingItemView = new AlarmListingItemView(context, itemView);
                alarmListingItemView.bind(currentNote);
                return itemView;
            }
        }
        catch(Exception e){
            Log.e(CreateNoteActivity.LOGGER_TAG, "error in adapter : " + e);
        }
        return null;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return (notes != null && !notes.isEmpty() ? notes.get(position) : null);
    }
}
