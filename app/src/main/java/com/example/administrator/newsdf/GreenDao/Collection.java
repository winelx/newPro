package com.example.administrator.newsdf.GreenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/5/21 0021.
 */
@Entity
public class Collection {

    //不能用int
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "price")
    //Id
    private String wbsId;
    @Generated(hash = 571556118)
    public Collection(Long id, String wbsId) {
        this.id = id;
        this.wbsId = wbsId;
    }
    @Generated(hash = 1149123052)
    public Collection() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWbsId() {
        return this.wbsId;
    }
    public void setWbsId(String wbsId) {
        this.wbsId = wbsId;
    }


}
