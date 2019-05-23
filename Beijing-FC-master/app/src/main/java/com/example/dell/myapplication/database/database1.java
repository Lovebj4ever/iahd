package com.example.dell.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Dell on 2019/4/17.
 */

public class database1 extends SQLiteOpenHelper {

    public static final String CREATE_EVENT = "create table event("
            +"name text primary key,"
            +"content text,"
            +"year int,"
            +"month int,"
            +"day int,"
            +"time text,"
            +"pic blob,"
            +"type text)";

    /*public static final String CREATE_LABEL = "create table label("
            +"id int primary key autoincrement, "
            +"background text)";*/
    private Context mContext;



    public database1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
       // db.execSQL(CREATE_LABEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
