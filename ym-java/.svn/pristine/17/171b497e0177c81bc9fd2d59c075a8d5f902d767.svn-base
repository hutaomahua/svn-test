package com.lyht.business.abm.signed.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.signed.dao.ProtocolEscrowDao;
import com.lyht.business.abm.signed.dao.ProtocolInfoDao;
import com.lyht.business.abm.signed.dao.TotalStateDao;
import com.lyht.business.abm.signed.entity.ProtocolEscrow;
import com.lyht.business.abm.signed.entity.ProtocolInfo;
import com.lyht.business.abm.signed.vo.EscrowPerviewVO;
import com.lyht.business.abm.signed.vo.GraphVO;
import com.lyht.business.abm.signed.vo.ProtocolStatisticsVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.FileDownUtil;
import com.lyht.util.Randomizer;
import com.lyht.util.WordReplaceKeyUtil;

@Service
public class ProtocolEscrowService {

	@Autowired
	private ProtocolEscrowDao dao;

	@Autowired
	private TotalStateDao stateDao;

	@Autowired
	private ProtocolInfoDao infoDao;

	@Autowired
	private AbmOwnerDao ownerDao;

	/**
	 * 首页统计 图标
	 * 
	 * @param mergerName 行政区 全称
	 * @return
	 */
	public LyhtResultBody<List<GraphVO>> getHomePageGraph(String mergerName, Integer levelType) {
		if (StringUtils.isBlank(mergerName)) {
			mergerName = "云南省,迪庆藏族自治州,维西傈僳族自治县";
		}
		return new LyhtResultBody<>(dao.getHomePageGraph(mergerName, levelType));
	}

	public ProtocolEscrow getOne(Integer id) {
		return dao.getOne(id);
	}

	/**
	 * 资金代管协议是否签订 未签订资金代管协议 但已已签订房屋可签订 为签订房屋协议 不可签订 已签订资金代管协议不可签订
	 * 
	 * @param ownerNm 权属人nm
	 * @return
	 */
	public LyhtResultBody<Integer> getEscrowCount(String ownerNm) {
		Integer count = dao.getEscrowCount(ownerNm);
		Integer result = 0;
		if (count != null && count == 0) {// 如果=0 则为未签订资金代管协议 即可签订
			result = 1;
		}
		return new LyhtResultBody<>(result);
	}

	/**
	 * 查询是否已签订 房屋协议 已签订可签订资金代管协议 未签订则不可以
	 * 
	 * @param ownerNm 权属人nm
	 * @return
	 */
	public LyhtResultBody<Integer> getHouseStatus(String ownerNm) {
		Integer status = dao.getHouseStatus(ownerNm);
		Integer buildingStatuss = dao.getBuildingStatusStatus(ownerNm);
		Integer isEscrow = dao.getISEscrowCount(ownerNm);
		Integer result = 0;
		if (status != null && status == 1 && buildingStatuss != null && buildingStatuss != 0) {// 如果=1为已签订房屋补偿协议且附属建筑物协议也签订或着无补偿
			if (isEscrow != null && isEscrow > 0) {// 如果大于零 则签订房屋协议并选择资金代管
				result = 1;
			}
		}
		return new LyhtResultBody<>(result);
	}

	/**
	 * 添加 修改
	 * 
	 * @param ProtocolEscrow
	 * @return
	 */
	public LyhtResultBody<ProtocolEscrow> save(ProtocolEscrow protocolEscrow) {
		// 参数校验
		if (protocolEscrow == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = protocolEscrow.getNm();
		if (StringUtils.isBlank(nm)) {
			protocolEscrow.setNm(Randomizer.generCode(10));
			protocolEscrow.setState(0);
			ProtocolInfo info = infoDao.findProtocolInfo(protocolEscrow.getOwnerNm());
			protocolEscrow.setProtocolCode(info.getProtocolCode());// 将房屋协议的值给入
			// 拿到当前权AbmOwnerDao.java属人的 安置点
			Map<String, Object> ownerEscrowInfo = ownerDao.getOwnerEscrowInfo(protocolEscrow.getOwnerNm());
			String country = ownerDao.getCountry(protocolEscrow.getOwnerNm());
			protocolEscrow.setContent("托巴水电站" + ownerEscrowInfo.get("place") + "安置点已完成了前期场平和建房意愿确认工作，"
					+ "为了实现在今年__月__日前完成建房的目标，经" + country + "人民政府和维西县搬迁安置办公室组织" + "由"
					 + "______移民安置建房管理委员会和施工方会议");
		}
		ProtocolEscrow result = dao.save(protocolEscrow);
		return new LyhtResultBody<>(result);
	}

	/**
	 * 单个删除
	 * 
	 * @param id nm
	 * @return
	 */
	@Transactional
	public LyhtResultBody<Integer> delete(Integer id, String nm) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		ProtocolEscrow escrow = dao.findByNm(nm);
		stateDao.updateProtocolStateToFail(escrow.getOwnerNm());
		dao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public LyhtResultBody<String> batchDel(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<ProtocolEscrow> deleteProtocolEscrow = dao.findAllById(idList);
		dao.deleteInBatch(deleteProtocolEscrow);
		return new LyhtResultBody<>(ids);
	}

	public void export(HttpServletResponse response, Integer id) {
		Map<String, Object> exportObejct = dao.export(id);
		String path = "D:\\Server\\uploads\\tuoba\\word\\escrow";
		String filepathString = path + "\\escrow.doc";
		String destpathString = path + "\\" + exportObejct.get("ownerName") + ".doc";
		Map<String, String> map = new HashMap<String, String>();
		if (!CommonUtil.isEmpty(exportObejct.get("place"))) {
			map.put("${PLCAE}", exportObejct.get("place") + "");
		} else {
			map.put("${PLCAE}", "");
		}
		if (!CommonUtil.isEmpty(exportObejct.get("ownerName"))) {
			map.put("${OWNERNAME}", exportObejct.get("ownerName") + "");
		} else {
			map.put("${OWNERNAME}", "");
		}
		if (!CommonUtil.isEmpty(exportObejct.get("homesteadCode"))) {
			map.put("${HOMESTEADCODE}", exportObejct.get("homesteadCode") + "");
		} else {
			map.put("${HOMESTEADCODE}", "");
		}
		if (!CommonUtil.isEmpty(exportObejct.get("houseType"))) {
			map.put("${HOUSETYPE}", exportObejct.get("houseType") + "");
		} else {
			map.put("${HOUSETYPE}", "");
		}
		map.put("${PLACEMENTMONEY}", exportObejct.get("placementMoney") + "");
		if (!CommonUtil.isEmpty(exportObejct.get("placementMoney"))) {
			map.put("${PLACEMENTMONEY}", String.format("%.2f", exportObejct.get("placementMoney")) );
		} else {
			map.put("${PLACEMENTMONEY}", "");
		}
		if (!CommonUtil.isEmpty(exportObejct.get("escrowMoney"))) {
			map.put("${ESCROWMONEY}",String.format("%.2f", exportObejct.get("escrowMoney"))  );
		} else {
			map.put("${ESCROWMONEY}", "");
		}
		map.put("${COMPANY}", exportObejct.get("company") + "");
		if (!CommonUtil.isEmpty(exportObejct.get("company"))) {
			map.put("${COMPANY}", exportObejct.get("company") + "");
		} else {
			map.put("${COMPANY}", "");
		}
		map.put("${CONTENT}", exportObejct.get("content") + "");
		WordReplaceKeyUtil.replaceAndGenerateWord(filepathString, destpathString, map);
		// 转为PDF后 删除源文件 再下载
		//WordTOPDF.wordToPdf(exportObejct.get("ownerName") + ".pdf", destpathString, path + "\\", "doc");
		FileDownUtil.getFile(path + "\\" + exportObejct.get("ownerName") + ".doc",
				exportObejct.get("ownerName") + "资金代管协议.doc", response);
		File file = new File(path + "\\" + exportObejct.get("ownerName") + ".doc");
		if (file.exists()) {
			file.delete();
		}
//		File file1 = new File(path + "\\" + exportObejct.get("ownerName") + ".pdf");
//		if (file1.exists()) {
//			file1.delete();
//		}
	}

	/**
	 * 预览
	 * 
	 * @param id
	 * @return
	 */
	public EscrowPerviewVO preview(Integer id) {
		EscrowPerviewVO perviewVO = dao.preview(id);
		return perviewVO;
	}

	/**
	 * 统计
	 */
	public LyhtResultBody<List<Map<String, Object>>> getStatistics() {
		List<Map<String, Object>> list = dao.getStatistics();
		// 将查询出的行政区转为树形结构
		list = toTree(list, "DCFD44B3E9", true, 0, null, new ArrayList<>());
		return new LyhtResultBody<List<Map<String, Object>>>(list);
	}

	/**
	 * 统计
	 */
	public LyhtResultBody<List<Map<String, Object>>> getStatisticsByParentCode(String parentCode) {
		List<Map<String, Object>> list = dao.getStatisticsByParentCode(parentCode);
		// 将查询出的行政区转为树形结构
		list = toTree(list, "", true, 0, null, new ArrayList<>());
		return new LyhtResultBody<List<Map<String, Object>>>(list);
	}

	/**
	 * 转为树形结构
	 * 
	 * @param mapList
	 * @param pCode   父节点编码
	 * @param isOne   是否第一次进入
	 * @param level   级别
	 * @param parent  父节点的序列号 1.1.2
	 * @param parents 配合前端做查询时展开所用到的，为当前节点所有父节点编码
	 * @return
	 */
	public List<Map<String, Object>> toTree(List<Map<String, Object>> mapList, String pCode, Boolean isOne,
			Integer level, String parent, List<String> parents) {
		List<Map<String, Object>> rData = new ArrayList<>();
		// 定义序号
		int serial = 1;
		for (Map<String, Object> map : mapList) {
			Map<String, Object> data = new HashMap<>();
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			if (parentCode.equals(pCode) || (cityCode.equals(pCode)) && isOne) {
				List<String> tempParents = new ArrayList<>();
				tempParents.addAll(parents);
				tempParents.add(cityCode);
				// 前端处理缩进时使用
				data.put("level", level);
				String serialS = isOne ? serial + "" : parent + "." + serial;
				if (!isOne) {
					data.put("serial", parent + "." + serial);
				} else {
					data.put("serial", serial);
				}
				serial++;
				// 寻找该节点的子节点
				List<Map<String, Object>> children = toTree(mapList, cityCode, false, level + 1, serialS, tempParents);
				data.putAll(map);
				data.put("parents", parents);
				// 判断该节点是否含有子节点 如果没有则设置标识为最后一个节点
				if (children != null && children.size() > 0) {
					data.put("children", children);
				} else {
					data.put("lastNode", true);
				}
				rData.add(data);
			}
		}
		return rData;
	}

	/**
	 * 主页统计查询点击不同列 type: 0（总户数 协议金额） 1(已签订户数，已签订协议金额) 2(资金代管户数,资金代管金额)
	 * 
	 * @param type   判断点击列
	 * @param region 行政区条件 merger_name
	 * @return
	 */
	public LyhtResultBody<List<ProtocolStatisticsVO>> getTableList(Integer type, String region) {
		List<ProtocolStatisticsVO> list = new ArrayList<ProtocolStatisticsVO>();
		if (type == 0) {
			list = dao.getHoldsAndProtocolAmount(region);
		} else if (type == 1) {
			list = dao.getProtocolHoldAndAmount(region);
		} else if (type == 2) {
			list = dao.getEscrowHoldAndAmount(region);
		}

		return new LyhtResultBody<>(list);
	}

}
