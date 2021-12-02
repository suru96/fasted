package com.example.schultetable.ui.loading;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.webkit.WebView;
import android.view.View;
import android.widget.TextView;

import com.example.schultetable.R;
//import com.example.schultetable.ui.webView.Constants;
//import com.example.schultetable.ui.webView.ImageUtil;

import java.security.PublicKey;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity<LodaingViewModel> extends AppCompatActivity {
    private LodaingViewModel lodaingViewModel;

    public static final String APP_PREFS_NAME = SyncStateContract.Constants.class.getPackage().getName();
    public static final String APP_CACHE_PATH =
            Environment.getStorageDirectory().getAbsolutePath() +
                    "/Android/data/" + APP_PREFS_NAME + "/cache/";
    protected WebView webView;
    protected View view;
    // protected  ImageUtil imageUtil;
    protected Image image;
    private Drawable drawable;
    Handler handler = new Handler();
    private int time = 0;


    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.setContentView(R.layout.fullscreen_layout);
        this.view = this.findViewById(R.id.some_id);
        this.view.setBackgroundColor(this.getColor(R.color.purpleBlue));
        this.view.setBackground(this.getDrawable(R.drawable.ic_logo));
        this.getSupportActionBar().hide();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (time == 1200){stopActivity();
                } else {
                time += 1;
                handler.postDelayed(this, 1);
                }
            }
        });
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(this.getColor(R.color.purpleBlue));
    }

    private void stopActivity(){
        Intent resultIntent =new Intent();
        this.setResult(this.RESULT_OK, resultIntent);
        this.finish();
    }
}
