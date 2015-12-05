package com.example.mohamed.mynotes.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.asyncTasks.DeleteNoteTask;
import com.example.mohamed.mynotes.asyncTasks.UpdateNoteTask;
import com.example.mohamed.mynotes.commons.ui.LinedTextView;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.Date;


public class ViewNoteActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private Note note;
    private Context context;
    private NoteDatabaseOperations noteDatabaseOperations;
    private TextView noteTitle;
    private LinedTextView linedTextView;
    private Button back;
    private ImageButton options;
    private int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute;
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    Toast.makeText
                            (context, R.string.done, Toast.LENGTH_SHORT).show();
                    alarmHour = selectedHour;
                    alarmMinute = selectedMinute;
                    note.setAlarm(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                    UpdateNoteTask updateNoteTask = new UpdateNoteTask(noteDatabaseOperations, context);
                    updateNoteTask.execute(note);
                }
            };
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                    alarmYear = selectedYear;
                    alarmMonth = selectedMonth + 1;
                    alarmDay = selectedDay;
                    showDialog(CreateNoteActivity.TIME_DIALOG_ID);
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        init();
        setBackClick();
        setOptionsClick();
    }

    private void init(){
        note = (Note) getIntent().getExtras().getSerializable("note");
        context = ViewNoteActivity.this;
        noteDatabaseOperations = new NoteDatabaseOperations(context);
        linedTextView = (LinedTextView) findViewById(R.id.view_note_body);
        linedTextView.setMovementMethod(new ScrollingMovementMethod());
        noteTitle = (TextView) findViewById(R.id.view_note_title);
        linedTextView.setText(note.getNoteBody());
        noteTitle.setText(note.getNoteHeader());
        back = (Button) findViewById(R.id.view_back_button);
        options = (ImageButton) findViewById(R.id.options_button);
    }

    private void setBackClick(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ListNotesActivity.class));
            }
        });

    }

    private void setOptionsClick(){

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.setOnMenuItemClickListener(ViewNoteActivity.this);
                popupMenu.inflate(R.menu.pop_up_for_note_view);
                Menu menu;
                menu=popupMenu.getMenu();
                if(note.getAlarmYear()==0) menu.removeItem(R.id.menu_edit_reminder);
                else menu.removeItem(R.id.menu_set_reminder);
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.menu_edit_note){
            Intent intent = new Intent(context, EditNoteActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId()==R.id.menu_delete_note){
            DeleteNoteTask deleteNoteTask = new DeleteNoteTask(noteDatabaseOperations, context);
            deleteNoteTask.execute(note);
            startActivity(new Intent(context,ListNotesActivity.class));
            return true;
        }
        else if(item.getItemId()==R.id.menu_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, note.getNoteHeader());
            sendIntent.putExtra(Intent.EXTRA_TEXT, note.getNoteBody());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        else if(item.getItemId()==R.id.menu_edit_reminder || item.getItemId()==R.id.menu_set_reminder){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            if(item.getItemId()==R.id.menu_set_reminder) alertDialogBuilder.setTitle("set a reminder");
            else alertDialogBuilder.setTitle("edit reminder");
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("set", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            showDialog(CreateNoteActivity.DATE_DIALOG_ID);
                        }
                    })
                    .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }

                    })
                    .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, AlarmReceiverActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                    note.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
                            am.cancel(pendingIntent);
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return true;
        }
        else if(item.getItemId()==R.id.menu_details){
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CreateNoteActivity.TIME_DIALOG_ID:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        timePickerListener, alarmHour, alarmMinute, false);
                return timePickerDialog;

            case CreateNoteActivity.DATE_DIALOG_ID:
                DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener, alarmYear, alarmMonth, alarmDay);
                dialog.getDatePicker().setMinDate(new Date().getTime());
                return dialog;
        }
        return null;
    }
}
