package com.winelx.z.expression.demo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static com.winelx.z.expression.demo.R.id.layout_content;



public class MyExpandableListAdapter extends BaseExpandableListAdapter implements LeftSlideView.IonSlidingButtonListener {
    private String[] classes;
    private String[][] stuents;
    private Context context;
    View.OnClickListener ivGoToChildClickListener;
    private LeftSlideView mMenu = null;

    public MyExpandableListAdapter(String[] classes, String[][] stuents, Context context,
                                   View.OnClickListener ivGoToChildClickListener) {
        this.classes = classes;
        this.stuents = stuents;
        this.context = context;
        this.ivGoToChildClickListener = ivGoToChildClickListener;

    }

    @Override
    public int getGroupCount() {    //组的数量
        return classes.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {    //某组中子项数量
        return stuents[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {     //某组
        return classes[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {  //某子项
        return stuents[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHold groupHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_elv_group, null);
            groupHold = new GroupHold();
            groupHold.tvGroupName = (TextView) convertView.findViewById(R.id.tv_groupName);
            groupHold.ivGoToChildLv = (ImageView) convertView.findViewById(R.id.iv_goToChildLV);

            convertView.setTag(groupHold);
        } else {
            groupHold = (GroupHold) convertView.getTag();
        }
        String groupName = classes[groupPosition];
        groupHold.tvGroupName.setText(groupName);

        //取消默认的groupIndicator后根据方法中传入的isExpand判断组是否展开并动态自定义指示器
        if (isExpanded) {   //如果组展开
            groupHold.ivGoToChildLv.setImageResource(R.mipmap.ic_launcher);
        } else {
            groupHold.ivGoToChildLv.setImageResource(R.mipmap.ic_launcher_round);
        }

        //setTag() 方法接收的类型是object ，所以可将position和converView先封装在Map中。Bundle中无法封装view,所以不用bundle
        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("groupPosition", groupPosition);
        tagMap.put("isExpanded", isExpanded);
        groupHold.tvGroupName.setTag(tagMap);
        //
        // 点击事件
        groupHold.tvGroupName.setOnClickListener(ivGoToChildClickListener);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ChildHold childHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_elv_child, null);
            childHold = new ChildHold();
            childHold.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            childHold.tv_set = (TextView) convertView.findViewById(R.id.tv_set);
            childHold.home_item_content = (TextView) convertView.findViewById(R.id.home_item_content);
            childHold.home_item_time = (TextView) convertView.findViewById(R.id.home_item_time);
            childHold.layout_content = (RelativeLayout) convertView.findViewById(layout_content);
            convertView.setTag(childHold);
            ((LeftSlideView) convertView).setSlidingButtonListener(MyExpandableListAdapter.this);
        } else {
            childHold = (ChildHold) convertView.getTag();
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        String childName = stuents[groupPosition][childPosition];
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        int width = dm.widthPixels;
        // 屏幕高度（像素）
        int height = dm.heightPixels;
        // 屏幕密度（0.75 / 1.0 / 1.5）
        float density = dm.density;
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度\
        // 屏幕高度(dp)
        int screenHeight = (int) (height / density/density);
        childHold.layout_content.setLayoutParams(new RelativeLayout.LayoutParams(width, screenHeight));
        childHold.home_item_content.setText(childName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;    //默认返回false,改成true表示组中的子条目可以被点击选中
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(LeftSlideView view) {
        mMenu = view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param leftSlideView
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
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    class GroupHold {
        TextView tvGroupName;
        ImageView ivGoToChildLv;
    }

    class ChildHold {
        TextView tv_delete, tv_set;
        TextView home_item_content, home_item_time;
        RelativeLayout layout_content;
    }
}

