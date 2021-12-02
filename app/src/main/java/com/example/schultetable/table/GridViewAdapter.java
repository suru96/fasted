package com.example.schultetable.table;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schultetable.R;

import java.util.List;


public class GridViewAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflater;
    private static final String TAG = Table.class.getSimpleName();
    private static final String[] mInt ={ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
    private int currentPosition;
    private boolean isRunning = false;
    Context mContext;
    List<String> mCurrendRound;

    // Конструктор
    public GridViewAdapter(Context context, int textViewResourceId, List<String> CurrendRound) {
        super(context, textViewResourceId, mInt);
        // TODO Auto-generated constructor stub
        mContext = context;
        mCurrendRound = CurrendRound;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView label = (TextView) convertView;
        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
        }
        //Log.d(TAG, "isRunning is:" + isRunning);
        if (this.isRunning) {
            if (isTrue(this.currentPosition, position)) {
               // Log.d(TAG, "isTrue is:" + isTrue(this.currentPosition, position) +  " position: " + position + " currentPosition: " +currentPosition);
                label.setWidth((int) convertDpToPixels(mContext, (float) 74.0));
                label.setHeight((int) convertDpToPixels(mContext, (float) 74.0));
                if (position == 0) {label.setBackgroundResource(R.drawable.single_cell_shape_inactive_top_left);}
                else if (position == 4) {label.setBackgroundResource(R.drawable.single_cell_shape_inactive_top_right);}
                else if (position == 20) {label.setBackgroundResource(R.drawable.single_cell_shape_inactive_bottom_left);}
                else if (position == 24) {label.setBackgroundResource(R.drawable.single_cell_shape_inactive_bottom_right);}
                else {label.setBackgroundResource(R.drawable.single_cell_shape_inactive_center);}
                label.setTextColor(mContext.getResources().getColor(R.color.colorinActiveText));
                label.setClickable(false);
            }
        } else {

            label.setText(mCurrendRound.get(position));
            label.setWidth((int) convertDpToPixels(mContext, (float) 74.0));
            label.setHeight((int) convertDpToPixels(mContext, (float) 74.0));
            label.setTextSize(48);
            if (position == 0) {label.setBackgroundResource(R.drawable.single_cell_shape_top_left);}
            else if (position == 4) {label.setBackgroundResource(R.drawable.single_cell_shape_top_right);}
            else if (position == 20) {label.setBackgroundResource(R.drawable.single_cell_shape_bottom_left);}
            else if (position == 24) {label.setBackgroundResource(R.drawable.single_cell_shape_bottom_right);}
            else {label.setBackgroundResource(R.drawable.single_cell_shape_center);}
            label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
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
