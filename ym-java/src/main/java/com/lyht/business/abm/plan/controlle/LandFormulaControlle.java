package com.lyht.business.abm.plan.controlle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.service.RequisitionPlanService;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.dao.TdPublicityDetailDao;
import com.lyht.business.abm.plan.entity.TdPublicityDetailEntity;
import com.lyht.business.abm.plan.entity.TdPublicityEntity;
import com.lyht.business.abm.plan.service.LandFormulaService;
import com.lyht.business.abm.plan.service.OwnerVerifyService;
import com.lyht.business.abm.plan.service.PublicityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/landFormula")
@Api(value = "土地复核申请", tags = "土地复核申请")
public class LandFormulaControlle {

	@Autowired
	TdPublicityDetailDao tdPublicityDetailDao;
	@Autowired
	RequisitionPlanService requisitionPlanService;
	@Autowired
	OwnerVerifyService service;
	@Autowired
	PublicityService publicityService;
	@Autowired
	ProjectPlanDao projectPlanDao;
	@Autowired
	private LandFormulaService landFormulaService;

	/**
	 * 查询公示列表
	 * @param pageVO
	 * @param region
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/getList")
	@ApiOperation(value = " 公示列表 ", notes = " 公示列表  ")
	public LyhtResultBody<List<Map>> getOwnerList(LyhtPageVO pageVO, String region, String name, String startTime,
			String endTime) {
		if ("Invalid date".equals(startTime)) {
			startTime = null;
		}
		if ("Invalid date".equals(endTime)) {
			endTime = null;
		}

		return landFormulaService.getList(pageVO, region, name, startTime, endTime);
	}

	/**
	 * 创建公式人及添加公示人
	 * @param entity 
	 * @param list 公示列表
	 * @return
	 */
	@PostMapping(value = "/savePublicity")
	@ApiOperation(value = "添加土地指标公示", notes = "添加土地指标公示")
	public LyhtResultBody<TdPublicityEntity> saveVerify(TdPublicityEntity entity, String list) {
		return landFormulaService.save(entity, list);
	}

	/**
	 * 查看行政区下的户主
	 * @param region 行政区汉字
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/getUserList")
	@ApiOperation(value = "土地公示人员列表 ", notes = " 土地公示人员列表  ")
	@ApiImplicitParams({ @ApiImplicitParam(name = "region", paramType = "query", value = "行政区") })
	public LyhtResultBody<List<Map>> getUserList(@RequestParam(required = false) String region) {
		return landFormulaService.getUserList(region);
	}

	/**
	 * 修改公示人
	 * @param list 添加公示人json格式
	 * @param nm 内码
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/saveGsTdHome")
	@ApiOperation(value = " 土地修改公示人信息 ", notes = "土地修改人信息")
	public List<Map> tdSavePublicity(String list, String nm) {
		// 先删除在保存
		if (nm != null) {
			landFormulaService.delete(nm);
		}

		List<Map> list1 = new ArrayList<>();
		Map map = new HashMap<>();
		try {
			List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(list);
			for (int i = 0; i < listObjectFir.size(); i++) {
				TdPublicityDetailEntity entity = new TdPublicityDetailEntity();

				String ownerNm = listObjectFir.get(i).getOrDefault(("owner_nm").toString(), "owner_nm".toString());
				String zbType = listObjectFir.get(i).getOrDefault(("zb_type").toString(), "zb_type".toString());
				// String nm=listObjectFir.get(i).getOrDefault(("nm").toString(),
				// "nm".toString());
				String endTime = listObjectFir.get(i).getOrDefault(("end_time").toString(), "end_time".toString());
				String processId = listObjectFir.get(i).getOrDefault(("process_id").toString(), "process_id".toString());

				entity.setOwnerNm(ownerNm);
				entity.setZbType(zbType);
				entity.setNm(nm);
				entity.setEndTime(endTime);
				entity.setProcessId(processId);
				service.editfgState("1", processId);// 0未公示 1待公示 2已公示
				tdPublicityDetailDao.save(entity);

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
	 * 删除公式
	 * @param request
	 * @param id 
	 * @param nm
	 * @param type
	 * @return
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = " 删除公示信息 ", notes = " 删除公示信息 ")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id, String nm, Integer type) {
		return landFormulaService.delete(id);
	}
	
	
	/**
	 * 查看土地公示户主列表
	 * @param nm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/detaiList")
	@ApiOperation(value = " 查看土地公示户主列表 ", notes = " 查看土地公示户主列表 ")
	public LyhtResultBody<List<Map>> detaiList(String nm) {
		 List<Map> detaiList = landFormulaService.detaiList(nm);
		 return new LyhtResultBody(detaiList);
	}
	
	
	
	/**
	 * 确认公示土地
	 * @param nm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping(value = "/confirmPublicity")
	@ApiOperation(value = " 确认公示土地", notes = " 确认公示土地 ")
	public LyhtResultBody confirmPublicity(Integer id) {
		 landFormulaService.confirmPublicity(id);
		 return new LyhtResultBody();
	}
	
	
	/**
	 * 公示结束手动
	 * 
	 * @param nm
	 * @param ownerNm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@ApiOperation(value = "手动公示结束", notes = "手动公示结束")
	@GetMapping("/endPublicity")
	public LyhtResultBody endPublicity(int id) {
		landFormulaService.endPublicity(id);
		return new LyhtResultBody();
	}
	

}
