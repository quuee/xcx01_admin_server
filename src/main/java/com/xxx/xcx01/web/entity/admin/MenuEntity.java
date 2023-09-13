package com.xxx.xcx01.web.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@TableName("t_menu")
public class MenuEntity implements GrantedAuthority {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 1菜单页面 2菜单目录 3按钮
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 菜单(有无路径)权限、按钮权限
     */
    @TableField("auth_url")
    private String authUrl;

    /**
     * 上级id
     */
    @TableField("pid")
    private Long pid;

    @TableField("authority")
    private String authority;

    @TableField("sort")
    private Integer sort;

    private String icon;

    @TableField(exist = false)
    private List<MenuEntity> subMenu = new ArrayList<>(0);


    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * 如果自己实现决策器，这个自定义
     * 如果交给spring决定，需要这个值判断
     * @return
     */
    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuEntity> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<MenuEntity> subMenu) {
        this.subMenu = subMenu;
    }
}
