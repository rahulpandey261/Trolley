package com.trolley.trolley.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trolley.trolley.R;

/**
 * Created by RAHUL on 24-Sep-15.
 */
public class TrolleyDialogs extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.trolley_dialog, container, false);
    }
}
