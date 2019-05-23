package com.example.dell.myapplication;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.myapplication.database.database1;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class NewEvent extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 1;
    Bitmap pic = null;
    byte[] texts = null;
    String path;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    EditText theme,content,fuck;
    RadioButton layout3_button;
    database1 dbHelper;
    TextView layout3_text;
    TextView layout5_text;
    TextView Layout2_text;
    int layout_y=0;
    int layout_m=0;
    int layout_d=0;
    int layout_b=0;
    int layout_h=0;
    int layout_mi=0;
    int year_now = 0;
    int month_now=0;
    int day_now=0;
    int sumday = 0;
    ImageView iv_photo;
    ArrayList<String> output1= new ArrayList<>();
    ArrayList<String> output2= new ArrayList<>();
    ArrayList<String> output3= new ArrayList<>();
    ArrayList<String> output4= new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    String type/*= new ArrayList<>()*/;
    class RadioListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Toast.makeText(NewEvent.this, "选择"+checkedId, Toast.LENGTH_SHORT);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.topbar,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    pic = setImage(selectedImage);
                    //setImage(selectedImage);

                    texts = bitmabToBytes(NewEvent.this,pic);
                    Bitmap show = BitmapFactory.decodeByteArray(texts,0,texts.length);
                    iv_photo.setImageBitmap(show);
                    break;
            }
        }
    }
    private Bitmap setImage(Uri uri){
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            //Bitmap show = BitmapFactory.decodeByteArray(texts,0,texts.length);
            //iv_photo.setImageBitmap(show);
            return bitmap;
            //iv_photo.setImageBitmap(bitmap);
        }
        catch(FileNotFoundException e ){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] bitmabToBytes(Context context,Bitmap bitmap){

        int size = bitmap.getWidth() * bitmap.getHeight() * 4;

        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        }catch (Exception e){
        }finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        layout1 = (LinearLayout) findViewById(R.id.Layout1);
        layout2 = (LinearLayout) findViewById(R.id.Layout2);
        layout3 = (LinearLayout) findViewById(R.id.Layout3);
        layout4 = (LinearLayout) findViewById(R.id.Layout4);
        layout5 = (LinearLayout) findViewById(R.id.Layout5);
        layout3_text = (TextView) findViewById(R.id.layout3_textView);
        layout5_text = (TextView) findViewById(R.id.layout5_textView);
        Layout2_text = (TextView) findViewById(R.id.Layout2_text);
        theme = (EditText) findViewById(R.id.theme);

        content = (EditText) findViewById(R.id.content);
        /*Toolbar topbar = (Toolbar) findViewById(R.id.toolbar);*/
        NewEvent.this.setResult(RESULT_OK);
        //setSupportActionBar(topbar);
        //创建数据库
        dbHelper = new database1(this,"SpecialDay.db",null,1);





        layout2.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                localBuilder.setTitle("选择日期");


                final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.activity_datepick, null);
                localBuilder.setView(layout_alert);

                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        DatePicker datepicker1= (DatePicker) layout_alert.findViewById(R.id.datepick);
                         layout_y=datepicker1.getYear();
                         layout_m=datepicker1.getMonth()+1;
                         layout_d=datepicker1.getDayOfMonth();
                        System.out.println("y:"+layout_y+" m:"+layout_m+" d:"+layout_d);
                        Layout2_text.setText(layout_y+"-"+layout_m+"-"+layout_d); //  获取时间


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {

                    }
                }).create().show();


            }

        });

        layout3.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                localBuilder.setTitle("选择类型");

                final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.radio_button, null);
                localBuilder.setView(layout_alert);


                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        RadioGroup day_type = (RadioGroup) layout_alert.findViewById(R.id.daytype);
                        day_type.setOnCheckedChangeListener(new RadioListener());
                        int id = day_type.getCheckedRadioButtonId();
                        System.out.println(id+"is");
                        layout3_button = (RadioButton) layout_alert.findViewById(id);


                        //获取文本到文本框
                        type = layout3_button.getText().toString();
                        System.out.println(type);
                        layout3_text.setText(type);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {

                    }
                }).create().show();
            }
        });

        layout4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                iv_photo = (ImageView) findViewById(R.id.img);
                startActivityForResult(i,IMAGE_REQUEST_CODE);
            }
        });

        layout5.setOnClickListener(new View.OnClickListener(){



                @Override
                public void onClick(View v) {
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                    localBuilder.setTitle("选择时间");


                    final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.time_pick, null);
                    localBuilder.setView(layout_alert);

                    localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                        {
                            TimePicker timepicker1= (TimePicker) layout_alert.findViewById(R.id.timepicker);

                            layout_b = timepicker1.getBaseline();
                            layout_h = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                layout_h = timepicker1.getHour();
                            }
                            layout_mi = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                layout_mi = timepicker1.getMinute();
                            }

                            //System.out.println("y:"+layout_b+" m:"+layout_h+" d:"+layout_mi);
                            //  获取时间
                            if(layout_h<10 && layout_mi<10){
                                layout5_text.setText("0"+layout_h+":0"+layout_mi);
                            }
                            else if(layout_h<10 && layout_mi>=10){
                                layout5_text.setText("0"+layout_h+":"+layout_mi);
                            }
                            else if(layout_h>=10 && layout_mi<10){
                                layout5_text.setText(layout_h+":0"+layout_mi);
                            }
                            else{
                                layout5_text.setText(layout_h+":"+layout_mi);

                            }




                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                        {

                        }
                    }).create().show();
            }
        });
        Button store = (Button)findViewById(R.id.store);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
                String text = null;
                text = content.getText().toString();
                System.out.println(text);
                String text2 = theme.getText().toString();
                String text3 = null;
                if(layout_mi!=0||layout_h!=0) {
                    text3 = layout5_text.getText().toString();
                }
                SQLiteDatabase d =dbHelper.getReadableDatabase();
                ContentValues values =new ContentValues();
                if(type!=null&& layout_y != 0 && layout_m != 0 && layout_d != 0 ) {
                    year_now = calendar.get(Calendar.YEAR);
                    month_now = calendar.get(Calendar.MONTH)+1;
                    day_now = calendar.get(Calendar.DAY_OF_MONTH);
                    daycalculate t = new daycalculate(year_now,layout_y,month_now,layout_m,day_now,layout_d);
                    sumday = t.sumdays();
                    if((type.equals("倒数日")&&sumday>=0)||type.equals("纪念日")&&sumday<=0) {
                        if (!"".equals(content.getText().toString().trim()) && text2 != null && !"".equals(layout5_text.getText().toString().trim()) && pic != null) {
                            values.put("name", text2);
                            values.put("content", text);
                            values.put("year", layout_y);
                            values.put("month", layout_m);
                            values.put("day", layout_d);
                            values.put("time", text3);
                            values.put("pic", texts);
                            values.put("type", type);
                            d.insert("event", null, values);
                            values.clear();
                            Intent back = new Intent(NewEvent.this, SpecialDay.class);
                            startActivity(back);
                            finish();
                        } else {
                            Toast.makeText(NewEvent.this, "Please enter all the data", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(NewEvent.this,"Please enter the correct date",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(NewEvent.this,"Please enter all the data",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
