package com.lyht.base.common.enums.imp;


/**
 *  消息类别：
 *  展示类: 点击弹窗展示消息内容
 * 	跳转类： 点击带参数跳转到指定功能界面
 */
public enum MessageTypeEnums {
    SHOW("SHOW", "展示"), JUMP("JUMP", "跳转");

    private String code;
    private String msg;


    MessageTypeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() { return code;}

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
