package com.example.dell.myapplication.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.TreeNodes;


public class calculator extends AppCompatActivity implements View.OnClickListener{

    Button buton1;
    Button buton2;
    Button buton3;
    Button buton4;
    Button buton5;
    Button buton6;
    Button buton7;
    Button buton8;
    Button buton9;
    Button buton0;
    Button buton_zkuo;
    Button buton_ykuo;
    Button buton_jia;
    Button buton_jian;
    Button buton_cheng;
    Button buton_chu;
    Button buton_deng;
    Button buton_point;
    Button buton_delete;
    Button buton_del;
    Button buton_hey;
    Button buton_binary;
    EditText text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        buton1 = (Button) findViewById(R.id.one);
        buton2 = (Button) findViewById(R.id.two);
        buton3 = (Button) findViewById(R.id.three);
        buton4 = (Button) findViewById(R.id.four);
        buton5 = (Button) findViewById(R.id.five);
        buton6 = (Button) findViewById(R.id.six);
        buton7 = (Button) findViewById(R.id.seven);
        buton8 = (Button) findViewById(R.id.eight);
        buton9 = (Button) findViewById(R.id.nine);
        buton0 = (Button) findViewById(R.id.zero);
        buton_hey = (Button) findViewById(R.id.hey);
        buton_binary = (Button) findViewById(R.id.binary);
        buton_del = (Button) findViewById(R.id.back);
        buton_zkuo = (Button) findViewById(R.id.zuokuo);
        buton_ykuo = (Button) findViewById(R.id.youkuo);
        buton_jia = (Button) findViewById(R.id.jia);
        buton_jian = (Button) findViewById(R.id.jian);
        buton_cheng = (Button) findViewById(R.id.cheng);
        buton_chu = (Button) findViewById(R.id.chu);
        buton_deng = (Button) findViewById(R.id.dengyu);
        buton_point = (Button) findViewById(R.id.point);
        buton_delete = (Button) findViewById(R.id.delete);
        text = (EditText) findViewById(R.id.text);



        buton1.setOnClickListener(this);
        buton2.setOnClickListener(this);
        buton3.setOnClickListener(this);
        buton4.setOnClickListener(this);
        buton5.setOnClickListener(this);
        buton6.setOnClickListener(this);
        buton7.setOnClickListener(this);
        buton8.setOnClickListener(this);
        buton9.setOnClickListener(this);
        buton0.setOnClickListener(this);
        buton_hey.setOnClickListener(this);
        buton_binary.setOnClickListener(this);
        buton_del.setOnClickListener(this);
        buton_zkuo.setOnClickListener(this);
        buton_ykuo.setOnClickListener(this);
        buton_jia.setOnClickListener(this);
        buton_jian.setOnClickListener(this);
        buton_chu.setOnClickListener(this);
        buton_cheng.setOnClickListener(this);
        buton_deng.setOnClickListener(this);
        buton_delete.setOnClickListener(this);
        buton_point.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){
        String str = text.getText().toString();
        switch (v.getId()) {
            case R.id.one:
                if(str.equals("0")){
                    text.setText("1");
                }else {
                    text.setText(str + "1");
                }
                break;

            case R.id.two:
                if(str.equals("0")){
                    text.setText("2");
                }else {
                    text.setText(str + "2");
                }
                break;

            case R.id.three:
                if(str.equals("0")){
                    text.setText("3");
                }else {
                    text.setText(str + "3");
                }
                break;

            case R.id.four:
                if(str.equals("0")){
                    text.setText("4");
                }else {
                    text.setText(str + "4");
                }
                break;

            case R.id.five:
                if(str.equals("0")){
                    text.setText("5");
                }else {
                    text.setText(str + "5");
                }
                break;

            case R.id.six:
                if(str.equals("0")){
                    text.setText("6");
                }else {
                    text.setText(str + "6");
                }
                break;

            case R.id.seven:
                if(str.equals("0")){
                    text.setText("7");
                }else {
                    text.setText(str + "7");
                }
                break;

            case R.id.eight:
                if(str.equals("0")){
                    text.setText("8");
                }else {
                    text.setText(str + "8");
                }
                break;

            case R.id.nine:
                if(str.equals("0")){
                    text.setText("9");
                }else {
                    text.setText(str + "9");
                }
                break;

            case R.id.zero:
                text.setText(str+"0");
                break;
            case R.id.jia:
                text.setText(str + "+");
                break;

            case R.id.jian:
                text.setText(str + "-");
                break;

            case R.id.chu:
                if(str.equals("")){
                    text.setText("");
                }else {
                    text.setText(str + "÷");
                }
                break;

            case R.id.cheng:
                if(str.equals("")){
                    text.setText("");
                }else {
                    text.setText(str + "×");
                }
                break;

            case R.id.point:
                if(str.equals("")){
                    text.setText("");
                }else {
                    text.setText(str + ".");
                }
                break;

            case R.id.zuokuo:
                text.setText(str + "(");
                break;
            case R.id.youkuo:
                text.setText(str + ")");
                break;

            case R.id.delete:
                text.setText("");
                break;

            case R.id.back:
                text.setText(str.substring(0,str.length()-1));
                break;

            case R.id.binary:


                break;

            case R.id.hey:

                break;

            case R.id.dengyu:
                TreeNodes s=new TreeNodes(str);
                double result = s.calculate();
                if(result%1==0){
                    int result1 = (int)result;
                    text.setText(String.valueOf(result1));
                }else {
                    text.setText(String.valueOf(result));
                }
                break;
        }

    }



}

