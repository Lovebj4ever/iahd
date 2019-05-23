package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.myapplication.model.Diary;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends AppCompatActivity{
    EditText editTitle;
    EditText editContent;
    ImageView delete;
    ImageView save;
    String temp;
    Diary mDiary;
    private String date,number,title,content;
    private Bundle getBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edtext);
        ButterKnife.bind(this);

        editTitle=(EditText)findViewById(R.id.title_edit);
        editContent=(EditText)findViewById(R.id.content_edit);
        delete=(ImageView)findViewById(R.id.edit_delete);
        save=(ImageView)findViewById(R.id.edit_save);

        getBundle=getIntent().getExtras();
        date=getBundle.getString("date");
        number = getBundle.getString("num");
        title = getBundle.getString("title");
        content=getBundle.getString("content");
        init();
    }
    private void init(){
        List<Diary> list=LitePal.where("date = ? and num = ?", date, number).find(Diary.class);
        if(!list.isEmpty()){
            mDiary=list.get(0);
            editTitle.setText(mDiary.getTitle());
            editContent.setText(mDiary.getContent());
        }else{
            editTitle.setText(title);
            editContent.setText(content);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    }
    //监听
    @OnClick({R.id.title_edit, R.id.content_edit, R.id.edit_save, R.id.edit_delete})
        public void Onclick(View view){
        switch (view.getId()){
            case R.id.title_edit:
                break;
            case R.id.content_edit:
                break;
            case R.id.edit_save:
                Intent intent=new Intent(EditActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("num", number);
                bundle.putString("title", editTitle.getText().toString());
                bundle.putString("content", editContent.getText().toString());

                mDiary=new Diary();
                mDiary.setDate(date);
                mDiary.setNum(number);
                mDiary.setTitle(editTitle.getText().toString().trim());
                mDiary.setContent(editContent.getText().toString().trim());

                bundle.putSerializable("mDiary",mDiary);
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();
                break;
            case R.id.edit_delete:
                editTitle.setText(title);
                editContent.setText("");
                break;
        }
    }
}
