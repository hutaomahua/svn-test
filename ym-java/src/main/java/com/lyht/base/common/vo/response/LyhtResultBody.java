package com.lyht.base.common.vo.response;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * list
 * 
 * @author hxl
 *
 * @param <T>
 */
@Data
@ApiModel(description = "LyhtlistBody")
public class LyhtResultBody<T> {

	public static final int CODE_SUCCESS = 0;// 成功
	public static final int CODE_FAIL = -1;// 失败
	public static final String MSG_SUCCESS = "成功";
	public static final String MSG_FAIL = "失败";
	public static final boolean SUCCESS = true;
	public static final boolean FAILURE = false;

	@ApiModelProperty(value = "状态码")
	private int code;// 状态码
	@ApiModelProperty(value = "true：成功,false：失败")
	private boolean flag;// 数据状态
	@ApiModelProperty(value = "结果")
	private T list;// 结果
	@ApiModelProperty(value = "描述")
	private String msg;// 描述信息
	@ApiModelProperty(value = "分页信息")
	private LyhtPageVO pagination;// 分页详情

	public LyhtResultBody() {
		this(CODE_SUCCESS, SUCCESS, MSG_SUCCESS, null);
	}

	public LyhtResultBody(T list) {
		this(CODE_SUCCESS, SUCCESS, MSG_SUCCESS, list);
	}

	public LyhtResultBody(T list, LyhtPageVO pageVO) {
		this(CODE_SUCCESS, SUCCESS, MSG_SUCCESS, list, pageVO);
	}

	public LyhtResultBody(int code, boolean flag, T list) {
		this(code, flag, null, list);
	}

	public LyhtResultBody(int code, boolean flag, String msg) {
		this(code, flag, msg, null);
	}

	public LyhtResultBody(int code, boolean flag, String msg, T list) {
		this(code, flag, msg, list, null);
	}

	public LyhtResultBody(int code, boolean flag, String msg, T list, LyhtPageVO pagination) {
		this.code = code;
		this.flag = flag;
		this.msg = msg;
		this.list = list;
		this.pagination = pagination;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
