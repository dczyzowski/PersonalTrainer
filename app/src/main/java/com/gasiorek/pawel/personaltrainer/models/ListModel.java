package com.gasiorek.pawel.personaltrainer.models;

/**
 * Created by Damian on 2017-05-23.
 */

public class ListModel  {

    private String traningName;
    private int repeats;

    ListModel(){
    }
    public ListModel(String traningName, int repats){
        this.traningName = traningName;
        this.repeats = repats ;
    }

    public String getTraningName() {
        return traningName;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }
}
