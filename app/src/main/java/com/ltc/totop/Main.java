package com.ltc.totop;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ltc.totop.Fragments.CreateFragment;
import com.ltc.totop.Fragments.GoalsFragment;
import com.ltc.totop.Fragments.StartFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.security.AccessController.getContext;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StartFragment.CallNavBar{

    StartFragment startFragment;
    private GoalsFragment goalsFragment;
    private CreateFragment createFragment;
    public static Fragment currentFragment;
    private DB db;
    Cursor cursor;
    User test;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    Toolbar toolbar;
    Bundle bundle;
    MenuItem addGoal;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    View headerView;
    TextView txtEfficiency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        txtEfficiency = headerView.findViewById(R.id.txtEfficiency);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

      db = new DB(getApplicationContext());
      db.open();

      cursor = db.getAllData(db.DB_TABLE2);

      String name;
        name = db.getUser(cursor);

        currentFragment = new Fragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (name == null){

            startFragment = new StartFragment();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            currentFragment = startFragment;
          mFragmentTransaction.replace(R.id.container, startFragment).commit();
          }else{
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setSupportActionBar(toolbar);

           bundle = new Bundle();
           bundle.putInt("status", 3);
           goalsFragment = new GoalsFragment();
          goalsFragment.setArguments(bundle);
          mFragmentTransaction = mFragmentManager.beginTransaction();
          mFragmentTransaction.replace(R.id.container, goalsFragment).commit();
         currentFragment = goalsFragment;
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            currentFragment = null;
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_goal) {

            if (currentFragment == createFragment) {
                Toast.makeText(getApplicationContext(), "Чтобы добавить задачу, нажмите на кнопку внизу страницы", Toast.LENGTH_LONG).show();
            }else
            {
            createFragment = new CreateFragment();
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.container, createFragment).addToBackStack("tag").commit();
            currentFragment = createFragment;
        }
            return true;
        }
        else if(id==android.R.id.home) {

            drawer.openDrawer(GravityCompat.START);  // OPEN DRAWER

            cursor = db.getAllData(db.DB_TABLE);

            double eff = getEfficiency(db.getGoalsFromDataBase(cursor));
            String format = new DecimalFormat("###.##").format(eff);
            txtEfficiency.setText(format + "%");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        // Bundle значения "status": 0 - все цели, 1 - закрытые, 2 - незакрытые, 3 - цели на сегодня
        if (id == R.id.nav_goalstoday) {

          bundle = new Bundle();
          bundle.putInt("status", 3);

          goalsFragment = new GoalsFragment();
          goalsFragment.setArguments(bundle);
          mFragmentTransaction.replace(R.id.container, goalsFragment).commit();
            currentFragment = goalsFragment;

          setTitle(item.getTitle());

        }  else if (id == R.id.nav_movedgoals) {

            bundle = new Bundle();
            bundle.putInt("status", 4);

            goalsFragment = new GoalsFragment();
            goalsFragment.setArguments(bundle);
            mFragmentTransaction.replace(R.id.container, goalsFragment).commit();
            currentFragment = goalsFragment;
            setTitle(item.getTitle());

        } else if (id == R.id.nav_closedgoals) {

            bundle = new Bundle();
            bundle.putInt("status", 1);

            goalsFragment = new GoalsFragment();
            goalsFragment.setArguments(bundle);
            mFragmentTransaction.replace(R.id.container, goalsFragment).commit();
            currentFragment = goalsFragment;
            setTitle(item.getTitle());

        } else if (id == R.id.nav_unclosedgoals) {

            bundle = new Bundle();
            bundle.putInt("status", 2);

            goalsFragment = new GoalsFragment();
            goalsFragment.setArguments(bundle);
            mFragmentTransaction.replace(R.id.container, goalsFragment).commit();
            currentFragment = goalsFragment;
            setTitle(item.getTitle());

        } else if (id == R.id.nav_allgoals) {

            bundle = new Bundle();
            bundle.putInt("status", 0);

            goalsFragment = new GoalsFragment();
            goalsFragment.setArguments(bundle);
            mFragmentTransaction.replace(R.id.container, goalsFragment).commit();
            currentFragment = goalsFragment;
            setTitle(item.getTitle());

        }else if (id == R.id.nav_effectivness) {

            Toast.makeText(this, "В разработке", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_share) {

            Toast.makeText(this, "В разработке", Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean listValidity(){


        Date systemDate = Calendar.getInstance().getTime();



        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();

    }

    @Override
    public void CallBack() {

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);

    }

    public double getEfficiency(ArrayList<Goal> goals){

        double efficiency = 0;
        double total_estimated_score = 0;
        double current_score = 0;


        try {
            for (int i = 0; i < goals.size(); i++) {

                if (goals.get(i).getStatus() != 6) {
                    total_estimated_score = total_estimated_score + Goal.convertScore(goals.get(i).getPriority());
                }
                if (goals.get(i).getStatus() == 1) {

                    current_score = current_score + goals.get(i).getScore();
                    Log.d("Test current_1", String.valueOf(current_score));

                }

            }

            Log.d("Test total", String.valueOf(total_estimated_score));
            Log.d("Test current", String.valueOf(current_score));

            efficiency = (current_score / total_estimated_score) * 100;
        }catch (NullPointerException e){

            Toast.makeText(this, "Относительное значение эффективности не может быть посчитано без 1 выполеннной задачи", Toast.LENGTH_LONG).show();

        }

        return efficiency;

    }
}
