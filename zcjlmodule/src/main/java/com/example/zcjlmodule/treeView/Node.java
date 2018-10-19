package com.example.zcjlmodule.treeView;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public Node() {
    }

    public Node(String id, String pId, String name, String isLeaf, boolean iswbs, boolean isperent,
                String type) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.isLeaf = isLeaf;
        this.iswbs = iswbs;
        this.isperent = isperent;
        this.type = type;


    }


    private String id;
    /**
     * 跟节点的pid=0
     */
    private String pId = "";
    private String name = "";
    private String type = "";
    private boolean iswbs;
    private boolean isperent;


    /**
     * 树的层级
     */
    private int level;
    /**
     * 是否是展开
     */
    private boolean isExpand = false;
    private int icon;

    /**
     * 是否是叶子节点
     */
    private String isLeaf;

    private Node parent;
    private List<Node> children = new ArrayList<Node>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean iswbs() {
        return iswbs;
    }

    public void setIswbs(boolean iswbs) {
        this.iswbs = iswbs;
    }

    public boolean isperent() {
        return isperent;
    }

    public void setIsperent(boolean isperent) {
        this.isperent = isperent;
    }

    public String getIsLeaf() {
        return isLeaf;
    }


    /**
     * 属否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断当前父节点的收缩状态
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return !"0".equals(isLeaf);
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 得到当前节点的层级
     *
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }


    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Node [id=" + id + ", pId=" + pId + ", name=" + name
                + ", level=" + level + ", isExpand=" + isExpand + ", icon="
                + icon + "]";
    }

}
