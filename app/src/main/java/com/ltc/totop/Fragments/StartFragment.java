package com.ltc.totop.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.Toast;

import com.ltc.totop.DB;
import com.ltc.totop.R;
import com.ltc.totop.User;

import java.sql.Time;

public class StartFragment extends Fragment {

    private DB db;
    private Cursor cursor;
    static User user;
    EditText name, dayStart, dayEnd;
    Button toTop;
    Context context;

    private GoalsFragment goalsFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public interface CallNavBar{
        void CallBack();
    }

    private CallNavBar cnb;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallNavBar) {
            cnb = (CallNavBar) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SampleCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        name = (EditText) v.findViewById(R.id.name);


        toTop = (Button) v.findViewById(R.id.totop);

        db = new DB(getContext());
        db.open();
        cursor = db.getAllData("user");

        toTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cnb.CallBack();

                user = new User(name.getText().toString(), 0);
                long i = db.addUser("user", user.name, 0);
                Toast.makeText(getContext(), user.name + i, Toast.LENGTH_LONG).show();

               Snackbar.make(v, "Регистрация прошла успешно", Snackbar.LENGTH_LONG).show();
                mFragmentManager = getActivity().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("status", 3);
                goalsFragment = new GoalsFragment();
                goalsFragment.setArguments(bundle);
                mFragmentTransaction.replace(R.id.container, goalsFragment).commit();

            }
        });

        return v;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
