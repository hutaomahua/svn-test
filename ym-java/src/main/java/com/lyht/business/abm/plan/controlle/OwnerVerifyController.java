package com.lyht.business.abm.plan.controlle;

import com.alibaba.fastjson.JSONArray;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.service.RequisitionPlanService;
import com.lyht.business.abm.plan.dao.OwnerVerifyDao;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.dao.PublicityDetailDao;
import com.lyht.business.abm.plan.entity.PublicityDetailEntity;
import com.lyht.business.abm.plan.entity.PublicityEntity;
import com.lyht.business.abm.plan.service.LandFormulaService;
import com.lyht.business.abm.plan.service.OwnerVerifyService;
import com.lyht.business.abm.plan.service.PublicityService;
import com.lyht.business.abm.plan.vo.PublicityFamilyVO;
import com.lyht.business.abm.removal.service.AbmFamilyService;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.util.ExportExcelWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/ownerVerify")
@Api(value = "复核申请", tags = "复核申请")
public class OwnerVerifyController {
	@Autowired
	OwnerVerifyDao ownerVerifyDao;
	@Autowired
	RequisitionPlanService requisitionPlanService;
	@Autowired
	OwnerVerifyService service;
	@Autowired
	PublicityService publicityService;
	@Autowired
	ProjectPlanDao projectPlanDao;
	@Autowired
	PublicityDetailDao publicityDetailDao;
	@Value("${iwind.process.flow.path.ownerVerify}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath;
	@Autowired
	private ProcessOperateService processOperateService; // 注入流程服务实现类

	@Autowired
	private LandFormulaService landFormulaService;

	@Autowired
	AbmFamilyService abmFamilyService;

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/getList")
	@ApiOperation(value = " 公示列表 ", notes = " 公示列表  ")
	public LyhtResultBody<List<Map>> getOwnerList(LyhtPageVO pageVO, String region, String name, String flag,
			String startTime, String endTime) {
		String zbType = "";

		if ("Invalid date".equals(startTime)) {
			startTime = null;
		}
		if ("Invalid date".equals(endTime)) {
			endTime = null;
		}
		if (null != region && !"".equals(region)) {
			String[] r = region.split(",");
			region = r[r.length - 1];
			List<Map> list = projectPlanDao.regionName(region);
			if (list.size() > 0) {
				region = list.get(0).get("merger_name").toString();
			}
		}
		if (flag != null) {
			zbType = !flag.equals("bq") ? "1" : "2";
		} else {
			zbType = "1";
		}
		return publicityService.getList(pageVO, region, name, zbType, startTime, endTime);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/getListDetails")
	@ApiOperation(value = " 公示详情列表 ", notes = " 公示详情列表  ")
	public LyhtResultBody<List<Map>> getListDetails(LyhtPageVO pageVO, String nm) {

		return publicityService.getListDetails(pageVO, nm);
	}

	@ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
	@SuppressWarnings("rawtypes")
	@GetMapping("/export")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String nm) {
		List<Map> list = requisitionPlanService.getHdListExcl(nm);

		String title = "搬迁人口基本信息";

		String fileName = title;
		String[] headers = { "序号", "行政区", "户主", "身份证", "原调查人口（人）", "现核定人口（人）" };
		String[] columnNames = { "xuhao", "region", "name", "id_card", "i_population", "ymNum" };
		try {
			ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
			exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
					ExportExcelWrapper.EXCEl_FILE_2007);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = " 删除公示信息 0 个人财产，1 土地 ， 2 人口 ", notes = " 删除公示信息 ")
	public LyhtResultBody<String> delete(String nm, Integer type) {
		return publicityService.deletePublicity(nm, type);
	}

	@PostMapping(value = "/deleteDetail")
	@ApiOperation(value = " 删除公示信息 ", notes = " 删除公示信息 ")
	public LyhtResultBody<Integer> deleteDetail(HttpServletRequest request, Integer id) {
		return publicityService.deleteDetail(id);
	}

	@PostMapping(value = "/savePublicity")
	@ApiOperation(value = "实物指标公示", notes = "实物指标公示")
	public LyhtResultBody<PublicityEntity> saveVerify(PublicityEntity entity, String list) {
		return publicityService.save(entity, list);
	}

	@PostMapping(value = "/fhAudit")
	@ApiOperation(value = "复核审批", notes = "复核审批")
	public void fhAudit(String state, String processId, String comment, String name) {
		int result1 = state.indexOf("乡镇意见");
		int result2 = state.indexOf("县移民");
		int result7 = state.indexOf("-1");// 拒绝

		if (name.equals("权属人复核申请")) {
			if (result1 != -1)// 乡镇审批
			{
				service.editFhState("2", processId);// 0未发起 1 发起 2乡确定通过 3县通过 -1未通过
				service.editFhxzYj(comment, processId);
			}
			if (result2 != -1) {
				service.editFhState("3", processId);
				service.editFhxzYj(comment, processId);
			}
			if (result7 != -1) {
				service.editFhState("-1", processId);
				service.editFhxzYj(comment, processId);
			}
		}
		if (name.equals("实物指标电子流程新")) {
			int result3 = state.indexOf("综合设计");
			int result4 = state.indexOf("综合监理");
			int result5 = state.indexOf("项目法人");
			// a1待审核 a2乡通过 a3县通过 a4综合设计 a5综合监理 a6项目法人 a-1拒绝
			if (result1 != -1) {
				service.editFhState("a2", processId);
			}
			if (result2 != -1) {
				service.editFhState("a3", processId);
			}
			if (result3 != -1) {
				service.editFhState("a4", processId);
			}
			if (result4 != -1) {
				service.editFhState("a5", processId);
			}
			if (result5 != -1) {
				service.editFhState("a6", processId);
			}
			if (result7 != -1) {
				service.editFhState("a-1", processId);
			}
		}

	}

	/**
	 * 保存公式户主
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/saveGsHome")
	@ApiOperation(value = " 安置方案修改公示人口 ", notes = "安置方案")
	public List<Map> saveJieding(String list, String nm) {

		if (nm != null) {
			publicityService.delete(nm);
		}

		List<Map> list1 = new ArrayList<>();
		Map map = new HashMap<>();
		try {
			List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(list);
			for (int i = 0; i < listObjectFir.size(); i++) {
				PublicityDetailEntity entity = new PublicityDetailEntity();

				String ownerNm = listObjectFir.get(i).getOrDefault(("owner_nm").toString(), "owner_nm".toString());
				String zbType = listObjectFir.get(i).getOrDefault(("zb_type").toString(), "zb_type".toString());
//				String nm = listObjectFir.get(i).getOrDefault(("nm").toString(), "nm".toString());
				String endTime = listObjectFir.get(i).getOrDefault(("end_time").toString(), "end_time".toString());

				entity.setOwnerNm(ownerNm);
				entity.setZbType(zbType);
				entity.setNm(nm);
				entity.setEndTime(endTime);
				service.editGsState("3", ownerNm);
				publicityDetailDao.save(entity);

			}
			map.put("code", 200);
			list1.add(map);
		} catch (Exception e) {
			map.put("code", 500);
			list1.add(map);
		}

		return list1;

	}

	/**
	 * 发起实物店子流程
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "发起实物指标变更", notes = "发起实物指标变更")
	@GetMapping("/start")
	public LyhtResultBody startChange(String nm, HttpServletRequest request) {
		ProcessOperateVO processOperateVO = new ProcessOperateVO();
		processOperateVO.setFlowPath(physicalFlowPath);
		Map<String, String> taskData = new HashMap<>();
		taskData.put("ownerNm", String.valueOf(nm));

		processOperateVO.setData(taskData);
		String processId = processOperateService.processStart(processOperateVO, request);
		service.updateAbmFamily(nm, "a1", processId);// 待审核
		// 第一次流程 1待审核 2乡通过 3通过 -1拒绝
		// 第二次流程 a1待审核 a2乡通过 a3县通过 a4综合设计 a5综合监理 a6项目法人
		return new LyhtResultBody();
	}

	/**
	 * 确认公示
	 * 
	 * @param nm
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "实物指标发起公示", notes = "实物指标发起公示")
	@PostMapping("/confirmPublicity")
	public LyhtResultBody<String> confirmPublicity(PublicityEntity publicityEntity,String senderNm) {
		String msg = "成功！";
		// 判断时间true(date 在endTime 日期之前),false(date 在endTime 日期之后)
//		boolean compareToDay = DateUtil.compareToDay(publicityEntity.getStartTime(), publicityEntity.getEndTime());
//		if (compareToDay == false) {
//			msg = "公式开始日期小于结束日期";
//			return new LyhtResultBody(msg);
//		}

		publicityService.updateState(publicityEntity,senderNm);
		return new LyhtResultBody<>(msg);
	}

	/**
	 * 
	 * 
	 * @param nm
	 * @param ownerNm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "实物指公示户主", notes = "实物指公示户主")
	@GetMapping("/detailPublicity")
	public LyhtResultBody findList(String nm) {

		List<Map> findList = publicityService.findList(nm);
		return new LyhtResultBody(findList);
	}

	/**
	 * 修改查询列表显示选中
	 * 
	 * @param nm
	 * @param ownerNm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "待公示户主回显", notes = "实物指公示户主")
	@GetMapping("/echoUser")
	public LyhtResultBody findEchoDisplay(String nm, String region) {
		Map findList = service.findEchoDisplay(nm, region);
		return new LyhtResultBody(findList);
	}

	/**
	 * 公示结束手动
	 * 
	 * @param nm
	 * @param ownerNm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@ApiOperation(value = "手动公示结束", notes = "手动公示结束")
	@GetMapping("/endPublicity")
	public LyhtResultBody endPublicity(int id, Integer type,String senderNm) {
		// type = 0 实物指标 ； type = 1 土地
		if (type == 0) {
			publicityService.endPublicity(id,senderNm);
		} else if (type == 1) {
			publicityService.endPublicityLand(id);
		}

		return new LyhtResultBody();
	}

	@ApiOperation(value = "移民人口公式 移民人口公示 查询所有已完成实物指标公式的权属人下的家庭成员", notes = "移民人口公示 查询人口")
	@PostMapping("/getPublicityFamily")
	public LyhtResultBody<List<PublicityFamilyVO>> getPublicityFamily(String region, String type) {
		LyhtResultBody<List<PublicityFamilyVO>> map = new LyhtResultBody<List<PublicityFamilyVO>>();
		if (type.equals("52C2A14B90")) {// 生产安置
			map = publicityService.getPublicityFamily(region);
		} else if (type.equals("91DDEE6352")) {// 搬迁安置
			map = publicityService.getPubRegionInfoRemovalFamily(region);
		} else if (type.equals("6598659035")) {// 逐年补偿

		}
		return map;
	}

	/**
	 * 移民人口开始公示 修改状态
	 */
	@ApiOperation(value = "移民人口公式 开始公示/结束公示 修改公示状态 type 开始:0/结束:1 ", notes = "移民人口公示 修改状态")
	@PostMapping("/startPublic")
	public LyhtResultBody<PublicityEntity> startPublic(Integer id, Integer type,String senderNm) {
		return publicityService.startPublic(id, type,senderNm);
	}

	@PostMapping(value = "/getOwnerAllList")
	@ApiOperation(value = " 公示权属人列表 土地、实物指标公示整合 ", notes = " 公示 权属人列表  ")
	public LyhtResultBody<List<Map>> getOwnerAllList(String region, String type) {
		if (StringUtils.isBlank(type) || StringUtils.isBlank(region)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		LyhtResultBody<List<Map>> map = new LyhtResultBody<List<Map>>();
		if (type.equals("E571237B71")) {// 土地
			map = landFormulaService.getUserList(region);
		} else if (type.equals("D5A8B0DB4F")) {// 个人财产
			map = abmFamilyService.getOwnerAllList(region);
		}
		return map;
	}

	@PostMapping(value = "/getPubRegionInfo")
	@ApiOperation(value = " 公示权属人列表 土地、实物指标公示整合 查询存在被公示人的行政区 ", notes = "查询行政区  ")
	public LyhtResultBody<List<Map<String, Object>>> getPubRegionInfo(String type) {
		if (StringUtils.isBlank(type)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		LyhtResultBody<List<Map<String, Object>>> map = new LyhtResultBody<List<Map<String, Object>>>();
		System.out.println(type);
		if (type.equals("E571237B71")) {// 土地
			map = publicityService.getPubRegionInfoLand();
		} else if (type.equals("D5A8B0DB4F")) {// 个人财产
			map = publicityService.getPubRegionInfo();
		} else if (type.equals("52C2A14B90")) {// 生产安置
			map = publicityService.getPubRegionInfoMove();
		} else if (type.equals("91DDEE6352")) {//搬迁安置
			map = publicityService.getPubRegionInfoRemoval();//
		} else if (type.equals("6598659035")) {// 逐年补偿

		}
		return map;
	}

	@PostMapping("/getByDetailsNm")
	@ApiOperation(value = "人口公示查看", notes = "人口公示查看")
	public LyhtResultBody<List<Map<String, Object>>> getByDetailsNm(String nm) {
		return publicityService.getByDetailsNm(nm);
	}
}
