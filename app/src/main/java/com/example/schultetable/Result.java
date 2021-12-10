package com.example.schultetable;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Result implements Parcelable {
    private int id, type;
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

    public Result(Integer id, Integer type, String time, String dueDate){
        this.id = id;
        this.time = time;
        this.type = type;
        this.dueDate = dueDate;
        this.totalTime = totalTime;
        this.lastTime = lastTime;

    }

    protected Result(Parcel in){
        this.id = in.readInt();
        this.type = in.readInt();
        this.time = in.readString();
        this.dueDate = in.readString();
        this.totalTime = in.readString();
        this.lastTime = in.readString();
    }

    //Заимствованные методы класса Parcelable
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(time);
        dest.writeString(lastTime);
        dest.writeString(totalTime);
        dest.writeString(dueDate);
    }

    //Геттеры и сеттеры для всех реквизитов объекта
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDueDate(int year, int month, int day, int hour, int minute, int second) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute, second);
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

    //Преобразование строки в число
    public Integer givenString_whenCallingIntegerValueOf_shouldConvertToInt(String givenString) {
        Integer result = Integer.valueOf(givenString);
        return  result;
    }

    @Override
    public String toString() {
        return dueDate;
    }

}

