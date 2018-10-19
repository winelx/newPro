package com.example.zcjlmodule.treeView;

import android.util.Log;

import com.example.zcjlmodule.treeView.inface.TreeNodeId;
import com.example.zcjlmodule.treeView.inface.TreeNodeIsLeaf;
import com.example.zcjlmodule.treeView.inface.TreeNodeLabel;
import com.example.zcjlmodule.treeView.inface.TreeNodePid;
import com.example.zcjlmodule.treeView.inface.TreeNodeTypes;
import com.example.zcjlmodule.treeView.inface.TreeNodeisparent;
import com.example.zcjlmodule.treeView.inface.TreeNodeiswas;
import com.example.zcmodule.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TreeHelper {
    /**
     * 将用户的数据转化为树形数据
     *
     * @param datas
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> List<Node> convertDatas2Nodes(List<T> datas)
            throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;
        for (T t : datas) {
            String id = "-1";
            String pid = "-1";
            String label = null;
            String isLeaf = null;
            boolean iswbs = false;
            boolean isparent = false;
            String type = null;
            String username = null;
            String number = null;
            String userID = null;
            String title = null;
            String phone = null;
            boolean isDrawingGroup = false;
            node = new Node();
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);
                    id = (String) field.get(t);
                }
                if (field.getAnnotation(TreeNodePid.class) != null) {
                    field.setAccessible(true);
                    pid = (String) field.get(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }
                if (field.getAnnotation(TreeNodeIsLeaf.class) != null) {
                    field.setAccessible(true);
                    isLeaf = (String) field.get(t);
                }
                if (field.getAnnotation(TreeNodeiswas.class) != null) {
                    field.setAccessible(true);
                    iswbs = field.getBoolean(t);
                }
                if (field.getAnnotation(TreeNodeisparent.class) != null) {
                    field.setAccessible(true);
                    isparent = field.getBoolean(t);
                }
                if (field.getAnnotation(TreeNodeTypes.class) != null) {
                    field.setAccessible(true);
                    type = (String) field.get(t);
                }
            }
            node = new Node(id, pid, label, isLeaf, iswbs, isparent,
                    type);
            nodes.add(node);
        }// for end

        Log.e("TAG", nodes + "");

        /**
         * 设置Node间的节点关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);

            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);

                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    public static <T> List<Node> getSortedNodes(List<T> datas,
                                                int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        List<Node> result = new ArrayList<Node>();
        List<Node> nodes = convertDatas2Nodes(datas);
        // 获得树的根结点
        List<Node> rootNodes = getRootNodes(nodes);
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 把一个节点的所有孩子节点都放入result
     *
     * @param result
     * @param node
     * @param defaultExpandLevel
     */
    private static void addNode(List<Node> result, Node node,
                                int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }

        for (int i = 0; i < node.getChildren().size(); i++) {
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
    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
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
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot()) {
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
    private static void setNodeIcon(Node n) {

        if (n.getChildren().size() > 0 && n.isExpand()) {
            n.setIcon(R.mipmap.tree_ture);
        } else if (n.getChildren().size() > 0 && !n.isExpand()) {
            n.setIcon(R.mipmap.tree_false);
        } else {
            n.setIcon(R.mipmap.tree_false);
        }

    }


}
