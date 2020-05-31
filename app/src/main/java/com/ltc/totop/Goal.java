package com.ltc.totop;

import android.content.Context;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Goal {

    public String name, description, timestamp;
    public Integer priority, status;
    public Double score;
    public long id;
    public static final double a = 100;
    public static final double b = 80;
    public static final double c = 60;
    public static final double d = 40;
    public static final double e = 20;

    DB db;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss");

    public Goal(long id, String name,String description,Integer priority,Double score,Integer status,String timestamp){

        this.name = name;
        this.description = description;
        this.priority = priority;
        this.score = score;
        this.status = status;
        this.timestamp = timestamp;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimestampDate() {

        try {
            date = dateFormat.parse(timestamp);
        }catch (ParseException pe){}

        return date;

    }

    public String getTimestamp() {

        return timestamp;

    }

    public long getId(){

        return id;

    }

    public Integer getPriority() {
        return priority;
    }

    public Double getScore(){

        return score;

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer i, Context ctx){

        db = new DB(ctx);
        db.open();

        db.updateGoal("goal", id, i);

        db.close();

    }

    public static double convertScore(Integer score){

        switch (score){

            case 5: return a;
            case 4: return b;
            case 3: return c;
            case 2: return d;
            case 1: return e;

        }
        return score;
    }

}