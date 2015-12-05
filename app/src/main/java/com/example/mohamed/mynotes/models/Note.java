package com.example.mohamed.mynotes.models;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String noteHeader;
    private String noteBody;
    private int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute;
    private int creationYear, creationMonth, creationDay, creationHour, creationMinute;
    private int modificationYear, modificationMonth, modificationDay, modificationHour, modificationMinute;

    public Note(String noteHeader, String noteBody, int alarmYear, int alarmMonth, int alarmDay, int alarmHour, int alarmMinute,
                int creationYear, int creationMonth, int creationDay, int creationHour, int creationMinute){
        this.noteHeader=noteHeader;
        this.noteBody=noteBody;
        this.alarmYear=alarmYear;
        this.alarmMonth=alarmMonth;
        this.alarmDay=alarmDay;
        this.alarmHour=alarmHour;
        this.alarmMinute=alarmMinute;
        this.creationYear=creationYear;
        this.creationMonth=creationMonth;
        this.creationDay=creationDay;
        this.creationHour=creationHour;
        this.creationMinute=creationMinute;
        modificationMinute=0;
        modificationHour=0;
        modificationDay=0;
        modificationMonth=0;
        modificationYear=0;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setNoteHeader(String noteHeader){
        this.noteHeader=noteHeader;
    }
    public void setNoteBody(String noteBody){
        this.noteBody=noteBody;
    }
    public void setAlarm(int year, int month, int day, int hour, int minute){
        alarmYear=year;
        alarmMonth=month;
        alarmDay=day;
        alarmHour=hour;
        alarmMinute=minute;
    }
    public void setModification(int year, int month, int day, int hour, int minute){
        modificationYear=year;
        modificationMonth=month;
        modificationDay=day;
        modificationHour=hour;
        modificationMinute=minute;
    }

    public String getNoteHeader() {
        return noteHeader;
    }
    public String getNoteBody() {
        return noteBody;
    }
    public int getAlarmYear() {
        return alarmYear;
    }
    public int getAlarmMonth() {
        return alarmMonth;
    }
    public int getAlarmDay() {
        return alarmDay;
    }
    public int getAlarmHour() {
        return alarmHour;
    }
    public int getAlarmMinute() {
        return alarmMinute;
    }
    public int getCreationYear() {
        return creationYear;
    }
    public int getCreationMonth() {
        return creationMonth;
    }
    public int getCreationDay() {
        return creationDay;
    }
    public int getCreationHour() {
        return creationHour;
    }
    public int getCreationMinute() {
        return creationMinute;
    }
    public int getModificationYear() {
        return modificationYear;
    }
    public int getModificationMonth() {
        return modificationMonth;
    }
    public int getModificationDay() {
        return modificationDay;
    }
    public int getModificationHour() {
        return modificationHour;
    }
    public int getModificationMinute() {
        return modificationMinute;
    }
    public int getId() {
        return id;
    }
}
