package com.example.dell.myapplication.focus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.myapplication.R;

import java.util.ArrayList;

public class TimerAdapter extends BaseAdapter {
    private ArrayList<String> datas;
    private LayoutInflater inflater;
    //是否用建立context变量


    public TimerAdapter(Context context, ArrayList<String> list) {
        this.datas = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(datas==null){
           return 0;
        }
        else{
            return datas.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(datas == null){
            return null;
        }else{
            return datas.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder a = null;
        if (convertView == null) {
            a = new ViewHolder();
            convertView = inflater.inflate(R.layout.clocklist, null);
            a.countTv = (TextView) convertView.findViewById(R.id.count);
            a.recordTv = (TextView) convertView.findViewById(R.id.record);
            convertView.setTag(a);

        }else {
            a = (ViewHolder) convertView.getTag();
        }


        a.countTv.setText("计次"+(position+1));
        a.recordTv.setText(datas.get(position));
        return convertView;

    }


    class ViewHolder{
        TextView countTv,recordTv;
    }
}
