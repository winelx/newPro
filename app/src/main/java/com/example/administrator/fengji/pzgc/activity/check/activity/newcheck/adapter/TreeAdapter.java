package com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.treeView.Node;
import com.example.administrator.fengji.treeView.TreeHelper;
import com.example.administrator.fengji.treeView.TreeListViewAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.util.List;

/**
 * 说明：
 * 创建时间： 2020/7/10 0010 18:02
 *
 * @author winelx
 */
public class TreeAdapter<T> extends TreeListViewAdapter<T> {

    public TreeAdapter(ListView tree, Context context,
                       List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }


    @Override
    public View getConvertView(final Node node, final int position, View convertView,
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
        if (!node.isperent()) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }
        if (node.iswbs()) {
            holder.statusImg.setBackgroundResource(R.mipmap.circular_blue);
        } else {
            holder.statusImg.setBackgroundResource(R.mipmap.checkbox_gray);
        }
        holder.statusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!node.iswbs()) {
                        node.setIswbs(true);
                        holder.statusImg.setBackgroundResource(R.mipmap.circular_blue);
                        LiveDataBus.get().with("ex_node").setValue(node.getName());
                    } else {
                        holder.statusImg.setBackgroundResource(R.mipmap.checkbox_gray);
                        node.setIswbs(false);
                    }
            }
        });

        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandOrCollapse(position);
                if (node.getChildren().size() == 0) {
                    mListener.onClick(node, position);
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
        ImageView statusImg;
        TextView mText;
    }

    /**
     * 动态插入节点
     *
     * @param position
     * @param id
     */
    public void addExtraNode(int position, String id, String pid, String name, String isLeaf,
                             boolean iswbs, boolean isparent, String type, String username,
                             String number, String userId, String title, String phone, boolean isDrawingGroup) {
        Node node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);

        Node extraNode = new Node(id, node.getId(), name, isLeaf, iswbs,
                isparent, type, username, number, userId, title, phone, isDrawingGroup);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

}
