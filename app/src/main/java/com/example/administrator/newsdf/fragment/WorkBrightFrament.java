package com.example.administrator.newsdf.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wrok_bright_fragment, container, false);
        content = view.findViewById(R.id.content);
        multiImageView = view.findViewById(R.id.multiImageView);
        try {
            content.setText(FragmentBrightAdapter.mData.get(pos).getOrgName());
//            SpannableString sp1 = new SpannableString(content);
//            sp1.setSpan(new ForegroundColorSpan(BrightspotActivity.getInstance().getResources()
//                            .getColor(R.color.brighr_people)), 0,
//                    length,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return view;
    }
}
