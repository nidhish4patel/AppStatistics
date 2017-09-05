package com.nidhi.as.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.nidhi.as.AppStaticsActivity;
import com.nidhi.as.R;

/**
 * Created by nidhi on 5/17/2017.
 */

public class QRCodeFragment extends Parent implements View.OnClickListener {

    private View view;

    private Button bt_scan;

    private AppStaticsActivity activity;

    public QRCodeFragment() {
        //Constructor.
    }

    public static QRCodeFragment newInstance() {
        return new QRCodeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (AppStaticsActivity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = (AppStaticsActivity) activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_camera, container, false);
        bt_scan = (Button) view.findViewById(R.id.button_scan);
        bt_scan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_scan:
                IntentIntegrator.forFragment(QRCodeFragment.this).initiateScan();
                break;
        }
    }
}

