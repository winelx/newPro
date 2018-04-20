package com.example.administrator.newsdf.activity.work;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class BridhtFragment extends Fragment {
    View view;
    private int pos = 0;

    public BridhtFragment(int pos) {
        this.pos = pos;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bright_list_view, container, false);

        return view;
    }
}
