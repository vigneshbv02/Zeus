package com.example.vignesh.zeus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

public class Add_car extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_add_car, container, false);
        final EditText editText=v.findViewById(R.id.editText);
        final EditText editText1=v.findViewById(R.id.editText3);
        Button button=v.findViewById(R.id.button2);
        Button verify=v.findViewById(R.id.button3);
        ((home)getActivity()).socket.on("otp_status",status_toast);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reg_no=editText.getText().toString();
                if(TextUtils.isEmpty(reg_no))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter car reg no",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("p_no",getActivity().getSharedPreferences("zeus", Context.MODE_PRIVATE).getString("phone","default"));
                        jsonObject.put("c_no",editText.getText().toString());
                        ((home)getActivity()).socket.emit("add_car",jsonObject.toString());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reg_no=editText.getText().toString();
                if(TextUtils.isEmpty(reg_no))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter car reg no",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("no",getActivity().getSharedPreferences("zeus", Context.MODE_PRIVATE).getString("phone","default"));
                        jsonObject.put("car_no",editText.getText().toString());
                        jsonObject.put("pin",editText1.getText().toString());

                        ((home)getActivity()).socket.emit("car_register",jsonObject.toString());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }


            }
        });


        return v;
    }

    Emitter.Listener status_toast=new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject= (JSONObject) args[0];
                        String message=jsonObject.getString("message");
                       Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_SHORT).show();

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
