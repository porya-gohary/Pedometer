package com.example.porya.pedometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    String input;
    SensorManager sensorManager;
    Calendar calendar = Calendar.getInstance();
    Date date=new Date();
    SharedPreferences preferences;

   SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
 //   Date date = inFormat.parse(input);
    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
    String day = outFormat.format(date);
    String d=inFormat.format(date);
    //int dayOfWeek=bday.get(Calendar.DAY_OF_WEEK);

    TextView steps;
    GraphView graph;

    int v[];

    boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        steps=(TextView) findViewById(R.id.textView5);
        v=new int[7];
        graph = (GraphView) findViewById(R.id.graph1);


        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Toast.makeText(this,goal,Toast.LENGTH_SHORT).show();
        preferences=getSharedPreferences("prefs",MODE_PRIVATE);




       // Toast.makeText(this,day,Toast.LENGTH_SHORT).show();


        switch (day){
            case "Saturday":
                v[0]=Integer.parseInt(preferences.getString(d,"0"));
                v[1]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                v[2]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                v[3]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                v[4]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                v[5]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                v[6]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                break;
            case "Sunday":
                v[0]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                v[1]=Integer.parseInt(preferences.getString(d,"0"));
                v[2]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                v[3]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                v[4]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                v[5]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                v[6]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                break;
            case "Monday":
                v[0]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                v[1]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                v[2]=Integer.parseInt(preferences.getString(d,"0"));
                v[3]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                v[4]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                v[5]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                v[6]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                break;
            case "Tuesday":
                v[0]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                v[1]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                v[2]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                v[3]=Integer.parseInt(preferences.getString(d,"0"));
                v[4]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                v[5]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                v[6]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                break;
            case "Wednesday":
                v[0]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                v[1]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                v[2]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                v[3]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                v[4]=Integer.parseInt(preferences.getString(d,"0"));
                v[5]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                v[6]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                break;
            case "Thursday":
                v[0]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                v[1]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                v[2]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                v[3]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                v[4]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                v[5]=Integer.parseInt(preferences.getString(d,"0"));
                v[6]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                break;
            case "Friday":
                v[0]=Integer.parseInt(preferences.getString(getCalculatedDate(-6),"0"));
                v[1]=Integer.parseInt(preferences.getString(getCalculatedDate(-5),"0"));
                v[2]=Integer.parseInt(preferences.getString(getCalculatedDate(-4),"0"));
                v[3]=Integer.parseInt(preferences.getString(getCalculatedDate(-3),"0"));
                v[4]=Integer.parseInt(preferences.getString(getCalculatedDate(-2),"0"));
                v[5]=Integer.parseInt(preferences.getString(getCalculatedDate(-1),"0"));
                v[6]=Integer.parseInt(preferences.getString(d,"0"));
                break;


        }

        initGraph(graph);

    }



    public void initGraph(GraphView graph) {
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, v[0]),
                new DataPoint(2, v[1]),
                new DataPoint(3, v[2]),
                new DataPoint(4, v[3]),
                new DataPoint(5, v[4]),
                new DataPoint(6, v[5]),
                new DataPoint(7, v[6]),
                new DataPoint(8, 0)
        });
        series.setSpacing(5); // 5% spacing between bars
        series.setAnimated(true);
        series.setDataWidth(0.8d);
        graph.addSeries(series);

        // set the viewport wider than the data, to have a nice view
        graph.getViewport().setMinY(0d);
        graph.getViewport().setMinX(0d);
        graph.getViewport().setMaxX(8d);

//        graph.getViewport().setMaxX(4d);
       graph.getViewport().setYAxisBoundsManual(true);
       graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(120);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه",""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        running=true;
        Sensor countSensor =sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor !=null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this,"سنسور قدم یافت نشد!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running=false;
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        preferences.edit().putString(d,String.valueOf((int)sensorEvent.values[0])).apply();
        if(running){

            steps.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public static String getCalculatedDate(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

}
