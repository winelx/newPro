package com.example.administrator.yanghu.treeviews.bean;


import com.example.administrator.yanghu.treeView.inface.TreeNodeTypes;
import com.example.administrator.yanghu.treeviews.utils.annotation.TreeNodeId;
import com.example.administrator.yanghu.treeviews.utils.annotation.TreeNodeIds;
import com.example.administrator.yanghu.treeviews.utils.annotation.TreeNodeLabel;
import com.example.administrator.yanghu.treeviews.utils.annotation.TreeNodePid;
import com.example.administrator.yanghu.treeviews.utils.annotation.TreeNodePids;

public class OrgBeans {
    @TreeNodeId
    private int _id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String name;
    @TreeNodeIds
    private String ids;
    @TreeNodePids
    private String pids;
    @TreeNodeTypes
    private String Types;


    public OrgBeans(int _id, int parentId, String name, String ids, String pids,String Types) {
        this._id = _id;
        this.parentId = parentId;
        this.name = name;
        this.ids = ids;
        this.pids = pids;
        this.Types = Types;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }
}
