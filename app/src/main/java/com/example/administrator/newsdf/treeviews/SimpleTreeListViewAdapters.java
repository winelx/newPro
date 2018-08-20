package com.example.administrator.newsdf.treeviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.example.administrator.newsdf.treeviews.utils.TreeHelpers;
import com.example.administrator.newsdf.treeviews.utils.adapter.TreeListViewAdapters;

import java.util.List;


/**
 * description:
 *
 * @author lx
 *         date: 2018/3/20 0020 下午 4:52
 *         update: 2018/3/20 0020
 *         version:
 */
public class SimpleTreeListViewAdapters<T> extends TreeListViewAdapters<T> {
    public SimpleTreeListViewAdapters(ListView tree, Context context,
                                      List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    Boolean lean;

    @Override
    public View getConvertView(final Nodes node, final int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder = null;
        final OrganizationaActivity mian = (OrganizationaActivity) mContext;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_node, parent, false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView
                    .findViewById(R.id.id_item_icon);
            holder.mText = (TextView) convertView
                    .findViewById(R.id.id_item_text);
            holder.id_item_lin = (LinearLayout) convertView
                    .findViewById(R.id.id_item_lin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        lean = mian.getmIcon(node);
        if (!lean) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }
        holder.id_item_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (node.getChildren().size() == 0) {
                    mian.getAdd(position, node);
                }
                expandOrCollapse(position);
            }
        });
        holder.mText.setText(node.getName());
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganizationaActivity activity = (OrganizationaActivity) mContext;
                    activity.member(node.getIds(), node.getName());
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        TextView mText;
        LinearLayout id_item_lin;
    }

    /**
     * 动态插入节点
     *
     * @param position
     * @param names
     */
    public void addExtraNode(int position, String names, String ids, String pids) {
        Nodes node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        // Node
        Nodes extraNode = new Nodes(-1, node.getId(), names, ids, pids);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelpers.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
        expandOrCollapse(position);

    }

}
