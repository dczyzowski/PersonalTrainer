package com.gasiorek.pawel.personaltrainer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.DragEvent;
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
 * Created by Damian on 2017-05-23.
 */

public class TrainingListAdapter extends ArrayAdapter<ListModel>{


    Activity context;
    List<ListModel> trainingList;

    /*
    adapter generuje liste rzeczy ktore maja sie znajdowac w ListView. Tutaj generujemy liste
    wszystkich produktów
    */

    public TrainingListAdapter(Activity context, List<ListModel> trainingList){
        super(context, R.layout.list_row, trainingList);

        this.context = context;
        this.trainingList = trainingList;
    }

    //deklaruje z klasy abstrakcyjnej klase statyczna
    public static class ViewHolder {
        public TextView trainingName;
        public NumberPicker mRepeats;
    }


    //generuje mi kazdy wiersz w listView

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_row, parent, false);

        final ViewHolder holder = new ViewHolder();
        final ListModel training = trainingList.get(position);

        holder.trainingName = (TextView) view.findViewById(R.id.text);
        holder.mRepeats = (NumberPicker) view.findViewById(R.id.repeats);

        holder.trainingName.setText(training.getTraningName());
        holder.mRepeats.setMinValue(0);
        holder.mRepeats.setMaxValue(10);

        holder.mRepeats.setValue(trainingList.get(position).getRepeats());

        holder.mRepeats.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                trainingList.get(position).setRepeats(newVal);
            }
        });

        return view;
    }

}