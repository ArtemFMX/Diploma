package com.ltc.totop.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ltc.totop.Adapters.GoalsAdapter;
import com.ltc.totop.DB;
import com.ltc.totop.Goal;
import com.ltc.totop.Main;
import com.ltc.totop.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment implements GoalsAdapter.CallInfoDialog{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mGoalsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Goal> goals;
    private DB db;
    Cursor cursor;
    Integer status = 0;
    DialogFragment infoTask;
    Bundle b = new Bundle();

    @Override
    public void CallBackShow(long id) {

        Toast.makeText(getContext(), "Создайте цель", Toast.LENGTH_LONG).show();

        b.putString("description", description(id));
        infoTask.setArguments(b);
        infoTask.show(getFragmentManager(), "Dialog");

    }

    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        infoTask = new Dialog();

        status = getArguments().getInt("status");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        db = new DB(getContext());
        db.open();
        cursor = db.getAllData("goal");
        goals = new ArrayList<Goal>();

        goals = db.getGoalsFromDataBase(cursor);

        if(goals != null) {
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            if((status==3) || (status==4)) {
                mGoalsAdapter = new GoalsAdapter(neededGoals(goals, status), getContext(), 0, this);
            }else{
                mGoalsAdapter = new GoalsAdapter(neededGoals(goals, status), getContext(), 1,this);
            }
            mRecyclerView.setAdapter(mGoalsAdapter);

        }else{

            Toast.makeText(getContext(), "Создайте цель", Toast.LENGTH_LONG).show();

        }
        Log.d("LOL","test");

        return v;
    }

    String description(long goalID){

        for(int i=0; i<goals.size();i++){

            if (goals.get(i).getId()==goalID){
                return goals.get(i).getDescription();
            }
        }

         return null;

    }

    public ArrayList<Goal> neededGoals(ArrayList<Goal> goals, Integer status){
        ArrayList<Goal> neededGoals = new ArrayList<Goal>();

        switch (status){

            case 0:

                return goals;

            case 1:

                for (int i = 0; i<goals.size(); i++){

                    if(goals.get(i).getStatus()==status){

                        neededGoals.add(goals.get(i));

                    }

                }

                break;

            case 2:

                for (int i = 0; i<goals.size(); i++){

                    if(goals.get(i).getStatus()==status){

                        neededGoals.add(goals.get(i));

                    }

                }

                break;

            case 3:

                for (int i = 0; i<goals.size(); i++){

                    if(goals.get(i).getStatus()==status){

                        neededGoals.add(goals.get(i));

                    }

                }

                Main.listValidity();

                break;

            case 4:

                for (int i = 0; i<goals.size(); i++){

                    if(goals.get(i).getStatus()==status){

                        neededGoals.add(goals.get(i));

                    }

                }

                break;

        }


        return neededGoals;
    }

}
