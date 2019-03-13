package com.example.administrator.newsdf.pzgc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckitemActivity;
import com.example.administrator.newsdf.pzgc.bean.ChekItemBean;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by Administrator on 2018/8/31 0031.
 * 检查项的检查标准适配器
 */

public class CheckitemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 10001;
    private static final int TYPE_TWO = 10002;

    private ArrayList<ChekItemBean> mData;
    private Context mContext;
    //edittext里的文字内容集合
    public SparseArray<String> etTextAry = new SparseArray();
    //edittext的焦点位置
    int etFocusPos = -1;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            //每次修改文字后，保存在数据集合中
            mData.get(etFocusPos).setResultscore(s.toString());
            try {
                BigDecimal bigDecimal = new BigDecimal(s.toString());
                //返回值 1 原数据比对比数据大，O 相等，-1小于对比数据
                int maxsize = bigDecimal.compareTo(mData.get(etFocusPos).getScore());
                int minsize = bigDecimal.compareTo(new BigDecimal("0"));
                if (maxsize < 1) {
                    if (minsize >= 0) {
                        etTextAry.put(etFocusPos, s.toString());
                    } else {
                        etTextAry.put(etFocusPos, "");
                        ToastUtils.showLongToast("分数小于等于" + mData.get(etFocusPos).getScore());
                    }
                } else {
                    etTextAry.put(etFocusPos, "");
                    ToastUtils.showLongToast("分数小于等于" + mData.get(etFocusPos).getScore());
                }
            } catch (NumberFormatException e) {
                //输入内容不是数字
            }
        }
    };
    private InputMethodManager inputMethodManager;

    public CheckitemAdapter(Context mContext, ArrayList<ChekItemBean> mData, InputMethodManager inputMethodManager) {
        this.mData = mData;
        this.mContext = mContext;
        this.inputMethodManager = inputMethodManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CheckitemAdapter.ViewHolder) {
            bindView((ViewHolder) holder, position);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.e("tag", "隐藏item=" + holder.getAdapterPosition());
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.checkItemEditext.removeTextChangedListener(textWatcher);
        viewHolder.checkItemEditext.clearFocus();
        if (etFocusPos == holder.getAdapterPosition()) {
            inputMethodManager.hideSoftInputFromWindow(((ViewHolder) holder).checkItemEditext.getWindowToken(), 0);
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.e("tag", "显示item=" + holder.getAdapterPosition());
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.checkItemEditext.addTextChangedListener(textWatcher);
        if (etFocusPos == holder.getAdapterPosition()) {
            viewHolder.checkItemEditext.requestFocus();
            viewHolder.checkItemEditext.setSelection(viewHolder.checkItemEditext.getText().length());
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindView(final ViewHolder holder, final int position) {
        CheckitemActivity activity = (CheckitemActivity) mContext;
        holder.checkItemEditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etFocusPos = position;
                    Log.e("tag", "etFocusPos焦点选中-" + etFocusPos);
                }
            }
        });

        if ("保存".equals(activity.getstatus())) {
            //编辑状态根据有无此项判断是否可以进行编辑
            if (activity.getswitchstatus()) {
                holder.checkItemEditext.setClickable(false);
                holder.checkItemEditext.setEnabled(false);
                holder.checkItemEditext.setText(mData.get(position).getScore() + "");
            } else {
                holder.checkItemEditext.setEnabled(true);
                holder.checkItemEditext.setClickable(true);
                holder.checkItemEditext.setText("");
            }
        } else if ("编辑".equals(activity.getstatus())) {
            //没有进入编辑状态不可编辑
            holder.checkItemEditext.setEnabled(false);
            holder.checkItemEditext.setClickable(false);
            holder.checkItemEditext.setText(mData.get(position).getResultscore());
        }
        String status = mData.get(position).getStype();
        if ("1".equals(status)) {
            holder.checkItemEditext.setVisibility(View.VISIBLE);
            holder.checkItemStatus.setVisibility(View.GONE);
        } else {
            holder.checkItemStatus.setVisibility(View.VISIBLE);
            holder.checkItemEditext.setVisibility(View.GONE);
        }

        BigDecimal str = mData.get(position).getScore();
        if (str.compareTo(new BigDecimal("0")) == 0) {
            holder.textView.setText(mData.get(position).getContent());
        } else {
            holder.textView.setText(mData.get(position).getContent() + "（" + str + "分" + ")");
        }
        //判断初始状态
        if ("true".equals(mData.get(position).getStatus())) {
            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_t);
            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
        } else if ("false".equals(mData.get(position).getStatus())) {
            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_t);
        } else {
            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
        }
        holder.checkItemTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckitemActivity activity = (CheckitemActivity) mContext;
                if ("保存".equals(activity.getstatus())) {
                    if (activity.getswitchstatus()) {
                        ToastUtils.showShortToast("已选择无此项，无法进行操作");
                    } else {
                        if ("true".equals(mData.get(position).getStatus())) {
                            activity.setScore(position);
                            mData.get(position).setStatus("");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
                        } else {
                            mData.get(position).setStatus("true");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_t);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
                        }
                        activity.setScore(position);
                    }
                } else if ("编辑".equals(activity.getstatus())) {
                    ToastUtils.showShortToast("不是编辑状态哦");
                } else {
                }
            }
        });
        holder.checkItemFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckitemActivity activity = (CheckitemActivity) mContext;
                if ("保存".equals(activity.getstatus())) {
                    if (activity.getswitchstatus()) {
                        ToastUtils.showShortToast("已选择无此项，无法进行操作");
                    } else {
                        if ("false".equals(mData.get(position).getStatus())) {
                            mData.get(position).setStatus("");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
                        } else {
                            mData.get(position).setStatus("false");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_t);
                        }
                        activity.setScore(position);
                    }
                } else if ("编辑".equals(activity.getstatus())) {
                    ToastUtils.showShortToast("不是编辑状态哦");
                } else {

                }
            }
        });
        //放在最后，避免界面刷新无法显示分数
        String number =mData.get(position).getResultscore();
        holder.checkItemEditext.setText(number);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView checkItemTrue, checkItemFalse;
        private EditText checkItemEditext;
        private LinearLayout checkItemStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.check_item_flont);
            checkItemTrue = itemView.findViewById(R.id.check_item_true);
            checkItemFalse = itemView.findViewById(R.id.check_item_false);
            checkItemEditext = itemView.findViewById(R.id.check_item_editext);
            checkItemStatus = itemView.findViewById(R.id.check_item_status);
        }
    }

    public void getData(ArrayList<ChekItemBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public SparseArray<String> scorelist() {
        return etTextAry;

    }
}
