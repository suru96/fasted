package com.example.schultetable.Database;

import android.content.Intent;

import com.example.schultetable.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Data {
    private int totalTimeInt, totalAttemptsInt, lastTimeInt, minTimeInt, type, id;

    public Data (Integer id, Integer type, Integer totalTimeInt,  Integer lastTimeInt, Integer minTimeInt, Integer totalAttemptsInt){
        this.totalAttemptsInt = totalAttemptsInt;
        this.totalTimeInt = totalTimeInt;
        this.lastTimeInt = lastTimeInt;
        this.minTimeInt = minTimeInt;
        this.type = type;
        this.id = id;
    }

    public Data(){
        this.id = 0;
        this.totalAttemptsInt = 0;
        this.totalTimeInt = 0;
        this.lastTimeInt = 0;
        this.minTimeInt = 0;
        this.type = 0;
    }

    public boolean isNew() {
        return id == 0;
    }

    public Integer getTotalTimeInt() {
        return totalTimeInt;
    }

    public void setTotalTimeInt(Integer totalTimeInt) {
        this.totalTimeInt = totalTimeInt;
    }

    public Integer getTotalAttemptsInt() {
        return totalAttemptsInt;
    }

    public void setTotalAttemptsInt(Integer totalAttemptsInt) {
        this.totalAttemptsInt = totalAttemptsInt;
    }

    public Integer getLastTimeInt() {
        return lastTimeInt;
    }

    public void setLastTimeInt(Integer lastTimeiInt) {
        this.lastTimeInt = lastTimeiInt;
    }

    public Integer getMinTimeInt() {
        return minTimeInt;
    }

    public void setMinTimeInt(Integer minTimeInt) {
        this.minTimeInt = minTimeInt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //Преобразование строки во время
    public static int[] unpack(@NotNull String time) throws NullPointerException, NumberFormatException {
        int[] output = {0, 0, 0};
        String[] parts = time.split(":");
        for (int x = 0; x < parts.length; x++) {
            if (x >= output.length)
                break;
            output[x] = Integer.parseInt(parts[x]);
        }
        return output;
    }

    @Override
    public String toString(){
        return ("id: " + this.getId().toString() + " Type: " +this. getType().toString()
                + " Total time: " + this.getTotalTimeInt() + " Last time " + this.getLastTimeInt()
                + " Min time "  + this.getMinTimeInt().toString() + " Total attempts "
                + this.getTotalAttemptsInt().toString());
    }
}