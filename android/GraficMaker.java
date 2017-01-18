package com.example.c0smy.gmaps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GraficMaker extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    LineGraphSeries<DataPoint> series;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic_maker);
        json_string = getIntent().getExtras().getString("json_data");
        drawGraph();
    }

    public void drawGraph(){
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            double x,y;
            String date,number;
            String month=null;
            int totalNumber=0;
            int medie=0;
            ArrayList<Integer> arrayList = new ArrayList<>();


            GraphView graph = (GraphView) findViewById(R.id.graph);
            series = new LineGraphSeries<DataPoint>();

            Log.d("MYINT++++++++++", "value: " + jsonArray.length());
            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                date = JO.getString("date");
                String parts[] = date.split("\\-");
                String day = parts[0];
                month = parts[1];
                Log.d("qqqqq", "value: " + day);
                number = JO.getString("totalnumber");

                totalNumber+= Integer.parseInt(number);
                arrayList.add(Integer.parseInt(number));
                x = Double.parseDouble(day);
                y = Double.parseDouble(number);
                series.appendData(new DataPoint(x,y),true,jsonArray.length());
                count ++;
            }
            Integer max = Collections.max(arrayList);
            Log.d("max", "value: " + max);

            medie = totalNumber/jsonArray.length();

            graph.addSeries(series);
            graph.getViewport().setScrollable(true); // enables horizontal scrolling
            graph.getViewport().setScrollableY(true); // enables vertical scrolling
            graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
            graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
            series.setDrawBackground(true);
            //series.setBackgroundColor(Color.BLUE);


            // set manual Y bounds<br />
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);  // set the min value of the viewport of y axis<br />
            graph.getViewport().setMaxY(max+10);    // set the max value of the viewport of y-axis<br />
            graph.getViewport().setScrollable(true);

            series.setColor(Color.rgb(99,244,255));
            graph.setTitle("Number of flights tracked per day for last month");
            graph.setTitleTextSize(40f);
            GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
            gridLabel.setHorizontalAxisTitle("Days");
            gridLabel.setVerticalAxisTitle("Number of flights");

            graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);

            textView = (TextView) findViewById(R.id.textView);
            textView.setText("Number of planes on "+month);

            textView = (TextView) findViewById(R.id.textView2);
            textView.setText(String.valueOf(totalNumber));

            textView = (TextView) findViewById(R.id.textView3);
            textView.setText("With an average of "+medie+" planes per day");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
