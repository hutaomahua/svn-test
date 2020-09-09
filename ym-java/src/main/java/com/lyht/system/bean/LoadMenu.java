package com.lyht.system.bean;

import java.util.List;

/**
 * @author HuangAn
 * @date 2019/9/23 10:27
 */
public class LoadMenu {
    private String name;
    private String path;
    private String icon;
    private String id;
    private String super_id;
    private Boolean hideInMenu;
    private List<LoadMenu> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuper_id() {
        return super_id;
    }

    public void setSuper_id(String super_id) {
        this.super_id = super_id;
    }

    public Boolean getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public List<LoadMenu> getChildren() {
        return children;
    }

    public void setChildren(List<LoadMenu> children) {
        this.children = children;
    }
}
