package com.example.mohamed.mynotes.commons.utils;

public class StringsValidation {
    public static boolean isEmptyString(String string){
        try {
            return string.isEmpty();
        } catch (Exception e) {
            e.notify();
        }
        return false;
    }
}
