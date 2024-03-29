package com.example.administrator.newsdf.treeView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.work.MmissPushActivity;
import com.example.administrator.newsdf.pzgc.activity.work.PopwindActivity;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class PushListviewAdapter<T> extends TreeListViewAdapter<T> {
    private Context context;


    public PushListviewAdapter(ListView tree, Context context,
                               List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
        this.context = context;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getConvertView(final Node node, final int position, View convertView,
                               ViewGroup parent) {
        PushListviewAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new PushListviewAdapter.ViewHolder();
            holder.mIcon = (ImageView) convertView
                    .findViewById(R.id.id_item_icon);
            holder.mText = (TextView) convertView
                    .findViewById(R.id.id_item_text);
            holder.taskNum = convertView.findViewById(R.id.taskNum);
            holder.tree_name = convertView.findViewById(R.id.tree_name);
            holder.tree_progress = convertView.findViewById(R.id.tree_progress);
            holder.Lin_WBS = convertView.findViewById(R.id.Lin_WBS);
            holder.itemRl = convertView.findViewById(R.id.dialog_mine);
            holder.image_ll = convertView.findViewById(R.id.image_ll);
            convertView.setTag(holder);
        } else {
            holder = (PushListviewAdapter.ViewHolder) convertView.getTag();
        }
        MmissPushActivity activity = (MmissPushActivity) mContext;
        String status = activity.getstatus();
        if (!node.isperent()) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }
        try {
            if (node.getUsername().length() > 0) {
                holder.Lin_WBS.setVisibility(View.VISIBLE);
            }else {
                holder.Lin_WBS.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        holder.tree_name.setText(node.getUsername());
        holder.tree_progress.setText(node.getNumber() + "%");
        holder.mText.setText(node.getName());
        //点击跳转界面
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MmissPushActivity activity = (MmissPushActivity) context;
                activity.switchAct(node);
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
        String num = node.getPhone();
        int str = 0;
        try {
            str = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (status.equals("push") || status.equals("details")) {
            if (str > 0) {
                holder.taskNum.setVisibility(View.VISIBLE);
            } else {
                holder.taskNum.setVisibility(View.GONE);
            }
        } else {
            holder.Lin_WBS.setVisibility(View.GONE);
            holder.taskNum.setVisibility(View.GONE);
        }

        //长按出现联系人信息
        holder.Lin_WBS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                okgo(node.getUserId());
                return true;
            }
        });
        //点击跳转界面
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MmissPushActivity activity = (MmissPushActivity) context;
                activity.switchAct(node);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        TextView mText, tree_name, tree_progress, taskNum;
        LinearLayout Lin_WBS;
        LinearLayout itemRl, image_ll;
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
    /**
     *@avoter winelx
     *创建时间：2019/11/21 0021 13:53
     *说明：根据ID查人员信息
     */
    void okgo(String staffId) {
        OkGo.post(Requests.Personal)
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
