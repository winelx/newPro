package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.TreeBean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.example.administrator.newsdf.treeviews.utils.TreeHelpers;
import com.example.administrator.newsdf.treeviews.utils.adapter.TreeListViewAdapters;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.util.List;

public class TreeAdapter<T> extends TreeListViewAdapters<T> {
    private List<OrgenBeans> mData;

    public TreeAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel, List<OrgenBeans> mData) throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
        this.mData = mData;
    }


    @Override
    public View getConvertView(final Nodes node, final int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_node_external, parent, false);
            holder = new ViewHolder();
            holder.mIcon = convertView.findViewById(R.id.item_icon);
            holder.statusImg = convertView.findViewById(R.id.status_img);
            holder.mText = convertView.findViewById(R.id.item_name);
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
        if ( getstatus(node)){
            holder.statusImg.setBackgroundResource(R.mipmap.circular_blue);
        }else {
            holder.statusImg.setBackgroundResource(R.mipmap.checkbox_gray);
        }
        holder.statusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mData.size(); i++) {
                    OrgenBeans beans = mData.get(i);
                    if (beans.getId().equals(node.getIds())) {
                        if (beans.isStatus()) {
                            holder.statusImg.setBackgroundResource(R.mipmap.checkbox_gray);
                            beans.setStatus(false);
                            LiveDataBus.get().with("ex_tree").setValue(new TreeBean(node.getName(),false));
                        } else {
                            holder.statusImg.setBackgroundResource(R.mipmap.circular_blue);
                            beans.setStatus(true);
                            LiveDataBus.get().with("ex_tree").setValue(new TreeBean(node.getName(),true));
                        }
                    }
                }
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
        ImageView mIcon, statusImg;
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

    //添加数据
    public void getAdd(int position, Nodes nodes) {
        String str = nodes.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType());
            }
        }
    }

    //判断是否显示图标
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

    public boolean getstatus(Nodes node) {
        boolean lean = false;
        for (int i = 0; i < mData.size(); i++) {
            OrgenBeans beans = mData.get(i);
            if (beans.getId().equals(node.getIds())) {
                lean = beans.isStatus();
            }
        }
        return lean;
    }

}
