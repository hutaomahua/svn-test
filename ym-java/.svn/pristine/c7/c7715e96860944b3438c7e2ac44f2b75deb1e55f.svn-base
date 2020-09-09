package com.lyht.base.common.vo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * page
 * 
 * @author hxl
 *
 */
@Data
@ApiModel(description = "分页信息")
public class LyhtPageVO{

	@ApiModelProperty(value="页码")
	private Integer current;//页码
	@ApiModelProperty(value="总页数")
	private Integer totalPages;//总页数
	@ApiModelProperty(value="每页显示数")
	private Integer pageSize;//每页显示数
	@ApiModelProperty(value="总记录数")
	private Long total;//总记录数
	@ApiModelProperty(value="排序(sorter=name,desc : 按name排序，降序)")
	private String sorter;//排序

	public LyhtPageVO() {
		super();
	}
	
	public LyhtPageVO(Integer current, Integer totalPages, Integer pageSize, Long total, String sorter) {
		super();
		this.current = current + 1;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.total = total;
		this.sorter = sorter;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	/**
	 * 转换为pageable
	 * @return
	 */
	public static Pageable getPageable(LyhtPageVO  lyhtPageVO) {
		String sortValue = lyhtPageVO.getSorter();
		Integer currentNumber = lyhtPageVO.getCurrent();
		Integer pageNumber = lyhtPageVO.getPageSize();
		//默认显示第一页，显示10条
		if (lyhtPageVO == null || currentNumber == null || pageNumber == null) {
			return PageRequest.of(0, 10);
		}
		//大于等于第一页 -1   否则 默认为 0
		currentNumber = currentNumber >= 1 ? currentNumber -1 : 0;
		//不排序
		if (StringUtils.isBlank(sortValue)) {
			return PageRequest.of(currentNumber, lyhtPageVO.getPageSize());
		}
		//排序
		String[] split = sortValue.split(",");
		Sort sort = new Sort(split.length > 1 ? StringUtils.startsWithIgnoreCase(split[1], "desc") ? Direction.DESC : Direction.ASC : Direction.ASC, split[0]);
		return PageRequest.of(currentNumber, lyhtPageVO.getPageSize(), sort);
	}
}
