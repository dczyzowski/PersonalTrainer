package com.gasiorek.pawel.personaltrainer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gasiorek.pawel.personaltrainer.R;
import com.gasiorek.pawel.personaltrainer.models.ListModel;

import java.util.List;

/**
 * Created by Damian on 2017-05-24.
 */

public class DailyTreningAdapter extends ArrayAdapter<ListModel>{


    Activity context;
    List<ListModel> trainingList;

    /*
    adapter generuje liste rzeczy ktore maja sie znajdowac w ListView. Tutaj generujemy liste
    wszystkich produktów
    */

    public DailyTreningAdapter(Activity context, List<ListModel> trainingList){
        super(context, R.layout.daily_list_row, trainingList);

        this.context = context;
        this.trainingList = trainingList;
    }

    //deklaruje z klasy abstrakcyjnej klase statyczna
    private static class ViewHolder {
        private TextView trainingName;
        private TextView mRepeats;
    }


    //generuje mi kazdy wiersz w listView

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.daily_list_row, parent, false);

        final DailyTreningAdapter.ViewHolder holder = new DailyTreningAdapter.ViewHolder();
        final ListModel training = trainingList.get(position);

        holder.trainingName = (TextView) view.findViewById(R.id.dailytext);
        holder.mRepeats = (TextView) view.findViewById(R.id.dailyrepeats);

        holder.trainingName.setText(training.getTraningName());
        holder.mRepeats.setText("" + trainingList.get(position).getRepeats());


        return view;
    }

}
