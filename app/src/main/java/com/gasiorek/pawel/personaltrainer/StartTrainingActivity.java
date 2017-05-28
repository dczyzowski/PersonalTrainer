package com.gasiorek.pawel.personaltrainer;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gasiorek.pawel.personaltrainer.adapters.DailyTreningAdapter;
import com.gasiorek.pawel.personaltrainer.databases.DatesDataBase;
import com.gasiorek.pawel.personaltrainer.databases.SharedDataBase;
import com.gasiorek.pawel.personaltrainer.models.ListModel;

import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;

public class StartTrainingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    SharedDataBase db;
    Toolbar toolbar;
    ArrayList<ListModel> dailyTreningsList;
    FrameLayout historyFragment;
    FrameLayout acctualFragment;
    FrameLayout upcommingFragment;
    int accTrainingIndex = 0;
    int seriesLeft = 4;
    int skillPoints = 0;

    NumberPicker numberPicker;

    TextView seriesLeftView;
    TextView timerTextView;

    boolean fabIsStart = true;
    boolean todayDone = false;

    TextView upcommingText;
    CalendarView calendarView;

    DatesDataBase datesDataBase;

    Calendar calendar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    historyFragment.setVisibility(View.GONE);
                    upcommingFragment.setVisibility(View.GONE);
                    acctualFragment.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    historyFragment.setVisibility(View.GONE);
                    upcommingFragment.setVisibility(View.VISIBLE);
                    acctualFragment.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    historyFragment.setVisibility(View.VISIBLE);
                    upcommingFragment.setVisibility(View.GONE);
                    acctualFragment.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }

    };

    int seconds = 0;
    int hours = 0;
    int minutes = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            seconds ++;
            if (seconds == 60){
                minutes++;
                if (minutes == 60)
                    hours++;
            }

            timerTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds));

            timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        historyFragment = (FrameLayout) findViewById(R.id.history);
        acctualFragment = (FrameLayout) findViewById(R.id.act_training);
        upcommingFragment = (FrameLayout) findViewById(R.id.upcomming);
        numberPicker = (NumberPicker) findViewById(R.id.weight_picker);
        timerTextView = (TextView) findViewById(R.id.card_4_info);

        datesDataBase = new DatesDataBase(getApplicationContext());

        numberPicker.setEnabled(false);

        listView = (ListView) findViewById(R.id.todays_training_list);
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                db = new SharedDataBase(getApplicationContext(), "sunday");
                break;
            case Calendar.MONDAY:
                db = new SharedDataBase(getApplicationContext(), "monday");
                break;
            case Calendar.TUESDAY:
                db = new SharedDataBase(getApplicationContext(), "tuesday");
                break;
            case Calendar.WEDNESDAY:
                db = new SharedDataBase(getApplicationContext(), "wednesday");
                break;
            case Calendar.THURSDAY:
                db = new SharedDataBase(getApplicationContext(), "thursday");
                break;
            case Calendar.FRIDAY:
                db = new SharedDataBase(getApplicationContext(), "friday");
                break;
            case Calendar.SATURDAY:
                db = new SharedDataBase(getApplicationContext(), "saturday");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dailyTreningsList = db.getTrainings();

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(41);

        final int[] numbers = new int[205/5];
        String[] showNumbers = new String[205/5];
        int newNumber = 5;
        for(int i = 0; i < 41; i++) {
            numbers[i] = newNumber * i;
            showNumbers[i] = newNumber * i + "kg";
        }

        numberPicker.setDisplayedValues(showNumbers);

        DailyTreningAdapter adapter = new DailyTreningAdapter(this, dailyTreningsList);
        listView.setAdapter(adapter);


        final TextView titleText = (TextView) findViewById(R.id.training_title);
        upcommingText= (TextView) findViewById(R.id.card_0_info);
        upcommingText.setText(dailyTreningsList.get(accTrainingIndex).getTraningName());


        seriesLeft = dailyTreningsList.get(accTrainingIndex).getRepeats();
        seriesLeftView = (TextView) findViewById(R.id.card_1_info);
        seriesLeftView.setText(seriesLeft + "");

        titleText.setText("Rozpocznij ćwiczenie");
        titleText.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        final FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_start_training);

        fabIsStart = true;

        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        final String dat = new SimpleDateFormat("M d yyyy").format(calendar.getTime());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabIsStart){
                    if(todayDone){
                        timerHandler.removeCallbacks(timerRunnable);
                    }
                    else {
                        datesDataBase.storeNewDate(dat, false, 0);
                        fabIsStart = false;
                        fab.setImageResource(R.drawable.ic_next);
                        timerHandler.postDelayed(timerRunnable, 0);
                        numberPicker.setEnabled(true);
                        upcommingText.setText(dailyTreningsList
                                .get(accTrainingIndex + 1)
                                .getTraningName());


                        titleText.setText(dailyTreningsList.get(accTrainingIndex)
                                .getTraningName());
                    }
                }

                else {
                    seriesLeft--;
                    if(seriesLeft == -1){
                        accTrainingIndex++;
                        if(dailyTreningsList.size() == accTrainingIndex){
                            titleText.setText("Koniec ćwiczeń na dziś");
                            todayDone = true;
                            fabIsStart = true;
                            fab.setImageResource(R.drawable.ic_done);
                            int weight = numbers[numberPicker.getValue()-1];

                            skillPoints += weight*dailyTreningsList.get(accTrainingIndex - 1)
                                    .getRepeats()*8;

                            datesDataBase.storeNewDate(dat, true, skillPoints);
                        }
                        else {
                            seriesLeft = dailyTreningsList.get(accTrainingIndex).getRepeats();

                            if(dailyTreningsList.size() != accTrainingIndex + 1)
                            upcommingText.setText(dailyTreningsList.get(accTrainingIndex + 1)
                                    .getTraningName());
                            else
                                upcommingText.setText(" - ");

                            titleText.setText(dailyTreningsList.get(accTrainingIndex)
                                    .getTraningName());
                        }
                    }
                    seriesLeftView.setText(seriesLeft + "");
                    if(seriesLeft == -1) {
                        seriesLeftView.setText(" - ");
                        numberPicker.setEnabled(false);
                    }

                }
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int thisMonth = month + 1;
                String thisDate = thisMonth + " " + dayOfMonth + " " + year;
                Snackbar.make(view, "Objętość treningowa tego dnia wyniosła: " +
                                datesDataBase.getDateInfo(thisDate).getmScore() + " ",
                                BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // tworzy sciezke do pliku
            File file = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), calendar.get(Calendar.DATE) + "_Siłka.jpg");
            Uri outputFileUri = Uri.fromFile(file);

            // uruchamia apaarat i zapisuje zrobione zdjecie do powyzszej sciezki
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, 1);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

        }  else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
