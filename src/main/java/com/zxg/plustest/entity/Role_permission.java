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
@TableName("role_permission")
public class Role_permission extends Model<Role_permission> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer rid;

    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public static final String ID = "id";

    public static final String RID = "rid";

    public static final String PID = "pid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Role_permission{" +
        "id=" + id +
        ", rid=" + rid +
        ", pid=" + pid +
        "}";
    }
}
