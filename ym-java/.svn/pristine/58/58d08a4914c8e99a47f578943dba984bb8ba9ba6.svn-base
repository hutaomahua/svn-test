package com.lyht.business.abm.paymentManagement.to;

import org.apache.poi.ss.formula.functions.T;

@SuppressWarnings("hiding")
public class Msg<T> {

	//操作状态
	private Boolean flag = true;
	
	//返回结果
	private T result;
	
	//消息提示
	private String msg = "成功";
	
	public Msg(Boolean flag, T result, String msg) {
		super();
		this.flag = flag;
		this.result = result;
		this.msg = msg;
	}
	public Msg(Boolean flag, T result) {
		super();
		this.flag = flag;
		this.result = result;
	}
	public Msg(Boolean flag, String msg) {
		super();
		this.flag = flag;
		this.msg = msg;
	}
	public Msg(T result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
	}
	public Msg(T result) {
		super();
		this.result = result;
	}


	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
