package com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.treeviews.bean.OrgenBeans;
import com.example.administrator.yanghu.treeviews.utils.Nodes;
import com.example.administrator.yanghu.treeviews.utils.TreeHelpers;
import com.example.administrator.yanghu.treeviews.utils.adapter.TreeListViewAdapters;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 *说明：
 *创建时间： 2020/7/2 0002 14:14
 *@author winelx
 */
public class ProjectTypeTreeAdapter<T> extends TreeListViewAdapters<T> {
    private List<OrgenBeans> mData;

    public ProjectTypeTreeAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel, List<OrgenBeans> mData) throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
        this.mData = mData;
    }

    @Override
    public View getConvertView(Nodes node, final int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_node, parent, false);
            holder = new ViewHolder();
            holder.mIcon = convertView.findViewById(R.id.id_item_icon);
            holder.mText = convertView.findViewById(R.id.id_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!getmIcon(node)) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("pos", position);
                map.put("node", node);
                LiveDataBus.get().with("pro_tree").setValue(map);
            }
        });
        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (node.getChildren().size() == 0) {
                    getAdd(position, node);
                } else {
                    expandOrCollapse(position);
                }
            }
        });
        holder.mText.setText(node.getName());
        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        TextView mText;
    }

    /**
     * 动态插入节点
     *
     * @param position
     */
    public void addExtraNode(int position, Nodes bean) {
        Nodes node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        Nodes extraNode = new Nodes(position, node.getId(), bean.getName(), bean.getIds(), bean.getPid(), bean.getType());
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelpers.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

    /**
     * 动态插入节点
     *
     * @param position
     * @param names
     */
    public void addExtraNode(int position, String names, String ids, String pids, String type) {
        Nodes node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        Nodes extraNode = new Nodes(position, node.getId(), names, ids, pids, type);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelpers.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

    /**
     * 说明：添加数据
     * 创建时间： 2020/7/2 0002 13:30
     *
     * @author winelx
     */
    public void getAdd(int position, Nodes nodes) {
        String str = nodes.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType());
            }
        }
    }

    /**
     * 说明：判断是否显示图标
     * 创建时间： 2020/7/2 0002 13:30
     *
     * @author winelx
     */
    public boolean getmIcon(Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId() + "";
            if (str.equals(pid)) {
                return true;
            }
        }
        return false;
    }


    public interface OnItemClickListener {
        void onclick(int position, Nodes nodes);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
