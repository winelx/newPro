package com.example.zcjlmodule.treeView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zcjlmodule.R;

import java.util.List;


public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {
    private Context context;

    public SimpleTreeListViewAdapter(ListView tree, Context context,
                                     List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
        this.context = context;
    }

    @Override
    public View getConvertView(final Node node, final int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tree_list_item_zc, parent, false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView
                    .findViewById(R.id.id_item_icon);
            holder.mText = (TextView) convertView
                    .findViewById(R.id.id_item_text);
            holder.image_ll = convertView.findViewById(R.id.image_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (node.isperent() == false) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }
        holder.mText.setText(node.getName());
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.image_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                if (mListener != null) {
                    mListener.onClick(mVisibleNodes.get(position), position);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        public TextView mText;
        LinearLayout image_ll;
    }


    /**
     * 动态插入节点
     *
     * @param position
     * @param id
     */
    public void addExtraNode(int position, String id, String pid, String name, String isLeaf,
                             boolean iswbs, boolean isparent, String type
                           ) {
        Node node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);

        Node extraNode = new Node(id, node.getId(), name, isLeaf, iswbs,
                isparent, type);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();

    }

}