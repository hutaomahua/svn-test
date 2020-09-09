package com.lyht.business.abm.landAllocation.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.entity.LandAuditEntity;
import com.lyht.business.abm.landAllocation.service.LandAuditService;
import com.lyht.business.abm.landAllocation.service.LandDataSyncService;
import com.lyht.business.abm.landAllocation.vo.LandAuditVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/landallocation")
@Api(value = "土地分解", tags = "土地分解")
public class LandDataSyncController {

	@Autowired
	LandDataSyncService service;

	@Autowired
	private LandAuditService landAuditService;

	@GetMapping(value = "/sync")
	@ApiOperation(value = "数据同步", notes = "土地数据同步")
	public LyhtResultBody<String> sync() {

		service.sync();

		return new LyhtResultBody<>("土地数据同步");
	}

	@PostMapping(value = "/queryLandScope")
	@ApiOperation(value = "查询征地范围", notes = "查询征地范围")
	@ApiImplicitParams({ @ApiImplicitParam(name = "region", paramType = "query", value = "行政区region", required = true),
			@ApiImplicitParam(name = "allType", paramType = "query", value = "第一级类型"),
			@ApiImplicitParam(name = "typeOne", paramType = "query", value = "第二级类型"),
			@ApiImplicitParam(name = "typeTwo", paramType = "query", value = "第三级类型"),
			@ApiImplicitParam(name = "typeThree", paramType = "query", value = "第四级类型") })
	public LyhtResultBody<List<Map<String, Object>>> queryLandScope(String region, String allType, String typeOne,
			String typeTwo, String typeThree) {
		return service.queryLandScope(region, allType, typeOne, typeTwo, typeThree);
	}

	@PostMapping(value = "/queryStatisticsList")
	@ApiOperation(value = "查询列表数据", notes = "查询土地分解列表数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码"),
			@ApiImplicitParam(name = "uname", paramType = "query", value = "户主姓名"),
			@ApiImplicitParam(name = "idCard", paramType = "query", value = "身份证") })
	public LyhtResultBody<List> queryStatisticsList(@RequestParam(required = false) String cityCode,
			@RequestParam(required = false) String uname, @RequestParam(required = false) String idCard) {
		List<Map<String, Object>> mapList = service.queryStatisticsList(cityCode, uname, idCard);
		return new LyhtResultBody<>(mapList);
	}

	@PostMapping(value = "/queryUserList")
	@ApiOperation(value = "查询用户列表数据", notes = "查询用户列表数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityName", paramType = "query", value = "行政区编码"),
			@ApiImplicitParam(name = "uname", paramType = "query", value = "户主姓名"),
			@ApiImplicitParam(name = "idCard", paramType = "query", value = "身份证") })
	public LyhtResultBody<List> queryUserList(@RequestParam(required = false) String cityName,
			@RequestParam(required = false) String uname, @RequestParam(required = false) String idCard,
			LyhtPageVO lyhtPageVO) {
		return service.queryLandInfoByRegion(cityName, uname, idCard, lyhtPageVO);
	}

	@PostMapping(value = "/queryScopeList")
	@ApiOperation(value = "查询范围列表数据", notes = "查询范围列表数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityName", paramType = "query", value = "行政区名称", required = true),
			@ApiImplicitParam(name = "typeNm", paramType = "query", value = "类型nm"),
			@ApiImplicitParam(name = "level", paramType = "query", value = "下钻的层数0、1、2、3 默认为0", dataType = "number") })
	public LyhtResultBody<List> queryScopeList(@RequestParam(required = true) String cityName,
			@RequestParam(required = false) String typeNm,
			@RequestParam(required = false, defaultValue = "0") int level, LyhtPageVO lyhtPageVO) {
		return service.queryScopeList(cityName, typeNm, level, lyhtPageVO);
	}

	@PostMapping(value = "/queryOwnerList")
	@ApiOperation(value = "查询户主列表数据", notes = "查询户主列表数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区cityCode", required = true),
			@ApiImplicitParam(name = "typeAll", paramType = "query", value = "第一级类型"),
			@ApiImplicitParam(name = "typeOne", paramType = "query", value = "第二级类型"),
			@ApiImplicitParam(name = "typeTwo", paramType = "query", value = "第三级类型"),
			@ApiImplicitParam(name = "typeThree", paramType = "query", value = "第四级类型") })
	public LyhtResultBody<Map> queryOwnerList(@RequestParam(required = true) String cityCode,
			@RequestParam(required = false) String typeAll, @RequestParam(required = false) String typeOne,
			@RequestParam(required = false) String typeTwo, @RequestParam(required = false) String typeThree,
			LyhtPageVO lyhtPageVO) {
		return service.queryOwnerList(cityCode, typeAll, typeOne, typeTwo, typeThree, lyhtPageVO);
	}

	@PostMapping(value = "/queryTypeList")
	@ApiOperation(value = "查询土地类型", notes = "查询土地类型")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区行政区cityCode", required = true) })
	public LyhtResultBody<List> queryTypeList(@RequestParam(required = true) String cityCode) {
		return service.queryTypeList(cityCode);

	}

	@GetMapping(value = "/queryUserLandInfo")
	@ApiOperation(value = "获得用户土地面积情况", notes = "根据权属人编码查询该户的土地面积情况")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "权属人编码", required = true) })
	public LyhtResultBody<List> queryUserLandInfo(@RequestParam(required = true) String ownerNm) {
		List<Map<String, Object>> mapList = service.queryUserLandInfo(ownerNm);
		return new LyhtResultBody<>(mapList);
	}

	@GetMapping(value = "/queryAreaInfo")
	@ApiOperation(value = "查询土地面积详情数据", notes = "返回值说明：{landType: '土地类型', limitsType: '征地范围', area: '面积'}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码", required = true),
			@ApiImplicitParam(name = "status", paramType = "query", value = "查询的土地状态共3种状态 总面积:totalArea,已使用面积:useArea,待分解面积:remainArea", required = true),
			@ApiImplicitParam(name = "level", paramType = "query", value = "下钻的层数0、1、2、3 默认为0", dataType = "number") })
	public LyhtResultBody<List> queryAreaInfo(@RequestParam(required = true) String cityCode,
			@RequestParam(required = true) String status,
			@RequestParam(required = false, defaultValue = "0") int level) {
		List<Map<String, Object>> mapList = service.queryAreaInfo(cityCode, status, level);
		return new LyhtResultBody<>(mapList);
	}

	@GetMapping(value = "/queryLandInfoTable")
	@ApiOperation(value = "查询土地分解详情表格", notes = "返回值说明：{landType: '土地类型', limitsType: '征地范围', area: '面积'}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码", required = true),
			@ApiImplicitParam(name = "status", paramType = "query", value = "查询的土地状态共3种状态 总面积:totalArea,已使用面积:useArea,待分解面积:remainArea", required = true) })
	public LyhtResultBody<List> queryLandInfoTable(@RequestParam(required = true) String cityCode,
			@RequestParam(required = true) String status, LyhtPageVO lyhtPageVO) {
		return service.queryLandInfoTable(cityCode, status, lyhtPageVO);
	}

	@GetMapping(value = "/landResolve")
	@ApiOperation(value = "土地分解", notes = "土地分解")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码", required = true),
			@ApiImplicitParam(name = "level", paramType = "query", value = "下钻的层数0、1、2、3 默认为0", dataType = "number"),
			@ApiImplicitParam(name = "cityCode", paramType = "query", value = "土地类型") })
	public LyhtResultBody<List> landResolve(@RequestParam(required = true) String cityCode,
			@RequestParam(required = false, defaultValue = "0") int level,
			@RequestParam(required = false) String landType) {
		List<Map<String, Object>> maps = service.landResolve(cityCode, level);
		return new LyhtResultBody<>(maps);
	}

	@GetMapping(value = "/queryLandChart")
	@ApiOperation(value = "土地分解统计图表", notes = "土地分解统计图表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码", required = true),
			@ApiImplicitParam(name = "typeAll", paramType = "query", value = "第一级类型"),
			@ApiImplicitParam(name = "typeOne", paramType = "query", value = "第二级类型"),
			@ApiImplicitParam(name = "typeTwo", paramType = "query", value = "第三级类型"),
			@ApiImplicitParam(name = "typeThree", paramType = "query", value = "第四级类型"),
			@ApiImplicitParam(name = "isLast", paramType = "query", value = "是否为最后一个节点"),
			@ApiImplicitParam(name = "status", paramType = "query", value = "查询的土地状态共3种状态 总面积:totalArea,已使用面积:useArea,待分解面积:remainArea") })
	public LyhtResultBody<List> queryLandChart(@RequestParam(required = true) String cityCode,
			@RequestParam(required = false) String typeAll, @RequestParam(required = false) String typeOne,
			@RequestParam(required = false) String typeTwo, @RequestParam(required = false) String typeThree,
			@RequestParam(required = false, defaultValue = "false") Boolean isLast,
			@RequestParam(required = false) String status) {
		List<Map<String, Object>> maps = service.queryLandChart(cityCode, typeAll, typeOne, typeTwo, typeThree, isLast,
				status);
		return new LyhtResultBody<>(maps);
	}

	@GetMapping(value = "/getLandType")
	@ApiOperation(value = "获得土地分解类型", notes = "根据行政区获得可分解的土地类型(选择分解的土地时用到的)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码", required = true),
			@ApiImplicitParam(name = "typeAll", paramType = "query", value = "第一级类型"),
			@ApiImplicitParam(name = "typeOne", paramType = "query", value = "第二级类型"),
			@ApiImplicitParam(name = "typeTwo", paramType = "query", value = "第三级类型"),
			@ApiImplicitParam(name = "typeThree", paramType = "query", value = "第四级类型"),
			@ApiImplicitParam(name = "isLast", paramType = "query", value = "是否为最后一个节点") })
	public LyhtResultBody<List> getLandType(@RequestParam(required = true) String cityCode,
			@RequestParam(required = false) String typeAll, @RequestParam(required = false) String typeOne,
			@RequestParam(required = false) String typeTwo, @RequestParam(required = false) String typeThree,
			@RequestParam(required = false, defaultValue = "false") Boolean isLast) {
		List<Map<String, Object>> maps = service.getLandType(cityCode, typeAll, typeOne, typeTwo, typeThree, isLast);
		return new LyhtResultBody<>(maps);
	}

	@GetMapping(value = "/getCity")
	@ApiOperation(value = "获得行政区", notes = "获得子行政区")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cityCode", paramType = "query", value = "行政区编码", required = true) })
	public LyhtResultBody<List> getCity(@RequestParam(required = true) String cityCode) {
		List<Map<String, Object>> maps = service.queryChildCity(cityCode);
		return new LyhtResultBody<>(maps);
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = "保存土地分解", notes = "保存土地分解信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "parm", paramType = "body", value = "{\n"
			+ " \"landAuditEntities\": [{\n" + "  \"sourceRegion\": \"源行政区编码\",\n"
			+ "  \"targetRegion\": \"目标行政区编码\",\n" + "  \"nm\": \"用户内码\",\n" + "  \"flag\": \"分解到户：0, 分解到行政区：1\",\n"
			+ "  \"landType\": \"土地类型\",\n" + "  \"scope\": \"征地范围\",\n" + "  \"typeLevel\": \"分类的级别\",\n"
			+ "  \"resolveArea\": \"分解面积\",\n" + "  \"separateArea\": \"待分解面积\" \n" + " }]\n }", required = true) })
	public LyhtResultBody<List> save(@RequestBody String parm, HttpServletRequest request) {
		List<LandAuditEntity> landAuditEntities = null;
		try {
			parm = URLDecoder.decode(parm, "UTF-8");
			// 进行参数转换
			Map<String, Object> map = JSON.parseObject(parm, Map.class);
			String sTableColumns = JSONObject.toJSONString((JSONArray) map.get("landAuditEntities"));
			if (StringUtils.isNotBlank(sTableColumns)) {
				landAuditEntities = JSONObject.parseArray(sTableColumns, LandAuditEntity.class);
			}
		} catch (LyhtRuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		if (landAuditEntities != null && landAuditEntities.size() > 0) {
			service.saveAll(landAuditEntities, request);
		}
		return new LyhtResultBody();
	}

	@PostMapping(value = "/startProcess")
	@ApiOperation(value = "开始土地分解流程", notes = "发起流程")
	public LyhtResultBody<LandAuditEntity> startProcess(Integer id, HttpServletRequest request) {
		return service.startProcess(id, request);
	}

	@PostMapping(value = "/startProcessBatch")
	@ApiOperation(value = "开始土地分解流程 批量发起", notes = "发起流程批量")
	public LyhtResultBody<String> startProcessBatch(String ids, HttpServletRequest request) {
		return service.startProcessBatch(ids, request);
	}

	@PostMapping(value = "/callBack")
	@ApiOperation(value = "土地分解流程 审核完成 回调", notes = "回调")
	public LyhtResultBody<LandAuditEntity> callBack(String processId, Integer isSuccess) {
		return service.callBack(processId, isSuccess);
	}

	@PostMapping(value = "/queryAuditList")
	@ApiOperation(value = "获取土地审核列表", notes = "查询所有土地分解审核信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "auditCode", paramType = "query", value = "审核状态码"),
			@ApiImplicitParam(name = "uname", paramType = "query", value = "户主姓名"),
			@ApiImplicitParam(name = "city", paramType = "query", value = "行政区名称"),
			@ApiImplicitParam(name = "sDate", paramType = "query", value = "起始时间"),
			@ApiImplicitParam(name = "eDate", paramType = "query", value = "截止时间") })
	public LyhtResultBody<List<Map<String, Object>>> queryAuditList(@RequestParam(required = false) String auditCode,
			@RequestParam(required = false) String uname, @RequestParam(required = false) String city,
			@RequestParam(required = false) String sDate, @RequestParam(required = false) String eDate,
			LyhtPageVO lyhtPageVO) {
		return landAuditService.queryAuditList(auditCode, uname, city, sDate, eDate, lyhtPageVO);
	}

	@GetMapping(value = "/landAudit")
	@ApiOperation(value = "土地分解审核", notes = "土地分解审核")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", paramType = "query", value = "主键id", required = true),
			@ApiImplicitParam(name = "auditCode", paramType = "query", value = "审核状态码", required = true),
			@ApiImplicitParam(name = "remarks", paramType = "query", value = "审核备注") })
	public LyhtResultBody<List> landAudit(@RequestParam(required = true) int id,
			@RequestParam(required = true) String auditCode, @RequestParam(required = false) String remarks) {
		landAuditService.landAudit(id, auditCode, remarks);
		return new LyhtResultBody<>();
	}

	@PostMapping("/IfMoreThan")
	@ApiOperation(value = "校验 查询可分解土地面积", notes = "查询可分解面积")
	public LyhtResultBody<Double> IfMoreThan(Integer id, Double number, String region, String scope, String allType,
			String typeOne, String typeTwo, String typeThree) {
		if (!StringUtils.isNotBlank(typeThree)) {
			typeThree = "";
		}
		return service.queryArea(id, number, region, scope, allType, typeOne, typeTwo, typeThree);
	}

	@PostMapping("/queryArea")
	@ApiOperation(value = "校验 查询可分解土地面积", notes = "查询可分解面积")
	public LyhtResultBody<Double> queryArea(String region, String scope, String allType, String typeOne, String typeTwo,
			String typeThree) {
		if (!StringUtils.isNotBlank(typeThree)) {
			typeThree = "";
		}
		return service.queryArea(region, scope, allType, typeOne, typeTwo, typeThree);
	}

	@PostMapping("/getDataByTaskId")
	@ApiOperation(value = "代办页面 根据流程id 查询申请信息", notes = "查询申请信息")
	public LyhtResultBody<LandAuditVO> getDataByTaskId(String taskId) {
		return landAuditService.getDataByTaskId(taskId);
	}

	@PostMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(Integer id) {
		return landAuditService.delete(id);
	}
}
