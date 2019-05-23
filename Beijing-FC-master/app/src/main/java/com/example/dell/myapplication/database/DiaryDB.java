package com.example.dell.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DiaryDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "diary.db";// 数据库名称
    public static final int VERSION = 1;

    public static final String TABLE= "diary";   //表

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "title" ;
    public static final String COLUMN_CONTENT = "content";

    private Context context;
    public DiaryDB(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DIARY="create table if not exists "+ TABLE +"("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE +  " TEXT , " +
                COLUMN_TITLE + "1 TEXT , " +
                COLUMN_CONTENT + "1 TEXT , " +
                COLUMN_TITLE + "2 TEXT , " +
                COLUMN_CONTENT + "2 TEXT , " +
                COLUMN_TITLE + "3 TEXT , " +
                COLUMN_CONTENT + "3 TEXT , " +
                COLUMN_TITLE + "4 TEXT , " +
                COLUMN_CONTENT + "4 TEXT)";
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table " + TABLE) ;
        onCreate(db);
    }
}
