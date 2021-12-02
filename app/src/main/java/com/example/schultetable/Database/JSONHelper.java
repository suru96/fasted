package com.example.schultetable.Database;

import android.content.Context;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {
    private static final String FILE_NAME = "data.json";

    public static boolean exportToJSON(Context context, Data dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setData(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.toString();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItems {
        private com.example.schultetable.Database.Data Data;

        Data getData() {
            return (com.example.schultetable.Database.Data) Data;
        }
        void setData(com.example.schultetable.Database.Data Data) {
            this.Data = Data;
        }
    }
}