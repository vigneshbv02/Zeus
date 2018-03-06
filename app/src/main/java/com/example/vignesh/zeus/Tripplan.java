package com.example.vignesh.zeus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class Tripplan extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_tripplan, container, false);
        final EditText editText=v.findViewById(R.id.editText10);
        editText.setText(((home)getActivity()).date);
        final EditText editText1=v.findViewById(R.id.editText11);
        Button button=v.findViewById(R.id.button8);
        editText1.setText(((home)getActivity()).time);
        final EditText editText2=v.findViewById(R.id.editText12);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Datepicker datepicker=new Datepicker();
                datepicker.show(getActivity().getSupportFragmentManager(),"Pick date");
                getDialog().dismiss();
            }
        });
        Button button1=v.findViewById(R.id.button9);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker datepicker=new TimePicker();
                datepicker.show(getActivity().getSupportFragmentManager(),"Pick date");
                getDialog().dismiss();
            }
        });
        Button button2=v.findViewById(R.id.button11);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://zeus75.herokuapp.com/upload_trip", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success"))
                        {
                            Toast.makeText(getActivity().getBaseContext(),"Successfully updated",Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError ||error instanceof NoConnectionError)
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"No network connectivity.. Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("no",getActivity().getSharedPreferences("zeus", Context.MODE_PRIVATE).getString("phone","default"));
                        hashMap.put("id",((home)getActivity()).car_reg_no);
                        hashMap.put("date",editText.getText().toString());
                        hashMap.put("tim",editText1.getText().toString());
                        hashMap.put("desc",editText2.getText().toString());
                        return hashMap;
                    }
                };

                RequestQueue requestQueue=newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });
        Button button3=v.findViewById(R.id.button12);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return v;
    }

}
