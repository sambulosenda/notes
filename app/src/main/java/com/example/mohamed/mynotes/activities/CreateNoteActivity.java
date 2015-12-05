package com.example.mohamed.mynotes.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.asyncTasks.IsExistNoteTask;
import com.example.mohamed.mynotes.asyncTasks.SaveNoteTask;
import com.example.mohamed.mynotes.commons.ui.LinedEditText;
import com.example.mohamed.mynotes.commons.utils.StringsValidation;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.Calendar;
import java.util.Date;


public class CreateNoteActivity extends Activity {
    public static final String LOGGER_TAG = "LOGGER_TAG";
    public static final int TIME_DIALOG_ID = 999;
    public static final int DATE_DIALOG_ID = 0;
    private Context context;
    private LinedEditText noteBody;
    private EditText noteTitle;
    private Button saveButton, cancelButton, reminderButton;
    private NoteDatabaseOperations noteDatabaseOperations;
    private int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute;
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    alarmHour = selectedHour;
                    alarmMinute = selectedMinute;
                    Calendar cal = Calendar.getInstance();
                    //cal.get(Calendar.DAY_OF_MONTH)
                    final Note note = new Note(noteTitle.getText().toString(), noteBody.getText().toString(),
                            alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute, new Date().getYear()+1900, (new Date().getMonth()) + 1,
                            cal.get(Calendar.DAY_OF_MONTH), new Date().getHours(), new Date().getMinutes());
                    saveNote(note);
                }
            };
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                    alarmYear = selectedYear;
                    alarmMonth = selectedMonth + 1;
                    alarmDay = selectedDay;
                    showDialog(TIME_DIALOG_ID);
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        init();
        setSaveButton();
        setReminderButton();
        setCancelButton();
    }

    private void init() {
        noteBody = (LinedEditText) findViewById(R.id.create_note_body);
        noteTitle = (EditText) findViewById(R.id.create_note_title);
        saveButton = (Button) findViewById(R.id.create_save_button);
        cancelButton = (Button) findViewById(R.id.create_cancel_button);
        reminderButton = (Button) findViewById(R.id.create_reminder_button);
        context = CreateNoteActivity.this;
        noteDatabaseOperations = new NoteDatabaseOperations(context);
    }

    private void setCancelButton() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateNoteActivity.this);
                alertDialogBuilder.setTitle("Cancel");
                alertDialogBuilder.setMessage("Do you want to cancel ?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(context, ListNotesActivity.class));
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void setSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringsValidation.isEmptyString(noteTitle.getText().toString()))
                    Toast.makeText
                            (context, R.string.empty_title, Toast.LENGTH_SHORT).show();

                else if (StringsValidation.isEmptyString(noteBody.getText().toString()))
                    Toast.makeText
                            (context, R.string.empty_body, Toast.LENGTH_SHORT).show();

                else if ((!StringsValidation.isEmptyString(noteBody.getText().toString())) &&
                        (!StringsValidation.isEmptyString(noteTitle.getText().toString()))) {

                    //(int alarmYear, int alarmMonth, int alarmDay, int alarmHour, int alarmMinute,
                    //int creationYear, int creationMonth, int creationDay, int creationHour, int creationMinute)

                    Calendar cal = Calendar.getInstance();
                    //cal.get(Calendar.DAY_OF_MONTH)

                    final Note note = new Note(noteTitle.getText().toString(), noteBody.getText().toString(),
                            0, 0, 0, 0, 0, new Date().getYear()+1900, (new Date().getMonth()) + 1, cal.get(Calendar.DAY_OF_MONTH),
                            new Date().getHours(), new Date().getMinutes());
                    saveNote(note);
                }
            }
        });
    }

    private void setReminderButton() {
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringsValidation.isEmptyString(noteTitle.getText().toString()))
                    Toast.makeText
                            (context, R.string.empty_title, Toast.LENGTH_SHORT).show();

                else if (StringsValidation.isEmptyString(noteBody.getText().toString()))
                    Toast.makeText
                            (context, R.string.empty_body, Toast.LENGTH_SHORT).show();

                else if ((!StringsValidation.isEmptyString(noteBody.getText().toString())) &&
                        (!StringsValidation.isEmptyString(noteTitle.getText().toString()))) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateNoteActivity.this);
                    alertDialogBuilder.setTitle("set a reminder");
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("choose date and time", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    showDialog(DATE_DIALOG_ID);
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            }
        });
    }

    public void saveNote(final Note note) {
        try {
            IsExistNoteTask isExistNoteTask = new IsExistNoteTask(noteDatabaseOperations, context);
            if (isExistNoteTask.execute(note).get()) {
                Toast.makeText
                        (context, "title has been added before !", Toast.LENGTH_SHORT).show();
            } else {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Confirmation");
                alertDialogBuilder
                        .setMessage("do you want to save note ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SaveNoteTask saveNoteTask = new SaveNoteTask(noteDatabaseOperations, context);
                                saveNoteTask.execute(note);
                                dialog.cancel();
                                //MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }

        } catch (Exception e) {
            Log.e(LOGGER_TAG, "error while saving note : " + e);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        timePickerListener, alarmHour, alarmMinute, false);
                return timePickerDialog;

            case DATE_DIALOG_ID:
                DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener, alarmYear, alarmMonth, alarmDay);
                dialog.getDatePicker().setMinDate(new Date().getTime());
                return dialog;
        }
        return null;
    }

}
