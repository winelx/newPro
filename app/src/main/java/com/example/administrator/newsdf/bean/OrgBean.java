package com.example.administrator.newsdf.bean;


import com.example.administrator.newsdf.treeView.inface.TreeNodeId;
import com.example.administrator.newsdf.treeView.inface.TreeNodeLabel;
import com.example.administrator.newsdf.treeView.inface.TreeNodePid;

public class OrgBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private String parentIds;
	private String id;

	public OrgBean(int _id, int parentId, String name,String parentIds,String ID)
	{
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
		this.parentIds = parentIds;
		this.id = ID;
	}
	public OrgBean(int _id, int parentId, String s, String name)
	{
		this._id = _id;
		this.parentId = parentId;
		this.name = name;

	}
	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
