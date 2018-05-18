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
 * description:
 *
 * @author lx
 *         date: 2018/5/17 0017 上午 11:00
 *         update: 2018/5/17 0017
 *         version:
 */
public class WorkBrightComFrament extends Fragment {
    private int pos;
    private View view;
    private LinearLayout brightspot;
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
        brightspot=view.findViewById(R.id.brightspot);
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
        brightspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.getInstance(), MoretaskActivity.class);
                intent.putExtra("TaskId", FragmentBrightAdapter.mData.get(pos).getTaskId());

                startActivity(intent);
            }
        });
        return view;
    }
}
