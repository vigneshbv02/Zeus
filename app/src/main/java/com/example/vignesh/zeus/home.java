package com.example.vignesh.zeus;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class home extends AppCompatActivity implements RequestQueue.RequestFinishedListener<Object> {

    FloatingActionButton floatingActionButton,floatingActionButton1,floatingActionButton2,floatingActionButton3;
    RelativeLayout relativeLayout,relativeLayout1,relativeLayout2;
    Animation animation1,animation2,animation3,animation4;
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10;

    String date="",time="";

    String car_reg_no="";

    boolean isopen=false;

    Socket socket;
    {
        try
        {
            socket= IO.socket("http://zeus75.herokuapp.com");
        }
        catch (Exception e)
         {
            e.printStackTrace();
        }
    }

    BarChart horizontalBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        connect_car connect_car=new connect_car();
        connect_car.show(getSupportFragmentManager(),"Connect car");


        horizontalBarChart=findViewById(R.id.bar_chart);

        textView1=findViewById(R.id.textView12);
        textView2=findViewById(R.id.textView13);
        textView3=findViewById(R.id.textView14);
        textView4=findViewById(R.id.textView15);
        textView5=findViewById(R.id.textView16);
        textView6=findViewById(R.id.textView17);
        textView7=findViewById(R.id.textView18);
        textView8=findViewById(R.id.textView19);
        textView9=findViewById(R.id.textView20);
        textView10=findViewById(R.id.textView21);

        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton1=findViewById(R.id.floatingActionButton2);
        floatingActionButton2=findViewById(R.id.floatingActionButton3);
        floatingActionButton3=findViewById(R.id.floatingActionButton4);

        relativeLayout=findViewById(R.id.relativeLayout_float_1);
        relativeLayout1=findViewById(R.id.relativeLayout_float_2);
        relativeLayout2=findViewById(R.id.relativeLayout_float_3);

        animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        animation2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        animation3=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        animation4=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isopen)
                {
                    floatingActionButton1.startAnimation(animation2);
                    floatingActionButton1.setClickable(false);
                    relativeLayout.startAnimation(animation2);
                    relativeLayout.setClickable(false);

                    floatingActionButton2.startAnimation(animation2);
                    floatingActionButton2.setClickable(false);
                    relativeLayout1.startAnimation(animation2);
                    relativeLayout1.setClickable(false);

                    floatingActionButton3.startAnimation(animation2);
                    floatingActionButton3.setClickable(false);
                    relativeLayout2.startAnimation(animation2);
                    relativeLayout2.setClickable(false);

                    floatingActionButton.startAnimation(animation4);
                    isopen=false;
                }
                else
                {
                    floatingActionButton1.startAnimation(animation1);
                    floatingActionButton1.setClickable(true);
                    floatingActionButton1.setVisibility(View.VISIBLE);

                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.startAnimation(animation1);
                    relativeLayout.setClickable(true);

                    floatingActionButton2.startAnimation(animation1);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton2.setVisibility(View.VISIBLE);

                    relativeLayout1.setVisibility(View.VISIBLE);
                    relativeLayout1.startAnimation(animation1);
                    relativeLayout1.setClickable(true);

                    floatingActionButton3.startAnimation(animation1);
                    floatingActionButton3.setClickable(true);
                    floatingActionButton3.setVisibility(View.VISIBLE);

                    relativeLayout2.setVisibility(View.VISIBLE);
                    relativeLayout2.startAnimation(animation1);
                    relativeLayout2.setClickable(true);

                    floatingActionButton.startAnimation(animation3);
                    isopen=true;
                }
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_car add_car=new Add_car();
                add_car.show(getSupportFragmentManager(),"Add car");
            }
        });

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tripplan tripplan=new Tripplan();
                tripplan.show(getSupportFragmentManager(),"tripplan");
            }
        });

        socket.connect();
        socket.on("message",get_id);

    }

    Emitter.Listener get_id=new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject= (JSONObject) args[0];
                        String message=jsonObject.getString("id");
                        JSONObject jsonObject1=new JSONObject();
                        jsonObject1.put("id",message);
                        jsonObject1.put("category","phone");
                        jsonObject1.put("no",getSharedPreferences("zeus", Context.MODE_PRIVATE).getString("phone","default"));
                        socket.emit("register",jsonObject1.toString());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public void setdata()
    {
        Toast.makeText(getApplicationContext(),new StringBuilder("Connecting to "+car_reg_no),Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://zeus75.herokuapp.com/car_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    textView1.setText(new StringBuilder("Type = "+jsonObject.getString("type")));
                    textView2.setText(new StringBuilder("Mileage = "+jsonObject.getString("mileage")));
                    textView3.setText(new StringBuilder("Service date = "+jsonObject.getString("service_date")));
                    textView4.setText(new StringBuilder("Throttle position = "+jsonObject.getString("Throttle_Position")));
                    textView5.setText(new StringBuilder("Coolent temperature = "+jsonObject.getString("coolent_temperature")));
                    textView6.setText(new StringBuilder("Engine load = "+jsonObject.getString("engine_load")));
                    textView7.setText(new StringBuilder("Barometric pressure = "+jsonObject.getString("barometric_pressure")));
                    textView8.setText(new StringBuilder("Power Supply = "+jsonObject.getString("power_supply")));
                    textView9.setText(new StringBuilder("Trouble codes = "+jsonObject.getString("trouble_codes")));
                    textView10.setText(new StringBuilder("Km = "+jsonObject.getString("km")));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError||error instanceof NoConnectionError)
                {
                    Toast.makeText(getApplicationContext(),"No network connectivity.. Try again",Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("car",car_reg_no);
                return hashMap;
            }
        };
        RequestQueue requestQueue=newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(this);
    }

    @Override
    public void onRequestFinished(Request<Object> request) {
        get_fuel_data();
    }

    private void get_fuel_data() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://zeus75.herokuapp.com/fuel_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                final HashMap<Integer, String>numMap = new HashMap<>();
                ArrayList<Integer> integers=new ArrayList<>();

                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    String jsonArray=jsonObject.getString("_id");
                    String consumption=jsonObject.getString("consumption");
                    JSONObject jsonObject1=new JSONObject(consumption);
                    Toast.makeText(getApplicationContext(),jsonArray,Toast.LENGTH_SHORT).show();
                    //Setting Manually
                    ArrayList<BarEntry> entries=new ArrayList<>();
                    entries.add(new BarEntry(4f,0));
                    entries.add(new BarEntry(8f,1));
                    entries.add(new BarEntry(6f,2));
                    entries.add(new BarEntry(12f,3));
                    entries.add(new BarEntry(18f,4));
                    entries.add(new BarEntry(9f,5));

                    BarDataSet barDataSet=new BarDataSet(entries,"No of liters");

                    ArrayList<String> strings=new ArrayList<>();
                    strings.add("28 Feb 2018");
                    strings.add("01 Mar 2018");
                    strings.add("03 Mar 2018");
                    strings.add("07 Mar 2018");
                    strings.add("10 Mar 2018");

                    BarData barData=new BarData(barDataSet);
                    horizontalBarChart.setData(barData);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError||error instanceof NoConnectionError)
                {
                    Toast.makeText(getApplicationContext(),"No network connectivity.. Try again",Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("car",car_reg_no);
                return hashMap;
            }
        };
        RequestQueue requestQueue=newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
