package com.example.dell.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class DataAdapter extends ArrayAdapter<Data> {
    private int resourceId;
    public DataAdapter(Context context, int textViewResourceId, List<Data> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Data data = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView dataImage = (ImageView) view.findViewById(R.id.data_image);
        TextView dataName = (TextView) view.findViewById(R.id.data_name);
        dataImage.setImageResource(data.getImageId());
        dataName.setText(data.getName());
        return view;
    }
}
