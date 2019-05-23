package com.example.dell.myapplication.focus;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.myapplication.R;

import java.util.ArrayList;

public class Timer extends Activity {

    private TextView minute, second, longmill;
    private Button resetBtn, startBtn;
    private ListView listView;
    private TimerAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();//计时
    private boolean isPaused = false;
    private String timeUsed;// 传入adapter的数据
    private long Time;
    private boolean leftBtn = false;// 判断是复位还是计次,ture为计次
    private boolean rightBtn = true;// 判断是开始还是暂停,true为开始


    //子线程通过handler更新时间
    private Handler ui = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    if (!isPaused) {
                        addTimeUsed();
                        UpdateUI();
                    }
                    ui.sendEmptyMessageDelayed(2, 100);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        minute = (TextView) findViewById(R.id.minuteTv);
        second = (TextView) findViewById(R.id.secondTv);
        longmill = (TextView) findViewById(R.id.longmillTv);
        listView = (ListView) findViewById(R.id.listview);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        //初始化计时器按钮text
        if (leftBtn) {
            resetBtn.setText("计次");
        } else {
            resetBtn.setText("复位");
        }
        if (rightBtn) {
            startBtn.setText("启动");
        } else {
            startBtn.setText("暂停");
        }
        adapter = new TimerAdapter(this,list);
        listView.setAdapter(adapter);


        //设置点击事件
        resetBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(rightBtn){
                    ui.removeMessages(2);
                    Time = 0;
                    minute.setText("00");
                    second.setText("00");
                    longmill.setText("00");
                    list.removeAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    list.add(timeUsed);
                    adapter.notifyDataSetChanged();

                }
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                rightBtn = !rightBtn;
                if (rightBtn) {
                    startBtn.setText("启动");
                    resetBtn.setText("复位");
                    leftBtn = false;
                    isPaused = true;
                    ui.removeMessages(2);
                } else {
                    startBtn.setText("暂停");
                    resetBtn.setClickable(true);
                    startTime();
                    resetBtn.setText("计次");
                    leftBtn = true;
                    isPaused = false;
                }
            }
        });

    }

    private void startTime() {
        ui.sendEmptyMessageDelayed(2, 100);
    }



    public void addTimeUsed() {
        Time += 100;
        timeUsed = this.getMin() + ":" + this.getSec() + ":" + this.getLongMill();
    }

    /**得到分*/
    public String getMin() {
        long min = (Time) / 60000;
        return min < 10 ? "0" + min : min + "";
    }
    /**得到秒*/
    public String getSec() {
        long sec = (Time / 1000) % 60;
        return sec < 10 ? "0" + sec : sec + "";
    }
    /**得到0.1秒*/
    public String getLongMill() {
        long longmill = (Time / 100) % 10;
        return "0" + longmill;
    }

    /**
      * 更新时间的显示
     */
    private void UpdateUI() {
        minute.setText(getMin());
        second.setText(getSec());
        longmill.setText(getLongMill());
    }


    //暂停
    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }


    //复位
    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }




}