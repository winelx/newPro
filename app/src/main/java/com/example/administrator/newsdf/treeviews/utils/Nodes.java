package com.example.administrator.newsdf.treeviews.utils;

import java.util.ArrayList;
import java.util.List;

public class Nodes {
    public Nodes() {
    }

    public Nodes(int id, int pId, String name, String ids, String pids,String type) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.ids = ids;
        this.pids = pids;
        this.type = type;
    }


    private int id;
    /**
     * 跟节点的pid=0
     */
    private int pId = 0;
    private String name;

    private String ids;
    private String pids;
    private String type;


    /**
     * 树的层级
     */
    private int level;
    /**
     * 是否是展开
     */
    private boolean isExpand = true;
    private int icon;

    private Nodes parent;
    private List<Nodes> children = new ArrayList<Nodes>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
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

    public Nodes getParent() {
        return parent;
    }

    public void setParent(Nodes parent) {
        this.parent = parent;
    }

    public List<Nodes> getChildren() {
        return children;
    }

    public void setChildren(List<Nodes> children) {
        this.children = children;
    }

    public String getPid() {
        return pids;
    }

    public void setPidd(String pids) {
        this.pids = pids;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return children.size() == 0;
    }

    /**
     * 得到当前节点的层级
     *
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }
    /**
     * 是否是展开
     */
    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;

        if (!isExpand) {
            for (Nodes node : children) {
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
