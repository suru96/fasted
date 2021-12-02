package com.example.schultetable.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.schultetable.Database.Data;
import com.example.schultetable.Database.JSONHelper;
import com.example.schultetable.R;
import com.example.schultetable.Result;
import com.example.schultetable.table.StopWatch;
import com.example.schultetable.table.Table;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private View view;
    private TextView textView, resultTimeTextView, lastTimeTextView, totalTimeTextView, minTimeTextView,  lastTimeText, totalTimeText, minTimeText;
    private Data data;
    private JSONHelper jsonHelper;
    private String totalAttempt, totalTime, lastTime, minTime;
    private String jsonString;
    private Result result;
    private StopWatch stopWatch;
    int[] resultIntArray;
    int totalAttemptsInt, totalTimeInt, lastTimeInt, minTimeInt, totalTimeIntTemp;
    private static final String TAG = Table.class.getSimpleName();
    private Drawable mActionBarBackgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUILayout();
        stopWatch = new StopWatch(textView);
        this.data = new Data();
        this.totalAttemptsInt = this.data.getTotalAttemptsInt();
        this.totalTimeInt = this.data.getTotalTimeInt();
        this.totalTimeIntTemp = 0;

    }
    private void initializeUILayout() {
        // Customize the back button
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
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
        hideSystemUI();
        this.setContentView(R.layout.activity_result);
        this.view = (View) findViewById(R.id.result_view);
        this.textView = findViewById(R.id.result_text);
        this.minTimeTextView = findViewById(R.id.min_time);
        this.lastTimeTextView = findViewById(R.id.last_time);
        this.totalTimeTextView = findViewById(R.id.total_time);
        this.lastTimeText = findViewById(R.id.last_time_text);
        this.totalTimeText = findViewById(R.id.total_time_text);
        this.minTimeText = findViewById(R.id.min_time_text);
        //final Typeface type = Typeface.createFromAsset(getAssets(),"fonts/roboto.ttf");
        this.view.setBackgroundResource(R.drawable.single_cell_shape);
        //this.textView.setTypeface(type);
        this.resultTimeTextView = findViewById(R.id.result_time_text);
        this.jsonString = jsonHelper.importFromJSON(getApplicationContext());
        this.parseJSONString(jsonString);
        Intent intent = new Intent(this, Table.class);
        this.startActivityForResult(intent ,1);

    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void parseJSONString(String jsonString){
        if (jsonString != null){
            Log.d(TAG, "JSON string is: " + jsonString);
        }
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (requestCode == 1) {
            Log.d(TAG, "resultCode is: " + resultCode);
            if (resultCode == RESULT_OK) {
                this.result = resultIntent.getParcelableExtra("result");
                String resultTime = result.getTime();
                this.resultTimeTextView.setText(resultTime);
                this.totalAttemptsInt++;
                this.resultIntArray= data.unpack(resultTime);
                Log.d(TAG, "resultIntArray is:" + this.resultIntArray[0] + " " + this.resultIntArray[1] +" " + this.resultIntArray[2]);
                this.lastTimeInt = 0;
                this.minTimeInt = 0;
                this.lastTimeInt = this.stopWatch.convertArrayToTime(this.resultIntArray);
                this.minTimeInt = this.stopWatch.convertArrayToTime(this.resultIntArray);
                this.totalTimeInt  = data.getTotalTimeInt() + this.stopWatch.convertArrayToTime(this.resultIntArray);
                this.totalTimeIntTemp =  totalTimeInt / this.totalAttemptsInt;
                this.totalTime = this.stopWatch.convertTimeToText(this.totalTimeIntTemp);
                this.lastTime = this.stopWatch.convertTimeToText(this.data.getLastTimeInt());
                if (this.data.getMinTimeInt() != 0 && this.data.getMinTimeInt()<= this.minTimeInt){
                    this.minTime = this.stopWatch.convertTimeToText(this.data.getMinTimeInt());
                    this.minTimeInt = this.data.getMinTimeInt();
                } else { this.minTime = this.stopWatch.convertTimeToText(this.minTimeInt); }

                this.minTimeTextView.setText(this.minTime);
                this.lastTimeTextView.setText(this.lastTime);
                this.totalTimeTextView.setText(this.totalTime);

                this.data.setTotalAttemptsInt(this.totalAttemptsInt);
                this.data.setMinTimeInt(this.minTimeInt);
                this.data.setLastTimeInt(this.lastTimeInt);
                this.data.setTotalTimeInt(this.totalTimeInt);

                Boolean writeTrue = jsonHelper.exportToJSON(getApplicationContext(), data);
                Log.d(TAG, "writeTrue is: " + writeTrue);

            }else {finish();}
        }

    }

    public void onRetryClick(View view) {
        Intent intent = new Intent(this, Table.class);
        this.startActivityForResult(intent ,1);
    }
}
