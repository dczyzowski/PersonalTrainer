package com.gasiorek.pawel.personaltrainer.models;

import java.util.Date;

/**
 * Created by Damian on 2017-05-27.
 */

public class DateInfo {

    String mDate;
    float mScore;
    boolean finished;

    public DateInfo(String newDate, float newScore, boolean isFinished) {
        mDate = newDate;
        mScore = newScore;
        finished = isFinished;
    }

    public String getmDate() {
        return mDate;
    }

    public float getmScore() {
        return mScore;
    }

    public boolean getFinished(){
        return finished;
    }
}
