package com.example.mohamed.mynotes.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.mynotes.R;
import com.example.mohamed.mynotes.asyncTasks.IsExistNoteTask;
import com.example.mohamed.mynotes.asyncTasks.UpdateNoteTask;
import com.example.mohamed.mynotes.commons.ui.LinedEditText;
import com.example.mohamed.mynotes.commons.utils.StringsValidation;
import com.example.mohamed.mynotes.database.NoteDatabaseOperations;
import com.example.mohamed.mynotes.models.Note;

import java.util.Calendar;
import java.util.Date;

public class EditNoteActivity extends Activity {

    private Note note;
    private Context context;
    private NoteDatabaseOperations noteDatabaseOperations;
    private EditText noteTitle;
    private LinedEditText noteBody;
    private Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        init();
        setCancelButton();
        setSaveButton();
    }
    private void init(){
        note = (Note) getIntent().getExtras().getSerializable("note");
        context = EditNoteActivity.this;
        noteDatabaseOperations = new NoteDatabaseOperations(context);
        noteBody = (LinedEditText) findViewById(R.id.edit_note_body);
        noteTitle = (EditText) findViewById(R.id.edit_note_title);
        noteBody.setText(note.getNoteBody());
        noteTitle.setText(note.getNoteHeader());
        save = (Button) findViewById(R.id.edit_save_button);
        cancel = (Button) findViewById(R.id.edit_cancel_button);
    }

    private void setCancelButton() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Cancel");
                alertDialogBuilder.setMessage("Do you want to cancel ?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(context, ViewNoteActivity.class);
                                intent.putExtra("note", note);
                                startActivity(intent);
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
        save.setOnClickListener(new View.OnClickListener() {
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
                    Calendar cal = Calendar.getInstance();
                    //cal.get(Calendar.DAY_OF_MONTH)
                    note.setModification(new Date().getYear()+1900, new Date().getMonth()+1, cal.get(Calendar.DAY_OF_MONTH),
                            new Date().getHours(), new Date().getMinutes());
                    updateNote();
                }
            }
        });
    }
        public void updateNote() {
        try {
            IsExistNoteTask isExistNoteTask = new IsExistNoteTask(noteDatabaseOperations, context);
            if (isExistNoteTask.execute(note).get()) {
                Toast.makeText
                        (context, "title has been added before !", Toast.LENGTH_SHORT).show();
            } else {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Confirmation");
                alertDialogBuilder
                        .setMessage("do you want to save update ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                note.setNoteHeader(noteTitle.getText().toString());
                                note.setNoteBody(noteBody.getText().toString());
                                UpdateNoteTask updateNoteTask = new UpdateNoteTask(noteDatabaseOperations, context);
                                updateNoteTask.execute(note);
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
            Log.e(CreateNoteActivity.LOGGER_TAG, "error while updating note : " + e);
        }
    }

}
