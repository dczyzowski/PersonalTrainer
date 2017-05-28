package com.gasiorek.pawel.personaltrainer.databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.gasiorek.pawel.personaltrainer.models.DateInfo;
import com.gasiorek.pawel.personaltrainer.models.ListModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Damian on 2017-05-27.
 */

public class DatesDataBase {
    public static final String SP_name = "dates";
    SharedPreferences userLocalDatabase;

    public DatesDataBase(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_name, 0);
    }

    public void storeNewDate(String newDate, boolean isFinished, float newScore){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        spEditor.putString("date:" + newDate, newDate);
        spEditor.putFloat("score:" + newDate, newScore);
        spEditor.putBoolean("finished:" + newDate, isFinished);

        spEditor.apply();
    }

    public boolean checkDateIsFinished(String date){

        return userLocalDatabase.getBoolean("finished:" + date, false);
    }

    public DateInfo getDateInfo(String date){
        return new DateInfo(userLocalDatabase.getString("date:" + date, ""),
                userLocalDatabase.getFloat("score:" + date, 0),
                userLocalDatabase.getBoolean("finished:" + date, false));
    }

    public void clearDatabase(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}
