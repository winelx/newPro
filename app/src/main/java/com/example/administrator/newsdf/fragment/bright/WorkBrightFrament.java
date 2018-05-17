package com.example.administrator.newsdf.fragment.bright;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.MainActivity;
import com.example.administrator.newsdf.activity.home.MoretaskActivity;
import com.example.administrator.newsdf.view.MultiImageView;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class WorkBrightFrament extends Fragment {
    private int pos;
    private View view;

    @SuppressLint("ValidFragment")
    public WorkBrightFrament(int pos) {
        this.pos = pos;
    }

    private MultiImageView multiImageView;
    private TextView content;
    private LinearLayout brightspot;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wrok_bright_fragment, container, false);
        content = view.findViewById(R.id.content);
        multiImageView = view.findViewById(R.id.multiImageView);
        brightspot=view.findViewById(R.id.brightspot);
        try {
            String Leadername = FragmentBrightAdapter.mData.get(pos).getLeadername();
            int namelength = Leadername.length();
            String titme = FragmentBrightAdapter.mData.get(pos).getLeadername() + FragmentBrightAdapter.mData.get(pos).getOrgName();
            SpannableString sp1 = new SpannableString(titme);
            sp1.setSpan(new ForegroundColorSpan(MainActivity.getInstance().getResources()
                            .getColor(R.color.brighr_people)), 0,
                    namelength,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setText(titme);
            content.setText(sp1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        brightspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.getInstance(), MoretaskActivity.class);
                intent.putExtra("TaskId", FragmentBrightAdapter.mData.get(pos).getTaskId());
                intent.putExtra("status","one");
                startActivity(intent);
            }
        });
        return view;
    }
}
