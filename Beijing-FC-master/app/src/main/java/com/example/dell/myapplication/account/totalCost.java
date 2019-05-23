package com.example.dell.myapplication.account;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapplication.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class totalCost extends AppCompatActivity {

    TextView tv;
    TickerView tickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int total=0;

        List<CostBean>alldata= (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalcost);
        String s=null;
        int costMoney=0;
        String items="";
        if(alldata!=null) {
            for (int i = 0; i < alldata.size(); i++) {
                CostBean costBean ;
                costBean = alldata.get(i);
                costMoney = Integer.parseInt(costBean.costMoney);
                total = total + costMoney;
                items=items+costBean.costTittle+" ";
            }
            s = Integer.toString(total);
            Toast.makeText(totalCost.this, s, Toast.LENGTH_SHORT).show();
        }
        tv=findViewById(R.id.txt);
        final TickerView tickerView = findViewById(R.id.amount_text);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());


        tickerView.setText("$" +total);
        tv.setText("您都消费在了："+"\n"+items);
//        List<Date>dates=new ArrayList<>();
//        String d=null;
//        String m=null;
//        String y=null;
//        Date costdate=null;
//        if(alldata!=null) {
//            for (int i = 0; i < alldata.size(); i++) {
//                CostBean costBean ;
//                costBean = alldata.get(0);
//            try {
//                costdate=getDate(costBean.costDate);
//                dates.add(costdate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            d=Integer.toString(costdate.getYear()+1900);
//            d= Integer.toString(costdate.getMonth()+1);
//            d=Integer.toString(costdate.getDate());
//            getday返回是周几
//            Toast.makeText(Main2Activity.this, d, Toast.LENGTH_SHORT).show();
//            }
//
//        }

    }

    public Date getDate(String str) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
        java.util.Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(date.getTime());
    }
    public int getmonthlycost(int n,List<CostBean> alldata) throws ParseException {
        Date costdate;
        int total=0;
        ArrayList<CostBean> list=new  ArrayList<CostBean>();
        if(alldata!=null) {
            for (int i = 0; i < alldata.size(); i++) {
                CostBean costBean;
                costBean = alldata.get(i);
                costdate=getDate(costBean.costDate);
                if (costdate.getMonth()+1==n) {
                    total = total + Integer.parseInt(costBean.costMoney);
                }
//                Toast.makeText(monthlycost.this, d, Toast.LENGTH_SHORT).show();
            }

        }
        return total;
    }



}