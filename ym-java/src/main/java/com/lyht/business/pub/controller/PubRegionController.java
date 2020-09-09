package com.lyht.business.pub.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.pub.service.PubRegionService;
import com.lyht.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pub/region")
@Api(value = "/pub/region", tags = "行政区域")
public class PubRegionController {
	@Autowired
	private PubRegionService pubRegionService;

	/**
	 * 根据id删除 一并删除旗下子集
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id删除", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody<List<PubRegionEntity>> delete(Integer id) {
		return new LyhtResultBody<List<PubRegionEntity>>(pubRegionService.delete(id));
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "修改 需要传入 id nm 值 , 新增", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<PubRegionEntity> save(PubRegionEntity pubRegionEntity) {
		PubRegionEntity original = null;
		String oldMergerName = "";
		String oldMergerShortName = "";
		if (StringUtils.isNotBlank(pubRegionEntity.getNm())) {
			original = pubRegionService.findByCityCode(pubRegionEntity.getCityCode());// 原来的数据信息
			pubRegionEntity.setCityCode(original.getCityCode());
			oldMergerName = original.getMergerName();
			oldMergerShortName = original.getMergerShortName();
			List<PubRegionEntity> regions = pubRegionService.getChildList(oldMergerName);
			for (PubRegionEntity re : regions) {// 遍历子级 判断不能选取本身或子级为父级
				if (pubRegionEntity.getParentCode().equals(re.getCityCode())
						|| pubRegionEntity.getParentCode().equals(pubRegionEntity.getCityCode())) {
					throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
				}
			}
		}

		PubRegionEntity save = pubRegionService.save(pubRegionEntity);// 修改后的数据信息
		if (!CommonUtil.isEmpty(original)) {
			pubRegionService.updateSons(oldMergerName, save.getMergerName(), oldMergerShortName,
					save.getMergerShortName());
		}
		return new LyhtResultBody<>(save);
	}

	/**
	 * 行政区域查询
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/findBylevelType")
	@ApiOperation(value = "行政区域列表", notes = "")
	public LyhtResultBody<List<PubRegionEntity>> findBylevelType() {
		List<PubRegionEntity> list = pubRegionService.findBylevelTypeTree();
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 行政区域查询
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/findBylevelType1")
	@ApiOperation(value = "行政区域列表", notes = "")
	public LyhtResultBody<List<PubRegionEntity>> findBylevelType1() {
		List<PubRegionEntity> list = pubRegionService.findBylevelTypeTree1();
		return new LyhtResultBody<>(list);
	}

	/**
	 * 行政区域查询
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/list")
	@ApiOperation(value = "行政区域查询", notes = "按搜索条件查询")
	public LyhtResultBody<List<PubRegionEntity>> list(PubRegionEntity pubRegionEntity) {
		List<PubRegionEntity> list = pubRegionService.list(pubRegionEntity);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 树状查询
	 *
	 * @param pubRegionEntity
	 * @return
	 */
	@PostMapping("/treeList")
	@ApiOperation(value = "行政区域树状查询", notes = "树状查询")
	public LyhtResultBody<List<PubRegionEntity>> treeList(PubRegionEntity pubRegionEntity) {
		List<PubRegionEntity> list = pubRegionService.treeList(pubRegionEntity);
		return new LyhtResultBody<>(list);
	}

	@PostMapping("/parents")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "cityCode", value = "当前节点的cityCode", paramType = "query"),
			@ApiImplicitParam(name = "mergerName", value = "当前节点的全称，以逗号分割(优先级最高)", paramType = "query"),
			@ApiImplicitParam(name = "levelType", value = "行政区域的级别（数字，只查询传入级别开始的数据；不传，则查询所有）", paramType = "query") })
	@ApiOperation(value = "查询所有父级节点", notes = "查询所有父级节点")
	public LyhtResultBody<List<PubRegionEntity>> getParents(String cityCode, String mergerName, Integer levelType) {
		List<PubRegionEntity> list = pubRegionService.getParents(cityCode, mergerName, levelType);
		return new LyhtResultBody<>(list);
	}

	@ApiOperation(value = "根据行政区域名称模糊查询（包含经纬度）", notes = "根据行政区域名称模糊查询（包含经纬度）")
	@PostMapping("/by/name")
	public LyhtResultBody<List<PubRegionEntity>> findAllByName(String name) {
		List<PubRegionEntity> findAllByName = pubRegionService.findAllByName(name);
		return new LyhtResultBody<>(findAllByName);
	}
	
	@ApiOperation(value = "从维西开始查询所有行政区一次性加载", notes = "从维西开始查询所有行政区一次性加载")
	@PostMapping("/getTypeThanThree")
	public LyhtResultBody<List<Map<String,Object>>> getTypeThanThree(){
		return pubRegionService.getTypeThanThree();
	}

}
