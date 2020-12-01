package com.sd.myimageview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemFragment extends Fragment {
    private static final String ARG_PARAM1 = "title";
    private String title;

    public static ItemFragment newInstance(String title) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            tvTitle.setText(title);
        }
        return view;
    }
}