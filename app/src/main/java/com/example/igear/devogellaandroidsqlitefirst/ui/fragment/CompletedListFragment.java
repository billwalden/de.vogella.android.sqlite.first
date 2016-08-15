package com.example.igear.devogellaandroidsqlitefirst.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.igear.devogellaandroidsqlitefirst.R;

/**
 * Created by IGear on 8/15/2016.
 */

public class CompletedListFragment extends Fragment  {
    private RelativeLayout _mainLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _mainLayout = (RelativeLayout) inflater.inflate(R.layout.completed_fragment_test, container, false);
        return _mainLayout;
    }
}
