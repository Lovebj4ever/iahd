package com.example.dell.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.myapplication.database.database1;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    String data;
    database1 dbHelper;
    ArrayList<String> output1= new ArrayList<>();
    ArrayList<String> output2= new ArrayList<>();
    ArrayList<String> output3= new ArrayList<>();
    ArrayList<String> output4= new ArrayList<>();
    EditText edit;
    ArrayList<Integer> year = new ArrayList<>();
    ArrayList<Integer> month = new ArrayList<>();
    ArrayList<Integer> day = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbHelper = new database1(this,"SpecialDay.db",null,1);
        edit = (EditText) findViewById(R.id.search);
        //data = edit.getText().toString();
        Button button = (Button) findViewById(R.id.sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase d = dbHelper.getWritableDatabase();
                Cursor cursor = d.query("event",null,null,null,null,null,null);
                data = edit.getText().toString();
                if(cursor.moveToFirst()){
                    do{
                        output1.add(cursor.getString(cursor.getColumnIndex("content")));
                        output2.add(cursor.getString(cursor.getColumnIndex("name")));
                        year.add(cursor.getInt(cursor.getColumnIndex("year")));
                        month.add(cursor.getInt(cursor.getColumnIndex("month")));
                        day.add(cursor.getInt(cursor.getColumnIndex("day")));
                        output3.add(cursor.getString(cursor.getColumnIndex("type")));
                        output4.add(cursor.getString(cursor.getColumnIndex("time")));
                        //System.out.println(output1);
                        //System.out.println(output2);
                        //System.out.println("yy:"+year+"mm:"+month+"dd:"+day);
                        //System.out.println(output2);
                        //System.out.println(output4);

                    }while(cursor.moveToNext());
                }
                cursor.close();
                int pos = output2.indexOf(data);
                Intent intent = new Intent(Search.this,Display.class);
                intent.putExtra("name","title: "+output2.get(pos)+"\n");
                intent.putExtra("content","content: "+output1.get(pos)+"\n");
                intent.putExtra("type","type: "+output3.get(pos)+"\n");
                intent.putExtra("time","time: "+output4.get(pos)+"\n");
                intent.putExtra("date","year: "+year.get(pos)+" month: "+month.get(pos)+" day: "+day.get(pos)+"\n");
                startActivity(intent);
                //System.out.println(output2.get(pos));
            }
        });
    }
}
