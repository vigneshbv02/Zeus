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

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class send_message extends DialogFragment {


    public send_message() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_send_message, container, false);
        View v= inflater.inflate(R.layout.fragment_send_message, container, false);

      final  EditText msg=v.findViewById(R.id.editText);
        Button b=v.findViewById(R.id.button2);
          b.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
             String message=msg.getText().toString();

                  try {
                      JSONObject jsonObject=new JSONObject();
                      jsonObject.put("message",message);
                      jsonObject.put("no",getActivity().getSharedPreferences("zeus", Context.MODE_PRIVATE).getString("phone","default"));
                      jsonObject.put("id","TN23CA0237");
                      ((home)getActivity()).socket.emit("send_message",jsonObject.toString());
                      Toast.makeText(getActivity().getApplicationContext(),"Message Sent",Toast.LENGTH_SHORT).show();

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
                  }
          });



        return v;
    }

}
