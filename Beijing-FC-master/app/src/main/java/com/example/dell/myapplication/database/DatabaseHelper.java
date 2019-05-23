package com.example.dell.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dell.myapplication.account.CostBean;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context){
        super(context,"db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists cost("+

                "cost_title varchar primary key, "+
                "cost_date varchar, "+
                "cost_money varchar)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertcost(CostBean costBean){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("cost_title",costBean.costTittle);
        cv.put("cost_date",costBean.costDate);
        cv.put("cost_money",costBean.costMoney);
        database.insert("cost",null,cv);


    }
    public Cursor getallcost(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query("cost",null,null,null,null,null,"cost_date ASC");
    }
    public void  deleteAlldata(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("cost",null,null);
    }
}
