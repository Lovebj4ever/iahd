package com.example.dell.myapplication.account;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.account.CostBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartActivity extends Activity {
    private LineChartView mchart;
    private LineChartData mdata;
    private Map<String,Integer>table=new TreeMap<>();
    TextView tv2;
    @Override
    public void onCreate(Bundle savedInstanceState){
        int total=0;
        List<CostBean>alldata= (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chartview);
        mdata= new LineChartData();
        mchart=findViewById(R.id.chart);

        mchart.setZoomEnabled(true);
        generateValue(alldata);
        generateData();

        mchart.setLineChartData(mdata);
    }
    private void generateData() {
        List<Line>lines=new ArrayList<>();
        List<PointValue>values=new ArrayList<>();
        int indexX=0;
        for(Integer value:table.values()){
            values.add(new PointValue(indexX,value));
            indexX++;
        }
        Line line=new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setName("X");
        axisY.setName("Y");
        mdata.setAxisXBottom(axisX);
        mdata.setAxisYLeft(axisY);
        mdata=new LineChartData(lines);
        mdata.setLines(lines);
        mchart.setLineChartData(mdata);

    }

    private void generateValue(List<CostBean> alldata) {
        if(alldata!=null){
            for(int i=0;i<alldata.size();i++){
                CostBean costBean=alldata.get(i);
                String costDate=costBean.costDate;
                int costMoney=Integer.parseInt(costBean.costMoney);
                if(!table.containsKey(costDate)){
                    table.put(costDate,costMoney);

                }
                else{
                    int originmoney=table.get(costDate);
                    table.put(costDate,originmoney+costMoney);
                }
            }
        }
    }

}
