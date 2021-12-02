package com.example.schultetable.Database;

import android.content.Intent;

import com.example.schultetable.BuildConfig;

import java.util.Map;

public class Data {
    private int totalTimeInt, totalAttemptsInt, lastTimeiInt, minTimeiInt;

    public Data (Integer totalTimeInt, Integer totalAttemptsInt, Integer lastTimeiInt, Integer minTimeiInt){
        this.totalAttemptsInt = totalAttemptsInt;
        this.totalTimeInt = totalTimeInt;
        this.lastTimeiInt = lastTimeiInt;
        this.minTimeiInt = minTimeiInt;
    }
    public Data (){
        this.totalAttemptsInt = 0;
        this.totalTimeInt = 0;
        this.lastTimeiInt = 0;
        this.minTimeiInt = 0;
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
        return lastTimeiInt;
    }

    public void setLastTimeInt(Integer lastTimeiInt) {
        this.lastTimeiInt = lastTimeiInt;
    }

    public Integer getMinTimeInt() {
        return minTimeiInt;
    }

    public void setMinTimeInt(Integer minTimeiInt) {
        this.minTimeiInt = minTimeiInt;
    }


    //Преобразование строки во время
    public static int[] unpack(String time) throws NullPointerException, NumberFormatException {
        int[] output = {0, 0, 0};
        String[] parts = time.split(":");
        for (int x = 0; x < parts.length; x++) {
            if (x >= output.length)
                break;
            output[x] = Integer.parseInt(parts[x]);
        }
        return output;
    }

}