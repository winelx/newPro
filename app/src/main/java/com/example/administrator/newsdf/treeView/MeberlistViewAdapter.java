package com.example.administrator.newsdf.treeView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.mine.ProjectmemberActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class MeberlistViewAdapter<T> extends TreeListViewAdapter<T> {
    private Context context;

    public MeberlistViewAdapter(ListView tree, Context context,
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
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.mIcon = convertView
                    .findViewById(R.id.id_item_icon);
            holder.mText =  convertView
                    .findViewById(R.id.id_item_text);
            holder.dialog_mine = convertView.findViewById(R.id.dialog_mine);
            holder.tree_name = convertView.findViewById(R.id.tree_name);
            holder.tree_progress = convertView.findViewById(R.id.tree_progress);
            holder.image_ll = convertView.findViewById(R.id.image_ll);
            holder.Lin_WBS = convertView.findViewById(R.id.Lin_WBS);
            holder.taskNum = convertView.findViewById(R.id.taskNum);
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
        if (node.getUsername().length() == 0) {
            holder.Lin_WBS.setVisibility(View.GONE);
        }
        holder.taskNum.setVisibility(View.GONE);
        holder.tree_name.setText(node.getUsername());
        holder.tree_progress.setText(node.getNumber() + "%");
        holder.mText.setText(node.getName());
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, ProjectmemberActivity.class);
                intent.putExtra("id",node.getId());
                //联系人的树没有用带getPhone，就用来存联系的树节点路径
                intent.putExtra("path",node.getPhone());
                intent.putExtra("title",node.getTitle());
                intent.putExtra("ids",node.getNumber());
                mContext.startActivity(intent);
            }
        });
    //伸缩展开节点
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
        public TextView mText, tree_name, tree_progress,taskNum;
        LinearLayout Lin_WBS;
        LinearLayout dialog_mine, image_ll;
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
