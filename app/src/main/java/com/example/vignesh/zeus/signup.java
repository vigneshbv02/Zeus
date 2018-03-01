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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void sign_up(View view)
    {
        final EditText editText=findViewById(R.id.editText7);
        final EditText editText1=findViewById(R.id.editText2);
        final EditText editText2=findViewById(R.id.editText8);
        final EditText editText3=findViewById(R.id.editText9);

        if(editText.getText().toString().isEmpty()||editText1.getText().toString().isEmpty()||editText2.getText().toString().isEmpty()||editText3.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Enter your credentials",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!editText2.getText().toString().equals(editText3.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_LONG).show();
            }
            else
            {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://zeus75.herokuapp.com/signup", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Signup success",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Something went wrong, Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof NoConnectionError || error instanceof TimeoutError)
                        {
                            Toast.makeText(getApplicationContext(),"Check your internet connectivity and try again",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> stringMap=new Hashtable<>();
                        stringMap.put("phone",editText.getText().toString());
                        stringMap.put("name",editText1.getText().toString());
                        stringMap.put("password",editText2.getText().toString());
                        return stringMap;
                    }
                };

                RequestQueue requestQueue=newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }
    }

    public void go_login(View v)
    {
        Intent intent=new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();
    }
}
