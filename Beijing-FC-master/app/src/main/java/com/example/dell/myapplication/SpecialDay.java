package com.example.dell.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dell.myapplication.database.database1;

import java.util.ArrayList;
import java.util.Calendar;

public class SpecialDay extends AppCompatActivity {
    DataAdapter adapter;
    FloatingActionButton newEvent;
    database1 dbHelper;
    byte[] img = null;
    String temp = null;
    ArrayList<byte[]> pic = new ArrayList<>();
    ArrayList<byte[]> imgData = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> uri = new ArrayList<>();
    ArrayList<Integer> year = new ArrayList<>();
    ArrayList<Integer> month = new ArrayList<>();
    ArrayList<Integer> day = new ArrayList<>();
    ArrayList<Data> new_data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_day);

        dbHelper = new database1(this,"SpecialDay.db",null,1);
        SQLiteDatabase d = dbHelper.getWritableDatabase();
        Cursor cursor = d.query("event",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Calendar calendar = Calendar.getInstance();
                int year_now = calendar.get(Calendar.YEAR);
                int month_now = calendar.get(Calendar.MONTH)+1;
                int day_now = calendar.get(Calendar.DAY_OF_MONTH);
                daycalculate t = new daycalculate(cursor.getInt(cursor.getColumnIndex("year")),year_now,cursor.getInt(cursor.getColumnIndex("month")),month_now,cursor.getInt(cursor.getColumnIndex("day")),day_now);
                int sumday = t.sumdays();
                if((cursor.getString(cursor.getColumnIndex("type")).equals("倒数日")&&sumday!=0)||cursor.getString(cursor.getColumnIndex("type")).equals("纪念日")) {
                    name.add(cursor.getString(cursor.getColumnIndex("name")));
                    content.add(cursor.getString(cursor.getColumnIndex("content")));
                    type.add(cursor.getString(cursor.getColumnIndex("type")));
                    time.add(cursor.getString(cursor.getColumnIndex("time")));
                    year.add(cursor.getInt(cursor.getColumnIndex("year")));
                    month.add(cursor.getInt(cursor.getColumnIndex("month")));
                    day.add(cursor.getInt(cursor.getColumnIndex("day")));

                    img = cursor.getBlob(cursor.getColumnIndex("pic"));
                    if (img != null) {
                        imgData.add(img);
                        pic.add(img);
                    }
                    data.add((cursor.getString(cursor.getColumnIndex("name"))) + " year " + cursor.getString(cursor.getColumnIndex("year")) + " month " + cursor.getString(cursor.getColumnIndex("month")) + " day " + cursor.getString(cursor.getColumnIndex("day")));
                    Data dd = new Data(cursor.getString(cursor.getColumnIndex("name")) + " " + cursor.getString(cursor.getColumnIndex("year")) + "-" + cursor.getString(cursor.getColumnIndex("month")) + "-" + cursor.getString(cursor.getColumnIndex("day")), R.drawable.d2);
                    new_data.add(dd);
                }
                //System.out.println(dd.getName());
            }while(cursor.moveToNext());
        }
        cursor.close();
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpecialDay.this,android.R.layout.simple_list_item_1,data);
        ListView listview = (ListView) findViewById(R.id.event_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SpecialDay.this,Display.class);
                intent.putExtra("name",name.get(position));
                intent.putExtra("content","content: "+content.get(position)+"\n");
                intent.putExtra("type","type: "+type.get(position)+"\n");
                intent.putExtra("time","time: "+time.get(position)+"\n");
                intent.putExtra("date","year: "+year.get(position)+" month: "+month.get(position)+" day: "+day.get(position)+"\n");

                startActivity(intent);
            }
        });*/

            adapter = new DataAdapter(SpecialDay.this, R.layout.data_item, new_data);
            ListView listview = (ListView) findViewById(R.id.event_list);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(SpecialDay.this,Display.class);
                    intent.putExtra("pos",position);
                    intent.putExtra("year",year.get(position));
                    intent.putExtra("month",month.get(position));
                    intent.putExtra("day",day.get(position));
                    intent.putExtra("name",name.get(position));
                    intent.putExtra("content","content: "+content.get(position)+"\n");
                    intent.putExtra("type","type: "+type.get(position)+"\n");
                    intent.putExtra("type1",type.get(position));
                    intent.putExtra("time","time: "+time.get(position)+"\n");
                    intent.putExtra("date1","date: "+year.get(position)+"-"+month.get(position)+"-"+day.get(position)+"\n");
                    intent.putExtra("date2","date: "+month.get(position)+"-"+day.get(position)+"\n");
                    startActivity(intent);
                }
            });

            newEvent = (FloatingActionButton) findViewById(R.id.new_event_button);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        newEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialDay.this, NewEvent.class);
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.calculater:
                Intent intent1 = new Intent(SpecialDay.this, date_calculate.class);
                startActivity(intent1);
                break;
            case R.id.d:
                SQLiteDatabase d = dbHelper.getWritableDatabase();
                d.delete("event",null,null);
                new_data.clear();
                adapter.notifyDataSetChanged();
                /*Intent intent2 = new Intent(SpecialDay.this,SpecialDay.class);
                startActivity(intent2);
                finish();*/
                return false;
            default:
        }
        return true;
    }


}
