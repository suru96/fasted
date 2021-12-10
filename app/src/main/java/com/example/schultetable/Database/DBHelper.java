package com.example.schultetable.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.schultetable.Result;
import com.google.android.material.timepicker.TimeFormat;

import org.jetbrains.annotations.NonNls;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper{
    private static final String KEY_ID = "_id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TT = "totalTime";
    private static final String KEY_LT = "lastTime";
    private static final String KEY_MT = "minTime";
    private static final String KEY_TA = "totalAttempts";
    private static final String KEY_TIME = "time";
    private static final String KEY_DD = "dueDate";
    private static final String TABLE_DATA = "totalResults";

    //Конструктор объекта базы данных
    public DBHelper(Context context) {
        super(context, "db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table resultsTable (" +
                "_id integer primary key autoincrement," +
                "time text," +
                "type integer," +
                "dueDate text);"
        );

        db.execSQL("create table totalResults (" +
                "_id integer primary key autoincrement," +
                "type integer," +
                "totalTime integer," +
                "lastTime integer," +
                "minTime integer," +
                "totalAttempts integer);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Data getData(int type) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID, KEY_TYPE
                        , KEY_TT, KEY_LT,  KEY_MT, KEY_TA }, KEY_TYPE + "=?",
                new String[] { String.valueOf(type) }, null, null, null, null);

        if (null != cursor && cursor.getCount()!=0){
            cursor.moveToFirst();
            return new Data(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1))
                , Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));
        } else {return new Data();}
    }

    //Получение результатов
    public List<Data> getAllData(String TABLE_NAME) {
        List<Data> dataList = new ArrayList<Data>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setType(Integer.parseInt(cursor.getString(1)));
                data.setTotalTimeInt(Integer.parseInt(cursor.getString(2)));
                data.setLastTimeInt(Integer.parseInt(cursor.getString(3)));
                data.setMinTimeInt(Integer.parseInt(cursor.getString(4)));
                data.setTotalAttemptsInt(Integer.parseInt(cursor.getString(5)));
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

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


