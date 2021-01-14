package com.example.administrator.fengji.pzgc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.activity.CheckTasklistActivity;
import com.example.administrator.fengji.pzgc.bean.Home_item;
import com.example.administrator.fengji.pzgc.utils.LeftSlideView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description: 检查管理适配器
 *
 * @author lx
 *         date: 2018/6/14 0014 上午 11:46
 *         update: 2018/6/14 0014
 *         version:
 */
public class CheckManagementAdapter extends BaseExpandableListAdapter implements LeftSlideView.IonSlidingButtonListener {
    private List<String> classes;
    private Map<String, List<Home_item>> content;
    private Context context;
    private View.OnClickListener ivGoToChildClickListener;
    private LeftSlideView mMenu = null;
    private String zero = "0";

    public CheckManagementAdapter(List<String> classes, Map<String, List<Home_item>> content, Context context,
                                  View.OnClickListener ivGoToChildClickListener) {
        this.classes = classes;
        this.content = content;
        this.context = context;
        this.ivGoToChildClickListener = ivGoToChildClickListener;

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
        GroupHold groupHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_elv_group, null);
            groupHold = new GroupHold();
            groupHold.tvGroupName = convertView.findViewById(R.id.tv_groupName);
            groupHold.ivGoToChildLv = convertView.findViewById(R.id.iv_goToChildLV);
            convertView.setTag(groupHold);
        } else {
            groupHold = (GroupHold) convertView.getTag();
        }
        String groupName = classes.get(groupPosition);
        groupHold.tvGroupName.setText(groupName);
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
        groupHold.tvGroupName.setTag(tagMap);
        // 点击事件
//        groupHold.tvGroupName.setOnClickListener(ivGoToChildClickListener);
        return convertView;
    }

    //子项目
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        final ChildHold childHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_elv_child, null);
            childHold = new ChildHold();
            childHold.tvDelete = convertView.findViewById(R.id.tv_delete);
            childHold.homeItemContent = convertView.findViewById(R.id.home_item_content);
            childHold.homeItemTime = convertView.findViewById(R.id.home_item_time);
            childHold.homeItemName = convertView.findViewById(R.id.home_item_name);
            childHold.homeItemMessage = convertView.findViewById(R.id.home_item_message);
            childHold.homeItemImg = convertView.findViewById(R.id.home_item_img);
            childHold.tvSet = convertView.findViewById(R.id.tv_set);
            childHold.layoutContent = convertView.findViewById(R.id.layout_content);
            convertView.setTag(childHold);
            ((LeftSlideView) convertView).setSlidingButtonListener(CheckManagementAdapter.this);
        } else {
            childHold = (ChildHold) convertView.getTag();
        }
        //随机数，改变标段的颜色
        int random = (int) (Math.random() * 4) + 1;

        if (groupPosition % 5 != 0) {
            childHold.homeItemImg.setBackgroundResource(R.drawable.home_item_blue);
        } else if (groupPosition % 2 != 0) {
            childHold.homeItemImg.setBackgroundResource(R.drawable.home_item_yello);
        } else if (groupPosition % 3 != 0) {
            childHold.homeItemImg.setBackgroundResource(R.drawable.home_item_style);
        } else if (groupPosition % 4 != 0) {
            childHold.homeItemImg.setBackgroundResource(R.drawable.homt_item_green);
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        String childName = content.get(classes.get(groupPosition)).get(childPosition).getContent();
        //消息数量
        Integer number = Integer.decode(content.get(classes.get(groupPosition)).get(childPosition).getUnfinish());
//        int mix = 99;
//        if (number > 0) {
//            if (number > mix) {
//                childHold.homeItemMessage.setText("99+");
//            } else {
//                childHold.homeItemMessage.setText(number + "");
//            }
//        } else {
//
//        }
        childHold.homeItemMessage.setVisibility(View.GONE);
        //动态设置字项item宽度(嵌套层次太深，无法获取父级宽度)
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        int width = dm.widthPixels;
        // 屏幕高度（像素）
        int height = dm.heightPixels;
        // 屏幕密度（0.75 / 1.0 / 1.5）
        float density = dm.density;
        // 屏幕高度(dp)
        int screenHeight = (int) (height / density / density) + 20;
        //代码设置layout_content子项的宽度
        childHold.layoutContent.setLayoutParams(new RelativeLayout.LayoutParams(width, screenHeight));
        //创建时间
        childHold.homeItemTime.setVisibility(View.GONE);
        //标段名称
        childHold.homeItemName.setText(content.get(classes.get(groupPosition)).get(childPosition).getOrgname());
        //标段所属公司名称
        childHold.homeItemImg.setText(content.get(classes.get(groupPosition)).get(childPosition).getParentname());
        //最后一条回复信息
        childHold.homeItemContent.setVisibility(View.GONE);
        childHold.tvSet.setVisibility(View.GONE);
        childHold.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer message = Integer.decode(content.get(classes.get(groupPosition)).get(childPosition).getUnfinish());
                if (message > 0) {
                    content.get(classes.get(groupPosition)).get(childPosition).setUnfinish("0");
                    childHold.homeItemMessage.setVisibility(View.GONE);
                }
                Intent intent = new Intent(context, CheckTasklistActivity.class);
                intent.putExtra("name", content.get(classes.get(groupPosition)).get(childPosition).getOrgname());
                intent.putExtra("orgId", content.get(classes.get(groupPosition)).get(childPosition).getOrgid());
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
        TextView tvDelete;
        TextView homeItemContent, homeItemTime, homeItemName, homeItemImg, homeItemMessage, tvSet;
        RelativeLayout layoutContent;
    }

}

