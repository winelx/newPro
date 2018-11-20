package com.example.zcjlmodule.treeView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.treeView.utils.TreeHelpers;
import com.example.zcjlmodule.treeView.utils.adapter.TreeListViewAdapters;
import com.example.zcjlmodule.ui.activity.mine.ChangeorganizeZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.DismantlingtypequeryZcActivity;

import java.util.List;


/**
 * description:需要变更组织
 * @author lx
 * date: 2018/10/25 0025 上午 10:38
 * update: 2018/10/25 0025
 * version:
*/
public class ZhengcTreeListViewAdapters<T> extends TreeListViewAdapters<T> {
    public ZhengcTreeListViewAdapters(ListView tree, Context context,
                                      List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    Boolean lean;
    private int page = 0;
    @Override
    public View getConvertView(final Nodes node, final int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder = null;
        final DismantlingtypequeryZcActivity mian = (DismantlingtypequeryZcActivity) mContext;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_node_zc, parent, false);
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
                if (page == 0) {
                    expandOrCollapse(position);
                    page = 1;
                }
            }
        });
        holder.mText.setText(node.getName());
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DismantlingtypequeryZcActivity activity = (DismantlingtypequeryZcActivity) mContext;
                activity.member(node.getIds(), node.getName(),node.getType());
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
     * @para
     * m position
     * @param names
     */
    public void addExtraNode(int position, String names, String ids, String pids,String type) {
        Nodes node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        Nodes extraNode = new Nodes(position, node.getId(), names, ids, pids,type);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelpers.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

}

