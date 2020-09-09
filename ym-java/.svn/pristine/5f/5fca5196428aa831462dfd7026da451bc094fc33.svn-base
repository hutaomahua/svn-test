package com.lyht.business.abm.production.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.production.service.ProduceProcessService;
import com.lyht.business.abm.production.service.ProductionAuditService;
import com.lyht.business.abm.production.service.ProductionService;
import com.lyht.business.abm.production.vo.ProduceCardStatisticsVO;
import com.lyht.business.abm.production.vo.ProduceProcessInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @version: V1.0
 * @author: hjs
 * @className: ProductionController
 * @packageName: com.lyht.business.abm.production.controller
 * @description: （类作用）
 * @data: 2020年02月25日 10:34
 * @see []
 **/
@RestController
@RequestMapping(value = "/production")
@Api(value = "生产安置", tags = "生产安置")
public class ProductionController {

	@Autowired
	private ProductionService productionService;

	@Autowired
	private ProduceProcessService produceProcessService;

	@Autowired
	private ProductionAuditService productionAuditService;
	
	@PostMapping(value = "/directlyToComplete")
	@ApiOperation(value = "批量确认功能")
	public LyhtResultBody<Object> directlyToComplete(String nms) {
		return productionService.directlyToComplete(nms);
	}

	@PostMapping(value = "/queryLandInfoTableKY")
	@ApiOperation(value = "查询生产安置计算人口")
	public LyhtResultBody<List<Map<String, Object>>> queryLandInfoTableKY() {
		return productionService.popuCountKY();
	}

	@PostMapping(value = "/queryLandInfoTable")
	@ApiOperation(value = "查询生产安置计算人口")
	public LyhtResultBody<List> queryLandInfoTable(String region, LyhtPageVO lyhtPageVO) {
		return productionService.popuCount(region, lyhtPageVO);
	}

	@RequestMapping(value = "/findinfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ApiOperation(value = "查询生产安置户主列表", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "uname", paramType = "query", value = "户主姓名", required = false),
			@ApiImplicitParam(name = "idCard", paramType = "query", value = "身份证", required = false),
			@ApiImplicitParam(name = "cityName", paramType = "query", value = "城市名称", required = false),
			@ApiImplicitParam(name = "nm", paramType = "query", value = "户主nm", required = false)})
	public LyhtResultBody<List<Map<String, Object>>> findUserLists(@RequestParam(required = false) String uname,
			@RequestParam(required = false) String cityCode, @RequestParam(required = false) String idCard,
			 @RequestParam(required = false) String nm, @RequestParam(required = false) String cityName,
			LyhtPageVO lyhtPageVO) {
		return productionService.findInfo(uname, idCard, cityName, nm,lyhtPageVO);

	}

	@RequestMapping(value = "/findList", method = { RequestMethod.POST, RequestMethod.GET })
	@ApiOperation(value = "查询生产安置列表", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "uname", paramType = "query", value = "户主姓名", required = false),
			@ApiImplicitParam(name = "idCard", paramType = "query", value = "身份证", required = false),
			@ApiImplicitParam(name = "qcityCode", paramType = "query", value = "查询条件中的行政区编码", required = false),
			@ApiImplicitParam(name = "cityName", paramType = "query", value = "城市名称", required = false) })
	public LyhtResultBody<List> findUserList(@RequestParam(required = false) String uname,
			@RequestParam(required = false) String idCard, @RequestParam(required = false) String qcityCode,
			@RequestParam(required = false) String cityName) {
		return productionService.findUserList(uname, idCard, qcityCode, cityName);
	}

	@RequestMapping(value = "/findUserData", method = { RequestMethod.POST, RequestMethod.GET })
	@ApiOperation(value = "查询该户人口界定详情", notes = "{'basicsData':'该户基础信息', 'landData':'土地的数据', 'personnelData':'该户的人员信息'}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "权属人编码", required = false) })
	public LyhtResultBody findUserList(@RequestParam(required = false) String ownerNm) {
		Map<String, Object> userData = productionService.findUserData(ownerNm);
		return new LyhtResultBody(userData);
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = "保存", notes = "返回值说明：{landType: '土地类型', limitsType: '征地范围', area: '面积'}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "parm", paramType = "body", value = "{\n" + " \"popuData\": [{\n"
			+ "  \"isProduce\": \"是否生产安置人口0:不是 1：是\",\n" + "  \"nm\": \"用户内码\",\n" + " }],\n"
			+ " \"ownerNm\": \"权属人编码\" }", required = true) })
	public LyhtResultBody<String> save(@RequestBody String parm, HttpServletRequest request) {
		try {
			parm = URLDecoder.decode(parm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 进行参数转换
		Map<String, Object> map = JSON.parseObject(parm, Map.class);
		String ownerNm = map.get("ownerNm") != null ? map.get("ownerNm").toString() : null;
		String jsonData = JSON.toJSONString(map);
		produceProcessService.save(ownerNm, jsonData);
		return new LyhtResultBody();
//        try {
//            parm = URLDecoder.decode(parm,"UTF-8");
//            //进行参数转换
//            Map<String, Object> map = JSON.parseObject(parm ,Map.class);
//            List<Map<String,Object>> sTableColumns = (List)map.get("popuData");
//            String ownerNm = map.get("ownerNm") != null ? map.get("ownerNm").toString() : null;
//            String place = map.get("place")!=null? map.get("place").toString() : null;
//            productionService.popuDefinition(place,ownerNm, sTableColumns);
//            return new LyhtResultBody("成功");
//        }catch (LyhtRuntimeException e){
//            throw e;
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
//        }
	}

	@GetMapping(value = "/export")
	@ApiOperation(value = "人口界定表导出", notes = "人口界定表导出")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "权属人编码", required = true) })
	public void export(String ownerNm, HttpServletResponse response) {
		productionAuditService.export(ownerNm, response);
	}
	
	@GetMapping(value = "/export01",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ApiOperation(value = "逐年补偿人口到户核定表", notes = "逐年补偿人口到户核定表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "权属人编码", required = true) })
	public void export01(String ownerNm, HttpServletResponse response) {
		productionAuditService.export01(ownerNm, response);
	}

	@GetMapping(value = "/findByProcessId")
	@ApiOperation(value = "查询流程申请的相关数据", notes = "根据流程id查询该次流程的相关审核数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "processId", paramType = "query", value = "流程id", required = false) })
	public LyhtResultBody<Map<String, Object>> findDataByProcessId(String processId) {
		Map<String, Object> dataByProcessId = productionAuditService.findDataByProcessId(processId);
		return new LyhtResultBody<Map<String, Object>>(dataByProcessId);
	}

	@PostMapping("/getCardStatisiscs")
	@ApiOperation(value = "点击汇总数字时显示卡片", notes = "卡片统计")
	public LyhtResultBody<List<ProduceCardStatisticsVO>> getCardStatisiscs(String region) {
		return productionService.getCardStatisiscs(region);
	}

	/**
	 * 根据流程id查询界定确认信息
	 */
	@PostMapping("/getInfoByTaskId")
	@ApiOperation(value = "根据流程id查询界定确认信息", notes = "根据流程id查询界定确认信息")
	public LyhtResultBody<ProduceProcessInfoVO> getInfoByTaskId(String taskId) {
		return productionService.getInfoByTaskId(taskId);
	}
	
	@GetMapping(value = "/exports")
	@ApiOperation(value = "托巴逐年补偿协议书导出", notes = "托巴逐年补偿协议书导出")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "权属人编码", required = true) })
	public void exports(String ownerNm, HttpServletResponse response) {
		productionService.export(ownerNm, response);
	}

}
