package com.example.dell.myapplication.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.myapplication.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.List;

public class test_month extends AppCompatActivity {
    TextView ti;
    TextView ti2;
    TickerView tickerView1;
    TickerView tickerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<CostBean> monthlydata = (List<CostBean>) getIntent().getSerializableExtra("mlist");
        String c= getIntent().getStringExtra("mtotal");
        String n= getIntent().getStringExtra("mday");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_month);
        ti=findViewById(R.id.textView2);
        ti2=findViewById(R.id.textView3);
        int C= Integer.parseInt(c);
        int N=Integer.parseInt(n);
        int daily=C/N;
        String dai= Integer.toString(daily);
        ti.setText("当月记录总消费为：");
        ti2.setText("当月日均消费为：");
        final TickerView tickerView1 = findViewById(R.id.r1);
        tickerView1.setCharacterLists(TickerUtils.provideNumberList());
        final TickerView tickerView2 = findViewById(R.id.r2);
        tickerView2.setCharacterLists(TickerUtils.provideNumberList());
        tickerView1.setText(c);
        tickerView2.setText(dai);

    }
}
