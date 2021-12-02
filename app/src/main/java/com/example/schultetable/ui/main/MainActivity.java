package com.example.schultetable.ui.main;

import com.example.schultetable.R;
import com.example.schultetable.Result;
import com.example.schultetable.table.Table;
import com.example.schultetable.ui.loading.LoadingActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.navigation.ui.AppBarConfiguration;

import static com.example.schultetable.R.color.purpleBlue;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Boolean start;
    private Button startButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
        startActivityForResult(intent, 1);

    }

    private void initializeUILayout() {
        this.setContentView(R.layout.main_activity_layout);
        this.startButton = (Button) this.findViewById(R.id.startButton);
        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });
    }
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(purpleBlue));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //changeContent();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                hideSystemUI();
                this.initializeUILayout();
            }
        }
    }
}

