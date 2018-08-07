package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.CheckuserActivity;
import com.example.administrator.newsdf.pzgc.utils.LeftSlideView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description: 检查模块——下发整改通知选择责任人
 *
 * @author lx
 *         date: 2018/6/14 0014 上午 11:46
 *         update: 2018/6/14 0014
 *         version:
 */
public class CheckUserListAdapter extends BaseExpandableListAdapter implements LeftSlideView.IonSlidingButtonListener {
    private List<String> classes;
    private Map<String, List<String>> content;
    private Context context;
    private View.OnClickListener ChildClickListener;
    private LeftSlideView mMenu = null;
    private String zero = "0";

    public CheckUserListAdapter(List<String> classes, Map<String, List<String>> content, Context context,
                                View.OnClickListener ivGoToChildClickListener) {
        this.classes = classes;
        this.content = content;
        this.context = context;
        this.ChildClickListener = ivGoToChildClickListener;

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
            groupHold.tv_group = convertView.findViewById(R.id.tv_group);
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
//        groupHold.tv_group.setTag(tagMap);
        // 点击事件

        return convertView;
    }

    //子项目
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        final ChildHold childHold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vw_list_item, null);
            childHold = new ChildHold();
            childHold.list_item_text = convertView.findViewById(R.id.list_item_text);

            convertView.setTag(childHold);
        } else {
            childHold = (ChildHold) convertView.getTag();
        }
        childHold.list_item_text.setText(content.get(classes.get(groupPosition)).get(childPosition));
        childHold.list_item_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckuserActivity activity = (CheckuserActivity) context;
                activity.getdata(content.get(classes.get(groupPosition)).get(childPosition));

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
        LinearLayout tv_group;
    }

    private class ChildHold {
        TextView list_item_text;
    }

}

