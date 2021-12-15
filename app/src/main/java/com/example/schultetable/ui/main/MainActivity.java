package com.example.schultetable.ui.main;

import com.example.schultetable.Database.DBHelper;
import com.example.schultetable.Database.Data;
import com.example.schultetable.Database.FastedApplication;
import com.example.schultetable.R;
import com.example.schultetable.table.StopWatch;
import com.example.schultetable.table.Table;
import com.example.schultetable.ui.loading.LoadingActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import org.jetbrains.annotations.NonNls;

import java.util.Locale;

import androidx.navigation.ui.AppBarConfiguration;



public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Boolean start;
    private Button startButton;
    private Drawable mActionBarBackgroundDrawable;
    private static final String TAG = Table.class.getSimpleName();
    private DBHelper dbHelper;
    @NonNls
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    TextView minTextView, minText, stopWatchTextView;
    StopWatch stopWatch;
    private Data result;
    private Integer type = 0, minTimeInt = 0, colored = 0;
    View viewMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbHelper = new DBHelper(getApplicationContext());
        this.setContentView(R.layout.main_activity_layout);
        this.stopWatchTextView = findViewById(R.id.stopWatchView);
        this.minText = findViewById(R.id.minTime);
        this.stopWatch = new StopWatch(stopWatchTextView);
        Intent intent = new Intent(this.getApplicationContext(), LoadingActivity.class);
        this.startActivityForResult(intent, 1);
        this.db = ((FastedApplication) this.getApplicationContext()).getDatabase();
        this.initializeUILayout();
        this.initListAdapter();
    }

    private void initListAdapter () {
        this.minTextView = findViewById(R.id.minTimeView);
        this.result = this.dbHelper.getData(this.type);
        /*List <Data> data = this.dbHelper.getAllData("totalResults");
        data.stream().forEach((c) -> Log.d(TAG, "result: " + c));*/
        Log.d(TAG, "result is:" + this.result.toString());
        if (this.result == null) {
            this.result = new Data();
        }
        Log.d(TAG, "result is:" + this.result.toString());
        if (this.result == null) {
            this.result = new Data();
        }
        this.minTimeInt = this.result.getMinTimeInt();
        Log.d(TAG, "minTimeInt is:" + this.minTimeInt.toString());

        String text = this.stopWatch.convertTimeToText(this.minTimeInt);
        Log.d(TAG, "text " + text);
        this.minTextView.setText(text);

    }

    private void initializeUILayout() {
        // Customize the back button
        // calling the action bar
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
        actionBar.setTitle("");
        this.mActionBarBackgroundDrawable = getResources().getDrawable((R.color.colorAccent));
        this.mActionBarBackgroundDrawable.setAlpha(0);
        actionBar.setBackgroundDrawable(this.mActionBarBackgroundDrawable);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
        this.hideSystemUI();
        this.startButton = (Button) this.findViewById(R.id.startButton);
        this.startButton.setBackgroundResource(R.drawable.button_shape);
        this.startButton.setTextColor(getResources().getColor(R.color.colorWhite));
        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                result.setType(type);
                intent.putExtra("id", result.getId());
                intent.putExtra("type", result.getType());
                intent.putExtra("totalTime", result.getTotalTimeInt());
                intent.putExtra("lastTime", result.getLastTimeInt());
                intent.putExtra("minTime", result.getMinTimeInt());
                intent.putExtra("totalAttempts", result.getTotalAttemptsInt());
                Log.d(TAG, "intent is" + intent.toString());
                MainActivity.this.startActivityForResult(intent, 1);
            }
        });
    }

    private void hideSystemUI() {
        final Window window = this.getWindow();
        final View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setBackgroundDrawableResource(R.drawable.background_layout_shape);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //changeContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_info:
                Intent intent = new Intent(getApplicationContext(), SliderActivity.class);
                startActivityForResult(intent, 2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        Switch switchMode = (Switch) findViewById(R.id.five_to_seven_switch);
        Switch switchNumberOrLetter = (Switch) findViewById(R.id.number_to_letter_switch);
        Switch switchColor = (Switch) findViewById(R.id.monochrome_to_colors_switch);

        boolean checked = ((Switch) view).isChecked();
        switch(view.getId()) {
            case R.id.five_to_seven_switch:
                if (checked) {
                    this.type = 1;
                    this.initListAdapter();
                }else {
                    this.type = 0;
                    this.result.setType(0);
                    this.initListAdapter();
                }
                break;
            case R.id.number_to_letter_switch:
                if (checked) {
                    if (Locale.getDefault().getLanguage() == "ru"){
                        this.type = 2;
                        switchMode.setClickable(false);
                        switchMode.setChecked(false);
                        this.result.setType(2);
                        this.initListAdapter();
                    } else {
                        this.type = 3;
                        this.result.setType(3);
                        this.initListAdapter();
                    }
                } else {
                    type = 0;
                    this.result.setType(0);
                    switchMode.setClickable(true);
                    this.initListAdapter();
                }
                break;
            case R.id.monochrome_to_colors_switch:
                if (checked)
                {
                    this.colored = 1;
                } else {
                    this.colored = 0;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.hideSystemUI();
        this.initListAdapter ();

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.initializeUILayout();
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                this.initializeUILayout();
                this.startButton.callOnClick();
            }
        }
    }
}

