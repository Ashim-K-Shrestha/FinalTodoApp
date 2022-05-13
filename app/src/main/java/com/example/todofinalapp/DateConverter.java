package com.example.todofinalapp;

import androidx.room.TypeConverter;

import java.util.Date;

// creating a class called DateConverter
public class DateConverter {
    // TypeConverter annotation means marking a method as a data type converter
    @TypeConverter
    // method to convert time stamp to date
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    // method to convert date to time stamp
    public static Long toTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

