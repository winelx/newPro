package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:原始勘丈表界面的数据实体 界面：OrginalZcActivity
 */

public class OriginalZcBean {
    String id;
    //标题
    String titile;
    //内容
    String content;
    //期数
    String datanumber;
    //户主名称
    String namecontent;
    //征拆类别
    String category;
    //创建人
    String createName;
    //创建时间
    String createdata;
    //总价格
    String totalPrice;


    public OriginalZcBean(String id, String titile, String content, String datanumber, String namecontent, String category, String createName, String createdata, String totalPrice) {
        this.id = id;
        this.titile = titile;
        this.content = content;
        this.datanumber = datanumber;
        this.namecontent = namecontent;
        this.category = category;
        this.createName = createName;
        this.createdata = createdata;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatanumber() {
        return datanumber;
    }

    public void setDatanumber(String datanumber) {
        this.datanumber = datanumber;
    }

    public String getNamecontent() {
        return namecontent;
    }

    public void setNamecontent(String namecontent) {
        this.namecontent = namecontent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreatedata() {
        return createdata;
    }

    public void setCreatedata(String createdata) {
        this.createdata = createdata;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
