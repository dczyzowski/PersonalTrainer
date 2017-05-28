package com.gasiorek.pawel.personaltrainer.databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.gasiorek.pawel.personaltrainer.models.ListModel;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Damian on 2017-05-23.
 */

public class SharedDataBase {

    public static final String SP_name = "trainings";
    SharedPreferences userLocalDatabase;

    public SharedDataBase(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_name, 0);
    }

    public SharedDataBase(Context context, String dbName){
        userLocalDatabase = context.getSharedPreferences(dbName, 0);
    }

    public void storeAllTrainingsList(ArrayList<ListModel> trainings){
        clearDatabase();
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        for(int position = 0 ; position < trainings.size() ; position++) {
            spEditor.putString("training" + position, trainings.get(position).getTraningName());
            spEditor.putInt("repeats" + position, trainings.get(position).getRepeats());
        }


        spEditor.putInt("position" , trainings.size());
        spEditor.apply();
    }

    public void storeAllTrainingsNames(ArrayList<String> trainings){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        for(int position = 0 ; position < trainings.size() ; position++)
            spEditor.putString("training" + position, trainings.get(position));

        spEditor.putInt("position" , trainings.size());
        spEditor.apply();
    }

    public void storeNewTraining(ListModel training){

        int i = userLocalDatabase.getInt("position", -1) + 1;

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("training" + i, training.getTraningName());
        spEditor.apply();
    }

    public void storeNewTraining(String training){

        int i = userLocalDatabase.getInt("position", -1) + 1;

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("training" + i, training);
        spEditor.putInt("position", i);
        spEditor.apply();
    }


    public ArrayList<ListModel> getTrainings(){

        int i = 0;
        ArrayList<ListModel> trainings = new ArrayList<>();
        while (!userLocalDatabase.getString("training" + i, "").isEmpty()){
            trainings.add(new ListModel(userLocalDatabase.getString("training" + i, ""),
                    userLocalDatabase.getInt("repeats" + i, 4)));
            i++;
        }

        return trainings;
    }

    public ArrayList<String> getTrainingsNames(){

        int i = 0;
        ArrayList<String> trainings = new ArrayList<>();
        while (!userLocalDatabase.getString("training" + i, "").isEmpty()){
            trainings.add(userLocalDatabase.getString("training" + i, ""));
            i++;
        }

        return trainings;
    }

    public void clearDatabase(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public boolean isSaved(){
        return userLocalDatabase.getBoolean("isSaved", false);
    }

    public void setSaved(boolean saved){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("isSaved" , saved);
        spEditor.apply();
    }
}
