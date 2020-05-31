package com.ltc.totop.Fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import com.ltc.totop.DB;
import com.ltc.totop.Goal;
import com.ltc.totop.Main;
import com.ltc.totop.R;
import com.ltc.totop.User;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {

    EditText edtName, edtDescription;
    RatingBar ratingBar;
    Button go;
    Goal goal;

    static private DB db;
    ArrayList<Goal> goals = new ArrayList<Goal>();

    private GoalsFragment goalsFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create, container, false);

        edtName = v.findViewById(R.id.edtName);
        edtDescription = v.findViewById(R.id.edtDescription);
        go = v.findViewById(R.id.go);

        ratingBar = v.findViewById(R.id.ratingBar);

        db = new DB(getContext());
        db.open();

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(edtName.getText().toString().equals(""))){

                    DateFormat df = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss");
                    Date date = Calendar.getInstance().getTime(); // pack date
                    String strdate = df.format(date);

                    float scoref = ratingBar.getRating();
                    int score = (int) Math.round(scoref);

                    Toast.makeText(getContext(), strdate, Toast.LENGTH_LONG).show();
                    goal = new Goal(0 ,edtName.getText().toString(), edtDescription.getText().toString(), score, Goal.convertScore(score), 3 , strdate);
                    Toast.makeText(getContext(),goal.getScore().toString(), Toast.LENGTH_LONG).show();
                    goals.add(goal);

                    db.addGoal("goal", goal.getName(), goal.getDescription(), goal.getPriority(), goal.getScore(), goal.getStatus(), goal.getTimestamp());
                    db.close();

                    mFragmentManager = getActivity().getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status", 3);
                    goalsFragment = new GoalsFragment();
                    goalsFragment.setArguments(bundle);
                    Main.currentFragment = goalsFragment;
                    mFragmentTransaction.replace(R.id.container, goalsFragment).commit();

                }else{

                    Toast.makeText(getContext(), "Заполните поля Имя и Время окончания цели", Toast.LENGTH_LONG).show();

                }

            }
        });


        return v;
    }

    String makeString(String hours, String minutes, String seconds){
        String a;

        a = hours+":"+minutes+":"+seconds;

        return a;
    }

}
