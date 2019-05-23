package com.example.dell.myapplication.focus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.dell.myapplication.NewEvent;
import com.example.dell.myapplication.R;
import com.example.dell.myapplication.date_calculate;

import java.sql.Time;


public class focus extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    FloatingActionButton new_label;
    Button start;
    TextView hour;
    TextView minute;
    TextView second;
    SeekBar bar;
    //    GifImageView loading;
    private boolean buttonText = false;
    private long longmillTime;
    int barlength = 0;

    private static final int TICK_WHAT = 2;
    private static final int TIME_TO_SEND = 100;

    //通过handler更新时间
    private Handler uiHandle = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case TICK_WHAT:
                    deleteTimeUsed();
                    upDateTime();
                    upBar();
                    uiHandle.sendEmptyMessageDelayed(TICK_WHAT, TIME_TO_SEND);
                    break;
                default:
                    break;
            }
        }
    };

    //改变按钮文字
    public void setButtonText(){
        if (buttonText) {
            start.setText("开始专注");
            buttonText = false;

        } else {//开始计时
            start.setText("停止专注");
            buttonText = true;
        }
    }

    //设置专注时间
    public void setFocuseTime(){
        int a = bar.getProgress();
        longmillTime = 2*a*60*1000;
        hour.setText(getHour());
        minute.setText(getMin());
        second.setText(getSce());
    }
    //更新时间
    public void upDateTime(){
        hour.setText(getHour());
        minute.setText(getMin());
        second.setText(getSce());
    }

    public void upBar(){
        if (barlength != 0 && barlength%120000 == 0){
            bar.setProgress(bar.getProgress()-1);
        }
    }


    //减少时间
    public void deleteTimeUsed() {
        if (longmillTime>=100){
            longmillTime -= 100;
            barlength += 100;
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.topbar,menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        Toolbar toolbar = findViewById(R.id.clock);
        setSupportActionBar(toolbar);

        start = findViewById(R.id.start);
        hour = findViewById(R.id.hourTv);
        minute = findViewById(R.id.minuteTv);
        second = findViewById(R.id.secondTv);
        bar = findViewById(R.id.bar);

//        new_label.setOnClickListener(new View.OnClickListener(){
//
//
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder localBuilder = new AlertDialog.Builder(focus.this);
//                localBuilder.setTitle("选择标签");
//
//                final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.label,null);
//                localBuilder.setView()
//            }
//        });



        bar.setOnSeekBarChangeListener(this);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!buttonText) {//开始计时
                    setButtonText();
                    startTime();
                } else {//终止计时
                    setButtonText();
                    bar.setProgress(0);
                    uiHandle.removeMessages(TICK_WHAT);
                    longmillTime = 0;
                    hour.setText("00");
                    minute.setText("00");
                    second.setText("00");
                }

                //时间到弹出窗口记录
                if (hour.getText().equals("00") && minute.getText().equals("00") && second.getText().equals("00")) {

                }
            }
        });


//        new_label.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(un_colck.this, label_record.class);
//            }
//        });
    }

    private void startTime() {
        uiHandle.sendEmptyMessageDelayed(TICK_WHAT, TIME_TO_SEND);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setFocuseTime();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**得到时*/
    public String getHour() {
        long min = (longmillTime) / 3600000;
        return min < 10 ? "0" + min : min + "";
    }
    /**得到分*/
    public String getMin() {
        long min = ((longmillTime) / 60000) % 60;
        return min < 10 ? "0" + min : min + "";
    }
    /**得到秒*/
    public String getSce() {
        long sec = (longmillTime / 1000) % 60;
        return sec < 10 ? "0" + sec : sec + "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.calcu:
                Intent intent1 = new Intent(focus.this, Timer.class);
                startActivity(intent1);
                break;

            default:
        }
        return true;
    }

}