package com.example.zcjlmodule.treeView.utils;

import android.util.Log;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.treeView.utils.annotation.TreeNodeId;
import com.example.zcjlmodule.treeView.utils.annotation.TreeNodeIds;
import com.example.zcjlmodule.treeView.utils.annotation.TreeNodeLabel;
import com.example.zcjlmodule.treeView.utils.annotation.TreeNodePid;
import com.example.zcjlmodule.treeView.utils.annotation.TreeNodePids;
import com.example.zcjlmodule.treeView.utils.annotation.TreeNodeTypes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * description:
 * @author lx
 * date: 2018/10/25 0025 上午 10:32
 * update: 2018/10/25 0025
 * version:
*/
public class TreeHelpers
{
	/**
	 * 将用户的数据转化为树形数据
	 *
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> List<Nodes> convertDatas2Nodes(List<T> datas)
			throws IllegalArgumentException, IllegalAccessException
	{
		List<Nodes> nodes = new ArrayList<Nodes>();
		Nodes node = null;
		for (T t : datas)
		{
			int id = -1;
			int pid = -1;
			String label = null;
			String pids = null;
			String ids = null;
			String type = null;

			node = new Nodes();
			Class clazz = t.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields)
			{
				if (field.getAnnotation(TreeNodeId.class) != null)
				{
					field.setAccessible(true);
					id = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodePid.class) != null)
				{
					field.setAccessible(true);
					pid = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodeLabel.class) != null)
				{
					field.setAccessible(true);
					label = (String) field.get(t);
				}
				if (field.getAnnotation(TreeNodeIds.class) != null)
				{
					field.setAccessible(true);
					ids = (String) field.get(t);
				}
				if (field.getAnnotation(TreeNodePids.class) != null)
				{
					field.setAccessible(true);
					pids = (String) field.get(t);
				}
				if (field.getAnnotation(TreeNodeTypes.class) != null)
				{
					field.setAccessible(true);
					type = (String) field.get(t);
				}
			}
			node = new Nodes(id, pid, label,ids,pids,type);
			nodes.add(node);
		}// for end

		Log.e("TAG", nodes+"");

		/**
		 * 设置Node间的节点关系
		 */
		for (int i = 0; i < nodes.size(); i++)
		{
			Nodes n = nodes.get(i);

			for (int j = i + 1; j < nodes.size(); j++)
			{
				Nodes m = nodes.get(j);

				if (m.getpId() == n.getId())
				{
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId() == n.getpId())
				{
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		for (Nodes n : nodes)
		{
			setNodeIcon(n);
		}
		return nodes;
	}

	public static <T> List<Nodes> getSortedNodes(List<T> datas,
                                                 int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException
	{
		List<Nodes> result = new ArrayList<Nodes>();
		List<Nodes> nodes = convertDatas2Nodes(datas);
		// 获得树的根结点
		List<Nodes> rootNodes = getRootNodes(nodes);

		for (Nodes node : rootNodes)
		{
			addNode(result, node, defaultExpandLevel, 1);
		}

		Log.e("TAG", result.size() + "");
		return result;
	}

	/**
	 * 把一个节点的所有孩子节点都放入result
	 *
	 * @param result
	 * @param node
	 * @param defaultExpandLevel
	 */
	private static void addNode(List<Nodes> result, Nodes node,
                                int defaultExpandLevel, int currentLevel)
	{
		result.add(node);
		if (defaultExpandLevel >= currentLevel)
		{
			node.setExpand(true);
		}
		if (node.isLeaf()) {
			return;
		}
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			addNode(result, node.getChildren().get(i), defaultExpandLevel,
					currentLevel + 1);
		}
	}

	/**
	 * 过滤出可见的节点
	 *
	 * @param nodes
	 * @return
	 */
	public static List<Nodes> filterVisibleNodes(List<Nodes> nodes)
	{
		List<Nodes> result = new ArrayList<Nodes>();
		for (Nodes node : nodes)
		{
			if (node.isRoot() || node.isParentExpand())
			{
				setNodeIcon(node);
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * 从所有节点中过滤出根节点
	 *
	 * @param nodes
	 * @return
	 */
	private static List<Nodes> getRootNodes(List<Nodes> nodes)
	{
		List<Nodes> root = new ArrayList<Nodes>();
		for (Nodes node : nodes)
		{
			if (node.isRoot())
			{
				root.add(node);
			}
		}
		return root;
	}

	/**
	 * 为Node设置图标
	 *
	 * @param n
	 */
	private static void setNodeIcon(Nodes n) {
		if (n.getChildren().size() > 0 && n.isExpand()) {
			n.setIcon(R.mipmap.tree_ture);
		} else if (n.getChildren().size() > 0 && !n.isExpand()) {
			n.setIcon(R.mipmap.tree_false);
		} else {
			n.setIcon(R.mipmap.tree_false);
		}
	}

}
