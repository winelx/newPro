package com.example.administrator.newsdf.treeView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.ListreadActivity;
import com.example.administrator.newsdf.activity.work.PopwindActivity;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.tree_name;

/**
 * description:
 *
 * @author lx
 *         date: 2018/3/28 0028 上午 10:11
 *         update: 2018/3/28 0028
 *         version:
 */
public class TaskTreeListViewAdapter<T> extends TreeListViewAdapter<T> {
    private Context context;

    public TaskTreeListViewAdapter(ListView tree, Context context,
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
            holder.mIcon = (ImageView) convertView
                    .findViewById(R.id.id_item_icon);
            holder.mText = (TextView) convertView
                    .findViewById(R.id.id_item_text);
            holder.dialog_mine = convertView.findViewById(R.id.dialog_mine);
            holder.tree_name = convertView.findViewById(tree_name);
            holder.tree_progress = convertView.findViewById(R.id.tree_progress);
            holder.Lin_WBS = convertView.findViewById(R.id.Lin_WBS);
            holder.image_ll = convertView.findViewById(R.id.image_ll);
            holder.taskNum = convertView.findViewById(R.id.taskNum);
            holder.handover_status_recycler = convertView.findViewById(R.id.handover_status_recycler);
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
        try {
            if (node.getUsername().length() == 0) {
                holder.Lin_WBS.setVisibility(View.GONE);
            } else {
                holder.Lin_WBS.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        holder.handover_status_recycler.setHorizontalScrollBarEnabled(false);
        String num = node.getPhone();
        int str = 0;
        try {
            str = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (str > 0) {
            holder.taskNum.setVisibility(View.VISIBLE);
        } else {
            holder.taskNum.setVisibility(View.GONE);
        }

        holder.tree_name.setText(node.getUsername());
        holder.tree_progress.setText(node.getNumber() + "%");
        holder.mText.setText(node.getName());

        holder.image_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                if (mListener != null) {
                    mListener.onClick(mVisibleNodes.get(position), position);
                }
            }
        });

        holder.dialog_mine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                okgo(node.getUserId());
                return true;
            }
        });
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListreadActivity activity = (ListreadActivity) context;
                activity.switchAct(node);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        public TextView mText, tree_name, tree_progress, taskNum;
        LinearLayout Lin_WBS;
        LinearLayout dialog_mine, image_ll;
        HorizontalScrollView handover_status_recycler;
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

    void okgo(String staffId) {
        OkGo.post(Request.Personal)
                .params("staffId", staffId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject json = jsonObject.getJSONObject("data");
                            //名字
                            String name = json.getString("name");
                            //手机号
                            String moblie = json.getString("moblie");
                            //组织名字
                            String orgName = json.getString("orgName");
                            //民族
                            String ethnicities = json.getString("ethnicities");
                            Intent intent = new Intent(mContext, PopwindActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("moblie", moblie);
                            intent.putExtra("orgName", orgName);
                            intent.putExtra("ethnicities", ethnicities);
                            mContext.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

}