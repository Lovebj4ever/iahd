package com.example.dell.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class date_calculate extends AppCompatActivity {

    LinearLayout layout1,layout2;
    TextView start;
    TextView end;
    TextView result;
    int y1,m1,d1,y2,m2,d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_calculate);

        layout1 = (LinearLayout) findViewById(R.id.date_cal_1);
        layout2 = (LinearLayout) findViewById(R.id.date_cal_2);
        start = (TextView) findViewById(R.id.startdate);
        end = (TextView) findViewById(R.id.enddate);
        result = (TextView) findViewById(R.id.result);

        Calendar today = Calendar.getInstance();
        String str = ((new SimpleDateFormat("yyyy-MM-dd")).format(today.getTime()));
        start.setText(str);
        end.setText(str);


        layout1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(date_calculate.this);
                localBuilder.setTitle("选择时间");

                final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.activity_datepick, null);
                localBuilder.setView(layout_alert);

                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        DatePicker datepicker1= (DatePicker) layout_alert.findViewById(R.id.datepick);

                        y1=datepicker1.getYear();
                        m1=datepicker1.getMonth()+1;
                        d1=datepicker1.getDayOfMonth();
                        System.out.println("y:"+y1+" m:"+m1+" d:"+d1);
                        start.setText(y1+"-"+m1+"-"+d1); //  获取时间


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {

                    }
                }).create().show();
            }
        });


        layout2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(date_calculate.this);
                localBuilder.setTitle("选择时间");

                final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.activity_datepick, null);
                localBuilder.setView(layout_alert);

                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        DatePicker datepicker1= (DatePicker) layout_alert.findViewById(R.id.datepick);

                        y2=datepicker1.getYear();
                        m2=datepicker1.getMonth()+1;
                        d2=datepicker1.getDayOfMonth();
                        System.out.println("y:"+y2+" m:"+m2+" d:"+d2);
                        end.setText(y2+"-"+m2+"-"+d2); //  获取时间

                        daycalculate cal = new daycalculate(y1,y2,m1,m2,d1,d2);
                        int days = cal.sumdays();
                        result.setText(String.valueOf(days));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {

                    }
                }).create().show();
            }
        });

    }

}
