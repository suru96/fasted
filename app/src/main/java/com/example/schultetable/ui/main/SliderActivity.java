package com.example.schultetable.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schultetable.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.example.schultetable.ui.main.Model.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {

    SliderView sliderView;
    private SliderAdapter adapter;
    private Button button;
    private Handler mHandler;
    Animation animTranslate;
    Animation animTranslateReverse;
    private Drawable mActionBarBackgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_slider);


        // Customize the back button
        // calling the action bar
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        this.mActionBarBackgroundDrawable = getResources().getDrawable(R.color.colorAccent);
        this.mActionBarBackgroundDrawable.setAlpha(0);
        actionBar.setBackgroundDrawable(this.mActionBarBackgroundDrawable);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();

        hideSystemUI();
        View view = findViewById(R.id.cv_slider_mainActivity);
        view.setBackground(getResources().getDrawable((R.color.colorAccent)));
        this.animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_traslate);
        this.animTranslateReverse = AnimationUtils.loadAnimation(this, R.anim.anim_traslate_reverse);
        this.sliderView = findViewById(R.id.imageSlider);
        this.button = findViewById(R.id.start);
        this.adapter = new SliderAdapter(this);
        this.sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.colorActiveSliderItem));
        this.sliderView.setIndicatorUnselectedColor(getResources().getColor(R.color.colorInActiveSliderItem));
        this.sliderView.setSliderAdapter(adapter);
        this.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        this.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        this.sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_TO_END);
        this.sliderView.setClickable(false);
        this.sliderView.setAutoCycle(true);
        this.sliderView.setIndicatorRadius(5);
        this. sliderView.setScrollTimeInSec(3);
        this.sliderView.startAutoCycle();
        this.addNewItem(sliderView, R.drawable.ic_1st_illustration, R.string.first_illustration);
        this.addNewItem(sliderView, R.drawable.ic_2nd_illustration, R.string.second_illustration);
        this.addNewItem(sliderView, R.drawable.ic_3rd_illustration, R.string.third_illustration);
        this.addNewItem(sliderView, R.drawable.ic_4th_illustration, R.string.fourth_illustration);

        this.sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        this.mHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if (sliderView.getCurrentPagePosition() == sliderView.getAdapterItemsCount() - 1) {
                    setUpButton(true);
                } else {setUpButton(false);}
                mHandler.sendEmptyMessageDelayed(0, 10);
            }
        };

        mHandler.sendEmptyMessage(0);

    }
    private void hideSystemUI() {
        final Window window = this.getWindow();
        final View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
    }

    public void setUpButton(boolean temp){
        if (temp==true){
            if(button.getVisibility()!=View.VISIBLE){
                button.setVisibility(View.VISIBLE);
                button.setClickable(true);
                button.startAnimation(animTranslate);
            }
        }
        else {
            if(button.getVisibility()!=View.INVISIBLE) {
                button.startAnimation(animTranslateReverse);
                button.setVisibility(View.INVISIBLE);
                button.setClickable(false);
            }
        }
    }
    private void startAnim() {
        // Установить анимацию, сдвинув ее высоту от нижней части позиции вверх, длительность составляет 500 мс
        Log.d("GGG", "it`s ok: ");
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, 0, TranslateAnimation.RELATIVE_TO_PARENT, 1);
        ctrlAnimation.setDuration (400L); // Установить время перехода анимации
        this.button.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setVisibility(View.VISIBLE);
                button.setClickable(true);
                button.startAnimation(ctrlAnimation);
            }
        }, 500);
    }
    private void startAnimReverse() {
        // Установить анимацию, сдвинув ее высоту от нижней части позиции вверх, длительность составляет 500 мс
        final TranslateAnimation ctrlAnimation1 = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, 1, TranslateAnimation.RELATIVE_TO_PARENT, 0);
        ctrlAnimation1.setDuration (400L); // Установить время перехода анимации
        this.button.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setClickable(false);
                button.startAnimation(ctrlAnimation1);
                //button.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 3; i++) {
            SliderItem sliderItem = new SliderItem();

            if (i == 1) {
                sliderItem.setImageUrl(R.drawable.ic_1st_illustration);
                sliderItem.setDescription(getResources().getText(R.string.first_illustration).toString());
            } else if (i == 1) {
                sliderItem.setImageUrl(R.drawable.ic_2nd_illustration);
                sliderItem.setDescription(getResources().getText(R.string.second_illustration).toString());
            } else if (i == 1) {
                sliderItem.setImageUrl(R.drawable.ic_3rd_illustration);
                sliderItem.setDescription(getResources().getText(R.string.third_illustration).toString());
            } else {
                sliderItem.setImageUrl(R.drawable.ic_4th_illustration);
                sliderItem.setDescription(getResources().getText(R.string.fourth_illustration).toString());
                sliderView.stopAutoCycle();
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view, Integer imageId, Integer stringId) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription(getResources().getText(stringId).toString());
        sliderItem.setImageUrl(imageId);
        adapter.addItem(sliderItem);
    }

    public void onStartClick(View view) {
        finish();
    }
}
