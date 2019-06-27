package com.example.administrator.newsdf.pzgc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceMessageAllActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.utils.LeftSlideView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/11/28 0028.
 * @description:
 */

public class DeviceExpandableAdapter extends BaseExpandableListAdapter implements LeftSlideView.IonSlidingButtonListener {
    private List<String> classes;
    private Map<String, List<Home_item>> content;
    private Context context;
    private LeftSlideView mMenu = null;


    public DeviceExpandableAdapter(List<String> classes, Map<String, List<Home_item>> content, Context context) {
        this.classes = classes;
        this.content = content;
        this.context = context;

    }

    @Override
    public int getGroupCount() {    //组的数量
        return classes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //某组中子项数量
        return content.get(classes.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {     //某组
        return classes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {  //某子项
        return content.get(classes.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //第一级
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DeviceExpandableAdapter.GroupHold groupHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_elv_group, null);
            groupHold = new DeviceExpandableAdapter.GroupHold();
            groupHold.tvGroupName = convertView.findViewById(R.id.tv_groupName);
            groupHold.ivGoToChildLv = convertView.findViewById(R.id.iv_goToChildLV);
            convertView.setTag(groupHold);
        } else {
            groupHold = (DeviceExpandableAdapter.GroupHold) convertView.getTag();
        }
        groupHold.tvGroupName.setText(classes.get(groupPosition));
        //取消默认的groupIndicator后根据方法中传入的isExpand判断组是否展开并动态自定义指示器
        //如果组展开
        if (isExpanded) {
            //未展开
            groupHold.ivGoToChildLv.setImageResource(R.mipmap.home_expan_arrow_down);
        } else {
            //展开
            groupHold.ivGoToChildLv.setImageResource(R.mipmap.home_expan_arrow_right);
        }
        //setTag() 方法接收的类型是object ，所以可将position和converView先封装在Map中。Bundle中无法封装view,所以不用bundle
        Map<String, Object> tagMap = new HashMap<>(16);
        tagMap.put("groupPosition", groupPosition);
        tagMap.put("isExpanded", isExpanded);
        return convertView;
    }

    //子项目
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        final DeviceExpandableAdapter.ChildHold childHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_checkdown_all, null);
            childHold = new DeviceExpandableAdapter.ChildHold();
            childHold.homeItemImg = convertView.findViewById(R.id.home_item_img);
            childHold.checkMeTitle = convertView.findViewById(R.id.check_me_title);
            childHold.layoutContent = convertView.findViewById(R.id.check_relati);
            childHold.homeItemMessage = convertView.findViewById(R.id.home_item_message);
            convertView.setTag(childHold);
        } else {
            childHold = (DeviceExpandableAdapter.ChildHold) convertView.getTag();
        }
        childHold.homeItemImg.setBackgroundResource(R.drawable.home_item_blue);
        childHold.homeItemImg.setText(content.get(classes.get(groupPosition)).get(childPosition).getParentname());
        childHold.checkMeTitle.setText(content.get(classes.get(groupPosition)).get(childPosition).getOrgname());
        int number = Integer.parseInt(content.get(classes.get(groupPosition)).get(childPosition).getUnfinish());
        if (number > 0) {
            childHold.homeItemMessage.setText(number + "");
            childHold.homeItemMessage.setVisibility(View.VISIBLE);
        } else {
            childHold.homeItemMessage.setVisibility(View.GONE);
        }

        childHold.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏消息数量
                childHold.homeItemMessage.setVisibility(View.GONE);
                //跳转界面
                Intent intent = new Intent(context, DeviceMessageAllActivity.class);
                intent.putExtra("orgId", content.get(classes.get(groupPosition)).get(childPosition).getId());
                intent.putExtra("orgName", content.get(classes.get(groupPosition)).get(childPosition).getOrgname());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        //默认返回false,改成true表示组中的子条目可以被点击选中
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }

    /**
     * 滑动或者点击了Item监听
     */
    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return Boolean
     */
    private Boolean menuIsOpen() {
        return mMenu != null;
    }

    private class GroupHold {
        TextView tvGroupName;
        ImageView ivGoToChildLv;
    }

    private class ChildHold {
        TextView checkMeTitle, homeItemMessage;
        TextView homeItemImg;
        LinearLayout layoutContent;
    }

}


