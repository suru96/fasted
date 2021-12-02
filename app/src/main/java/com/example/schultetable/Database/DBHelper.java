package com.example.schultetable.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper{
    //Конструктор объекта базы данных
    public DBHelper(Context context) {
        super(context, "db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table checkedHabbits (" +
                "_id integer primary key autoincrement," +
                "time text," +
                "dueDate text);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Получение всех событий
    /*public List<EventObjects> getAllFutureEvents(){
        Date dateToday = new Date();
        List<EventObjects> events = new ArrayList<>();
        String query = "select * from reminder";
        return events;
    }*/

    //Преобразование строки в дату
    private Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}


