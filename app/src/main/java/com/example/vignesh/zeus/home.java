package com.example.vignesh.zeus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity {

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
