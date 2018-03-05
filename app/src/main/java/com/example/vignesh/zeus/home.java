package com.example.vignesh.zeus;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONObject;

public class home extends AppCompatActivity {

    FloatingActionButton floatingActionButton,floatingActionButton1,floatingActionButton2,floatingActionButton3;
    RelativeLayout relativeLayout,relativeLayout1,relativeLayout2;
    Animation animation1,animation2,animation3,animation4;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}
