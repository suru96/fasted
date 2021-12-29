package com.example.schultetable.table;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schultetable.R;

import org.jetbrains.annotations.NonNls;

import java.util.List;


public class GridViewAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflater;
    private static final String TAG = Table.class.getSimpleName();
    private static  String[] mInt = {};
    private int currentPosition, type, lineCount = 5, topLeftPosition=0, topRightPosition=0,
            endLeftPosition=0, endRightPosition=0;
    private boolean isRunning = false;
    Context mContext;
    List<String> mCurrendRound;

    // Конструктор
    public GridViewAdapter(Context context, int textViewResourceId, List<String> CurrendRound, String[] mInt) {
        super(context, textViewResourceId, mInt);

        this.mContext = context;
        this.mCurrendRound = CurrendRound;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(5 == this.lineCount){
            this.topLeftPosition = 0;
            this.topRightPosition = 4;
            this.endLeftPosition = 20;
            this.endRightPosition = 24;
        } else {
            this.topLeftPosition = 0;
            this.topRightPosition = 6;
            this.endLeftPosition = 42;
            this.endRightPosition = 48;
        }
        // TODO Auto-generated method stub
        TextView label = (TextView) convertView;
        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
            label.setSingleLine(true);
            label.setCursorVisible(false);
        }
        Log.d(TAG, "lineCount is:" + lineCount);
        if (this.isRunning) {
            if (isTrue(this.currentPosition, position)) {
               // Log.d(TAG, "isTrue is:" + isTrue(this.currentPosition, position) +  " position: "
                // + position + " currentPosition: " +currentPosition);
                if (position == this.topLeftPosition) {
                    label.setBackgroundResource(R.drawable.single_cell_shape_inactive_top_left);}
                else if (position == this.topRightPosition) {
                    label.setBackgroundResource(R.drawable.single_cell_shape_inactive_top_right);}
                else if (position == this.endLeftPosition) {
                    label.setBackgroundResource(R.drawable.single_cell_shape_inactive_bottom_left);}
                else if (position == this.endRightPosition) {
                    label.setBackgroundResource(R.drawable.single_cell_shape_inactive_bottom_right);}
                else {label.setBackgroundResource(R.drawable.single_cell_shape_inactive_center);}
                label.setTextColor(this.mContext.getResources().getColor(R.color.colorinActiveText));
                label.setClickable(false);
            }
        } else {
            Log.d(TAG, "Current state^ " + this.isRunning);

            label.setText(this.mCurrendRound.get(position));
            label.setTextSize(48);
            if (position == this.topLeftPosition) {
                label.setBackgroundResource(R.drawable.single_cell_shape_top_left);}
            else if (position == this.topRightPosition) {
                label.setBackgroundResource(R.drawable.single_cell_shape_top_right);}
            else if (position == this.endLeftPosition) {
                label.setBackgroundResource(R.drawable.single_cell_shape_bottom_left);}
            else if (position == this.endRightPosition) {
                label.setBackgroundResource(R.drawable.single_cell_shape_bottom_right);}
            else {label.setBackgroundResource(R.drawable.single_cell_shape_center);
            }
        }

        label.setWidth((int) convertDpToPixels(mContext, (float) 375 / this.lineCount));
        label.setHeight((int) convertDpToPixels(mContext, (float) 375 / this.lineCount));
        label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        label.setTextSize(25);
        label.setGravity(Gravity.CENTER);
        return (convertView);
    }
    // Java
    float convertDpToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    float convertPixelsToDp(Context context, float pixels) {
        return pixels / context.getResources().getDisplayMetrics().density;
    }
    // возвращает содержимое выделенного элемента списка
    public String getItem(int position) {
        return mInt[position];
    }
    public String getItemList(int position) {
        return mCurrendRound.get(position);
    }

    public boolean isTrue(int currentItem, int position){
        return mInt[currentItem] == mCurrendRound.get(position);
    }

    public void  setCurrentPosition(){
        if (isRunning == true) {
            currentPosition++;
        } else {currentPosition = 0;}
        Log.d(TAG, "isRunning is:" + isRunning);

        Log.d(TAG, "currentPosition set:" + currentPosition);

    }

    public void setIsRunning(Boolean run) {
        this.isRunning = run;
        Log.d(TAG, "isRunning set:" + isRunning);
    }

    public void setMass(String[] mass) {
        mInt = mass;
        Log.d(TAG, "mass set:" + mass.toString());
    }

    public String[] getMass() {
        return mInt;
    }

    public void setLineCount(Integer lineCount){
        this.lineCount = lineCount;
        Log.d(TAG, "lineCount set:" + lineCount);

    }

    public Boolean getIsRunning() {
        return isRunning;
    }

    public Integer getCurrentPosition(){
        return currentPosition;
    }

    public Integer getFirstPosition(){
        return 0;
    }
}
