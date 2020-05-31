package com.ltc.totop.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ltc.totop.Goal;
import com.ltc.totop.R;

import java.util.ArrayList;
import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private ArrayList<Goal> goals;
    private Context context;
    static Integer listCheck = 0;

    public interface CallInfoDialog{

        void CallBackShow(long id);

    }

    CallInfoDialog cid;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName, tvPriority, txtStatus;
        public Button completed, x;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPriority = (TextView) view.findViewById(R.id.tvPriority);

            if(listCheck == 0) {

                completed = (Button) view.findViewById(R.id.completed);
                x = (Button) view.findViewById(R.id.x);

            }else{

                txtStatus = (TextView) view.findViewById(R.id.txtstatus);

            }

        }

    }

    public GoalsAdapter(ArrayList<Goal> goals, Context context, Integer listCheck, CallInfoDialog callback) {
        this.goals = goals;
        this.context = context;
        this.listCheck = listCheck;
        this.cid = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if(listCheck==0) {

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_goals_list_item, parent, false);
        }else{

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_goals_list_item_status, parent, false);

        }
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public int getItemCount() {

        return goals.size();
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Goal goal = goals.get(position);

        holder.tvName.setText(goal.getName() + "\n" + "Дата исполнения: " + goal.getTimestamp());

        holder.tvPriority.setText(goal.getPriority().toString());

        if(listCheck == 0) {
            holder.completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    goal.setStatus(1, context);
                    goals.remove(position);

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());

                    Snackbar.make(v, "Status has been changed on 'closed'", Snackbar.LENGTH_LONG).show();

                }
            });

            holder.x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    goal.setStatus(2, context);
                    goals.remove(position);

                    Toast.makeText(context, "It works " + position + " status " + goal.getStatus(), Toast.LENGTH_LONG).show();

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());

                    Snackbar.make(v, "Status has been changed on 'unclosed'", Snackbar.LENGTH_LONG).show();

                }
            });
        }else{
            String output;

            switch (goal.getStatus()){

                case 1:
                    output = "Выполнено";
                    break;

                case 2:
                    output = "Невыполнено";
                    break;

                default: output = "Неопределено";

            }
            holder.txtStatus.setText(output);

        }

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cid.CallBackShow(goal.getId());

            }
        });

    }
}