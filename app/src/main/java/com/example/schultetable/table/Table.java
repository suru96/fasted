package com.example.schultetable.table;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schultetable.R;
import com.example.schultetable.Result;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Table extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private TextView textView;
    private TextView textViewValue;
    private TextView textSearch;
    private TextView textSearchNext;
    private Button startButton, saveButton;
    private Integer type =  0;
    private boolean isRunning = false;
    private StopWatch time;
    private static final String TAG = Table.class.getSimpleName();
    private String value;
    private Context context;
    private GridViewAdapter mAdapter;
    private Integer currentPosition = 0;
    private Result result;
    ActionBar actionBar;
    Calendar calendar = Calendar.getInstance();
    private static final String[] mIntShort ={ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
    private static final String[] mIntLarge ={ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25","26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"};
    private static final String[] mStringEU ={ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y"};
    private static final String[] mStringRU ={ "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч"};
    private static  String[] mass;
    private MenuInflater inflater ;
    private Drawable mActionBarBackgroundDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tab);
        hideSystemUI();
        this.initializeUILayout();
    }


    private void initializeUILayout() {
        // Использование кастомной кнопки возврата
        // Вызов actionBar
        this.actionBar = this.getSupportActionBar();

        // Замена на стандартной кнопки возврата на собственнуюx
        this.actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        this.actionBar.setTitle("");
        this.mActionBarBackgroundDrawable = getResources().getDrawable((R.color.colorAccent));
        this.mActionBarBackgroundDrawable.setAlpha(0);
        this.actionBar.setBackgroundDrawable(this.mActionBarBackgroundDrawable);

        // Показать сосбвенную кнопку возврата
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        this.actionBar.show();


        if (type == 0) {
            mass = mIntShort;
        }else if (type == 1){
                mass = mIntLarge;
        }else if (type == 2){
            mass = mStringEU;
        }else if (type == 3){
            mass = mStringRU;
        }

        final List<String> mCurrendRound = new ArrayList<>(mass.length);
        for (int i = 0; i < mass.length; i++) {
            mCurrendRound.add(i, mass[i]);
        }
        Collections.shuffle(mCurrendRound);
        final GridView g = (GridView) this.findViewById(R.id.table_grid);
        final Context applicationContext = this.getApplicationContext();
        this.mAdapter = new GridViewAdapter(applicationContext, R.layout.single_cell_layout, mCurrendRound);
        this.setUpGridAdapter(mCurrendRound, g);
        this.textViewValue = this.findViewById(R.id.chronometer);
        this.textViewValue.setText("00:00:00");
        this.startButton = this.findViewById(R.id.buttonStart);
        this.time = new StopWatch(this.textViewValue);
        this.textSearch =  this.findViewById(R.id.search_text);
        this.textSearch.setText(R.string.search);
        this.textSearchNext = this.findViewById(R.id.next_search);
        this.textSearchNext.setText("0");
    }

    private void hideSystemUI() {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void runTime(boolean isRunning) {
        if (isRunning) {
            Log.d(Table.TAG, "onCreate:" + this.time);
            this.time.start();
        } else {
            this.time.stop();
        }
    }

    private void setUpGridAdapter(List<String> mCurrendRound, @NotNull GridView g) {

        this.mAdapter.setCurrentPosition();

        g.setAdapter(this.mAdapter);
        g.setOnItemSelectedListener(this);
        g.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stubz`
                if (mAdapter.getIsRunning() && (mAdapter.getCurrentPosition() == 24)){
                    mAdapter.getView(position, v, parent);
                    mAdapter.setIsRunning(false);
                    runTime(false);
                    mAdapter.setCurrentPosition();
                    saveResult();
                }else if (mAdapter.getIsRunning() && mAdapter.isTrue(mAdapter.getCurrentPosition(), position)){
                    textSearchNext = findViewById(R.id.next_search);
                    currentPosition = mAdapter.getCurrentPosition() + 1;
                    textSearchNext.setText(mAdapter.getItem(currentPosition).toString());
                    mAdapter.getView(position, v, parent);
                    mAdapter.setCurrentPosition();
                }
            }
        });
    }

    public void saveResult(){
        Result result = getIntent().getParcelableExtra("result");

        if (result == null) result = new Result();
        result.setDueDate(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH), this.calendar.get(Calendar.DAY_OF_MONTH), this.calendar.get(Calendar.HOUR_OF_DAY), this.calendar.get(Calendar.MINUTE), this.calendar.get(Calendar.SECOND));
        result.setTime(this.textViewValue.getText().toString());
        Intent resultIntent =new Intent();
        resultIntent.putExtra("result", (Parcelable) result);
        Log.d(TAG, "Result is create: " + result.toString());
        this.setResult(this.RESULT_OK, resultIntent);
        this.finish();
    }

    public void onStartClick(View view) {
        this.mAdapter.setIsRunning(true);
        this.runTime(true);
        currentPosition = mAdapter.getFirstPosition();
        textSearchNext.setText(mAdapter.getItem(currentPosition).toString());
        this.startButton.setClickable(false);
        this.startButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        Toast.makeText(getApplicationContext(), "Выбранный элемент: "
                        + mAdapter.getItem(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "Выбранный элемент ничего",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.nav_refresh:
                if (mAdapter.getIsRunning()) {
                    this.mAdapter.setIsRunning(false);
                    this.runTime(false);
                    this.textViewValue.setText("00:00:00");
                    this.mAdapter.setIsRunning(true);
                    this.runTime(true);
                    currentPosition = mAdapter.getFirstPosition();
                    textSearchNext.setText(mAdapter.getItem(currentPosition).toString());
                    initializeUILayout();
                }
        }

        return super.onOptionsItemSelected(item);
    }


}


