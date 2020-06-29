package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TreeHelper;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.example.baselibrary.base.BaseActivity;

import java.util.List;

/**
 * 说明：选择组织
 * 创建时间： 2020/6/29 0029 17:29
 *
 * @author winelx
 */
public class ExternalTreeActivity extends BaseActivity {
    private ListView tree;
    private TextView com_title, com_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_tree);
        tree = findViewById(R.id.tree);
        com_title = findViewById(R.id.com_title);
        com_title.setText("选择部位");
        com_button = findViewById(R.id.com_button);
        com_button.setText("确定");
        findViewById(R.id.com_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.toolbar_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private class Adaper<T> extends TreeListViewAdapter<T> {

        public Adaper(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
            super(tree, context, datas, defaultExpandLevel);
        }

        @Override
        public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_node_external, parent, false);
                holder = new ViewHolder();
                holder.mIcon = (ImageView) convertView
                        .findViewById(R.id.id_item_icon);
                holder.item_name = (TextView) convertView
                        .findViewById(R.id.item_name);
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
            holder.item_name.setText(node.getName());
            return convertView;
        }


        private class ViewHolder {
            ImageView mIcon;
            TextView item_name;
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

            Node extraNode = new Node(id, node.getId(), name, isLeaf, iswbs, isparent,
                    type, username, number, userId, title, phone, isDrawingGroup);
            extraNode.setParent(node);
            node.getChildren().add(extraNode);
            mAllNodes.add(indexOf + 1, extraNode);
            mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }


    }

    public interface OnClickListener {
        void onClick(Nodes nodes, int position);

        void getAdd(Nodes nodes, int position);
    }

    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}


