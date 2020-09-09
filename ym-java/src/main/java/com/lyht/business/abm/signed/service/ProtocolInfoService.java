package com.lyht.business.abm.signed.service;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.signed.dao.ProtocolEscrowDao;
import com.lyht.business.abm.signed.dao.ProtocolInfoDao;
import com.lyht.business.abm.signed.dao.TotalStateDao;
import com.lyht.business.abm.signed.entity.ProtocolEscrow;
import com.lyht.business.abm.signed.entity.ProtocolInfo;
import com.lyht.business.abm.signed.entity.TotalState;
import com.lyht.business.abm.signed.vo.OwnerInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolFileInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolInfoAndEscrowVO;
import com.lyht.business.abm.signed.vo.ProtocolInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolItemVO;
import com.lyht.business.abm.signed.vo.ProtocolVO;
import com.lyht.business.cost.dao.CostDao;
import com.lyht.business.cost.entity.CostEntity;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.business.pub.dao.PubFilesDao;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.enums.PubExceptionEnums;
import com.lyht.util.AmountToChineseUtil;
import com.lyht.util.CommonUtil;
import com.lyht.util.FileDownUtil;
import com.lyht.util.PinYinUtil;
import com.lyht.util.Randomizer;
import com.lyht.util.SystemUtil;
import com.lyht.util.WordReplaceKeyUtil;

/**
 * 协议信息表
 * 
 * @author wzw
 *
 */
@Service
public class ProtocolInfoService {

	private Logger log = LoggerFactory.getLogger(ProtocolInfoService.class);

	@Autowired
	private MessageNoticeService messageNoticeService ;
	
	@Autowired
	private PubFilesDao pubFilesDao;

	@Autowired
	private ProtocolInfoDao dao;

	@Autowired
	private CostDao costDao;

	@Autowired
	private TotalStateDao stateDao;

	@Autowired
	private AbmOwnerDao infoOwnerDao;

	@Autowired
	private ProtocolEscrowDao escrowDao;

	@Value("${lyht.file.upload.path}")
	private String filePath;

	@Autowired
	private PubDictValueDao dictDao;

	@Value("${iwind.process.flow.path.protocol}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath;
	@Autowired
	private ProcessOperateService processOperateService; // 注入流程服务实现类

	public void exportWord(HttpServletResponse response, Integer id) {
		Map<String, Object> map = dao.exportVersionTwo(id);
		if (map == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		String path = "D:\\Server\\uploads\\tuoba\\word\\protocol";// 模板地址
		String house = map.get("house") + "";// 房屋
		String decoration = map.get("decoration") + "";// 装修
		String building = map.get("building") + "";// 附属设施
		String facilities = map.get("facilities") + "";// 农副业设施补
		String individual = map.get("individual") + "";// 个体工商户
		String relocation = map.get("relocation") + "";// 搬迁补助费用
		String other = map.get("other") + "";// 其他
		String basics = map.get("basics") + "";// 基础补助
		String trees = map.get("trees") + "";// 零星树木
		String young = map.get("young") + "";// 成片青苗及果木
		// String land = map.get("land") + "";// 土地
		String homestead = map.get("homestead") + "";// 宅基地
		String productionAmount = map.get("productionAmount") + "";
		String placeType = map.get("placeType") + "";
		// 总金额
		Double total = 0.0;
		Map<String, String> m = new HashMap<String, String>();
		StringBuffer three = new StringBuffer("");
		StringBuffer buffer = new StringBuffer("");
		Integer i = 1;
		String filepathString = "";
		if (placeType == "分散货币" || placeType.equals("分散货币")) {
			filepathString = path + "\\protocol01.doc";
			if (!CommonUtil.isEmpty(map.get("place"))) {
				m.put("${PLACE}", map.get("place") + "");
			} else {
				m.put("${PLACE}", "  ");
			}
		} else if (placeType == "分散后靠" || placeType.equals("分散后靠")) {
			filepathString = path + "\\protocol02.doc";
			if (!CommonUtil.isEmpty(map.get("place"))) {
				m.put("${PLACE}", map.get("place") + "");
			} else {
				m.put("${PLACE}", "  ");
			}
		} else {
			filepathString = path + "\\protocol.doc";
			if (!CommonUtil.isEmpty(map.get("placeName"))) {
				m.put("${PLACENAME}", map.get("placeName") + "");
			} else {
				m.put("${PLACENAME}", "  ");
			}
		}
		String destpathString = path + "\\" + map.get("name") + ".doc";// 模板替换后生成新文档地址

		if (!CommonUtil.isEmpty(map.get("code"))) {
			m.put("${CODE}", map.get("code") + "");
		} else {
			m.put("${CODE}", "  ");
		}
		if (!CommonUtil.isEmpty(map.get("mergerName"))) {
			m.put("${MERGERNAME}", map.get("mergerName") + "");
		} else {
			m.put("${MERGERNAME}", "  ");
		}
		if (!CommonUtil.isEmpty(map.get("ownerName"))) {
			m.put("${OWNERNAME}", map.get("ownerName") + "");
		} else {
			m.put("${OWNERNAME}", "  ");
		}
		if (!CommonUtil.isEmpty(map.get("idCard"))) {
			m.put("${IDCARD}", map.get("idCard") + "");
		} else {
			m.put("${IDCARD}", "  ");
		}
		if (!CommonUtil.isEmpty(map.get("placeType"))) {
			m.put("${PLACETYPE}", map.get("placeType") + "");
		} else {
			m.put("${PLACETYPE}", "  ");
		}
		if (!CommonUtil.isEmpty(map.get("moveNum"))) {
			m.put("${MOVENUM}", map.get("moveNum") + "");
		} else {
			m.put("${MOVENUM}", "×");
		}
		if (!CommonUtil.isEmpty(map.get("productionNum"))) {
			m.put("${PRODUCTIONNUM}", map.get("productionNum") + "");
		} else {
			m.put("${PRODUCTIONNUM}", "×");
		}
		if (!CommonUtil.isEmpty(map.get("productionPlaceType"))) {
			m.put("${PRODUCTIONPLACETYPE}", map.get("productionPlaceType") + "");
		} else {
			m.put("${PRODUCTIONPLACETYPE}", "×");
		}
		if (!CommonUtil.isEmpty(map.get("productionMoney"))) {
			m.put("${PRODUCTIONPLACEMONEY}", map.get("productionMoney") + "");
		} else {
			m.put("${PRODUCTIONPLACEMONEY}", "×");
		}
		if (StringUtils.isNotBlank(house)) {
			total = total + Double.parseDouble(house);
			if (Double.parseDouble(house) > 0) {
				buffer.append("  " + i.toString() + "、房屋补偿费用" + (char) 11);
				buffer.append("  房屋补偿款共计 " + house + " 元，详见附表“房屋补偿费用计算表”。" + (char) 11);
				three.append("房屋补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(decoration)) {
			total = total + Double.parseDouble(decoration);
			if (Double.parseDouble(decoration) > 0) {
				buffer.append("  " + i.toString() + "、装修补偿费用" + (char) 11);
				buffer.append("  装修补偿款共计 " + decoration + " 元，详见附表“装修补偿费用计算表”。" + (char) 11);
				three.append("装修补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(building)) {
			total = total + Double.parseDouble(building);
			if (Double.parseDouble(building) > 0) {
				buffer.append("  " + i.toString() + "、附属设施补偿费用" + (char) 11);
				buffer.append("  附属设施补偿款共计 " + building + " 元，详见附表“附属设施补偿费用计算表”。" + (char) 11);
				three.append("附属设施补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(facilities)) {
			total = total + Double.parseDouble(facilities);
			if (Double.parseDouble(facilities) > 0) {
				buffer.append("  " + i.toString() + "、农副业设施补偿费用" + (char) 11);
				buffer.append("  农副业设施补偿款共计 " + facilities + " 元，详见附表“农副业设施补偿费用计算表”。" + (char) 11);
				three.append("农副业设施补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(individual)) {
			total = total + Double.parseDouble(individual);
			if (Double.parseDouble(individual) > 0) {
				buffer.append("  " + i.toString() + "、个体工商户停业搬迁补偿费用" + (char) 11);
				buffer.append("  个体工商户停业搬迁补偿款共计 " + individual + " 元，详见附表“个体工商户停业搬迁补偿费用计算表”。" + (char) 11);
				three.append("个体工商户停业搬迁补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(relocation)) {
			total = total + Double.parseDouble(relocation);
			if (Double.parseDouble(relocation) > 0) {
				buffer.append("  " + i.toString() + "、搬迁补助费用" + (char) 11);
				buffer.append("  搬迁补助费共计 " + relocation + " 元，详见附表“搬迁补助费用计算表”。" + (char) 11);
				three.append("搬迁补助费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(other)) {
			total = total + Double.parseDouble(other);
			if (Double.parseDouble(other) > 0) {
				buffer.append("  " + i.toString() + "、其他补助费用" + (char) 11);
				buffer.append("  其他补助费用共计 " + other + " 元，详见附表“其他补助费用计算表。" + (char) 11);
				three.append("其他补助费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(basics)) {
			total = total + Double.parseDouble(basics);
			if (Double.parseDouble(basics) > 0) {
				buffer.append("  " + i.toString() + "、搬迁安置基础设施补助费用" + (char) 11);
				buffer.append("  搬迁安置基础设施补助费用共计 " + basics + " 元，详见附表“搬迁安置基础设施补助费用计算表”。" + (char) 11);
				three.append("搬迁安置基础设施补助费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(homestead)) {
			total = total + Double.parseDouble(homestead);
			if (Double.parseDouble(homestead) > 0) {
				buffer.append("  " + i.toString() + "、宅基地补偿费用" + (char) 11);
				buffer.append("  宅基地补偿费用共计 " + homestead + " 元，详见附表“宅基地补偿费用计算表”。" + (char) 11);
				three.append("宅基地补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(trees)) {
			total = total + Double.parseDouble(trees);
			if (Double.parseDouble(trees) > 0) {
				buffer.append("  " + i.toString() + "、零星果木补偿费用" + (char) 11);
				buffer.append("  零星果木补偿费用共计 " + trees + " 元，详见附表“零星果木补偿费用计算表”。" + (char) 11);
				three.append("零星果木补偿费用、");
				i++;
			}
		}
		if (StringUtils.isNotBlank(young)) {
			total = total + Double.parseDouble(young);
			if (Double.parseDouble(young) > 0) {
				buffer.append("  " + i.toString() + "、成片青苗及果木补偿费用" + (char) 11);
				buffer.append("  成片青苗及果木补偿费用共计 " + young + " 元，详见附表“成片青苗及果木补偿费用计算表”。" + (char) 11);
				three.append("成片青苗及果木补偿费用、");
				i++;
			}
		}
//		if (StringUtils.isNotBlank(land)) {
//			total = total + Double.parseDouble(land);
//			if (Double.parseDouble(land) > 0) {
//				buffer.append("  " + i.toString() + "、土地补偿补助费用" + (char) 11);
//				buffer.append("  土地补偿补助费用共计 " +land   + " 元，详见附表“土地补偿补助费用计算表”。" + (char) 11);
//				i++;
//			}
//		}
		String str = three.toString();
		str = str.substring(0, str.length() - 1);//去掉最后多余的标点符号
		if (StringUtils.isNotBlank(productionAmount)) {
			total = total + Double.parseDouble(productionAmount);
			if (placeType == "分散后靠" || placeType.equals("分散后靠")) {
				m.put("${ONE}", String.format("%.2f", (total * 0.3)));
				m.put("${ONEUPPER}", AmountToChineseUtil.toChinese(String.format("%.2f", (total * 0.3))));
				m.put("${TWO}", String.format("%.2f", (total * 0.4)));
				m.put("${TWOUPPER}", AmountToChineseUtil.toChinese(String.format("%.2f", (total * 0.4))));
				m.put("${THREE}", String.format("%.2f", (total * 0.3)));
				m.put("${THREEUPPER}", AmountToChineseUtil.toChinese(String.format("%.2f", (total * 0.3))));
			}
		}
		m.put("${ITEM}", buffer.toString());// 小写
		if (total > 0) {
			m.put("${LOWER}", String.format("%.2f", total));// 小写
			m.put("${UPPER}", AmountToChineseUtil.toChinese(String.format("%.2f", total)));// 大写
		} else {
			m.put("${LOWER}", "0");
			m.put("${UPPER}", "零");// 大写
		}
		// String area = map.get("area") + "";
		m.put("${AREA}", "(" + map.get("area") + ")");// 签订项
		m.put("${CONTENT}", str);
		m.put("${CONTEXT}", str);
		WordReplaceKeyUtil.replaceAndGenerateWord(filepathString, destpathString, m);// 替换并生成新的文档

		// 转为PDF后 删除源文件 再下载
		//WordTOPDF.wordToPdf(map.get("name") + ".pdf", destpathString, path + "\\", "doc");
		FileDownUtil.getFile(path + "\\" + map.get("name") + ".doc", map.get("name") + ".doc", // 下载新的文档
				response);
		File file = new File(destpathString);// 将生成的文档从内存中删除
		if (file.exists()) {
			file.delete();
		}
//		File file1 = new File(path + "\\" + map.get("name") + ".pdf");// 将生成的文档从内存中删除
//		if (file1.exists()) {
//			file1.delete();
//		}

	}

	public LyhtResultBody<List<ProtocolVO>> getProtocolByIdCard(String idCard) {
		return new LyhtResultBody<>(dao.getProtocolByIdCard(idCard));
	}

	/**
	 * 根据查询权属人选择资金代管的补偿协议信息
	 * 
	 * @param ownerNm 权属人nm
	 * @return
	 */
	public ProtocolInfo findProtocolInfo(String ownerNm) {
		return dao.findProtocolInfo(ownerNm);
	}

	@Transactional // 根据流程id 修改 协议状态 流程申通通过后 协议未完成-->完成
	public void updateProtocolState(Integer type,String processId,String senderNm) {
		// 参数校验
		if (processId == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		String roleNm= "BCA5AA572F";
		ProtocolInfo info = dao.findByProcessId(processId);
		String jsonString = JSON.toJSONString(info);
		String toStaffNm = info.getOwnerNm();
		if(type == 0) {
			Integer flag = dao.updateStateFinishByProcessId(processId);
			
			if (info.getIsEscrow() == 1) {// 选择签订资金代管协议 同时改变附带项
				flag = escrowDao.updateStateFinishByProcessId(processId);
			}
			setStatusFinish(info);// 判断该权属人协议是否全部签完
			//角色nm
			
			messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm, "移民协议签订审批通过", jsonString, "SHOW");
			messageNoticeService.sendMessageNoticeByRole(senderNm, roleNm, "移民协议签订审批通过", jsonString, "SHOW");
		}else if(type ==1){
			messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm, "移民协议签订审批通过", jsonString, "SHOW");
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("name", "移民协议签订审批");
			data.put("info", info);
			messageNoticeService.sendMessageNoticeByRole(senderNm, roleNm, "移民协议签订审批拒绝", JSON.toJSONString(data), "JUMP");
		}
	}

	/**
	 * 发起流程
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public ProtocolInfo startChange(HttpServletRequest request, Integer id, String fileNm) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// step1：获得变更数据
		ProtocolInfo protocolInfo = null;
		Optional<ProtocolInfo> findById = dao.findById(id);
		if (findById.isPresent()) {
			ProcessOperateVO processOperateVO = new ProcessOperateVO();

			HashMap<String, String> taskData = new HashMap<>();
			processOperateVO.setFlowPath(physicalFlowPath);

			processOperateVO.setAttachment(fileNm);
			ProtocolInfo info = findById.get();
			AbmOwnerEntity ownerEntity = infoOwnerDao.findByNm(info.getOwnerNm());
			taskData.put("name", ownerEntity.getName() + "移民协议签订申请");// 自定义流程名称
			processOperateVO.setData(taskData);
			// 判断附件是否已上传
			int fileCount = pubFilesDao.countByTablePkColumn(info.getNm());
			if (fileCount > 0 ) {
				if (info.getIsEscrow() == 1) {// 判断是否需要签订资金代管协议
					ProtocolEscrow escrow = escrowDao.findByOwnerNm(info.getOwnerNm());
					if (escrow != null) {
						int escrowFileCount = pubFilesDao.countByTablePkColumn(escrow.getNm());
						if (escrowFileCount > 0) {
							if (escrow != null) {// 存在代管协议
								String processId = processOperateService.processStart(processOperateVO, request); // 返回流程实例Id
								info.setProcessId(processId);
								escrow.setProcessId(processId);
								escrowDao.save(escrow);
								protocolInfo = dao.save(info);
							}
						} else {
							throw new LyhtRuntimeException(LyhtExceptionEnums.ESCROW_FILE_COUNT_EXCEPTION);
						}
					} else {
						throw new LyhtRuntimeException(LyhtExceptionEnums.ESCROW_NOTFOUND);
					}
				} else {// 不签订资金代管
					String processId = processOperateService.processStart(processOperateVO, request); // 返回流程实例Id
					info.setProcessId(processId);
					protocolInfo = dao.save(info);
				}

			} else {
				throw new LyhtRuntimeException(info.getProtocolName() +"未上传附件");
			}
		}
		return protocolInfo;
	}

	/**
	 * 根据流程id查询展示信息
	 * 
	 * @param processId
	 * @return
	 */
	public LyhtResultBody<ProtocolInfoAndEscrowVO> getByTaskId(String processId) {
		// 参数校验
		if (processId == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		ProtocolInfo info = dao.findByProcessId(processId);
		ProtocolInfoAndEscrowVO protocolInfoAndEscrowVO = null;
		// 参数校验
		if (info == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		if (info.getIsEscrow() == 1) {
			protocolInfoAndEscrowVO = dao.getBytaskIdEscrow(processId);
		} else {
			protocolInfoAndEscrowVO = dao.getBytaskId(processId);
		}
		return new LyhtResultBody<>(protocolInfoAndEscrowVO);
	}

	/**
	 * 查询已签订完成的协议
	 */
	public List<ProtocolInfoVO> getFinishProtocol(String ownerNm) {
		List<ProtocolInfoVO> list = dao.getFinishProtocol(ownerNm);
		return list;
	}

	/**
	 * 查询已存在协议的权属人信息列表
	 */
	public List<OwnerInfoVO> getOwnerInfo() {
		List<OwnerInfoVO> list = dao.getOwnerInfo();
		return list;
	}

	/**
	 * 移民协议签订主页列表查询
	 * 
	 * @param region
	 * @param word
	 * @param pageable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<OwnerInfoVO>> page(Integer flag, String region, String word, String idCard,String nm,
			LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		// 将cityCode转为全名称 方便模糊查询
		Page<OwnerInfoVO> page = dao.page(flag, region, word, idCard,nm, pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<OwnerInfoVO> list = (List<OwnerInfoVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 移民协议签订项名称、金额及状态查询
	 */
	public LyhtResultBody<List<ProtocolItemVO>> getProtocolItem(String nm) {
		List<ProtocolItemVO> list = dao.getProtocolItem(nm);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 判断所有项目签订后，应当禁用确定按钮。
	 */
	public LyhtResultBody<Boolean> getIsDisable(String nm) {
		List<ProtocolItemVO> list = dao.getProtocolItem(nm);
		boolean bool = true;// 默认禁用
		for (ProtocolItemVO protocolItemVO : list) {
			if (protocolItemVO.getFlag() == 1) {// 如果有一项 可以签订就 不禁用
				bool = false;
			}
		}
		return new LyhtResultBody<>(bool);
	}

	/**
	 * 协议签订页面 列表 nm:
	 */
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<ProtocolInfoVO>> getInfoByPage(String nm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<ProtocolInfoVO> page = dao.getInfoByPage(nm, pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<ProtocolInfoVO> list = (List<ProtocolInfoVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 根据 nm 查询权属人查询补偿信息
	 */
	public LyhtResultBody<OwnerInfoVO> getOwnerInfoByNm(String nm) {
		OwnerInfoVO info = dao.getOwnerInfoByNm(nm);
		return new LyhtResultBody<>(info);
	}

	/**
	 * 添加 修改
	 *
	 * @param t_funds_info
	 * @return
	 */
	@Transactional
	public LyhtResultBody<ProtocolInfo> save(ProtocolInfo protocolInfo) {
		// 参数校验
		if (protocolInfo == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		AbmOwnerEntity owner = infoOwnerDao.findByNm(protocolInfo.getOwnerNm());
		Calendar now = Calendar.getInstance();
		// 内码赋值
		String nm = protocolInfo.getNm();
		if (StringUtils.isBlank(nm)) {
			protocolInfo.setNm(Randomizer.generCode(10));
			protocolInfo.setState(0);
			protocolInfo.setProtocolCode("XY-" + PinYinUtil.getPinYin(owner.getName()) + now.get(Calendar.YEAR) + ""
					+ (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH) + "");
			// 流水号赋值
			String code = dao.getProtocolCode();// 查询当日最大编码
			// 判断是否存在最大值
			if (StringUtils.isNotBlank(code)) {// 如果存在 截取最后两位流水号
				code = dao.getProtocolCode().substring(dao.getProtocolCode().length() - 2,
						dao.getProtocolCode().length());// 截取
				if (Integer.parseInt(code) < 99) {// 判断是否已到最大值
					// 赋值 +1
					code = protocolInfo.getProtocolCode() + String.format("%02d", Integer.parseInt(code) + 1);
				} else {
					throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
				}
			} else {// 不存在则直接赋值01
				code = protocolInfo.getProtocolCode() + "01";
			}
			protocolInfo.setProtocolCode(code);// 将最终值给入
		}
		if (protocolInfo.getProtocolArea().indexOf("房屋") == -1) {
			protocolInfo.setIsEscrow(0);
		}
		ProtocolInfo result = dao.save(protocolInfo);
		Integer count = stateDao.getCountByOwnerNm(protocolInfo.getOwnerNm());
		if (count == 0) {// 如改权属人没有总状态信息 则为其添加一条
			TotalState totalState = new TotalState();
			totalState.setOwnerNm(protocolInfo.getOwnerNm());
			totalState.setProtocolState(0);
			totalState.setNm(Randomizer.generCode(10));
			totalState.setBuildingStatus(0);
			totalState.setAgriculturalFacilitiesStatus(0);
			totalState.setTreesStatus(0);
			totalState.setHouseStatus(0);
			totalState.setOtherStatus(0);
			totalState.setHouseDecorationStatus(0);
			totalState.setRelocationAllowanceStatus(0);
			totalState.setIndividualStatus(0);
			totalState.setDifficultStatus(0);
			totalState.setYoungCropsStatus(0);
			totalState.setInfrastructureStatus(0);
			totalState.setHomesteadStatus(0);
			totalState.setLevyLandStatus(0);
			// totalState.setProtocoledAmount(0.00);
			stateDao.save(totalState);
		}
		setItemState(protocolInfo);// 修改单项状态信息
		// stateDao.updateTotal(protocolInfo.getOwnerNm());// 修改协议总金额
		return new LyhtResultBody<>(result);
	}

	/**
	 * 根据id进行查询
	 */
	public ProtocolInfo findById(Integer id) {
		return dao.getOne(id);
	}

	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@Transactional
	public LyhtResultBody<Integer> delete(Integer id, String nm) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		ProtocolInfo info = dao.findByNm(nm);
		if (info.getIsEscrow() == 1) {
			escrowDao.deleteByOwnerNm(info.getOwnerNm());
		}
		// step1：获取当前补偿费用总记录
		setStatusFail(nm);
		dao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param files
	 * @param pubFileEntity
	 */
	@Transactional
	public LyhtResultBody<List<PubFilesEntity>> uploads(HttpServletRequest request, MultipartFile[] files,
			PubFilesEntity pubFileEntity, Integer flag) {
		try {
			List<PubFilesEntity> pubList = new ArrayList<>();
			for (MultipartFile multipartFile : files) {
				String originalFilename = multipartFile.getOriginalFilename();// 文件全名
				long size = multipartFile.getSize();// 文件大小
				String nm = Randomizer.generCode(10);// 内码
				String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + "_" + nm;// 文件名
				String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);// 文件类型
				String dateString = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");// 当前年月字符串
				StringBuffer fileUrl = new StringBuffer();// 文件路径
				fileUrl.append("/upload/");
				fileUrl.append(fileType);
				fileUrl.append("/");
				fileUrl.append(dateString);

				// 创建文件夹以及写入文件
				File fileDir = new File(filePath + fileUrl.toString());
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				// 保存文件
				fileUrl.append("/");
				fileUrl.append(fileName);
				fileUrl.append(".");
				fileUrl.append(fileType);
				File file = new File(filePath + fileUrl.toString());
				multipartFile.transferTo(file);

				// 附件详情
				PubFilesEntity fileEntity = new PubFilesEntity();
				fileEntity.setFileName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
				fileEntity.setFileSize(String.valueOf(size));
				fileEntity.setFileType(fileType);
				fileEntity.setFileUrl(fileUrl.toString());
				fileEntity.setNm(nm);
				fileEntity.setTableName(pubFileEntity.getTableName());
				fileEntity.setTablePkColumn(pubFileEntity.getTablePkColumn());
				fileEntity.setUploadStaffName(SystemUtil.getLoginStaffName(request));
				fileEntity.setUploadTime(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
				fileEntity.setSubject(pubFileEntity.getSubject());
				pubList.add(fileEntity);
			}
			List<PubFilesEntity> save = pubFilesDao.saveAll(pubList);
			// setStatusFinish(pubFileEntity.getTablePkColumn(), flag); 原判断完成方式为 附件是否上传
			return new LyhtResultBody<List<PubFilesEntity>>(save);
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=uploads=====Params:" + pubFileEntity + "=====", e);
			throw new LyhtRuntimeException(PubExceptionEnums.FILE_UPLOAD_FAILURE);
		}
	}

	/**
	 * 判断权属人协议是否签完(总状态修改为完成)
	 */
	public void setStatusFinish(ProtocolInfo info) {
		// step1：获取当前补偿费用总记录
		TotalState costEntity = stateDao.findByOwnerNm(info.getOwnerNm());
		if (costEntity.getHouseStatus() != 0 && costEntity.getHouseDecorationStatus() != 0
				&& costEntity.getBuildingStatus() != 0 && costEntity.getAgriculturalFacilitiesStatus() != 0
				&& costEntity.getTreesStatus() != 0 && costEntity.getIndividualStatus() != 0
				&& costEntity.getRelocationAllowanceStatus() != 0 && costEntity.getOtherStatus() != 0
				&& costEntity.getDifficultStatus() != 0 && costEntity.getInfrastructureStatus() != 0
				&& costEntity.getHomesteadStatus() != 0 && costEntity.getLevyLandStatus() != 0
				&& costEntity.getYoungCropsStatus() != 0) {
			Integer pCount = dao.getProtoColCount(info.getOwnerNm());// 获取个人未完成补偿协议数量
			Integer fCount = dao.getProtocolEscrowCount(info.getOwnerNm());// 获取个人未完成资金代管协议数量
			Integer escrowState = escrowDao.getISEscrowCount(info.getOwnerNm());// 查询是否勾选资金代管协议
			Integer escrowCount = escrowDao.getEscrowCount(info.getOwnerNm());// 查询资金代管协议是否签订
			if (escrowState > 0) {// 勾选资金代管协议
				if (escrowCount > 0) {// 已签订资金代管协议
					if (pCount == 0 && fCount == 0) {// 未完成协议数量大于零
						stateDao.updateProtocolStateToFinish(info.getOwnerNm());// 修改权属人协议签订总状态码
					}
				}
			} else {// 未勾选是否签订资金代管协议
				if (pCount == 0 && fCount == 0) {// 未完成协议数量大于零
					stateDao.updateProtocolStateToFinish(info.getOwnerNm());// 修改权属人协议签订总状态码
				}
			}
		}
	}

	/**
	 * 签订协议时改变单项状态
	 */
	public void setItemState(ProtocolInfo protocolInfo) {
		TotalState totalState = stateDao.findByOwnerNm(protocolInfo.getOwnerNm());
		if (protocolInfo.getProtocolArea().indexOf("附属建筑物") != -1) {// 改变单项补偿状态 签订范围中包含附属建筑物
			totalState.setBuildingStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("农副业设施") != -1) {// 改变单项补偿状态 签订范围中包含农副业设施
			totalState.setAgriculturalFacilitiesStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("零星树木") != -1) {// 改变单项补偿状态 签订范围中包含零星树木
			totalState.setTreesStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("房屋") != -1) {// 改变单项补偿状态 签订范围中包含房屋
			totalState.setHouseStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("其他补助") != -1) {// 改变单项补偿状态 签订范围中包含其他补助
			totalState.setOtherStatus(1);
			totalState.setDifficultStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("装修") != -1) {// 改变单项补偿状态 签订范围中包含房屋装修
			totalState.setHouseDecorationStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("搬迁补助") != -1) {// 改变单项补偿状态 签订范围中包含搬迁补助
			totalState.setRelocationAllowanceStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("个体工商户") != -1) {// 改变单项补偿状态 签订范围中包含个体工商户
			totalState.setIndividualStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("困难户补助") != -1) {// 改变单项补偿状态 签订范围中包含困难户补助
			totalState.setDifficultStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("成片青苗及果木") != -1) {// 改变单项补偿状态 签订范围中包含成片青苗及果木
			totalState.setYoungCropsStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("搬迁安置基础设施补助") != -1) {// 改变单项补偿状态 签订范围中包含搬迁安置基础设施补助
			totalState.setInfrastructureStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("宅基地") != -1) {// 改变单项补偿状态 签订范围中包含宅基地
			totalState.setHomesteadStatus(1);
			totalState.setMoveStatus(1);
		}
		if (protocolInfo.getProtocolArea().indexOf("征收土地") != -1) {// 改变单项补偿状态 签订范围中包含征收土地
			totalState.setLevyLandStatus(1);
			totalState.setProductionStatus(1);
			totalState.setMoveStatus(1);
		}
		// 将无补偿的单项实物指标 同步到协议总状态表中
		CostEntity cost = costDao.findByOwnerNm(protocolInfo.getOwnerNm());
		Integer buildingStatus = cost.getBuildingStatus();
		if (buildingStatus != null) {
			if (buildingStatus == -1) {// 附属建筑
				totalState.setBuildingStatus(2);
			}
		}
		Integer agriculturalFacilitiesStatus = cost.getAgriculturalFacilitiesStatus();
		if (agriculturalFacilitiesStatus != null) {
			if (agriculturalFacilitiesStatus == -1) {// 农副业
				totalState.setAgriculturalFacilitiesStatus(2);
			}
		}
		Integer treesStatus = cost.getTreesStatus();
		if (treesStatus != null) {
			if (treesStatus == -1) {// 零星树木
				totalState.setTreesStatus(2);
			}
		}
		Integer houseStatus = cost.getHouseStatus();
		if (houseStatus != null) {
			if (houseStatus == -1) {// 房屋
				totalState.setHouseStatus(2);
			}
		}
		Integer otherStatus = cost.getOtherStatus();
		if (otherStatus != null) {
			if (otherStatus == -1) {// 其他补助
				totalState.setOtherStatus(2);
			}
		}
		Integer houseDecorationStatus = cost.getHouseDecorationStatus();
		if (houseDecorationStatus != null) {
			if (houseDecorationStatus == -1) {// 装修
				totalState.setHouseDecorationStatus(2);
			}
		}
		Integer relocationAllowanceStatus = cost.getRelocationAllowanceStatus();
		if (relocationAllowanceStatus != null) {
			if (relocationAllowanceStatus == -1) {// 搬迁
				totalState.setRelocationAllowanceStatus(2);
			}
		}
		Integer individualStatus = cost.getIndividualStatus();
		if (individualStatus != null) {
			if (individualStatus == -1) {// 个体工商户
				totalState.setIndividualStatus(2);
			}
		}
		Integer difficultStatus = cost.getDifficultStatus();
		if (difficultStatus != null) {
			if (difficultStatus == -1) {// 困难户补助
				totalState.setDifficultStatus(2);
			}
		}
		Integer youngCropsStatus = cost.getYoungCropsStatus();
		if (youngCropsStatus != null) {
			if (youngCropsStatus == -1) {// 成片青苗及果木
				totalState.setYoungCropsStatus(2);
			}
		}
		Integer infrastructureStatus = cost.getInfrastructureStatus();
		if (infrastructureStatus != null) {
			if (infrastructureStatus == -1) {// 搬迁安置基础设施补助
				totalState.setInfrastructureStatus(2);
			}
		}
		Integer homesteadStatus = cost.getHomesteadStatus();
		if (homesteadStatus != null) {
			if (homesteadStatus == -1) {// 宅基地
				totalState.setHomesteadStatus(2);
			}
		}
		Integer levyLandStatus = cost.getLevyLandStatus();
		if (levyLandStatus != null) {
			if (levyLandStatus == -1) {// 土地
				totalState.setLevyLandStatus(2);
			}
		}
		Integer producePopulationStatus = cost.getProducePopulationStatus();
		if (producePopulationStatus != null) {
			if (producePopulationStatus == -1) {
				totalState.setProductionStatus(2);
			}
		}
		// if(r==0) 等于 if(r==1) 大于if(r==-1) //小于
		if (CommonUtil.isEmpty(cost.getMoveAmount())) {// 为空
			totalState.setMoveStatus(2);
		} else {// 搬迁安置补偿为0
			int r = cost.getMoveAmount().compareTo(BigDecimal.ZERO);
			if (r == 0) {
				totalState.setMoveStatus(2);
			}
		}
		stateDao.save(totalState);
	}

	/**
	 * 单个删除 文件
	 * 
	 * @param id
	 */
	@Transactional
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id) {
		try {
			Optional<PubFilesEntity> findById = pubFilesDao.findById(id);
			PubFilesEntity pubFileEntity = findById.get();
			pubFilesDao.deleteById(id);
			File file = new File(filePath + pubFileEntity.getFileUrl());
			if (file.exists()) {
				file.delete();
			}
			// setStatusFailOfFile(pubFileEntity.getTablePkColumn()); 原判定失败方式 为附件修改 或协议删除
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<Integer>(id);
	}

	/**
	 * 当有附件删除时 修改 单项签订状态及总状态修改为原状态
	 */
	public void setStatusFailOfFile(String nm) {
		String ownerNm = "";
		ProtocolInfo protocolInfo = dao.findByNm(nm);
		ownerNm = protocolInfo.getOwnerNm();
		if (!StringUtils.isNotBlank(ownerNm)) {
			ProtocolEscrow escrow = escrowDao.findByNm(nm);
			ownerNm = escrow.getOwnerNm();
			escrowDao.updateStateFail(nm);
		} else {
			dao.updateStateFail(nm);
		}
		stateDao.updateMoveStatus(ownerNm);
		stateDao.updateProtocolStateToFail(ownerNm);
	}

	/**
	 * 当有协议删除时 修改 单项签订状态及总状态修改为原状态 先判断协议类型
	 */
	public void setStatusFail(String nm) {
		ProtocolInfo protocolInfo = dao.findByNm(nm);
		TotalState totalState = stateDao.findByOwnerNm(protocolInfo.getOwnerNm());
		// step1：获取当前补偿费用总记录
		if (protocolInfo.getProtocolArea().indexOf("附属建筑物") != -1) {// 改变单项补偿 态----------
			totalState.setBuildingStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("农副业设施") != -1) {
			totalState.setAgriculturalFacilitiesStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("零星树木") != -1) {
			totalState.setTreesStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("房屋") != -1) {
			totalState.setHouseStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("其他补助") != -1) {
			totalState.setOtherStatus(0);
			totalState.setDifficultStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("装修") != -1) {
			totalState.setHouseDecorationStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("搬迁补助") != -1) {
			totalState.setRelocationAllowanceStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("个体工商户") != -1) {
			totalState.setIndividualStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("困难户补助") != -1) {
			totalState.setDifficultStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("成片青苗及果木") != -1) {
			totalState.setYoungCropsStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("搬迁安置基础设施补助") != -1) {
			totalState.setInfrastructureStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("宅基地") != -1) {
			totalState.setHomesteadStatus(0);
		}
		if (protocolInfo.getProtocolArea().indexOf("征收土地") != -1) {
			totalState.setLevyLandStatus(0);
		}
		totalState.setProtocolState(0);
		stateDao.save(totalState);// 修改权属人协议签订总状态码
	}

	public Map<String, Object> preview(Integer id) {// 预览
		Map<String, Object> map = dao.export(id);
		return map;
	}

	public void export(Integer id, HttpServletResponse response) {
		Map<String, Object> exportWordVO = dao.export(id);// 查询导出信息
		String path = "D:\\Server\\uploads\\tuoba\\word\\protocol";// 模板地址
		// 总金额
		Double column23 = 0.0;
		String filepathString = "";
		if (exportWordVO.get("") == "") {
			filepathString = path + "\\protocol.doc";
		} else if (exportWordVO.get("") == "") {
			filepathString = path + "\\protocol01.doc";
		} else if (exportWordVO.get("") == "") {
			filepathString = path + "\\protocol02.doc";
		} else {
			filepathString = path + "\\protocol.doc";
		}
		String destpathString = path + "\\" + exportWordVO.get("name") + ".doc";// 模板替换后生成新文档地址
		Map<String, String> map = new HashMap<String, String>();
		// 根据文档中关键字做替换 判断集合中是否存在值 存在则替换关键字不存在则用0 或控制代替
		if (!CommonUtil.isEmpty(exportWordVO.get("column00"))) {
			map.put("${COLUMN00}", exportWordVO.get("column00") + "");
		} else {
			map.put("${COLUMN00}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column01"))) {
			map.put("${COLUMN01}", exportWordVO.get("column01") + "");
		} else {
			map.put("${COLUMN01}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column02"))) {
			map.put("${COLUMN02}", exportWordVO.get("column02") + "");
		} else {
			map.put("${COLUMN02}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column03"))) {
			map.put("${COLUMN03}", exportWordVO.get("column03") + "");
		} else {
			map.put("${COLUMN03}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column04"))) {
			map.put("${COLUMN04}", exportWordVO.get("column04") + "");
		} else {
			map.put("${COLUMN04}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column05"))) {
			map.put("${COLUMN05}", exportWordVO.get("column05") + "");
		} else {
			map.put("${COLUMN05}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column06"))) {
			map.put("${COLUMN06}", exportWordVO.get("column06") + "");
		} else {
			map.put("${COLUMN06}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column07"))) {
			map.put("${COLUMN07}", exportWordVO.get("column07") + "");
		} else {
			map.put("${COLUMN07}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column08"))) {
			map.put("${COLUMN08}", exportWordVO.get("column08") + "");
		} else {
			map.put("${COLUMN08}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column09"))) {
			map.put("${COLUMN09}", exportWordVO.get("column09") + "");
		} else {
			map.put("${COLUMN09}", "  ");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column10"))) {
			map.put("${COLUMN10}", exportWordVO.get("column10") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column10") + "");
		} else {
			map.put("${COLUMN10}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column11"))) {
			map.put("${COLUMN11}", exportWordVO.get("column11") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column11") + "");
		} else {
			map.put("${COLUMN11}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column12"))) {
			map.put("${COLUMN12}", exportWordVO.get("column12") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column12") + "");
		} else {
			map.put("${COLUMN12}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column13"))) {
			map.put("${COLUMN13}", exportWordVO.get("column13") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column13") + "");
		} else {
			map.put("${COLUMN13}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column14"))) {
			map.put("${COLUMN14}", exportWordVO.get("column14") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column14") + "");
		} else {
			map.put("${COLUMN14}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column15"))) {
			map.put("${COLUMN15}", exportWordVO.get("column15") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column15") + "");
		} else {
			map.put("${COLUMN15}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column16"))) {
			map.put("${COLUMN16}", exportWordVO.get("column16") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column16") + "");
		} else {
			map.put("${COLUMN16}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column17"))) {
			map.put("${COLUMN17}", exportWordVO.get("column17") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column17") + "");
		} else {
			map.put("${COLUMN17}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column18"))) {
			map.put("${COLUMN18}", exportWordVO.get("column18") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column18") + "");
		} else {
			map.put("${COLUMN18}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column19"))) {
			map.put("${COLUMN19}", exportWordVO.get("column19") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column19") + "");
		} else {
			map.put("${COLUMN19}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column20"))) {
			map.put("${COLUMN20}", exportWordVO.get("column20") + "");
			column23 = column23 + Double.parseDouble(exportWordVO.get("column20") + "");
		} else {
			map.put("${COLUMN20}", "0");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column21"))) {
			map.put("${COLUMN21}", exportWordVO.get("column21") + "");
		} else {
			map.put("${COLUMN21}", "  ");
		}
		map.put("${COLUMN22}", AmountToChineseUtil.toChinese(String.format("%.2f", column23) + ""));
		map.put("${COLUMN23}", String.format("%.2f", column23) + "");
		map.put("${COLUMN25}", exportWordVO.get("column25") + "");
		if (!CommonUtil.isEmpty(exportWordVO.get("column24"))) {
			map.put("${COLUMN24}", exportWordVO.get("column24") + "");
		} else {
			map.put("${COLUMN24}", "  ");
		}
		if (exportWordVO.get("column03") == "分散后靠") {

		}
		WordReplaceKeyUtil.replaceAndGenerateWord(filepathString, destpathString, map);// 替换并生成新的文档
		FileDownUtil.getFile(path + "\\" + exportWordVO.get("name") + ".doc", exportWordVO.get("name") + ".doc", // 下载新的文档
				response);
		File file = new File(path + "\\" + exportWordVO.get("name") + ".doc");// 将生成的文档从内存中删除
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 根据协议编码查询协议 author:Yanxh
	 * 
	 * @param protocolCode
	 * @return
	 */
	public ProtocolInfo findByProtocolCode(String protocolCode) {
		return dao.findByProtocolCode(protocolCode);
	}

	/**
	 * 查询协议模板信息
	 */
	public LyhtResultBody<List<ProtocolFileInfoVO>> getProtocolFileInfo() {
		// D:\\Server\\uploads\\tuoba\\word\\protocol 补偿协议
		List<File> files = getFiles("D:\\Server\\uploads\\tuoba\\word\\protocol_template");
		List<ProtocolFileInfoVO> list = new ArrayList<ProtocolFileInfoVO>();
		DecimalFormat df = new DecimalFormat("######0.00");
		if (files.size() > 0) {
			for (File file : files) {
				ProtocolFileInfoVO info = new ProtocolFileInfoVO();
				info.setFileName(file.getName());
				Double size = Double.parseDouble(file.length() + "");
				size = size / 1024;
				info.setFileSize(df.format(size) + " KB");
				info.setFileType(file.getName().substring(file.getName().lastIndexOf(".")));
				info.setFileUrl(file.getPath());
				list.add(info);
			}
		}
//		// D:\\Server\\uploads\\tuoba\\word\\escrow 资金代管协议
//		List<File> fileList = getFiles("D:\\Server\\uploads\\tuoba\\word\\escrow");
//		if (fileList.size() > 0) {
//			for (File file : fileList) {
//				ProtocolFileInfoVO info = new ProtocolFileInfoVO();
//				info.setFileName(file.getName());
//				Double size = Double.parseDouble(file.length() + "");
//				size = size / 1024;
//				info.setFileSize(df.format(size) + " KB");
//				info.setFileType(file.getName().substring(file.getName().lastIndexOf(".")));
//				info.setFileUrl(file.getPath());
//				list.add(info);
//			}
//		}
		return new LyhtResultBody<>(list);
	}

	/**
	 * 递归文件
	 * 
	 * @param path
	 * @return
	 */
	public List<File> getFiles(String path) {
		File root = new File(path);
		List<File> files = new ArrayList<File>();
		if (!root.isDirectory()) {
			files.add(root);
		} else {
			File[] subFiles = root.listFiles();
			for (File f : subFiles) {
				files.addAll(getFiles(f.getAbsolutePath()));
			}
		}
		return files;
	}

	/**
	 * 复核项目 查询权属人已生成但未签订的协议
	 */
	public Boolean deleteByProject(String reviewProject, String ownerNm) {
		String[] projects = reviewProject.split(",");// 复核项目nm数组
		List<PubDictValue> projectList = new ArrayList<PubDictValue>();
		// 根据nm查到所有名字
		for (int i = 0; i < projects.length; i++) {
			PubDictValue pubDictValue = dictDao.findByNm(projects[i]);
			if (pubDictValue.getName().equals("房屋装修")) {
				pubDictValue.setName("装修");
			}
			projectList.add(pubDictValue);
		}
		List<ProtocolInfo> list = dao.getByOwnerNmIsNotSuccess(ownerNm);// 查询权属人的所有未完成的协议
		for (ProtocolInfo protocolInfo : list) {// 外层循环协议
			for (PubDictValue project : projectList) {// 里层循环复核项目
				// 如果协议签订项目中包含复核项目 就删掉这个协议！
				if (protocolInfo.getProtocolArea().indexOf(project.getName()) != -1) {
					dao.deleteById(protocolInfo.getId());
					if (protocolInfo.getIsEscrow() == 1) {// 如果附带了 资金代管协议也一并删除
						escrowDao.deleteByOwnerNm(protocolInfo.getOwnerNm());
					}
				}
			}
		}
		// 拿到所有未完成的协议与传入项目比对
		return false;
	}

}
