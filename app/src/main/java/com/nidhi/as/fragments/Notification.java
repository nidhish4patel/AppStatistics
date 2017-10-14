package com.nidhi.as.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nidhi.as.AppStaticsActivity;
import com.nidhi.as.R;
import com.nidhi.as.adapters.ItemsAdapter;
import com.nidhi.as.database.DBHelper;
import com.nidhi.as.database.UserModel;

import java.util.ArrayList;

/**
 * Created by nidhi on 8/31/2017.
 */

public class Notification extends Parent {

    private View view;
    private AppStaticsActivity activity;
    private RecyclerView recyclerView;
    DBHelper dbHelper;
    UserModel userModel;


    public Notification() {
        //Constructor.
    }

    public static Notification newInstance() {
        return new Notification();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_Notification);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        userModel = new UserModel();

        dbHelper = new DBHelper(activity);

        TextView Sno = new TextView(activity);
        userModel.setSno(Sno.getText().toString());
        recyclerView.addView(Sno);

        TextView tiltle = new TextView(activity);
        userModel.setTitle(tiltle.getText().toString());
        recyclerView.addView(tiltle);

        TextView Desc = new TextView(activity);
        userModel.setDesc(Desc.getText().toString());
        recyclerView.addView(Desc);

        TextView Expdate = new TextView(activity);
        userModel.setExpdate(Expdate.getText().toString());
        recyclerView.addView(Expdate);

        if (Sno != null && Sno.getText() != null && Sno.getText().toString() != null) {

            UserModel userModel = dbHelper.getData(Sno.getText().toString());
            if (Sno.length() > 0) {
                if (userModel != null && userModel.getSno() != null) {
                    tiltle.setText(userModel.getTitle());
                    Desc.setText(userModel.getDesc());
                    Expdate.setText(userModel.getExpdate());

                    ItemsAdapter itemsAdapter = new ItemsAdapter(activity, Sno, tiltle, Desc, Expdate);

                    recyclerView.setAdapter(itemsAdapter);

                } else {
                    Sno.setText("");
                    Toast.makeText(activity, "No notification received", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, "No notification received", Toast.LENGTH_SHORT).show();
            }

        }
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


