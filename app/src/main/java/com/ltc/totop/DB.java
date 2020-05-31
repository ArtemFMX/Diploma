package com.ltc.totop;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Artem on 01.03.2016.
 */
public class DB {

    private static final String DB_NAME = "MDP";
    private static final int DB_VERSION = 1;
    public static final String COLUMN_ID = "_id";

    public static final String DB_TABLE = "goal";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIMETOCLOSE = "timetoclose";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_STATUS = "status"; // 1 - закрыта, 2 - незакрыта, 3 - цель на сегодня, 4 - перенесена, 5 - скрыта, 6 - удалена.
    public static final String COLUMN_DATE = "date";

    public static final String DB_TABLE2 = "user";
    public static final String COLUMN_USERNAME = "name";
    public static final String COLUMN_RATE = "rate";

    public static final String DB_TABLE3 = "rates";

    private static final String DB_CREATE = "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, "+
                    COLUMN_NAME +" text, "+
                    COLUMN_DESCRIPTION + " text," +
                    COLUMN_TIMETOCLOSE + " text," +
                    COLUMN_PRIORITY + " integer," +
                    COLUMN_SCORE + " double," +
                    COLUMN_STATUS + " integer," +
                    COLUMN_DATE + " text" +
                    ");";

    private static final String DB_CREATE2 = "create table " + DB_TABLE2 + "("+
                    COLUMN_ID + " integer primary key autoincrement, "+
                    COLUMN_USERNAME + " text,"+
                    COLUMN_RATE + " double"+
                    ");";

    private static final String DB_CREATE3 = "create table " + DB_TABLE3 + "("+
            COLUMN_ID + " integer primary key autoincrement, "+
            COLUMN_DATE + " text,"+
            COLUMN_RATE + " double"+
            ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }


    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }


    public Cursor getAllData(String tab) {
        if(tab == null || tab.equals("")){
            return null;
        }else {
            String[] a = new String[1];
            a[0] = COLUMN_NAME;
            return mDB.query(tab, null, null, null, null, null, null);
        }
    }

    public void deleteAll(){
        mDB.delete("goal", null, null);
        mDB.delete("user", null, null);
    }
    public void delDel(long id){

        mDB.delete("goal", COLUMN_ID + " = " + id, null);
        Log.d("SQLid", String.valueOf(id));

    }

    public long addUser(String db, String name, Integer rate) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, name);
        cv.put(COLUMN_RATE, rate);
        return mDB.insert(db, null, cv);
    }

    public long addGoal(String db, String name, String description , Integer priority, Double score, Integer status, String timeStamp) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_PRIORITY, priority);
        cv.put(COLUMN_SCORE, score);
        cv.put(COLUMN_STATUS, status);
        cv.put(COLUMN_DATE, timeStamp);
        return mDB.insert(db, null, cv);
    }

    public void delRec(String db, long id) {

            mDB.delete(db, COLUMN_ID + " = " + id, null);

    }

    public void updateGoal(String db, long id, Integer update){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, update);
          mDB.update(db, cv, COLUMN_ID + " = " + id, null);

    }

    /////////////////////////////////////
    public String[] getStringFromDataBase(Cursor c, String text) {

        String str[] = new String[c.getCount()];
        if (c.moveToFirst()) {
            int a = 0;
            do {
                str[a] = c.getString(c.getColumnIndex(text));

                a++;

            } while (c.moveToNext());

        } else {
            return null;
        }

        return str;
    }

    public String getUser(Cursor c){

        String a = new String();

        if(c.moveToFirst()){
            Log.d("test","Test6.1");

            do{

                a = c.getString(c.getColumnIndex(COLUMN_NAME));
                Log.d("test",a);

            } while (c.moveToNext());

            return a;

        }else{
            return null;
        }
    }

    public ArrayList<Goal> getGoalsFromDataBase(Cursor c) {

        ArrayList<Goal> goals = new ArrayList<Goal>();

        if (c.moveToFirst()) {

            String a,b,date;
            long id;
            Integer e,r;
            Double d;

            do {
                id = c.getLong(c.getColumnIndex(COLUMN_ID));
                a = c.getString(c.getColumnIndex(COLUMN_NAME));
                b = c.getString(c.getColumnIndex(COLUMN_DESCRIPTION));
                e = c.getInt(c.getColumnIndex(COLUMN_PRIORITY));
                d = c.getDouble(c.getColumnIndex(COLUMN_SCORE));
                r = c.getInt(c.getColumnIndex(COLUMN_STATUS));
                date = c.getString(c.getColumnIndex(COLUMN_DATE));

                goals.add(new Goal(id, a, b, e, d, r, date));


            } while (c.moveToNext());

        } else {
            return null;
        }

        return goals;
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            db.execSQL(DB_CREATE2);
            db.execSQL(DB_CREATE3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}