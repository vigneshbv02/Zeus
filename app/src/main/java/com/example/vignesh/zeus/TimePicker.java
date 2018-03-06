package com.example.vignesh.zeus;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int minutes=calendar.get(Calendar.MINUTE);
        int hour=calendar.get(Calendar.HOUR);
        return new TimePickerDialog(getActivity(),this,hour,minutes,true);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute)
    {
        ((home)getActivity()).time=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
        Tripplan tripplan=new Tripplan();
        tripplan.show(getFragmentManager(),"Trip");
    }
}
