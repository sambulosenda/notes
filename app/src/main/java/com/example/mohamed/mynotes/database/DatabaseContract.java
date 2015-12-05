package com.example.mohamed.mynotes.database;

import android.provider.BaseColumns;

public final class DatabaseContract
{

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myAPP.db";

    public DatabaseContract(){}


    public static abstract class UserEntries implements BaseColumns
    {

        public static final String TABLE_NAME = "NotesEntries";
        public static final String NOTE_HEADER = "noteHeader";
        public static final String NOTE_BODY = "noteBody";
        public static final String ALARM_YEAR = "alarmYear";
        public static final String ALARM_MONTH = "alarmMonth";
        public static final String ALARM_DAY = "alarmDay";
        public static final String ALARM_HOUR = "alarmHour";
        public static final String ALARM_MINUTE = "alarmMinute";
        public static final String CREATION_YEAR = "creationYear";
        public static final String CREATION_MONTH = "creationMonth";
        public static final String CREATION_DAY = "creationDay";
        public static final String CREATION_HOUR = "creationHour";
        public static final String CREATION_MINUTE = "creationMinute";
        public static final String MODIFICATION_YEAR = "modificationYear";
        public static final String MODIFICATION_MONTH = "modificationMonth";
        public static final String MODIFICATION_DAY = "modificationDay";
        public static final String MODIFICATION_HOUR = "modificationHour";
        public static final String MODIFICATION_MINUTE = "modificationMinute";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        NOTE_HEADER + TEXT_TYPE + COMMA_SEP +
                        NOTE_BODY + TEXT_TYPE + COMMA_SEP +
                        ALARM_YEAR + INT_TYPE + COMMA_SEP +
                        ALARM_MONTH + INT_TYPE + COMMA_SEP +
                        ALARM_DAY + INT_TYPE + COMMA_SEP +
                        ALARM_HOUR + INT_TYPE + COMMA_SEP +
                        ALARM_MINUTE + INT_TYPE + COMMA_SEP +
                        CREATION_YEAR + INT_TYPE + COMMA_SEP +
                        CREATION_MONTH + INT_TYPE + COMMA_SEP +
                        CREATION_DAY + INT_TYPE + COMMA_SEP +
                        CREATION_HOUR + INT_TYPE + COMMA_SEP +
                        CREATION_MINUTE + INT_TYPE + COMMA_SEP +
                        MODIFICATION_YEAR + INT_TYPE + COMMA_SEP +
                        MODIFICATION_MONTH + INT_TYPE + COMMA_SEP +
                        MODIFICATION_DAY + INT_TYPE + COMMA_SEP +
                        MODIFICATION_HOUR + INT_TYPE + COMMA_SEP +
                        MODIFICATION_MINUTE + INT_TYPE + ");";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
