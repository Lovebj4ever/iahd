package com.example.dell.myapplication.account;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.dell.myapplication.Adapter.CostListAdapter;
import com.example.dell.myapplication.R;
import com.example.dell.myapplication.database.DatabaseHelper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccountBookActivity extends AppCompatActivity {
    private List<CostBean>mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter adapter;
    private  int totals=0;
    private String get;
    private  String month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountbook);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper=new DatabaseHelper(this);

        mCostBeanList=new ArrayList<>();
        final ListView costList=findViewById(R.id.lv_main);
        initCostDate();
        adapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(adapter);

        costList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                CostBean temp = mCostBeanList.remove(position);

                SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                String[] args = {String.valueOf(temp.costTittle),String.valueOf(temp.costDate),String.valueOf(temp.costMoney)};
                db.delete("cost","cost_title=? and cost_date=? and cost_money=?",args);
                adapter.notifyDataSetChanged();
                return false;

            }
        });




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AccountBookActivity.this);
                LayoutInflater inflater=LayoutInflater.from(AccountBookActivity.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_data,null);
                final EditText title = viewDialog.findViewById(R.id.ed_cost_title);
                final EditText money = viewDialog.findViewById(R.id.ed_cost_money);
                final DatePicker datePicker=viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("新的消费记录~");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title.getText().toString().equals("")||money.getText().toString().equals("")) {
                            Toast.makeText(AccountBookActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            CostBean costBean = new CostBean();
                            costBean.costTittle = title.getText().toString();
                            costBean.costMoney = money.getText().toString();
                            costBean.costDate = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" +
                                    datePicker.getDayOfMonth();
                            mDatabaseHelper.insertcost(costBean);
                            mCostBeanList.add(costBean);
                            adapter.notifyDataSetChanged();
                            int goalcost = 0;
                            int month2 = 0;
                            try {
                                goalcost = Integer.parseInt(get);
                                month2 = Integer.parseInt(month);
                            }
                            catch (Exception  e) {
                                e.printStackTrace();
                            }

                            if (check(month2, goalcost) == true) {

                            } else {
                                NotificationUtils notificationUtils = new NotificationUtils(AccountBookActivity.this);
                                notificationUtils.sendNotification("提醒", "超出预算啦 啊啊啊");
                            }
                        }

                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returndata=data.getStringExtra("return");
                    String returndata2=data.getStringExtra("return_date");
                    get=returndata;
                    month=returndata2;
                }
                break;
            default:
        }
    }
    private void initCostDate() {
        Cursor cursor = mDatabaseHelper.getallcost();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.costTittle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);

            }
            cursor.close();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_chart) {
            Intent intent=new Intent(AccountBookActivity.this,ChartActivity.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivity(intent);
            return true;

        }
        if (id == R.id.action_total) {
            Intent intent=new Intent(AccountBookActivity.this,totalCost.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_monthly) {
            Intent intent=new Intent(AccountBookActivity.this,monthlycost.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_goal) {
            Intent intent=new Intent(AccountBookActivity.this,goal_cost.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivityForResult(intent,1);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public boolean check (int month,int cost_goal){
        int ed=0;
        if(mCostBeanList!=null) {
            totals=0;
            for (int i = 0; i < mCostBeanList.size(); i++) {
                CostBean mcostBean;
                mcostBean = mCostBeanList.get(i);
                try {
                    if (getDate(mcostBean.costDate).getMonth() + 1 == month) {
                        ed = Integer.parseInt(mcostBean.costMoney);
                        totals = totals + ed;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            if (cost_goal < totals) {
                return false;
            }
            else{
                return true;
            }
        }

        return false;
    }


}
