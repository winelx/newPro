package com.example.administrator.newsdf.fragment.bright;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.MainActivity;
import com.example.administrator.newsdf.view.MultiImageView;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class WorkBrightComFrament extends Fragment {
    private int pos;
    private View view;

    @SuppressLint("ValidFragment")
    public WorkBrightComFrament(int pos) {
        this.pos = pos;
    }

    private MultiImageView multiImageView;
    private TextView content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wrok_bright_fragment, container, false);
        content = view.findViewById(R.id.content);
        multiImageView = view.findViewById(R.id.multiImageView);
        try {
            String Leadername = FragmentBrightcomAdapter.mData.get(pos).getLeadername();
            int namelength = Leadername.length();
            String titme = FragmentBrightcomAdapter.mData.get(pos).getLeadername() + FragmentBrightcomAdapter.mData.get(pos).getOrgName();
            SpannableString sp1 = new SpannableString(titme);
            sp1.setSpan(new ForegroundColorSpan(MainActivity.getInstance().getResources()
                            .getColor(R.color.brighr_people)), 0,
                    namelength,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setText(titme);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return view;
    }
}
