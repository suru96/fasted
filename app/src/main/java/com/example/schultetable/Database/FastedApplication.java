package com.example.schultetable.Database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

public class FastedApplication extends Application {
    private DBHelper dbHelper;

    public FastedApplication() {
    }

    @NonNull
    public SQLiteDatabase getDatabase() {
        return this.dbHelper.getWritableDatabase();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.dbHelper = new DBHelper(this);
    }


}
