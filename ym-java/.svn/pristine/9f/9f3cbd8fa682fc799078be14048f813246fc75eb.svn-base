package com.lyht.business.engineering.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.entity.EngineeringReclaimInfo;
import com.lyht.business.engineering.service.EngineeringReclaimInfoService;
import com.lyht.business.engineering.vo.EngineeringReclaimInfoVO;
import com.lyht.business.pub.service.PubFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "/engineering/reclaimInfo", tags = "临时用地复垦实施信息表")
@RestController
@RequestMapping("/engineering/reclaimInfo")
public class EngineeringReclaimInfoController {

	@Autowired
	private EngineeringReclaimInfoService service;
	
	@Autowired
	private PubFilesService pubFilesService;

	/**
	 * 分页
	 * @param lyhtPageVO
	 * @param engineeringReclaimInfoVo
	 * @return
	 */
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<EngineeringReclaimInfoVO>> page(LyhtPageVO lyhtPageVO, EngineeringReclaimInfoVO engineeringReclaimInfoVo) {
		return service.page(lyhtPageVO, engineeringReclaimInfoVo);
	}
	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(HttpServletRequest request,Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		EngineeringReclaimInfo engineeringReclaimInfo = service.findOneById(id);
		pubFilesService.deleteBytablePkColumn(request, engineeringReclaimInfo.getNm());
		return service.delete(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/batchDel")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	public LyhtResultBody<String> batchDel(String ids) {
		return service.batchDel(ids);
	}
	
	/**
	 * 添加 修改
	 * @param engineeringReclaimInfo
	 * @return
	 */
	@ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
	@PostMapping("/save")
	public LyhtResultBody<EngineeringReclaimInfo> save(EngineeringReclaimInfo engineeringReclaimInfo) {
		return service.save(engineeringReclaimInfo);
	}
	
	
}
