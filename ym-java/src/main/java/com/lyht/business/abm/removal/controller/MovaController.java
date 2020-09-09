package com.lyht.business.abm.removal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.service.RequisitionPlanService;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.removal.bean.MoveIdDetail;
import com.lyht.business.abm.removal.dao.MoveApproveDao;
import com.lyht.business.abm.removal.dao.MoveDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.MoveApproveEntity;
import com.lyht.business.abm.removal.entity.MoveIdentity;
import com.lyht.business.abm.removal.service.MovaKyService;
import com.lyht.business.abm.removal.service.MovaService;
import com.lyht.business.abm.removal.vo.MoveIdVo;
import com.lyht.business.abm.removal.vo.PlacementVO;
import com.lyht.business.abm.removal.vo.RemovalCountVO;
import com.lyht.util.ExportExcelWrapper;
import com.lyht.util.Randomizer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jms 2019/10/30 搬迁安置
 */
@RestController
@RequestMapping(value = "/mova/placement")
@Api(value = "搬迁安置", tags = "搬迁安置")
public class MovaController {
	private Logger log = LoggerFactory.getLogger(MovaController.class);
	@Autowired
	MovaService movaService;
	@Autowired
	private MoveDao dao;

	@Autowired
	MovaKyService movaKyService;
	@Autowired
	MoveApproveDao moveApproveDao;

	@PostMapping(value = "/saveQuXiang")
	@ApiOperation(value = " 搬迁安置去向表 ", notes = "搬迁安置去向")
	public List<Map> saveQuXiang(String list) {
		List<Map> list1 = new ArrayList<>();
		Map map = new HashMap<>();
		try {
			map.put("code", 200);
			list1.add(map);
			List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(list);
			for (int i = 0; i < listObjectFir.size(); i++) {
				String id = listObjectFir.get(i).getOrDefault(("id").toString(), "".toString());

				String owenrNm = listObjectFir.get(i).getOrDefault(("nm").toString(), "".toString());
				String toWhere = listObjectFir.get(i).getOrDefault("toWhere".toString(), "".toString());
				String placeType = listObjectFir.get(i).getOrDefault("placeType".toString(), "".toString());
				String placeName = listObjectFir.get(i).getOrDefault("placeName".toString(), "".toString());
				String placeAddr = listObjectFir.get(i).getOrDefault("placeAddr".toString(), "".toString());

				if (!id.equals("")) {
					MoveApproveEntity entity = new MoveApproveEntity();
					entity.setId(Integer.parseInt(id));
					entity.setNm(owenrNm);
					entity.setOwnerNm(owenrNm);
					entity.setPlaceAddr(placeAddr);
					entity.setPlaceName(placeName);
					entity.setToWhere(toWhere);
					entity.setPlaceType(placeType);
					moveApproveDao.save(entity);

				} else {
					MoveApproveEntity entity = new MoveApproveEntity();
					entity.setNm(owenrNm);
					entity.setOwnerNm(owenrNm);
					entity.setPlaceAddr(placeAddr);
					entity.setPlaceName(placeName);
					entity.setToWhere(toWhere);
					entity.setPlaceType(placeType);
					moveApproveDao.save(entity);

				}

			}
		} catch (Exception e) {
			map.put("code", 500);
			list1.add(map);
		}

		return list1;

	}

	@PostMapping(value = "/getList")
	@ApiOperation(value = "查询列表 条件查询", notes = "搬迁安置")
	public LyhtResultBody getList(LyhtPageVO pageVO, MoveIdVo moveIdVo) {
		return movaService.getList(pageVO, moveIdVo);
	}

	@PostMapping(value = "/getListKy")
	@ApiOperation(value = "查询列表 条件查询", notes = "搬迁安置可研")
	public LyhtResultBody<List<Map>> getListKy(LyhtPageVO pageVO, MoveIdVo moveIdVo) {

		return movaService.getListKy(pageVO, moveIdVo.getRegion(), moveIdVo.getName(), moveIdVo.getNextCount(),
				moveIdVo.getHome());
	}

	@PostMapping(value = "/getHzDate")
	@ApiOperation(value = "查询列表 条件查询", notes = "户主数据")
	public Map getHzDate(LyhtPageVO pageVO, MoveIdVo moveIdVo) {
		LyhtResultBody<List<Map>> data = movaService.getListKy(pageVO, moveIdVo.getRegion(), moveIdVo.getName(),
				moveIdVo.getNextCount(), moveIdVo.getHome());
		List<Map> list = data.getList();
		Map map = new HashMap();
		Map map1 = new HashMap();

		List<Map> lists = new ArrayList();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).get("ownerType") != null) {
					if (list.get(i).get("ownerType").equals("户主")) {
						map.put("regionName", list.get(i).get("regionName"));
						map.put("scopeName", list.get(i).get("scopeName"));
						map.put("householdsType", list.get(i).get("householdsType"));

						break;
					}
				}
			}
		}
		lists.add(map);
		map1.put("list", lists);
		return map1;
	}

	@PostMapping(value = "/getQxList")
	@ApiOperation(value = "查询列表 条件查询", notes = "搬迁去向")
	public LyhtResultBody<List<Map>> getQxList(LyhtPageVO pageVO, MoveIdVo moveIdVo) {
		return movaService.getQxList(pageVO, moveIdVo.getRegion(), moveIdVo.getName(), moveIdVo.getNextCount());
	}

	@ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
	@SuppressWarnings("rawtypestwo")
	@GetMapping("/export")
	public void exportExcel(HttpServletResponse response, MoveIdVo moveIdVo) {
		List<Map> list = movaService.list(moveIdVo.getRegion(), moveIdVo.getName(), moveIdVo.getIsno());
		String title = "搬迁安置人口身份定界表";
		String fileName = title;
		String[] headers = { "姓名", "所属户主", "户主关系", "性别", "身份证号码", "是否符合", "界定条件", "备注", "附件信息" };
		String[] columnNames = { "name", "hzName", "ownerType", "gender", "idCard", "isno", "djtj", "remark",
				"remarksa" };
		try {
			ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
			exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
					ExportExcelWrapper.EXCEl_FILE_2007);
		} catch (Exception e) {
			log.error("excel导出失败：", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
	}

	@PostMapping(value = "/getListscKy")
	@ApiOperation(value = "查询列表 条件查询", notes = "生产安置可研")
	public LyhtResultBody<List<Map>> getListscKy(LyhtPageVO pageVO, String region, int nextCount) {
		return movaKyService.getList(pageVO, region, nextCount);
	}

	@PostMapping(value = "/getBQKY")
	@ApiOperation(value = "查询列表 条件查询", notes = "搬迁安置可研")
	public LyhtResultBody<List<Map>> getBQKY(LyhtPageVO pageVO, String region, String name, String research,
			String researchType) {
		return movaService.getBQKY(pageVO, region, name, research, researchType);
	}

	@PostMapping(value = "/getHomeSS")
	@ApiOperation(value = "搬迁人口权属人查询列表 条件查询", notes = "搬迁安置实施")
	public LyhtResultBody<List<Map>> getHomeSs(LyhtPageVO pageVO, String region, String name, String research,
			String researchType, String idCard) {
		return movaService.getHomeSs(pageVO, region, name, research, researchType, idCard);
	}

	@PostMapping(value = "/getHomeSSs")
	@ApiOperation(value = "搬迁人口权属人查询列表 条件查询", notes = "搬迁安置实施-户主列表")
	public LyhtResultBody<List<Map>> getHomeSss(LyhtPageVO pageVO, String region, String name, String research,
			String researchType, String idCard) {
		return movaService.getHomeSss(pageVO, region, name, research, researchType, idCard);
	}

	@PostMapping(value = "/getRegion")
	@ApiOperation(value = "查询列表 条件查询", notes = "搬迁安置可研")
	public List<Map> getRegion(Integer type, String name) {
		return movaService.getRegion(type, name);
	}

	@PostMapping(value = "/ojbk")
	@ApiOperation(value = "权属人编码", notes = "")
	public String ojbk() {
		List<Map> list = dao.getListss();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("research") != null) {
				String type = list.get(i).get("research").toString();
				if (type.equals("货币安置") || type.equals("分散安置")) {
					movaService.updateAnzhi(type, Integer.valueOf(list.get(i).get("id").toString()));
				}
				int result1 = type.indexOf("集中安置点");
				if (result1 != -1) {
					movaService.updateAnzhiss("集中安置点", Integer.valueOf(list.get(i).get("id").toString()));
				}
			}

		}
		return "";
	}

	@GetMapping(value = "/getRemovalCount")
	@ApiOperation(value = "查询列表 条件查询", notes = "搬迁汇总")
	public LyhtResultBody<RemovalCountVO> getRemovalCount() {
		return new LyhtResultBody(movaService.getRemovalAggreate());
	}

	@GetMapping(value = "/getPlacementCount")
	@ApiOperation(value = "查询列表 条件查询", notes = "安置按户汇总")
	public LyhtResultBody<List<PlacementVO>> getPlacementCount(String nm,
			@RequestParam(required = false) Integer isSatisfy) {
		if (StringUtils.isBlank(nm)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(movaService.getPlacementCount(nm, isSatisfy));
	}

}
