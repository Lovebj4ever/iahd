package com.example.dell.myapplication.account;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapplication.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class monthlycost extends AppCompatActivity {
    TextView tv;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<CostBean> alldata = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthlycost);
            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View arg0) {
                    // 指定控件
                    tv = (TextView) findViewById(R.id.action_monthly);
                    editText = findViewById(R.id.monthinput);
                    String temp = editText.getText().toString();
                    if (!(temp.equals("1")||temp.equals("2")||temp.equals("3")||temp.equals("4")||temp.equals("5")||temp.equals("6")||temp.equals("7")||temp.equals("8")||temp.equals("9")||temp.equals("10")||temp.equals("11")||temp.equals("12"))) {
                        Toast.makeText(monthlycost.this, "请输入有效月份", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int c = Integer.parseInt(temp);
                        int[] monthlycost = new int[2];

                        try {
                            monthlycost = getmonthlycost(c, alldata);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(monthlycost[0]==0&&monthlycost[1]==0){
                            Toast.makeText(monthlycost.this, "当月没有消费记录", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String cost = Integer.toString(monthlycost[0]);
                            String days = Integer.toString(monthlycost[1]);
                            ArrayList<CostBean> clist = null;
                            try {
                                clist = getlist(c, alldata);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(monthlycost.this, test_month.class);
                            intent.putExtra("mtotal", cost);
                            intent.putExtra("mday", days);
                            intent.putExtra("mlist", (Serializable) clist);
//                    Toast.makeText(monthlycost.this,cost+" total "+days, Toast.LENGTH_SHORT).show();


                            startActivity(intent);
                        }

                    }
                }


            });
        }
    public  ArrayList<CostBean> getlist(int n,List<CostBean> alldata) throws ParseException {
        Date costdate;
        int total=0;
        ArrayList<CostBean> list=new  ArrayList<CostBean>();
        if(alldata!=null) {
            for (int i = 0; i < alldata.size(); i++) {
                CostBean costBean;
                costBean = alldata.get(i);
                costdate=getDate(costBean.costDate);
                if (costdate.getMonth()+1==n) {
                    list.add(costBean);
                }
//                Toast.makeText(monthlycost.this, d, Toast.LENGTH_SHORT).show();
            }

        }
        return list;
    }
    public int[] getmonthlycost(int n,List<CostBean> alldata) throws ParseException {
        int[]object=new int[2];
        int k=0;
        Date costdate;
        int total=0;
        ArrayList<CostBean> list=new  ArrayList<CostBean>();
        if(alldata!=null) {
            for (int i = 0; i < alldata.size(); i++) {
                CostBean costBean;
                costBean = alldata.get(i);
                costdate=getDate(costBean.costDate);
                if (costdate.getMonth()+1==n) {
                    k++;
                    total = total + Integer.parseInt(costBean.costMoney);
                }
//                Toast.makeText(monthlycost.this, d, Toast.LENGTH_SHORT).show();
            }

        }
        object[0]=total;
        object[1]=k;
        return object;
    }

    public Date getDate(String str) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(date.getTime());
    }


}