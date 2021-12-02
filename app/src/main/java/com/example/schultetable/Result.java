package com.example.schultetable;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Result implements Parcelable {
    private int id;
    private String time, dueDate, totalTime, lastTime;
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            new ThreadLocal<SimpleDateFormat>() {
                @SuppressLint("SimpleDateFormat")
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("dd-MM-yyyy");
                }
            };

    //Конструторы объекта
    public Result(){

    }

    public Result(Integer id, String time, String dueDate){
        this.id = id;
        this.time = time;
        this.dueDate = dueDate;
        this.totalTime = totalTime;
        this.lastTime = lastTime;

    }

    protected Result(Parcel in){
        id = in.readInt();
        time = in.readString();
        dueDate = in.readString();
        totalTime = in.readString();
        lastTime = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    //Заимствованные методы класса Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(time);
        dest.writeString(dueDate);
    }

    //Геттеры и сеттеры для всех реквизитов объекта
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDueDate(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        this.dueDate = dateFormat.get().format(calendar.getTime());
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String time) {
        this.lastTime = lastTime;
    }

    public Integer givenString_whenCallingIntegerValueOf_shouldConvertToInt(String givenString) {
        Integer result = Integer.valueOf(givenString);
        return  result;
    }
}

