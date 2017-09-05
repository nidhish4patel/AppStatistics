package com.nidhi.as.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class Parent extends Fragment {

    private static final String TAG = "PATENT";

    public boolean back() {
        return false;
    }

    public void onFragmentChildClick(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void refresh() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int titleid, boolean blockMenu, boolean showTitle);

        void onFragmentInteraction(String titleid, boolean blockMenu, boolean showTitle);
    }

    public interface OnRingSelectedLiestner {
        void onRingResult(String strurl);
    }

    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }*/


}

