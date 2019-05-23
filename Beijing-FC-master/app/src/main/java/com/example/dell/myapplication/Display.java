package com.example.dell.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.myapplication.database.database1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

public class Display extends AppCompatActivity {
    database1 dbHelper = new database1(this,"SpecialDay.db",null,1);
    byte[] temp = null;
    byte[] img = null;
    ArrayList<byte[]> imgData = new ArrayList<>();
    ImageView p1;
    Calendar calendar = Calendar.getInstance();
    int year_now;
    int month_now;
    int day_now;
    String[] key;
    String output1;
    String output2;
    String output3;
    String output4;
    String output5;
    String output3_1;
    int sumday;
    int year;
    int month;
    int day;
    daycalculate t;
    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }


    private void setImage(Uri uri){
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            p1.setImageBitmap(bitmap);
        }
        catch(FileNotFoundException e ){
            e.printStackTrace();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        System.out.println("Display start");
        Intent intent = getIntent();
        Button f = (Button) findViewById(R.id.finish);
        Button delete = (Button) findViewById(R.id.delete);
        SQLiteDatabase d = dbHelper.getWritableDatabase();
        Cursor cursor = d.query("event",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                img = cursor.getBlob(cursor.getColumnIndex("pic"));
                imgData.add(img);
            }while (cursor.moveToNext());
        }
        cursor.close();
        if(img==null){
            System.out.println("null");
        }
        else{
            System.out.println("not null");
        }
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Display.this,SpecialDay.class);
                finish();
            }
        });

        TextView t1 = (TextView) findViewById(R.id.t1);
        TextView t2 = (TextView) findViewById(R.id.t2);
        TextView t3 = (TextView) findViewById(R.id.t3);
        TextView t4 = (TextView) findViewById(R.id.t4);
        TextView t5 = (TextView) findViewById(R.id.t5);
        TextView t6 = (TextView)findViewById(R.id.t6);
        int position = intent.getIntExtra("pos",0);
        Bitmap show = BitmapFactory.decodeByteArray(imgData.get(position),0,imgData.get(position).length);
        p1 = (ImageView) findViewById(R.id.p1);
        p1.setImageBitmap(show);

        output1 = intent.getStringExtra("name");
        output2 = intent.getStringExtra("content");
        output3 = intent.getStringExtra("type");
        output3_1 = intent.getStringExtra("type1");
        System.out.println(output3);

        output4 = intent.getStringExtra("time");
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day = intent.getIntExtra("day",0);
        year_now = calendar.get(Calendar.YEAR);
        month_now = calendar.get(Calendar.MONTH)+1;
        day_now = calendar.get(Calendar.DAY_OF_MONTH);
        if(output3_1.equals("倒数日")) {
            output5 = intent.getStringExtra("date1");
            t = new daycalculate(year_now, year, month_now, month, day_now, day);
            sumday = t.sumdays();
            String s = Integer.toString(sumday);
            t6.setText("剩余时间"+s+"天");
            if(sumday==0){
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String[] args = {String.valueOf(output1)};
                db.delete("event","name=?",args);
                Intent start = new Intent(Display.this,SpecialDay.class);
                startActivity(start);
                finish();
            }
        }
        else{
            output5 = intent.getStringExtra("date2");
            t = new daycalculate(year_now,year_now,month_now,month,day_now,day);
            if(month_now>month||(month_now==month&&day_now>=day)){
                t = new daycalculate(year_now,year_now+1,month,month_now,day,day_now);
            }
            sumday = t.sumdays();
            if(sumday<=0){
                sumday = 0-sumday;
            }
            String s = Integer.toString(sumday);
            t6.setText("纪念日剩余时间"+s+"天");
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String[] args = {String.valueOf(output1)};
                db.delete("event","name=?",args);
                Intent start = new Intent(Display.this,SpecialDay.class);
                startActivity(start);
                finish();
            }
        });

       /// System.out.println(uri);
        t1.setText(output1);
        t2.setText(output2);
        t3.setText(output3);
        t4.setText(output4);
        t5.setText(output5);

       // Uri input = Uri.parse(uri);
       // setImage(input);
    }
}
