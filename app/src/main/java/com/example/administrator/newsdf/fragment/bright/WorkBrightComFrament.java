package com.example.administrator.newsdf.fragment.bright;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.MainActivity;
import com.example.administrator.newsdf.activity.home.MoretaskActivity;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.view.MultiImageView;

import java.util.List;


/**
 * description:分公司
 *
 * @author lx
 *         date: 2018/5/17 0017 上午 11:00
 *         update: 2018/5/17 0017
 *         version:
 */
public class WorkBrightComFrament extends Fragment {
    private int pos;
    private View view;
    private RelativeLayout brightspot;
    @SuppressLint("ValidFragment")
    public WorkBrightComFrament(int pos) {
        this.pos = pos;
    }

    private MultiImageView multiImageView;
    private TextView content;
    private ImageView brightmark;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wrok_bright_fragment, container, false);
        content = view.findViewById(R.id.content);
        multiImageView = view.findViewById(R.id.multiImageView);
        brightspot=view.findViewById(R.id.brightspot);
        brightmark=view.findViewById(R.id.brightmark);
        brightmark.setBackgroundResource(R.mipmap.marktwo);
        try {
            String Leadername = FragmentBrightProAdapter.mData.get(pos).getLeadername();
            String titme = FragmentBrightProAdapter.mData.get(pos).getLeadername() + FragmentBrightProAdapter.mData.get(pos).getOrgName();
            SpannableString sp1 = new SpannableString(titme);
            sp1.setSpan(new ForegroundColorSpan(MainActivity.getInstance().getResources()
                            .getColor(R.color.brighr_people)), 0,
                    Leadername.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new AbsoluteSizeSpan(13, true), 0, Leadername.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setText(sp1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        multiImageView.setMaxChildCount(5);
        multiImageView.setMoreImgBg(R.mipmap.image_error);
        try {
            List<String> path= FragmentBrightAdapter.mData.get(pos).getList();
                String imagepath= Dates.listToString(path);
                String[] urls =imagepath.split("，");
                multiImageView.setImgs(urls, 5);
        }catch (NullPointerException e){
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
