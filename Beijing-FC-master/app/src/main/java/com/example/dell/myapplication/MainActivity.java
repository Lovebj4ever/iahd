package com.example.dell.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapplication.account.AccountBookActivity;
import com.example.dell.myapplication.database.database1;
import com.example.dell.myapplication.focus.focus;
import com.example.dell.myapplication.model.Diary;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static org.litepal.LitePal.find;
import static org.litepal.LitePal.where;

public class MainActivity extends AppCompatActivity {
    ImageView ivEventsCounter;
    TextView tvEventsCounter;
    ImageView ivAccountBook;
    TextView tvAccountBook;
    TextView tvFocus;
    ImageView ivFocus;

    TextView mainTitle1;
    TextView mainTitle2;
    TextView mainTitle3;
    TextView mainTitle4;
    TextView mainContent1;
    TextView mainContent2;
    TextView mainContent3;
    TextView mainContent4;
    TextView Day;
    TextView Week;
    TextView Year;
    database1 dbHelper;
    private Calendar mCalendar = Calendar.getInstance();
    private int mDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private int mMonth=Calendar.getInstance().get(Calendar.MONTH);
    private int mYear=Calendar.getInstance().get(Calendar.YEAR);

    private Diary mDiary;

    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTitle1=(TextView)findViewById(R.id.main_title1);
        mainTitle2=(TextView)findViewById(R.id.main_title2);
        mainTitle3=(TextView)findViewById(R.id.main_title3);
        mainTitle4=(TextView)findViewById(R.id.main_title4);
        mainContent1=(TextView)findViewById(R.id.main_content1);
        mainContent2=(TextView)findViewById(R.id.main_content2);
        mainContent3=(TextView)findViewById(R.id.main_content3);
        mainContent4=(TextView)findViewById(R.id.main_content4);

        Day=(TextView)findViewById(R.id.main_day);
        Week=(TextView)findViewById(R.id.main_week);
        Year=(TextView)findViewById(R.id.main_year);

        //纪念日功能跳转
        ivEventsCounter= findViewById(R.id.ivEventsCounter);
        ivEventsCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new database1(MainActivity.this,"SpecialDay.db",null,1);
                Intent intent=new Intent(MainActivity.this,SpecialDay.class);
                startActivity(intent);
            }
        });
        tvEventsCounter= findViewById(R.id.tvEventsCounter);
        tvEventsCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SpecialDay.class);
                startActivity(intent);
            }
        });
        //账本功能跳转
        ivAccountBook=findViewById(R.id.ivAccountBook);
        ivAccountBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AccountBookActivity.class);
                startActivity(intent);
            }
        });
        tvAccountBook=findViewById(R.id.tvAccountBook);
        tvAccountBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AccountBookActivity.class);
                startActivity(intent);
            }
        });
        //专注功能跳转
        tvFocus=findViewById(R.id.tvFocus);
        tvFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, focus.class);
                startActivity(intent);
            }
        });
        ivFocus=findViewById(R.id.ivFocus);
        ivFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, focus.class);
                startActivity(intent);
            }
        });

        ButterKnife.bind(this);

        String date=getIntent().getStringExtra("date");
        if(date!=null){
            SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
            Date d=null;
            try {
                d=f.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mCalendar.setTime(d);
            setDate(mCalendar);
            initData(date);
        }else{
            setDate(mCalendar);
            initData(getDate(mCalendar));
        }

        gestureDetector=new GestureDetector(MainActivity.this, onGestureListener);
    }
    private GestureDetector.OnGestureListener onGestureListener=new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float v, float v1) {
            float x=motionEvent2.getX()-motionEvent1.getX();
            if(x>0){
                flip(0);
            }else{
                flip(1);
            }
            return true;
        }
    };
    public void flip(int action){
        Intent intent;
        switch (action){
            case 0:
                //向左
                mCalendar.add(Calendar.DATE,-1);
                intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("date",getDate(mCalendar));
                startActivity(intent);
                overridePendingTransition(R.anim.move_in_left, R.anim.move_out_right);
                finish();
                break;
            case 1:
                //向右
                mCalendar.add(Calendar.DATE, 1);
                intent=new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("date", getDate(mCalendar));
                startActivity(intent);
                overridePendingTransition(R.anim.move_in_right, R.anim.move_out_left);
                finish();
                break;
        }
    }
    @OnTouch({R.id.main_title1, R.id.main_content1, R.id.main_title2, R.id.main_content2,  R.id.main_title3
            , R.id.main_content3, R.id.main_title4, R.id.main_content4, R.id.today, R.id.events_counter, R.id.account_book})
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private void initData(String date){
        if (where("date = ?", date).find(Diary.class).isEmpty()){
            initDiaryData();
        }else{
            initDiaryData();
            //加载数据库中的数据到界面
            List<Diary> list=where("date = ?", date).find(Diary.class);
            int[] titles = {R.id.main_title1, R.id.main_title2, R.id.main_title3, R.id.main_title4};
            int[] contents = {R.id.main_content1, R.id.main_content2, R.id.main_content3, R.id.main_content4};
            TextView title, content;
            for(int i=0;i<list.size();i++){
                int num = Integer.parseInt(list.get(i).getNum()) - 1;
                title = (TextView) findViewById(titles[num]);
                content = (TextView) findViewById(contents[num]);
                title.setText(list.get(i).getTitle());
                content.setText(list.get(i).getContent());
            }
        }
    }
    private void initDiaryData(){
        int titles[]={R.id.main_title1, R.id.main_title2, R.id.main_title3, R.id.main_title4};
        int contents[]={R.id.main_content1, R.id.main_content2, R.id.main_content3, R.id.main_content4};
        String text[]={"日记内容", "任务", "想法", "摘录"};
        TextView title, content;
        for(int i=0;i<titles.length;i++){
            title=(TextView)findViewById(titles[i]);
            content=(TextView)findViewById(contents[i]);
            title.setText(text[i]);
            content.setText("");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                Bundle bundle=data.getExtras();
                mDiary=(Diary)bundle.getSerializable("mDiary");
                String show = "时间 ：" + mDiary.getDate() + "\n" +
                        "模块 : " + mDiary.getNum() + "\n" +
                        "标题 : " + mDiary.getTitle() + "\n" +
                        "内容 : " + mDiary.getContent() + "\n";
                Toast.makeText(this, show, Toast.LENGTH_LONG).show();
                Log.e("onActivityResult 1", show);
                if (mDiary.saveOrUpdate("date = ? and num = ?", mDiary.getDate(), mDiary.getNum())) {
                    Log.e("onActivityResult  1  ", "日记存储成功");
                }
                initData(mDiary.getDate());
                break;
        }
    }
    public void addContent(int number, String title, String content){
        Intent intent=new Intent(MainActivity.this, EditActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("date", getDate(mCalendar));
        bundle.putString("num", String.valueOf(number));
        bundle.putString("title", title);
        bundle.putString("content", content);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }
    public String getNowDate(Long l) {
        long time=l;
        Date date=new Date(time);
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
        String str=f.format(date);
        return str;
    }
    public String getDate(Calendar calendar){
        String month = null;
        String day = null;
        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            month = "0" + (calendar.get(Calendar.MONTH) + 1);
        } else {
            month = "" + (calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DATE) < 10) {
            day = "0" + calendar.get(Calendar.DATE);
        } else {
            day = "" + calendar.get(Calendar.DATE);
        }
        String date = "" + calendar.get(Calendar.YEAR) + month + day;
        return date;
    }
    public void setDate(Calendar calendar) {
        String month = null;
        String day = null;
        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            month = "0" + (calendar.get(Calendar.MONTH) + 1);
        } else {
            month = "" + (calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DATE) < 10) {
            day = "0" + calendar.get(Calendar.DATE);
        } else {
            day = "" + calendar.get(Calendar.DATE);
        }
        Day.setText(day);
        String[] weeks = {"星期日","星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Week.setText(weeks[(calendar.get(Calendar.DAY_OF_WEEK))-1]);
        Year.setText( month + "/"+calendar.get(Calendar.YEAR));
    }
    public void showCalendar() {
        Calendar nowDay=Calendar.getInstance();
        nowDay.set(mYear, mMonth, mDay);
        DatePickerDialog dialog= DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                Calendar c=Calendar.getInstance();
                c.clear();
                c.set(year, monthOfYear, dayOfMonth);
                if (Integer.parseInt(getDate(mCalendar)) != Integer.parseInt(getNowDate(c.getTimeInMillis()))) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("date", getNowDate(c.getTimeInMillis()));
                    startActivity(intent);
                    //翻页
                    if(Integer.parseInt(getDate(mCalendar))<Integer.parseInt(getNowDate(c.getTimeInMillis()))){
                        overridePendingTransition(R.anim.move_in_right, R.anim.move_out_left);
                    }else {
                        overridePendingTransition(R.anim.move_in_left, R.anim.move_out_right);
                    }
                    finish();
                }
            }
        },nowDay.get(Calendar.YEAR), nowDay.get(Calendar.MONTH), nowDay.get(Calendar.DAY_OF_MONTH));

        // 可选日期范围
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        minDate.set(2008, 1, 1);
        maxDate.set(2022, 1, 1);

        dialog.setMinDate(minDate);
        dialog.setMaxDate(maxDate);
        dialog.vibrate(true);
        dialog.setTitle("My Diary");
        dialog.show(getFragmentManager(), "DatePickerDialog");
    }
    //监听
    @OnClick({R.id.main_title1, R.id.main_title2, R.id.main_title3, R.id.main_title4
            , R.id.main_content1, R.id.main_content2, R.id.main_content3, R.id.main_content4
            , R.id.main_day, R.id.main_week, R.id.main_year,R.id.today, R.id.main_delete})
    public void onClick(View view){
           switch(view.getId()){
               case R.id.main_day:
                   showCalendar();
                   break;
               case R.id.main_week:
                   showCalendar();
                   break;
               case R.id.main_year:
                   showCalendar();
                   break;
               case R.id.main_title1:
                    addContent(1,mainTitle1.getText().toString(),mainContent1.getText().toString());
                    break;
                case R.id.main_content1:
                    addContent(1,mainTitle1.getText().toString(),mainContent1.getText().toString());
                    break;
                case R.id.main_title2:
                    addContent(2, mainTitle2.getText().toString(), mainContent2.getText().toString());
                    break;
                case R.id.main_content2:
                    addContent(2, mainTitle2.getText().toString(), mainContent2.getText().toString());
                    break;
                case R.id.main_title3:
                    addContent(3, mainTitle3.getText().toString(), mainContent3.getText().toString());
                    break;
                case R.id.main_content3:
                    addContent(3, mainTitle3.getText().toString(), mainContent3.getText().toString());
                    break;
                case R.id.main_title4:
                    addContent(4, mainTitle4.getText().toString(), mainContent4.getText().toString());
                    break;
                case R.id.main_content4:
                    addContent(4, mainTitle4.getText().toString(), mainContent4.getText().toString());
                    break;
               case R.id.today:
                    String a=getDate(mCalendar);
                    if((Integer.parseInt(getDate(mCalendar)) != Integer.parseInt(a))){
                        Intent intent=new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("date", a);
                        startActivity(intent);
                        if (Integer.parseInt(getDate(mCalendar)) < Integer.parseInt(a)) {
                            overridePendingTransition(R.anim.move_in_left, R.anim.move_out_right);
                        } else {
                            overridePendingTransition(R.anim.move_in_right, R.anim.move_out_left);
                        }
                        finish();
                    }
                   break;
               case R.id.main_delete:
                   AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                   builder.setTitle("您要删除今天的日记吗？");
                   builder.setIcon(R.mipmap.timg);
                   builder.setCancelable(true);
                   builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           initDiaryData();
                           DataSupport.deleteAll(Diary.class, "date = ?", getDate(mCalendar));
                       }
                   });
                   builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
                   builder.show();
        }
    }
}
