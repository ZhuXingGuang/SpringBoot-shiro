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
@TableName("permission")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String url;

    private String menuName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public static final String ID = "id";

    public static final String URL = "url";

    public static final String MENUNAME = "menuName";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Permission{" +
        "id=" + id +
        ", url=" + url +
        ", menuName=" + menuName +
        "}";
    }
}
