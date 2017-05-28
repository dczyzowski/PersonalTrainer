package com.gasiorek.pawel.personaltrainer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gasiorek.pawel.personaltrainer.adapters.TrainingListAdapter;
import com.gasiorek.pawel.personaltrainer.databases.SharedDataBase;
import com.gasiorek.pawel.personaltrainer.dialogs.AllTraingingsDialog;
import com.gasiorek.pawel.personaltrainer.models.ListModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedDataBase shared = new SharedDataBase(getApplicationContext());

        if (shared.isSaved()){
            Intent intent = new Intent(getBaseContext(), StartTrainingActivity.class);
            startActivity(intent);
            finish();
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setStatusBarTranslucent(true);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        TrainingListAdapter trainingAdapter;

        public static ArrayList<ListModel> monday = new ArrayList<>();
        public static ArrayList<ListModel> tuesday = new ArrayList<>();
        public static ArrayList<ListModel> wednesday = new ArrayList<>();
        public static ArrayList<ListModel> thursday = new ArrayList<>();
        public static ArrayList<ListModel> friday = new ArrayList<>();
        public static ArrayList<ListModel> saturday = new ArrayList<>();
        public static ArrayList<ListModel> sunday = new ArrayList<>();

        ListView listView;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            TextView textView = (TextView) rootView.findViewById(R.id.main_label);
            RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.fragment_relative);

            listView = (ListView) rootView.findViewById(R.id.trainingList);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    layout.setGravity(Gravity.CENTER);
                    textView.setText(R.string.welcome_title);
                    fab.setVisibility(View.GONE);
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorLightBlue));
                    return rootView;
                case 2:
                    trainingAdapter = new TrainingListAdapter(getActivity(), monday);

                    listView.setAdapter(trainingAdapter);
                    textView.setTextSize(56);
                    textView.setText(R.string.monday);

                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(), R.color.colorAccentForGreen)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorGreen));

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 1);
                            startActivityForResult(intent, 1);
                        }
                    });
                    return rootView;
                case 3:
                    trainingAdapter = new TrainingListAdapter(getActivity(), tuesday);
                    listView.setAdapter(trainingAdapter);
                    textView.setText(R.string.tuesday);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(), R.color.colorAccentForOrange)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorOrange));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 2);
                            startActivityForResult(intent, 2);
                        }
                    });
                    return rootView;
                case 4:
                    trainingAdapter = new TrainingListAdapter(getActivity(), wednesday);
                    listView.setAdapter(trainingAdapter);
                    textView.setText(R.string.wednesday);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(), R.color.colorAccentForBlue)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorBlue));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 3);
                            startActivityForResult(intent, 3);
                        }
                    });
                    return rootView;
                case 5:
                    trainingAdapter = new TrainingListAdapter(getActivity(), thursday);
                    listView.setAdapter(trainingAdapter);
                    textView.setText(R.string.thursday);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(),  R.color.colorAccentForRed)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorRed));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 4);
                            startActivityForResult(intent, 4);
                        }
                    });
                    return rootView;
                case 6:
                    trainingAdapter = new TrainingListAdapter(getActivity(), friday);
                    listView.setAdapter(trainingAdapter);
                    textView.setText(R.string.friday);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(), R.color.colorAccentForLime)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorLime));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 5);
                            startActivityForResult(intent, 5);
                        }
                    });
                    return rootView;
                case 7:
                    trainingAdapter = new TrainingListAdapter(getActivity(), saturday);
                    listView.setAdapter(trainingAdapter);
                    textView.setText(R.string.saturday);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(), R.color.colorAccentForPurple)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorPurple));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 6);
                            startActivityForResult(intent, 6);
                        }
                    });
                    return rootView;
                case 8:
                    trainingAdapter = new TrainingListAdapter(getActivity(), sunday);
                    listView.setAdapter(trainingAdapter);
                    textView.setText(R.string.sunday);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat
                            .getColor(getContext(), R.color.colorAccentForTeal)));
                    rootView.setBackgroundColor(ContextCompat.getColor(getContext(),
                            R.color.colorTeal));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AllTraingingsDialog.class);
                            intent.putExtra("day", 7);
                            startActivityForResult(intent, 7);
                        }
                    });

                    Button saveButton = (Button) rootView.findViewById(R.id.save_button);
                    saveButton.setVisibility(View.VISIBLE);

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SharedDataBase(getContext(), "monday").storeAllTrainingsList(monday);
                            new SharedDataBase(getContext(), "tuesday").storeAllTrainingsList(tuesday);
                            new SharedDataBase(getContext(), "wednesday").storeAllTrainingsList(wednesday);
                            new SharedDataBase(getContext(), "thursday").storeAllTrainingsList(thursday);
                            new SharedDataBase(getContext(), "friday").storeAllTrainingsList(friday);
                            new SharedDataBase(getContext(), "saturday").storeAllTrainingsList(saturday);
                            new SharedDataBase(getContext(), "sunday").storeAllTrainingsList(sunday);

                            new SharedDataBase(getContext()).setSaved(true);

                            Intent intent = new Intent(getContext(), StartTrainingActivity.class);
                            startActivity(intent);
                        }
                    });
                    return rootView;
            }
            return null;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            listView.setAdapter(trainingAdapter);

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
                case 5:
                    return "SECTION 6";
                case 6:
                    return "SECTION 7";
                case 7:
                    return "SECTION 8";
            }
            return null;
        }

    }
    }
