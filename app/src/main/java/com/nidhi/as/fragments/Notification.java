package com.nidhi.as.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.nidhi.as.AppStaticsActivity;
import com.nidhi.as.R;

import java.util.List;

/**
 * Created by nidhi on 8/31/2017.
 */

public class Notification extends Parent{

    private View view;
    private AppStaticsActivity activity;
    private RecyclerView recyclerView;

    public Notification() {
        //Constructor.
    }

    public static Notification newInstance() {
        return new Notification();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_Notification);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        ItemsAdapter itemsAdapter = new ItemsAdapter();

        recyclerView.setAdapter(itemsAdapter);



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

        view = inflater.inflate(R.layout.fragment_notification, container, false);

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

}

