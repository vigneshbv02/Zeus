package com.example.vignesh.zeus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView=findViewById(R.id.textView2);
        TextView textView1=findViewById(R.id.textView3);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_screen_animation);
        textView.setAnimation(animation);
        textView1.setAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                    SharedPreferences sharedPreferences=getSharedPreferences("zeus", Context.MODE_PRIVATE);
                    String val=sharedPreferences.getString("phone","default");
                    if(val.equals("default"))
                    {
                        Intent intent=new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent=new Intent(getApplicationContext(),home.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
