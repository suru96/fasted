package com.example.schultetable.ui.main;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.schultetable.Database.Data;
import com.example.schultetable.Database.FastedApplication;
import com.example.schultetable.Database.JSONHelper;
import com.example.schultetable.R;
import com.example.schultetable.Result;
import com.example.schultetable.table.StopWatch;
import com.example.schultetable.table.Table;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private View view;
    private TextView textView, resultTimeTextView, lastTimeTextView, totalTimeTextView,
            minTimeTextView,  lastTimeText, totalTimeText, minTimeText;
    private Data data;
    private JSONHelper jsonHelper;
    private String totalAttempt, totalTime, lastTime, minTime, type = "0";
    private String jsonString;
    private Result result;
    private StopWatch stopWatch;
    int[] resultIntArray;
    int totalAttemptsInt, totalTimeInt, lastTimeInt, minTimeInt, totalTimeIntTemp, typeInt;
    private static final String TAG = Table.class.getSimpleName();
    private Drawable mActionBarBackgroundDrawable;
    Intent resultIntent;
    Bundle arguments;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeDatabase(this.resultIntent);
        this.initializeUILayout();
    }

    private void initializeDatabase(Intent resultIntent ) {
        Calendar calendar = Calendar.getInstance();
        int[] date = { calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)};
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int currentdate = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        this.stopWatch = new StopWatch(this.resultTimeTextView);

        if (null == resultIntent) {
            this.arguments = getIntent().getExtras();
            this.resultIntArray = new int[]{0, 0, 0};
            this.db = ((FastedApplication) this.getApplicationContext()).getDatabase();
            this.data = new Data(this.arguments.getInt("id", 0),
                    this.arguments.getInt("type", 0),
                    this.arguments.getInt("totalTime", 0),
                    this.arguments.getInt("lastTime", 0),
                    this.arguments.getInt("minTime", 0),
                    this.arguments.getInt("totalAttempts", 0));
            this.result = new Result(0, this.arguments.getInt("type", 0),
                    "0", null);

            Log.d(TAG, "resultCode is: " + this.arguments.getInt("id", 0));
        } else {
            this.type = resultIntent.getStringExtra("type");
            this.result = new Result(0, resultIntent.getIntExtra("type",
            0),"0", null);
        }
        /*if(null != this.type){
            this.typeInt = this.result.givenString_whenCallingIntegerValueOf_shouldConvertToInt(type);
        } else {
            this.typeInt = 0;
        }*/
        this.totalAttemptsInt = this.data.getTotalAttemptsInt();
        this.typeInt = this.data.getType();
        this.totalTimeInt = this.data.getTotalTimeInt();
        this.result.setDueDate(year, month, currentdate, currentHour, currentMinute, second);
        this.totalTimeIntTemp = 0;
        Log.d(TAG, "typeInt is: " + this.typeInt);

    }

    private void initializeUILayout() {
        // Customize the back button
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        assert null != actionBar;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_clear_24);
        actionBar.setTitle("");
        this.mActionBarBackgroundDrawable = getResources().getDrawable((R.color.colorAccent));
        this.mActionBarBackgroundDrawable.setAlpha(0);
        actionBar.setBackgroundDrawable(this.mActionBarBackgroundDrawable);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
        this.hideSystemUI();
        this.setContentView(R.layout.activity_result);
        this.view = (View) this.findViewById(R.id.result_view);
        this.textView = this.findViewById(R.id.result_text);
        this.minTimeTextView = this.findViewById(R.id.min_time);
        this.lastTimeTextView = this.findViewById(R.id.last_time);
        this.totalTimeTextView = this.findViewById(R.id.total_time);
        this.lastTimeText = this.findViewById(R.id.last_time_text);
        this.totalTimeText = this.findViewById(R.id.total_time_text);
        this.minTimeText = this.findViewById(R.id.min_time_text);
        //final Typeface type = Typeface.createFromAsset(getAssets(),"fonts/roboto.ttf");
        this.view.setBackgroundResource(R.drawable.single_cell_shape);
        //this.textView.setTypeface(type);
        this.resultTimeTextView = this.findViewById(R.id.result_time_text);
        Intent newIntent = new Intent(this, Table.class);
        newIntent.putExtra("type", this.typeInt);
        Log.d(TAG, "typeInt set: " + this.typeInt);
        Log.d(TAG, "type set: " + newIntent.getExtras().getString("type"));
        ResultActivity.this.startActivityForResult(newIntent ,1);
    }

    private void hideSystemUI() {
        final Window window = this.getWindow();
        final View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setBackgroundDrawableResource(R.drawable.background_layout_shape);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent resultIntent) {

        super.onActivityResult(requestCode, resultCode, resultIntent);
        this.initializeDatabase(resultIntent);
        if (requestCode == 1) {
            Log.d(TAG, "resultCode is: " + resultCode);
            if (resultCode == RESULT_OK) {
                this.result = resultIntent.getParcelableExtra("result");
                assert this.result != null;
                String resultTime = this.result.getTime();
                this.resultTimeTextView.setText(resultTime);
                this.totalAttemptsInt++;

                this.resultIntArray = Data.unpack(resultTime);
                Log.d(TAG, "resultIntArray is:" + this.resultIntArray[0] + " "
                        + this.resultIntArray[1] + " " + this.resultIntArray[2]);

                this.lastTimeInt = this.stopWatch.convertArrayToTime(this.resultIntArray);
                this.minTimeInt = this.stopWatch.convertArrayToTime(this.resultIntArray);
                this.totalTimeInt = this.data.getTotalTimeInt().intValue()
                        + this.stopWatch.convertArrayToTime(this.resultIntArray);
                this.totalTimeIntTemp = this.totalTimeInt / this.totalAttemptsInt;
                this.totalTime = this.stopWatch.convertTimeToText(this.totalTimeIntTemp);
                this.lastTime = this.stopWatch.convertTimeToText(arguments.getInt("lastTime"));
                if (this.data.getMinTimeInt() != 0 && this.data.getMinTimeInt() <= this.minTimeInt)
                {
                    this.minTime = this.stopWatch.convertTimeToText(this.data.getMinTimeInt());
                    this.minTimeInt = this.data.getMinTimeInt();
                } else {
                    this.minTime = this.stopWatch.convertTimeToText(this.minTimeInt);
                }

                this.minTimeTextView.setText(this.minTime);
                this.lastTimeTextView.setText(this.lastTime);
                this.totalTimeTextView.setText(this.totalTime);

                this.data.setTotalAttemptsInt(totalTimeInt + 1);
                this.data.setMinTimeInt(this.minTimeInt);
                this.data.setLastTimeInt(this.lastTimeInt);
                this.data.setTotalTimeInt(this.totalTimeInt);

                ContentValues cv = new ContentValues();
                cv.put("totalTime", this.totalTimeInt);
                cv.put("lastTime", this.lastTimeInt);
                cv.put("minTime", this.minTimeInt);
                cv.put("totalAttempts", this.totalAttemptsInt);
                cv.put("type", this.typeInt);

                if (data.isNew()) {
                    this.db.insert("totalResults", null, cv);
                    Log.d(TAG, "Result is new! Result is: " + cv.get("type").toString() + " "
                    + cv.get("totalTime").toString());
                } else {
                    db.update("totalResults", cv, "_id = ?",
                            new String[]{String.valueOf(data.getId())});
                    Log.d(TAG, "Result is update!" + cv);
                }
            } else {
                finish();
            }
        }

    }

    public void onRetryClick(View view) {
        Intent intent = new Intent(this, Table.class);
        this.startActivityForResult(intent ,1);
    }
}
