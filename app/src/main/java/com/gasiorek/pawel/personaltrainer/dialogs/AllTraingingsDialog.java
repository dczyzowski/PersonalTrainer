package com.gasiorek.pawel.personaltrainer.dialogs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gasiorek.pawel.personaltrainer.MainActivity;
import com.gasiorek.pawel.personaltrainer.R;
import com.gasiorek.pawel.personaltrainer.models.ListModel;

import java.util.ArrayList;

public class AllTraingingsDialog extends ListActivity {

    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_traingings_dialog);
        list = new ArrayList<>();
        list.add("Ławka");
        list.add("Hantle");
        list.add("Podciąganie na drązku");
        list.add("Wyciskanie na ławce");
        list.add("Wiosłowanie");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, list);

        setListAdapter(adapter);

        Intent intent = getIntent();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (getIntent().getIntExtra("day", 0)) {
            case 1:
            MainActivity.PlaceholderFragment.monday.add(new ListModel(list.get(position), 1));
                break;
            case 2:
                MainActivity.PlaceholderFragment.tuesday.add(new ListModel(list.get(position), 1));
                break;
            case 3:
                MainActivity.PlaceholderFragment.wednesday.add(new ListModel(list.get(position), 1));
                break;
            case 4:
                MainActivity.PlaceholderFragment.thursday.add(new ListModel(list.get(position), 1));
                break;
            case 5:
                MainActivity.PlaceholderFragment.friday.add(new ListModel(list.get(position), 1));
                break;
            case 6:
                MainActivity.PlaceholderFragment.saturday.add(new ListModel(list.get(position), 1));
                break;
            case 7:
                MainActivity.PlaceholderFragment.sunday.add(new ListModel(list.get(position), 1));
                break;
        }

        finish();
    }
}
