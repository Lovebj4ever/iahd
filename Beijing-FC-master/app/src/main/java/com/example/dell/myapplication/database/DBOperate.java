package com.example.dell.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBOperate {
    private DiaryDB mDiaryDB;
    private SQLiteDatabase mSQLiteDatabase;
    public DBOperate(Context context){
        mDiaryDB=new DiaryDB(context);
        mSQLiteDatabase=mDiaryDB.getWritableDatabase();
    }
    //添加数据
    public long insertData(String table , ContentValues values) {
        return  mSQLiteDatabase.insert(table, null, values);
    }
    //更新数据
    public void updateData(String table, ContentValues values, String whereClause, String[] whereArgs){
        mSQLiteDatabase.update(table,values,whereClause,whereArgs);
    }
    //删除数据
    public  void deleteData(String table, String whereClause, String[] whereArgs){
        mSQLiteDatabase.delete(table,whereClause,whereArgs);
    }
}
