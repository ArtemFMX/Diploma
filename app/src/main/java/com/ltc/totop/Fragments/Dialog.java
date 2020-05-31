package com.ltc.totop.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ltc.totop.R;

public class Dialog extends DialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    private String description;
    TextView txtdescription;

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

        description = getArguments().getString("description");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog, null);

        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.btnNo).setOnClickListener(this);
        txtdescription = v.findViewById(R.id.txtdescription);
        txtdescription.setText(description);

        return v;
    }

    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setLogo(R.drawable.ic_menu_camera);
        window.setGravity(Gravity.CENTER);
    }
}
