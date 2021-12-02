package com.example.schultetable.table;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class StopWatch {
    TextView textView;
    Handler handler = new Handler();
    boolean isRunning;// to keep track of the state of handler to avoid creating multiple threads.
    private int time = 0, seconds, minutes;
    private String prefixSS, prefixMM, prefixMS;
    private static final String TAG = StopWatch.class.getSimpleName();

    public StopWatch(TextView textView) {
        super();// this textview would be updated by the stop-watch
        this.textView = textView;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @NotNull
    public String convertTimeToText(int time) {
        prefixMS = "";
        prefixSS = "";
        prefixMM = "";
        seconds = (time / 100) % 60;
        minutes = time / 6000;
        if ((time / 10) % 10 == 0) {
            prefixMS = "0";
        }
        if (seconds / 10 == 0) {
            prefixSS = "0";
        }
        if (minutes / 10 == 0) {
            prefixMM = "0";
        }

        String timeString = prefixMM + minutes + ":" + prefixSS + seconds + ":" + prefixMS + time % 100;
        //Log.d(TAG, "Time is: " + timeString);
        // Log.d(TAG, "Time is update!" + timeString + "time " + time%10);
        return timeString;
    }


    public void start() {

        if (!isRunning) {
            time = 0;
            startTime();

        }
    }

    public int convertArrayToTime(int [] time) {
        Log.d(TAG, "Time is update!" + (time[1] * 6000)%1000);
        return time[0] * 6000 + (time[1] * 100) + time[2];
    }

    public void stop() {
        time = 0;
        //textView.setText("00:00:00");
        handler.removeCallbacksAndMessages(null);
        isRunning = false;
    }

    public void pause() {
        handler.removeCallbacksAndMessages(null);
        isRunning = false;
    }

    public void resume() {
        startTime();
        isRunning = true;
    }

    private void startTime() {
        if (!isRunning) {
            isRunning = true;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    time += 1;
                    textView.setText(convertTimeToText(time));
                    handler.postDelayed(this, 1);
                }
            });
        }
    }
}
