package com.example.vignesh.zeus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.android.volley.toolbox.Volley;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view)
    {
        final EditText editText=findViewById(R.id.editText4);
        final EditText editText1=findViewById(R.id.editText6);
        if(editText.getText().toString().isEmpty()||editText1.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Enter your credentials",Toast.LENGTH_LONG).show();
        }
        else
        {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("success"))
                    {
                        Intent intent=new Intent(getApplicationContext(),home.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Welcome to zeus",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof NoConnectionError|| error instanceof TimeoutError)
                    {
                        Toast.makeText(getApplicationContext(),"Check your internet connectivity and try again",Toast.LENGTH_LONG).show();
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> stringStringHashMap=new HashMap<>();
                    stringStringHashMap.put("",editText.getText().toString());
                    stringStringHashMap.put("",editText1.getText().toString());
                    return stringStringHashMap;
                }
            };

            RequestQueue requestQueue=newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void go_signup(View v)
    {
        Intent intent=new Intent(getApplicationContext(),signup.class);
        startActivity(intent);
        finish();
    }
}
