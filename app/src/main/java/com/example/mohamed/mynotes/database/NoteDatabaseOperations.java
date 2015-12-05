package com.example.mohamed.mynotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mohamed.mynotes.activities.CreateNoteActivity;
import com.example.mohamed.mynotes.models.Note;

import java.util.ArrayList;

public class NoteDatabaseOperations {
    private DatabaseHelper dataBaseHelper;

    public NoteDatabaseOperations(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public boolean addNote(Note note) {
        try {
            SQLiteDatabase SQ = dataBaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseContract.UserEntries.NOTE_HEADER, note.getNoteHeader());
            values.put(DatabaseContract.UserEntries.NOTE_BODY, note.getNoteBody());

            values.put(DatabaseContract.UserEntries.ALARM_YEAR, note.getAlarmYear());
            values.put(DatabaseContract.UserEntries.ALARM_MONTH, note.getAlarmMonth());
            values.put(DatabaseContract.UserEntries.ALARM_DAY, note.getAlarmDay());
            values.put(DatabaseContract.UserEntries.ALARM_HOUR, note.getAlarmHour());
            values.put(DatabaseContract.UserEntries.ALARM_MINUTE, note.getAlarmMinute());

            values.put(DatabaseContract.UserEntries.CREATION_YEAR, note.getCreationYear());
            values.put(DatabaseContract.UserEntries.CREATION_MONTH, note.getCreationMonth());
            values.put(DatabaseContract.UserEntries.CREATION_DAY, note.getCreationDay());
            values.put(DatabaseContract.UserEntries.CREATION_HOUR, note.getCreationHour());
            values.put(DatabaseContract.UserEntries.CREATION_MINUTE, note.getCreationMinute());

            values.put(DatabaseContract.UserEntries.MODIFICATION_YEAR, note.getModificationYear());
            values.put(DatabaseContract.UserEntries.MODIFICATION_MONTH, note.getModificationMonth());
            values.put(DatabaseContract.UserEntries.MODIFICATION_DAY, note.getModificationDay());
            values.put(DatabaseContract.UserEntries.MODIFICATION_HOUR, note.getModificationHour());
            values.put(DatabaseContract.UserEntries.MODIFICATION_MINUTE, note.getModificationMinute());
            long newRowId = SQ.insert(
                    DatabaseContract.UserEntries.TABLE_NAME,
                    null,
                    values);
            return true;

        } catch (Exception e) {
            // Log.e(MainActivity.LOGGER_TAG, note.getNoteHeader() + e);
        }
        return false;
    }

    public boolean updateNote(Note note) {
        try {
            SQLiteDatabase SQ = dataBaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseContract.UserEntries.NOTE_HEADER, note.getNoteHeader());
            values.put(DatabaseContract.UserEntries.NOTE_BODY, note.getNoteBody());

            values.put(DatabaseContract.UserEntries.ALARM_YEAR, note.getAlarmYear());
            values.put(DatabaseContract.UserEntries.ALARM_MONTH, note.getAlarmMonth());
            values.put(DatabaseContract.UserEntries.ALARM_DAY, note.getAlarmDay());
            values.put(DatabaseContract.UserEntries.ALARM_HOUR, note.getAlarmHour());
            values.put(DatabaseContract.UserEntries.ALARM_MINUTE, note.getAlarmMinute());

            values.put(DatabaseContract.UserEntries.MODIFICATION_YEAR, note.getModificationYear());
            values.put(DatabaseContract.UserEntries.MODIFICATION_MONTH, note.getModificationMonth());
            values.put(DatabaseContract.UserEntries.MODIFICATION_DAY, note.getModificationDay());
            values.put(DatabaseContract.UserEntries.MODIFICATION_HOUR, note.getModificationHour());
            values.put(DatabaseContract.UserEntries.MODIFICATION_MINUTE, note.getModificationMinute());

            long updateRow = SQ.update(DatabaseContract.UserEntries.TABLE_NAME, values, "_ID=" + note.getId(), null);
            return true;

        } catch (Exception e) {
            // Log.e(MainActivity.LOGGER_TAG, note.getNoteHeader() + e);
        }
        return false;
    }

    public boolean deleteNote(Note note){
        try{
            SQLiteDatabase SQ = dataBaseHelper.getWritableDatabase();
            long deleteNote = SQ.delete(DatabaseContract.UserEntries.TABLE_NAME, "_ID="+note.getId(),null);
            return true;
        }
        catch(Exception e){
            // Log.e(MainActivity.LOGGER_TAG, note.getNoteHeader() + e);
        }
        return false;
    }

    public boolean isNoteHeaderExists(String header) {
        try {
            Cursor cursor = retrieveNotesCursor();
            if (cursor.getCount() != 0 && cursor != null) {
                cursor.moveToFirst();
                do {
                    if (header.equals(cursor.getString(0))) return true;
                }
                while (cursor.moveToNext());
                return false;
            }
        } catch (Exception e) {
            Log.e(CreateNoteActivity.LOGGER_TAG, "isEmailExists : " + e);
        }
        return false;
    }

    private Cursor retrieveNotesCursor() {
        SQLiteDatabase SQ = dataBaseHelper.getReadableDatabase();
        String[] coloumns = {DatabaseContract.UserEntries._ID, DatabaseContract.UserEntries.NOTE_HEADER, DatabaseContract.UserEntries.NOTE_BODY,
                DatabaseContract.UserEntries.ALARM_YEAR, DatabaseContract.UserEntries.ALARM_MONTH,
                DatabaseContract.UserEntries.ALARM_DAY, DatabaseContract.UserEntries.ALARM_HOUR,
                DatabaseContract.UserEntries.ALARM_MINUTE, DatabaseContract.UserEntries.CREATION_YEAR,
                DatabaseContract.UserEntries.CREATION_MONTH, DatabaseContract.UserEntries.CREATION_DAY,
                DatabaseContract.UserEntries.CREATION_HOUR, DatabaseContract.UserEntries.CREATION_MINUTE};
        Cursor cursor = SQ.query(DatabaseContract.UserEntries.TABLE_NAME, coloumns, null, null, null, null, null);
        return cursor;
    }

    public ArrayList<Note> retrieveNotesList() {
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = retrieveNotesCursor();
        if (cursor.getCount() != 0 && cursor != null) {
            cursor.moveToFirst();
            do {
                Note note = new Note(cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
                        cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getInt(11), cursor.getInt(12));
                note.setId(cursor.getInt(0));
                notes.add(note);
            }
            while (cursor.moveToNext());
        }
        return notes;
    }

    public Note retrieveNoteByHeader(String header){
        ArrayList<Note> notes = retrieveNotesList();
        for(int i=0;i<notes.size();i++) if(header.equals(notes.get(i).getNoteHeader())) return notes.get(i);
        return null;
    }

    public ArrayList<Note> retrieveNotesByAlarm(int year, int month, int day, int hour, int minute){
        ArrayList<Note> res = new ArrayList<>();
        ArrayList<Note> notes = retrieveNotesList();
        for(int i=0;i<notes.size();i++) if(year==notes.get(i).getAlarmYear() &&
                month==notes.get(i).getAlarmMonth() && hour==notes.get(i).getAlarmHour()
                &&minute==notes.get(i).getAlarmMinute() ) res.add(notes.get(i));
        return res;
    }
}