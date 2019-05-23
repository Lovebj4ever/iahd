package com.example.dell.myapplication.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.myapplication.R;

import java.util.List;

public class goal_cost extends AppCompatActivity {
    EditText edit;
    String temp;
    String month;
    EditText edit_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_cost);
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit = findViewById(R.id.goal);
                edit_date=findViewById(R.id.month);
                temp = edit.getText().toString();
                month=edit_date.getText().toString();
                if(temp.equals("")||month.equals("")){
                    Toast.makeText(goal_cost.this, "无效", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!(month.equals("1") || month.equals("2") || month.equals("3") || month.equals("4") || month.equals("5") || month.equals("6") || month.equals("7") || month.equals("8") || month.equals("9") || month.equals("10") || month.equals("11") || month.equals("12"))) {
                        Toast.makeText(goal_cost.this, "请输入有效月份", Toast.LENGTH_SHORT).show();
                    }
                    if (!(month.equals("1") || month.equals("2") || month.equals("3") || month.equals("4") || month.equals("5") || month.equals("6") || month.equals("7") || month.equals("8") || month.equals("9") || month.equals("10") || month.equals("11") || month.equals("12"))) {
                        Toast.makeText(goal_cost.this, "请输入有效月份", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent1 = new Intent();//构造Intent，用于传递数据
                        intent1.putExtra("return", temp);
                        intent1.putExtra("return_date", month);
                        setResult(RESULT_OK, intent1);//专门向上一个活动传递数据的
                        finish();
                    }
                }
            }
        });
    }

}