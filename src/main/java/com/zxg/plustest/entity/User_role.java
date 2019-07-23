package com.zxg.plustest.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxg
 * @since 2019-07-22
 */
@TableName("user_role")
public class User_role extends Model<User_role> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer uid;

    private Integer rid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public static final String ID = "id";

    public static final String UID = "uid";

    public static final String RID = "rid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User_role{" +
        "id=" + id +
        ", uid=" + uid +
        ", rid=" + rid +
        "}";
    }
}
