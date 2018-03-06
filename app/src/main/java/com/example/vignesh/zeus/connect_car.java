package com.example.vignesh.zeus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class connect_car extends DialogFragment {

    ArrayList<String> car_no;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_connect_car, container, false);

        ListView listView=v.findViewById(R.id.listView);

        Button button=v.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                Add_car add_car=new Add_car();
                add_car.show(getFragmentManager(),"Add car");
            }
        });
        Button button1=v.findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        car_no=new ArrayList<>();
        car_no.add("TN23CA0237");
        car_no.add("TN23CJ2048");

        BaseAdapter baseAdapter=new CustomAdapter(car_no);
        listView.setAdapter(baseAdapter);
        return v;
    }

    private class CustomAdapter extends BaseAdapter
    {
        ArrayList<String> car_no=new ArrayList<>();

        CustomAdapter(ArrayList<String> car_no)
        {
            this.car_no=car_no;
        }

        @Override
        public int getCount()
        {
            return car_no.size();
        }

        @Override
        public Object getItem(int i)
        {
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup)
        {
            LayoutInflater layoutInflater= (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1=layoutInflater.inflate(R.layout.custom_connect_car_layout,null);
            final TextView car_reg_no= view1.findViewById(R.id.textView11);
            car_reg_no.setText(new StringBuilder("Car Register no: "+car_no.get(i)));

            Button button=  view1.findViewById(R.id.button6);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ((home)getActivity()).car_reg_no=car_no.get(i);
                    getDialog().dismiss();
                    ((home)getActivity()).setdata();
                }
            });
            return view1;
        }
    }

}
