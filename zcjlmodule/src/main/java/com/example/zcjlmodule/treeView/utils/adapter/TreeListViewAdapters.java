package com.example.zcjlmodule.treeView.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.treeView.utils.TreeHelpers;

import java.util.List;

public abstract class TreeListViewAdapters<T> extends BaseAdapter
{
	protected Context mContext;
	protected List<Nodes> mAllNodes;
	protected List<Nodes> mVisibleNodes;
	protected LayoutInflater mInflater;

	protected ListView mTree;

	/**
	 * 设置Node的点击回调
	 * 
	 * @author zhy
	 * 
	 */
	public interface OnTreeNodeClickListener
	{
		void onClick(Nodes node, int position);
	}

	public OnTreeNodeClickListener mListener;

	public void setOnTreeNodeClickListener(OnTreeNodeClickListener mListener)
	{
		this.mListener = mListener;
	}

	public TreeListViewAdapters(ListView tree, Context context, List<T> datas,
                                int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException
	{
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mAllNodes = TreeHelpers.getSortedNodes(datas, defaultExpandLevel);
		mVisibleNodes = TreeHelpers.filterVisibleNodes(mAllNodes);
		mTree = tree;
		mTree.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
			{
				expandOrCollapse(position);

				if (mListener != null)
				{
					mListener.onClick(mVisibleNodes.get(position), position);
				}

			}

		});

	}

	/**
	 * 点击搜索或者展开
	 * 
	 * @param position
	 */
	public void expandOrCollapse(int position)
	{
		Nodes n = mVisibleNodes.get(position);
		if (n != null) {
			if (n.isLeaf()) {
				return;
			}
			n.setExpand(!n.isExpand());
			mVisibleNodes = TreeHelpers.filterVisibleNodes(mAllNodes);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount()
	{
		return mVisibleNodes.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mVisibleNodes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Nodes node = mVisibleNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		// 设置内边距
		convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(Nodes node, int position,
                                        View convertView, ViewGroup parent);

}
