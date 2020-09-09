package com.lyht.system.bean;


import java.util.List;

/**
 * @author HuangAn
 * @date 2019/9/23 11:22
 */
public class SysMenuTree {
    private String isbtn;
    private String menuflag;
    private String menu_url;
    private String menu_icon;
    private String flag;
    private String name;
    private String fcode;
    private String scode;
    private String id;
    private String nm;
    private String key;
    private  String title;
    private String value;
    private String super_id;
    private  String superName;
    private List<SysMenuTree> children;

    public String getIsbtn() {
        return isbtn;
    }

    public void setIsbtn(String isbtn) {
        this.isbtn = isbtn;
    }

    public String getMenuflag() {
        return menuflag;
    }

    public void setMenuflag(String menuflag) {
        this.menuflag = menuflag;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SysMenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuTree> children) {
        this.children = children;
    }

    public String getSuper_id() {
        return super_id;
    }

    public void setSuper_id(String super_id) {
        this.super_id = super_id;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }
}
