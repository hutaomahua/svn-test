package com.lyht.business.cost.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.lyht.business.abm.production.service.ProductionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.cost.common.constant.CostConstant;
import com.lyht.business.cost.common.enums.LyhtCostExceptionEnums;
import com.lyht.business.cost.dao.CostDao;
import com.lyht.business.cost.entity.CostAgriculturalFacilitiesEntity;
import com.lyht.business.cost.entity.CostBuildingEntity;
import com.lyht.business.cost.entity.CostEntity;
import com.lyht.business.cost.entity.CostHomesteadEntity;
import com.lyht.business.cost.entity.CostHousesDecorationEntity;
import com.lyht.business.cost.entity.CostHousesEntity;
import com.lyht.business.cost.entity.CostIndividualEntity;
import com.lyht.business.cost.entity.CostInfrastructureEntity;
import com.lyht.business.cost.entity.CostOtherEntity;
import com.lyht.business.cost.entity.CostProducePopulationEntity;
import com.lyht.business.cost.entity.CostRelocationAllowanceEntity;
import com.lyht.business.cost.entity.CostTreesEntity;
import com.lyht.business.cost.entity.CostYoungCropsEntity;
import com.lyht.business.cost.vo.CostAggregateCardVO;
import com.lyht.business.cost.vo.CostAggregateChatVO;
import com.lyht.business.cost.vo.CostAggregateTreeVO;
import com.lyht.business.cost.vo.CostAggregateVO;
import com.lyht.business.cost.vo.CostDetailVO;
import com.lyht.business.cost.vo.CostFamilyVO;
import com.lyht.business.cost.vo.CostOwnerVO;
import com.lyht.business.cost.vo.CostSignVO;
import com.lyht.business.cost.vo.CostVO;
import com.lyht.util.AmountToChineseUtil;
import com.lyht.util.CommonUtil;
import com.lyht.util.DateUtil;
import com.lyht.util.ExcelSheet;
import com.lyht.util.ExcelUtils;
import com.lyht.util.QRCodeUtil;
import com.lyht.util.Randomizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostService {
	@Value("${lyht.cost.handbook.file.path}")
	private String costHandbookFilePath;

	@Value("${lyht.cost.handbook.qrcode.path}")
	private String qrcodeFilePath;
	@Autowired
	private AbmOwnerDao ownerDao;
	@Autowired
	private CostDao costDao;
	@Autowired
	private CostUpdateService costUpdateService;
	@Autowired
	private CostHousesService costHousesService;
	@Autowired
	private CostHousesDecorationService costHousesDecorationService;
	@Autowired
	private CostBuildingService costBuildingService;
	@Autowired
	private CostAgriculturalFacilitiesService costAgriculturalFacilitiesService;
	@Autowired
	private CostTreesService costTreesService;
	@Autowired
	private CostIndividualService costIndividualService;
	@Autowired
	private CostRelocationAllowanceService costRelocationAllowanceService;
	@Autowired
	private CostOtherService costOtherService;
	@Autowired
	private CostInfrastructureService costInfrastructureService;
	@Autowired
	private CostHomesteadService costHomesteadService;
//	@Autowired
//	private CostLevyLandService costLevyLandService;
	@Autowired
	private CostYoungCropsService costYoungCropsService;
	@Autowired
	private CostProducePopulationService costProducePopulationService;
//	@Autowired
//	private CostProduceLandService costProduceLandService;

	@Autowired private ProductionService productionService;

	/**
	 * 新增，修改
	 * 
	 * @param costEntity
	 * @return
	 */
	public CostEntity save(CostEntity costEntity) {
		CostEntity save = null;
		try {
			save = costDao.save(costEntity);
		} catch (Exception e) {
			log.error("=====CostService=====Method：save=====param：" + costEntity, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return save;
	}

	/**
	 * 删除（by id）
	 * 
	 * @param id
	 * @return
	 */
	public String delete(String id) {
		try {
			costDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostService=====Method：delete=====param：" + id, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return id;
	}

	/**
	 * 删除（by ids）
	 * 
	 * @param ids
	 * @return
	 */
	public String deleteAll(String ids) {
		try {
			List<String> idList = Arrays.asList(StringUtils.split(ids, ","));
			List<CostEntity> findAllById = costDao.findAllById(idList);
			costDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostService=====Method：deleteAll=====param：" + ids, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return ids;
	}

	/**
	 * 按户主nm查询详情（不包含户主信息）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public CostEntity findByOwnerNm(String ownerNm) {
		CostEntity costEntity = null;
		try {
			costEntity = costDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return costEntity;
	}

	/**
	 * 按户主nm查询详情（包含户主信息）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public CostDetailVO findCostByOwnerNm(String ownerNm) {
		CostDetailVO costDetailVO = null;
		try {
			costDetailVO = costDao.findCostByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostService=====Method：findCostByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return costDetailVO;
	}

	/**
	 * 分页查询
	 * 
	 * @param ownerNm
	 * @param scopeNm
	 * @param region
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<CostDetailVO>> page(String ownerName, String scopeNm, String region, Integer status,
			String idCard, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<CostDetailVO> page = costDao.page(ownerName, scopeNm, region, status, idCard, pageable);
		// 结果集
		List<CostDetailVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 获取补偿总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amount = costDao.costMoveAmountByOwnerNm(ownerNm);
		return amount;
	}

	/**
	 * 查询户主的补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	public CostVO findCostDetailByOwnerNm(String ownerNm) {
		CostVO costVO = null;
		try {
			CostDetailVO costDetailVO = findCostByOwnerNm(ownerNm);

			// 查询各分项补偿费用
			List<CostHousesEntity> costHousesList = costHousesService.findByOwnerNm(ownerNm);// 房屋
			List<CostHousesDecorationEntity> costHousesDecorationList = costHousesDecorationService
					.findByOwnerNm(ownerNm);// 房屋装修
			List<CostBuildingEntity> costBuildingList = costBuildingService.findByOwnerNm(ownerNm);// 附属建筑物
			List<CostAgriculturalFacilitiesEntity> costAgriculturalFacilitiesList = costAgriculturalFacilitiesService
					.findByOwnerNm(ownerNm);// 农副业设施
			List<CostTreesEntity> costTreesList = costTreesService.findByOwnerNm(ownerNm);// 零星树木
			List<CostIndividualEntity> costIndividualList = costIndividualService.findByOwnerNm(ownerNm);// 个体工商户
			List<CostRelocationAllowanceEntity> costRelocationAllowanceList = costRelocationAllowanceService
					.findByOwnerNm(ownerNm);// 搬迁补助
			List<CostOtherEntity> costOtherList = costOtherService.findOtherCostByOwnerNm(ownerNm);// 其他补助
			List<CostOtherEntity> costDifficultList = costOtherService.findDifficultCostByOwnerNm(ownerNm);// 困难户补助
			List<CostInfrastructureEntity> costInfrastructureList = costInfrastructureService.findByOwnerNm(ownerNm);// 搬迁安置基础设施补助
			List<CostHomesteadEntity> costHomesteadList = costHomesteadService.findByOwnerNm(ownerNm);// 宅基地补偿
//			List<CostLevyLandEntity> costLevyLandList = costLevyLandService.findByOwnerNm(ownerNm);// 征收土地补偿
			List<CostYoungCropsEntity> costYoungCropsList = costYoungCropsService.findByOwnerNm(ownerNm);// 成片青苗及果木补偿
			List<CostProducePopulationEntity> costProducePopulationList = costProducePopulationService
					.findByOwnerNm(ownerNm);// 生产安置人口补偿
			System.err.println("costProducePopulationList:" + costProducePopulationList);
//			List<CostProduceLandEntity> costProduceLandList = costProduceLandService.findByOwnerNm(ownerNm);// 生产安置土地补偿

			costVO = new CostVO();
			costVO.setCostDetailVO(costDetailVO);
			costVO.setCostHousesList(costHousesList);
			costVO.setCostHousesDecorationList(costHousesDecorationList);
			costVO.setCostBuildingList(costBuildingList);
			costVO.setCostAgriculturalFacilitiesList(costAgriculturalFacilitiesList);
			costVO.setCostTreesList(costTreesList);
			costVO.setCostIndividualList(costIndividualList);
			costVO.setCostRelocationAllowanceList(costRelocationAllowanceList);
			costVO.setCostOtherList(costOtherList);
			costVO.setCostDifficultList(costDifficultList);
			costVO.setCostInfrastructureList(costInfrastructureList);
			costVO.setCostHomesteadList(costHomesteadList);
//			costVO.setCostLevyLandList(costLevyLandList);
			costVO.setCostYoungCropsList(costYoungCropsList);
			costVO.setCostProducePopulationList(costProducePopulationList);
//			costVO.setCostProduceLandList(costProduceLandList);

		} catch (Exception e) {
			log.error("=====CostService=====Method：findCostDetailByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtCostExceptionEnums.COST_FAILURE);
		}
		return costVO;
	}

	/**
	 * 校验是否能够计算
	 * 
	 * @param ownerNm
	 */
	public void costCheck(String ownerNm) {
		int costCheck = costDao.costCheck(ownerNm);
		if (costCheck <= 0) {
			throw new LyhtRuntimeException("无法计算费用！（该户未进行搬迁安置界定！）");
		}
	}

	/**
	 * 计算整户的补偿费（按户主NM计算整户的补偿费用）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public CostVO costByOwnerNm(String ownerNm) {
		CostVO costVO = null;
		try {

			CostEntity costEntity = findByOwnerNm(ownerNm);
			// step1：校验该户是否已计算过，如果存在，则删除重新计算
			if (costEntity != null) {
				costDao.deleteByOwnerNm(ownerNm);
			}

			// step2：计算各分项补偿费用，并保存
			List<CostHousesEntity> costHousesList = costHousesService.costByOwnerNm(ownerNm);// 房屋
			List<CostHousesDecorationEntity> costHousesDecorationList = costHousesDecorationService
					.costByOwnerNm(ownerNm);// 房屋装修
			List<CostBuildingEntity> costBuildingList = costBuildingService.costByOwnerNm(ownerNm);// 附属建筑物
			List<CostAgriculturalFacilitiesEntity> costAgriculturalFacilitiesList = costAgriculturalFacilitiesService
					.costByOwnerNm(ownerNm);// 农副业设施
			List<CostTreesEntity> costTreesList = costTreesService.costByOwnerNm(ownerNm);// 零星树木
			List<CostIndividualEntity> costIndividualList = costIndividualService.costByOwnerNm(ownerNm);// 个体工商户
			List<CostRelocationAllowanceEntity> costRelocationAllowanceList = costRelocationAllowanceService
					.costByOwnerNm(ownerNm);// 搬迁补助
			List<CostOtherEntity> costOtherList = costOtherService.costOtherByOwnerNm(ownerNm);// 其他补助
			List<CostOtherEntity> costDifficultList = costOtherService.costDifficultByOwnerNm(ownerNm);// 困难户补助
			List<CostInfrastructureEntity> costInfrastructureList = costInfrastructureService.costByOwnerNm(ownerNm);// 搬迁安置基础设施补助
			List<CostHomesteadEntity> costHomesteadList = costHomesteadService.costByOwnerNm(ownerNm);// 宅基地补偿
//			List<CostLevyLandEntity> costLevyLandList = costLevyLandService.costByOwnerNm(ownerNm);// 征收土地补偿
			List<CostYoungCropsEntity> costYoungCropsList = costYoungCropsService.costByOwnerNm(ownerNm);// 成片青苗及果木补偿
			List<CostProducePopulationEntity> costProducePopulationList = costProducePopulationService
					.costByOwnerNm(ownerNm);// 生产安置人口补偿
			System.err.println("costProducePopulationList22222:" + costProducePopulationList);
//			List<CostProduceLandEntity> costProduceLandList = costProduceLandService.costByOwnerNm(ownerNm);// 生产安置土地补偿

			// step3：保存补偿费用计算总记录，并更新补偿金额与状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
			CostDetailVO costDetailVO = findCostByOwnerNm(ownerNm);

			// step4：拼装当前户的补偿费用明细，并返回
			costVO = new CostVO();
			costVO.setCostDetailVO(costDetailVO);
			costVO.setCostHousesList(costHousesList);
			costVO.setCostHousesDecorationList(costHousesDecorationList);
			costVO.setCostBuildingList(costBuildingList);
			costVO.setCostAgriculturalFacilitiesList(costAgriculturalFacilitiesList);
			costVO.setCostTreesList(costTreesList);
			costVO.setCostIndividualList(costIndividualList);
			costVO.setCostRelocationAllowanceList(costRelocationAllowanceList);
			costVO.setCostOtherList(costOtherList);
			costVO.setCostDifficultList(costDifficultList);
			costVO.setCostInfrastructureList(costInfrastructureList);
			costVO.setCostHomesteadList(costHomesteadList);
//			costVO.setCostLevyLandList(costLevyLandList);
			costVO.setCostYoungCropsList(costYoungCropsList);
			costVO.setCostProducePopulationList(costProducePopulationList);
//			costVO.setCostProduceLandList(costProduceLandList);

		} catch (Exception e) {
			log.error("=====CostService=====Method：costByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtCostExceptionEnums.COST_FAILURE);
		}
		return costVO;
	}

	/**
	 * 导出整户的补偿费用手册（移民户登记手册）
	 * 
	 * @param ownerNm
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void export(String ownerNm, HttpServletResponse response) {
		try {
			List<ExcelSheet> excelSheetList = new ArrayList<>();
			String fileName = "云南省澜沧江托巴水电站移民户登记册-";// 导出的手册名称
			int totalPages = 1;// 总页数(默认有汇总页)
			int costCount = 0;// 已补偿项目总数
			List<List<Object>> aggregateData = new ArrayList<>();// 汇总表格

			// step1：查询对应户主编码的相关补偿信息
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null) {
				return;
			}
			
			String signNumber = Randomizer.generCode(10);
			String jsonString = JSON.toJSONString(costOwnerVO);
			CostSignVO costSignVO = JSON.parseObject(jsonString, CostSignVO.class);
			String ownerName = costSignVO.getOwnerName();
			fileName += ownerName + "-";// 拼接手册名称----户主姓名
			fileName += signNumber;// 拼接手册名称----协议编号

			// step2：协议相关属性赋值
			String signDate = DateUtil.getDateTime();
			costSignVO.setSignDate(signDate);// 协议签订日期
			String signChineseDate = DateUtil.getChineseDate();
			costSignVO.setSignChineseDate(signChineseDate);// 协议签订中文日期
			String newRegionName = "";
			String county = costSignVO.getCounty();// 县
			if (StringUtils.isNotBlank(county)) {
				newRegionName += county;
			}
			String towns = costSignVO.getTowns();// 乡镇
			if (StringUtils.isNotBlank(towns)) {
				newRegionName += towns + "镇（乡）";
			}
			String village = costSignVO.getVillage();// 村
			if (StringUtils.isNotBlank(village)) {
				newRegionName += village + "村";
			}
			costSignVO.setNewRegionName(newRegionName);// 搬迁新住址
			costSignVO.setSignNumber(signNumber);// 协议编号
			//设置生产安置方式默认为“逐年补偿”
			costSignVO.setProduceType("逐年补偿");

			// step3：sheet内容处理（项目名从表3开始）
			/*-----------封面-----------*/
			Map<String, Object> coverContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
			ExcelSheet coverExcelsheet = new ExcelSheet();
			coverExcelsheet.setSheetName("封面");
			coverExcelsheet.setReplaceContent(coverContent);
			/*-----------目录-----------*/
			ExcelSheet catalogueExcelsheet = new ExcelSheet();
			catalogueExcelsheet.setSheetName("目录");
			catalogueExcelsheet.setStartRowIndex(3);
			catalogueExcelsheet.setStartColIndex(1);
			List<List<Object>> cataloguelist = new ArrayList<>();
			/*-----------移民户信息-----------*/
			Map<String, Object> ownerContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
			ExcelSheet ownerExcelsheet = new ExcelSheet();
			ownerExcelsheet.setSheetName("移民户基本信息");
			ownerExcelsheet.setReplaceContent(ownerContent);
			excelSheetList.add(ownerExcelsheet);
			// 统计页数
			totalPages++;
			/*-----------2家庭成员-----------*/
			Map<String, Object> familyContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
			ExcelSheet familyExcelsheet = new ExcelSheet();
			familyExcelsheet.setSheetName("移民户家庭成员情况表");
			familyExcelsheet.setBorder(true);
			familyExcelsheet.setReplaceContent(familyContent);
			familyExcelsheet.setStartRowIndex(4);
			List<List<Object>> familyDataList = getFamilyDataList(ownerNm);
			familyExcelsheet.setListData(familyDataList);
			excelSheetList.add(familyExcelsheet);
			// 统计页数
			totalPages++;
			/*-----------分项-----------*/
			CostEntity costEntity = findByOwnerNm(ownerNm);
			if(null == costEntity){
				throw new LyhtRuntimeException("补偿费用总表数据为空");
			}
			/*-----------分项----房屋补偿-------*/
			Integer houseStatus = costEntity.getHouseStatus();
			ExcelSheet houseExcelsheet = new ExcelSheet();
			houseExcelsheet.setSheetName("房屋");
			if (houseStatus != null && houseStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costHousesService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costHousesService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					houseExcelsheet.setListData(dataList);
					houseExcelsheet.setReplaceContent(replaceContent);
					houseExcelsheet.setBorder(true);
					houseExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("房屋补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					houseExcelsheet.setRemove(true);
				}
			} else {
				houseExcelsheet.setRemove(true);
			}
			excelSheetList.add(houseExcelsheet);
			/*-----------分项----房屋装修补偿-------*/
			Integer houseDecorationStatus = costEntity.getHouseDecorationStatus();
			ExcelSheet houseDecorationExcelsheet = new ExcelSheet();
			houseDecorationExcelsheet.setSheetName("装修");
			if (houseDecorationStatus != null && houseDecorationStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costHousesDecorationService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costHousesDecorationService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					houseDecorationExcelsheet.setListData(dataList);
					houseDecorationExcelsheet.setReplaceContent(replaceContent);
					houseDecorationExcelsheet.setBorder(true);
					houseDecorationExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("装修补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					houseDecorationExcelsheet.setRemove(true);
				}
			} else {
				houseDecorationExcelsheet.setRemove(true);
			}
			excelSheetList.add(houseDecorationExcelsheet);
			/*-----------分项----附属建筑物补偿-------*/
			Integer buildingStatus = costEntity.getBuildingStatus();
			ExcelSheet buildingExcelsheet = new ExcelSheet();
			buildingExcelsheet.setSheetName("附属设施");
			if (buildingStatus != null && buildingStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costBuildingService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costBuildingService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					buildingExcelsheet.setListData(dataList);
					buildingExcelsheet.setReplaceContent(replaceContent);
					buildingExcelsheet.setBorder(true);
					buildingExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("附属设施补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					buildingExcelsheet.setRemove(true);
				}
			} else {
				buildingExcelsheet.setRemove(true);
			}
			excelSheetList.add(buildingExcelsheet);
			/*-----------分项----农副业设施补偿-------*/
			Integer agriculturalFacilitiesStatus = costEntity.getAgriculturalFacilitiesStatus();
			ExcelSheet agriculturalFacilitiesExcelsheet = new ExcelSheet();
			agriculturalFacilitiesExcelsheet.setSheetName("农副业设施");
			if (agriculturalFacilitiesStatus != null
					&& agriculturalFacilitiesStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costAgriculturalFacilitiesService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costAgriculturalFacilitiesService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					agriculturalFacilitiesExcelsheet.setListData(dataList);
					agriculturalFacilitiesExcelsheet.setReplaceContent(replaceContent);
					agriculturalFacilitiesExcelsheet.setBorder(true);
					agriculturalFacilitiesExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("农副业设施补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					agriculturalFacilitiesExcelsheet.setRemove(true);
				}
			} else {
				agriculturalFacilitiesExcelsheet.setRemove(true);
			}
			excelSheetList.add(agriculturalFacilitiesExcelsheet);
			/*-----------分项----个体工商户补偿-------*/
			Integer individualStatus = costEntity.getIndividualStatus();
			ExcelSheet individualExcelsheet = new ExcelSheet();
			individualExcelsheet.setSheetName("个体工商户停业搬迁补偿");
			if (individualStatus != null && individualStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costIndividualService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costIndividualService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					individualExcelsheet.setListData(dataList);
					individualExcelsheet.setReplaceContent(replaceContent);
					individualExcelsheet.setBorder(true);
					individualExcelsheet.setStartRowIndex(5);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("个体工商户停业搬迁补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					individualExcelsheet.setRemove(true);
				}
			} else {
				individualExcelsheet.setRemove(true);
			}
			excelSheetList.add(individualExcelsheet);
			/*-----------分项----搬迁补助(近迁)-------*/
			Integer relocationAllowanceRecentStatus = costEntity.getRelocationAllowanceStatus();
			ExcelSheet relocationAllowanceRecentExcelsheet = new ExcelSheet();
			relocationAllowanceRecentExcelsheet.setSheetName("搬迁补助(近迁)");
			if (relocationAllowanceRecentStatus != null
					&& relocationAllowanceRecentStatus.equals(CostConstant.COST_RELOCATION_STATE_RECENT)) {
				List<List<Object>> dataList = costRelocationAllowanceService.getRecentDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costRelocationAllowanceService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					relocationAllowanceRecentExcelsheet.setListData(dataList);
					relocationAllowanceRecentExcelsheet.setReplaceContent(replaceContent);
					relocationAllowanceRecentExcelsheet.setBorder(true);
					relocationAllowanceRecentExcelsheet.setStartRowIndex(5);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("搬迁补助(近迁)");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					relocationAllowanceRecentExcelsheet.setRemove(true);
				}
			} else {
				relocationAllowanceRecentExcelsheet.setRemove(true);
			}
			excelSheetList.add(relocationAllowanceRecentExcelsheet);
			/*-----------分项----搬迁补助(远迁)-------*/
			Integer relocationAllowanceRemoteStatus = costEntity.getRelocationAllowanceStatus();
			ExcelSheet relocationAllowanceRemoteExcelsheet = new ExcelSheet();
			relocationAllowanceRemoteExcelsheet.setSheetName("搬迁补助(远迁)");
			if (relocationAllowanceRemoteStatus != null
					&& relocationAllowanceRemoteStatus.equals(CostConstant.COST_RELOCATION_STATE_REMOTE)) {
				List<List<Object>> dataList = costRelocationAllowanceService.getRemoteDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costRelocationAllowanceService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					relocationAllowanceRemoteExcelsheet.setListData(dataList);
					relocationAllowanceRemoteExcelsheet.setReplaceContent(replaceContent);
					relocationAllowanceRemoteExcelsheet.setBorder(true);
					relocationAllowanceRemoteExcelsheet.setStartRowIndex(5);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("搬迁补助(远迁)");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					relocationAllowanceRemoteExcelsheet.setRemove(true);
				}
			} else {
				relocationAllowanceRemoteExcelsheet.setRemove(true);
			}
			excelSheetList.add(relocationAllowanceRemoteExcelsheet);
			/*-----------分项----其他补助(农村)-------*/
			Integer otherRuraStatus = costEntity.getOtherStatus();
			ExcelSheet otherRuraExcelsheet = new ExcelSheet();
			otherRuraExcelsheet.setSheetName("其他补助(农村)");
			if (otherRuraStatus != null && otherRuraStatus.equals(CostConstant.COST_OTHER_STATE_RURA)) {
				List<List<Object>> dataList = costOtherService.getRuraDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costOtherService.amountOtherCostByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					otherRuraExcelsheet.setListData(dataList);
					otherRuraExcelsheet.setReplaceContent(replaceContent);
					otherRuraExcelsheet.setBorder(true);
					otherRuraExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("其他补助(农村)");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					otherRuraExcelsheet.setRemove(true);
				}
			} else {
				otherRuraExcelsheet.setRemove(true);
			}
			excelSheetList.add(otherRuraExcelsheet);
			/*-----------分项----其他补助(集镇)-------*/
			Integer otherUrbanStatus = costEntity.getOtherStatus();
			ExcelSheet otherUrbanExcelsheet = new ExcelSheet();
			otherUrbanExcelsheet.setSheetName("其他补助(集镇)");
			if (otherUrbanStatus != null && otherUrbanStatus.equals(CostConstant.COST_OTHER_STATE_URBAN)) {
				List<List<Object>> dataList = costOtherService.getUrbanDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costOtherService.amountOtherCostByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					otherUrbanExcelsheet.setListData(dataList);
					otherUrbanExcelsheet.setReplaceContent(replaceContent);
					otherUrbanExcelsheet.setBorder(true);
					otherUrbanExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("其他补助(集镇)");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					otherUrbanExcelsheet.setRemove(true);
				}
			} else {
				otherUrbanExcelsheet.setRemove(true);
			}
			excelSheetList.add(otherUrbanExcelsheet);
			/*-----------分项----困难补助-------*/
			Integer difficultStatus = costEntity.getDifficultStatus();
			ExcelSheet difficultExcelsheet = new ExcelSheet();
			difficultExcelsheet.setSheetName("其他补助-困难户补助");
			if (difficultStatus != null && difficultStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costOtherService.getDifficultDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costOtherService.amountDifficultCostByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					difficultExcelsheet.setListData(dataList);
					difficultExcelsheet.setReplaceContent(replaceContent);
					difficultExcelsheet.setBorder(true);
					difficultExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("其他补助-困难户补助");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					difficultExcelsheet.setRemove(true);
				}
			} else {
				difficultExcelsheet.setRemove(true);
			}
			excelSheetList.add(difficultExcelsheet);
			/*-----------分项----基础设施补助-------*/
			Integer infrastructureStatus = costEntity.getInfrastructureStatus();
			ExcelSheet infrastructureExcelsheet = new ExcelSheet();
			infrastructureExcelsheet.setSheetName("搬迁安置基础设施(分散)");
			if (infrastructureStatus != null && infrastructureStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costInfrastructureService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costInfrastructureService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					infrastructureExcelsheet.setListData(dataList);
					infrastructureExcelsheet.setReplaceContent(replaceContent);
					infrastructureExcelsheet.setBorder(true);
					infrastructureExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("搬迁安置基础设施补助(分散)");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					infrastructureExcelsheet.setRemove(true);
				}
			} else {
				infrastructureExcelsheet.setRemove(true);
			}
			excelSheetList.add(infrastructureExcelsheet);
			/*-----------分项----宅基地补偿(分散)-------*/
			Integer homesteadDispersedStatus = costEntity.getHomesteadStatus();
			ExcelSheet homesteadDisperseExcelsheet = new ExcelSheet();
			homesteadDisperseExcelsheet.setSheetName("搬迁安置基础设施(分散)-宅基地");
			if (homesteadDispersedStatus != null
					&& homesteadDispersedStatus.equals(CostConstant.COST_HOMESTEAD_STATE_DISPERSED)) {
				List<List<Object>> dataList = costHomesteadService.getDispersedDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costHomesteadService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					homesteadDisperseExcelsheet.setListData(dataList);
					homesteadDisperseExcelsheet.setReplaceContent(replaceContent);
					homesteadDisperseExcelsheet.setBorder(true);
					homesteadDisperseExcelsheet.setStartRowIndex(5);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("搬迁安置基础设施补助(分散)-宅基地");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					homesteadDisperseExcelsheet.setRemove(true);
				}
			} else {
				homesteadDisperseExcelsheet.setRemove(true);
			}
			excelSheetList.add(homesteadDisperseExcelsheet);
			/*-----------分项----宅基地补偿(集中)-------*/
			Integer homesteadFocusStatus = costEntity.getHomesteadStatus();
			ExcelSheet homesteadFocusExcelsheet = new ExcelSheet();
			homesteadFocusExcelsheet.setSheetName("搬迁安置基础设施(集中)-宅基地");
			if (homesteadFocusStatus != null && homesteadFocusStatus.equals(CostConstant.COST_HOMESTEAD_STATE_FOCUS)) {
				List<List<Object>> dataList = costHomesteadService.getFocusDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costHomesteadService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					homesteadFocusExcelsheet.setListData(dataList);
					homesteadFocusExcelsheet.setReplaceContent(replaceContent);
					homesteadFocusExcelsheet.setBorder(true);
					homesteadFocusExcelsheet.setStartRowIndex(5);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("搬迁安置基础设施补助(集中)-宅基地");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					homesteadFocusExcelsheet.setRemove(true);
				}
			} else {
				homesteadFocusExcelsheet.setRemove(true);
			}
			excelSheetList.add(homesteadFocusExcelsheet);
			/*-----------分项----零星树木补偿-------*/
			Integer treesStatus = costEntity.getTreesStatus();
			ExcelSheet treesExcelsheet = new ExcelSheet();
			treesExcelsheet.setSheetName("零星果木");
			if (treesStatus != null && treesStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costTreesService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costTreesService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					treesExcelsheet.setListData(dataList);
					treesExcelsheet.setReplaceContent(replaceContent);
					treesExcelsheet.setBorder(true);
					treesExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("零星果木补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					treesExcelsheet.setRemove(true);
				}
			} else {
				treesExcelsheet.setRemove(true);
			}
			excelSheetList.add(treesExcelsheet);
			/*-----------分项----成片青苗及果木补偿-------*/
			Integer youngCropsStatus = costEntity.getYoungCropsStatus();
			ExcelSheet youngCropsExcelsheet = new ExcelSheet();
			youngCropsExcelsheet.setSheetName("成片青苗及果木");
			if (youngCropsStatus != null && youngCropsStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				List<List<Object>> dataList = costYoungCropsService.getDataList(ownerNm);
				if (dataList != null && !dataList.isEmpty()) {
					// 替换内容
					String amount = costYoungCropsService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
					Map<String, Object> replaceContent = (Map<String, Object>) JSON
							.parse(JSON.toJSONString(costSignVO));
					replaceContent.put("totalAmount", new BigDecimal(amount));
					if (amount != null) {
						String totalAmountCN = AmountToChineseUtil.toChinese(amount);
						replaceContent.put("totalAmountCN", totalAmountCN);
					}
					// sheet属性
					youngCropsExcelsheet.setListData(dataList);
					youngCropsExcelsheet.setReplaceContent(replaceContent);
					youngCropsExcelsheet.setBorder(true);
					youngCropsExcelsheet.setStartRowIndex(4);
					// 统计页数
					totalPages++;
					// 统计已计算项目
					costCount++;
					// 汇总数据
					List<Object> aggregateRowData = new ArrayList<>();
					aggregateRowData.add(costCount);
					aggregateRowData.add("成片青苗及林果木补偿");
					aggregateRowData.add(new BigDecimal(amount));
					aggregateData.add(aggregateRowData);
				} else {
					youngCropsExcelsheet.setRemove(true);
				}
			} else {
				youngCropsExcelsheet.setRemove(true);
			}
			excelSheetList.add(youngCropsExcelsheet);
			/*-----------分项----征收土地补偿-------暂不导出该项*/
//			Integer levyLandStatus = costEntity.getLevyLandStatus();
			ExcelSheet levyLandExcelsheet = new ExcelSheet();
			levyLandExcelsheet.setSheetName("征收土地");
//			if (levyLandStatus != null && (levyLandStatus == CostConstant.COST_SUB_STATE_FEE || levyLandStatus == CostConstant.COST_SUB_STATE_SIGNED)) {
//				List<List<Object>> dataList = costLevyLandService.getDataList(ownerNm);
//				if (dataList != null && !dataList.isEmpty()) {
//					//替换内容
//					BigDecimal amount = costLevyLandService.amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
//					Map<String, Object> replaceContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
//					replaceContent.put("totalAmount", amount);
//					if (amount != null) {
//						String string = amount.toString();
//						String totalAmountCN = AmountToChineseUtil.toChinese(string);
//						replaceContent.put("totalAmountCN", totalAmountCN);
//					}
//					//sheet属性
//					levyLandExcelsheet.setListData(dataList);
//					levyLandExcelsheet.setReplaceContent(replaceContent);
//					levyLandExcelsheet.setBorder(true);
//					levyLandExcelsheet.setStartRowIndex(4);
//					//统计页数
//					totalPages ++;
//					//统计已计算项目
//					costCount ++;
//					//汇总数据
//					List<Object> aggregateRowData = new ArrayList<>();
//					aggregateRowData.add(costCount);
//					aggregateRowData.add("征收土地补偿");
//					aggregateRowData.add(new BigDecimal(amount));
//					aggregateData.add(aggregateRowData);
//				} else {
//					levyLandExcelsheet.setRemove(true);
//				}
//			} else {
			levyLandExcelsheet.setRemove(true);
//			}
			excelSheetList.add(levyLandExcelsheet);
			/*-----------分项----征用土地及恢复期补助-------*/
			ExcelSheet requisitionExcelsheet = new ExcelSheet();
			requisitionExcelsheet.setSheetName("征用土地及恢复期补助");
			requisitionExcelsheet.setRemove(true);
			excelSheetList.add(requisitionExcelsheet);

			Integer producePopulationStatus = costEntity.getProducePopulationStatus();
			ExcelSheet producePopulationExcelsheet2 = new ExcelSheet();
			producePopulationExcelsheet2.setSheetName("生产安置");
			/*if (producePopulationStatus != null && producePopulationStatus.equals(CostConstant.COST_SUB_STATE_FEE)) {
				// 替换内容
				List<CostProducePopulationEntity> findByOwnerNm = costProducePopulationService.findByOwnerNm(ownerNm);
				CostProducePopulationEntity costProducePopulationEntity = findByOwnerNm.get(0);
				BigDecimal price = costProducePopulationEntity.getPrice().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
				BigDecimal num = costProducePopulationEntity.getNum().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
				BigDecimal amount = costProducePopulationEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
				Map<String, Object> replaceContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
				replaceContent.put("totalAmount", new BigDecimal(amount.toPlainString()));
				replaceContent.put("price", new BigDecimal(price.toPlainString()));
				replaceContent.put("num", num);
				replaceContent.put("amount", new BigDecimal(amount.toPlainString()));
				if (amount != null) {
					String string = amount.toPlainString();
					String totalAmountCN = AmountToChineseUtil.toChinese(string);
					replaceContent.put("totalAmountCN", totalAmountCN);
				}
				// sheet属性
				producePopulationExcelsheet2.setReplaceContent(replaceContent);
				// 统计页数
				totalPages++;
				// 统计已计算项目
				costCount++;
				// 汇总数据
				List<Object> aggregateRowData = new ArrayList<>();
				aggregateRowData.add(costCount);
				aggregateRowData.add("生产安置");
				aggregateRowData.add(amount.toPlainString());
				aggregateData.add(aggregateRowData);
			} else {
				producePopulationExcelsheet2.setRemove(true);
			}*/
			producePopulationExcelsheet2.setRemove(true);
			excelSheetList.add(producePopulationExcelsheet2);
			/*-----------分项----生产安置人口补偿（兰永）-------*/
			ExcelSheet producePopulationExcelsheet = new ExcelSheet();
			producePopulationExcelsheet.setSheetName("逐年补偿");
			BigDecimal producePopulationNewNum = productionService.getYearByYearPopulation(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
			if (producePopulationNewNum != null && producePopulationNewNum.compareTo(BigDecimal.ZERO) == 1) {
				// 替换内容
				List<CostProducePopulationEntity> findByOwnerNm = costProducePopulationService.findByOwnerNm(ownerNm);
				CostProducePopulationEntity costProducePopulationEntity = findByOwnerNm.get(0);
				BigDecimal price = costProducePopulationEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
				Map<String, Object> replaceContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
				BigDecimal amount = producePopulationNewNum.multiply(price).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
				replaceContent.put("totalAmount", new BigDecimal(amount.toPlainString()));
				replaceContent.put("price", new BigDecimal(price.toPlainString()));
				replaceContent.put("num", new BigDecimal(producePopulationNewNum.toPlainString()));
				replaceContent.put("amount", new BigDecimal(amount.toPlainString()));
				if (amount != null) {
					String string = amount.toPlainString();
					String totalAmountCN = AmountToChineseUtil.toChinese(string);
					replaceContent.put("totalAmountCN", totalAmountCN);
				}
				// sheet属性
				producePopulationExcelsheet.setReplaceContent(replaceContent);
				// 统计页数
				totalPages++;
				// 统计已计算项目
				costCount++;
				// 汇总数据
				/*List<Object> aggregateRowData = new ArrayList<>();
				aggregateRowData.add(costCount);
				aggregateRowData.add("逐年补偿");
				aggregateRowData.add(amount.toPlainString());
				aggregateData.add(aggregateRowData);*/
			} else {
				producePopulationExcelsheet.setRemove(true);
			}
			excelSheetList.add(producePopulationExcelsheet);
			/*-----------分项----生产安置(非兰永)-------*/
//			Integer produceLandStatus = costEntity.getProduceLandStatus();
			ExcelSheet produceLandExcelsheet = new ExcelSheet();
			produceLandExcelsheet.setSheetName("生产安置(非兰永)");
//			if (produceLandStatus != null && produceLandStatus == CostConstant.COST_SUB_STATE_FEE) {
//				// 替换内容
//				List<CostProduceLandEntity> findByOwnerNm = costProduceLandService.findByOwnerNm(ownerNm);
//				Map<String, Object> replaceContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
//				for (CostProduceLandEntity costProduceLandEntity : findByOwnerNm) {
//					String projectName = costProduceLandEntity.getProjectName();
//					BigDecimal price = costProduceLandEntity.getPrice();
//					BigDecimal num = costProduceLandEntity.getNum();
//					BigDecimal amount = costProduceLandEntity.getAmount();
//					if (StringUtils.equals(projectName, "水田")) {
//						replaceContent.put("shuitianPrice", price);
//						replaceContent.put("shuitianNum", num);
//						replaceContent.put("shuitianAmount", amount);
//					} else if (StringUtils.equals(projectName, "旱地")) {
//						replaceContent.put("handiPrice", price);
//						replaceContent.put("handiNum", num);
//						replaceContent.put("handiAmount", amount);
//					} else if (StringUtils.equals(projectName, "陡坡地")) {
//						replaceContent.put("doupoPrice", price);
//						replaceContent.put("doupoNum", num);
//						replaceContent.put("doupoAmount", amount);
//					} else if (StringUtils.equals(projectName, "板栗成园地")) {
//						replaceContent.put("banlichenPrice", price);
//						replaceContent.put("banlichenNum", num);
//						replaceContent.put("banlichenAmount", amount);
//					} else if (StringUtils.equals(projectName, "板栗幼园地")) {
//						replaceContent.put("banliyouPrice", price);
//						replaceContent.put("banliyouNum", num);
//						replaceContent.put("banliyouAmount", amount);
//					} else if (StringUtils.equals(projectName, "核桃成园地")) {
//						replaceContent.put("hetaochenPrice", price);
//						replaceContent.put("hetaochenNum", num);
//						replaceContent.put("hetaochenAmount", amount);
//					} else if (StringUtils.equals(projectName, "核桃幼园地")) {
//						replaceContent.put("hetaoyouPrice", price);
//						replaceContent.put("hetaoyouNum", num);
//						replaceContent.put("hetaoyouAmount", amount);
//					} else if (StringUtils.equals(projectName, "葡萄成园地")) {
//						replaceContent.put("putaochenPrice", price);
//						replaceContent.put("putaochenNum", num);
//						replaceContent.put("putaochenAmount", amount);
//					} else if (StringUtils.equals(projectName, "葡萄幼园地")) {
//						replaceContent.put("putaoyouPrice", price);
//						replaceContent.put("putaoyouNum", num);
//						replaceContent.put("putaoyouAmount", amount);
//					} else if (StringUtils.contains(projectName, "园地")) {
//						replaceContent.put("qitaPrice", price);
//						replaceContent.put("qitaNum", num);
//						replaceContent.put("qitaAmount", amount);
//					}
//				}
//				BigDecimal amount = costYoungCropsService.amountByOwnerNm(ownerNm);
//				replaceContent.put("totalAmount", amount);
//				if (amount != null) {
//					String string = amount.toString();
//					String totalAmountCN = AmountToChineseUtil.toChinese(string);
//					replaceContent.put("totalAmountCN", totalAmountCN);
//				}
//				// sheet属性
//				produceLandExcelsheet.setReplaceContent(replaceContent);
//				// 统计页数
//				totalPages++;
//				// 统计已计算项目
//				costCount++;
//				// 汇总数据
//				List<Object> aggregateRowData = new ArrayList<>();
//				aggregateRowData.add(costCount);
//				aggregateRowData.add("生产安置补助费");
//				aggregateRowData.add(new BigDecimal(amount));
//				aggregateData.add(aggregateRowData);
//			} else {
				produceLandExcelsheet.setRemove(true);
//			}
			excelSheetList.add(produceLandExcelsheet);

			// 给表格排序
			int serialNumber = 1;
			for (int i = 0; i < excelSheetList.size(); i++) {
				ExcelSheet excelSheet = excelSheetList.get(i);
				String sheetName = excelSheet.getSheetName();
				boolean remove = excelSheet.isRemove();
				if (!remove) {
					// 页码
					Map<String, Object> replace = excelSheet.getReplaceContent();
					replace.put("current", serialNumber);// 页码
					replace.put("totalPages", totalPages);// 总页数
					// 目录以及sheet名
					excelSheet.setSheetNewName(serialNumber + sheetName);
					List<Object> catalogueRowlist = new ArrayList<>();
					catalogueRowlist.add("表" + serialNumber);
					catalogueRowlist.add(sheetName);
					cataloguelist.add(catalogueRowlist);
					serialNumber++;
				}
			}

			/*-----------汇总-------*/
			// 替换内容
			String amount = amountByOwnerNm(ownerNm).setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
			Map<String, Object> aggregateContent = (Map<String, Object>) JSON.parse(JSON.toJSONString(costSignVO));
			aggregateContent.put("current", totalPages);
			aggregateContent.put("totalPages", totalPages);
			aggregateContent.put("totalAmount", new BigDecimal(amount));
			if (amount != null) {
				String totalAmountCN = AmountToChineseUtil.toChinese(amount);
				aggregateContent.put("totalAmountCN", totalAmountCN);
			}
			// sheet属性
			ExcelSheet aggregateExcelsheet = new ExcelSheet();
			aggregateExcelsheet.setSheetName("汇总表");
			aggregateExcelsheet.setBorder(true);
			aggregateExcelsheet.setReplaceContent(aggregateContent);
			aggregateExcelsheet.setStartRowIndex(4);

			aggregateExcelsheet.setListData(aggregateData);
			// 单元格合并属性
			List<CellRangeAddress> mergerList = new ArrayList<>();
			for (int i = 0; i < aggregateData.size(); i++) {
				List<Object> list = aggregateData.get(i);
				int row = 4 + i;// 从第四行开始，列表的每一行
				int startCol = list.size() - 1;// 从数据的最后一列开始
				int endCol = startCol + 2;// 合并三列
				CellRangeAddress cellRangeAddress = new CellRangeAddress(row, row, startCol, endCol);
				mergerList.add(cellRangeAddress);
			}
			aggregateExcelsheet.setMergerList(mergerList);
			excelSheetList.add(aggregateExcelsheet);
			// 汇总表创建目录
			List<Object> catalogueRowlist = new ArrayList<>();
			catalogueRowlist.add("表" + serialNumber);
			catalogueRowlist.add("移民户补偿补助资金汇总表");
			cataloguelist.add(catalogueRowlist);
			// 添加“封面”、“目录”sheet
			catalogueExcelsheet.setListData(cataloguelist);
			excelSheetList.add(catalogueExcelsheet);
			excelSheetList.add(coverExcelsheet);
			//生成二维码  查出需要放入二维码的信息
			AbmOwnerEntity findByNm = ownerDao.findByNm(ownerNm);
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("id", findByNm.getId());
			map.put("nm", findByNm.getNm());
			map.put("idCard", findByNm.getIdCard());
			QRCodeUtil.generateQRCode(map, qrcodeFilePath);//生成二维码
			ExcelUtils.exportAndReplaceExcel(costHandbookFilePath, fileName, response, excelSheetList,qrcodeFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException("错误原因:" + e.getMessage());
		}
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getFamilyDataList(String ownerNm) {
		List<CostFamilyVO> findCostFamily = costDao.findCostFamily(ownerNm);
		if (findCostFamily != null && !findCostFamily.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findCostFamily.size(); i++) {
				CostFamilyVO costFamilyVO = findCostFamily.get(i);
				List<Object> rowData = new ArrayList<>();
				String name = costFamilyVO.getName();
				String householdsType = costFamilyVO.getHouseholdsType();
				String masterRelationship = costFamilyVO.getMasterRelationship();
				String national = costFamilyVO.getNational();
				String gender = costFamilyVO.getGender();
				String educationLevel = costFamilyVO.getEducationLevel();
				String idCard = costFamilyVO.getIdCard();
				String isMove = costFamilyVO.getIsMove();
				String isProduce = costFamilyVO.getIsProduce();
				String remark = costFamilyVO.getRemark();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(name);
				rowData.add(householdsType);
				rowData.add(masterRelationship);
				rowData.add(national);
				rowData.add(gender);
				rowData.add(educationLevel);
				rowData.add(idCard);
				rowData.add(isMove);
				rowData.add(isProduce);
				rowData.add(remark);
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

	/**
	 * 获取汇总树
	 * 
	 * @return
	 */
	public CostAggregateTreeVO findCostAggregate() {
		// 子节点集合
		List<CostAggregateVO> findCostAggregate = costDao.findCostAggregate();
		String jsonString = JSON.toJSONString(findCostAggregate);
		List<CostAggregateTreeVO> list = JSON.parseArray(jsonString, CostAggregateTreeVO.class);

		// 根节点
		CostAggregateVO findCostAggregateRoot = costDao.findCostAggregateRoot();
		String jsonString2 = JSON.toJSONString(findCostAggregateRoot);
		CostAggregateTreeVO root = JSON.parseObject(jsonString2, CostAggregateTreeVO.class);
		root.setSerialNumber("1");// 根节点序号 --- 1

		getAggregateTree(list, root);
		// 清空空数据
		clearCostEmptyData(root);

		return root;
	}

	/**
	 * 递归遍历树
	 * 
	 * @param list
	 * @param root
	 */
	private CostAggregateTreeVO getAggregateTree(List<CostAggregateTreeVO> list, CostAggregateTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}
		String cityCode = root.getCityCode();
		String parentSerialNumber = root.getSerialNumber();
		BigDecimal amount = root.getAmount();
		BigDecimal houseAmount = root.getHouseAmount();
		BigDecimal buildingAmount = root.getBuildingAmount();
		BigDecimal treesAmount = root.getTreesAmount();
		BigDecimal youngCropsAmount = root.getYoungCropsAmount();
		BigDecimal otherAmount = root.getOtherAmount();
		List<String> parentCodes = root.getParentCodes();// 所有上级ID
		// step1：遍历找出子节点
		List<CostAggregateTreeVO> children = new ArrayList<>();
		for (CostAggregateTreeVO costAggregateTreeVO : list) {
			String parentCode = costAggregateTreeVO.getParentCode();
			if (StringUtils.equals(parentCode, cityCode)) {
				children.add(costAggregateTreeVO);
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);// 删除多余的已匹配的节点
			int startSerialNumber = 1;
			for (CostAggregateTreeVO costAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				costAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 获取所有的上级ID
				List<String> childrenParentCodes = new ArrayList<>();
				if (parentCodes == null || parentCodes.isEmpty()) {
					childrenParentCodes.add(cityCode);
				} else {
					childrenParentCodes.addAll(parentCodes);
					childrenParentCodes.add(cityCode);
				}
				costAggregateTreeVO.setParentCodes(childrenParentCodes);
				// 递归
				CostAggregateTreeVO aggregateTree = getAggregateTree(list, costAggregateTreeVO);
				// 统计父节点的总值
				BigDecimal amount2 = aggregateTree.getAmount();
				amount = amount.add(amount2);
				BigDecimal houseAmount2 = aggregateTree.getHouseAmount();
				houseAmount = houseAmount.add(houseAmount2);
				BigDecimal buildingAmount2 = aggregateTree.getBuildingAmount();
				buildingAmount = buildingAmount.add(buildingAmount2);
				BigDecimal treesAmount2 = aggregateTree.getTreesAmount();
				treesAmount = treesAmount.add(treesAmount2);
				BigDecimal youngCropsAmount2 = aggregateTree.getYoungCropsAmount();
				youngCropsAmount = youngCropsAmount.add(youngCropsAmount2);
				BigDecimal otherAmount2 = aggregateTree.getOtherAmount();
				otherAmount = otherAmount.add(otherAmount2);
			}
			root.setAmount(amount);
			root.setHouseAmount(houseAmount);
			root.setBuildingAmount(buildingAmount);
			root.setTreesAmount(treesAmount);
			root.setYoungCropsAmount(youngCropsAmount);
			root.setOtherAmount(otherAmount);
			root.setChildren(children);// 拼装树
		}

		return root;
	}

	/**
	 * 递归清空空数据
	 * 
	 * @param data
	 */
	public void clearCostEmptyData(CostAggregateTreeVO data) {
		if (data != null) {
			List<CostAggregateTreeVO> children = data.getChildren();
			if (CollectionUtils.isNotEmpty(children)) {
				List<CostAggregateTreeVO> removeList = new ArrayList<>();
				for (CostAggregateTreeVO costAggregateTreeVO : children) {
					if (costAggregateTreeVO != null) {
						BigDecimal amount = costAggregateTreeVO.getAmount();
						if (CommonUtil.isZeroOrNull(amount)) {
							// 被清楚的空数据
							removeList.add(costAggregateTreeVO);
						} else {
							// 递归
							clearCostEmptyData(costAggregateTreeVO);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(removeList)) {
					children.removeAll(removeList);
				}
			}
		}
	}

	/**
	 * 获取汇总--分项
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<CostAggregateCardVO> findCostAggregateCard(String mergerName, String type) {
		List<CostAggregateCardVO> list = null;
		if (StringUtils.equalsIgnoreCase(CostConstant.AGGREGATE_CARD_ALL, type)) {
			list = costDao.findCostAggregateAll(mergerName);
		}
		if (StringUtils.equalsIgnoreCase(CostConstant.AGGREGATE_CARD_HOUSE, type)) {
			list = costDao.findCostAggregateHouse(mergerName);
		}
		if (StringUtils.equalsIgnoreCase(CostConstant.AGGREGATE_CARD_BUILDING, type)) {
			list = costDao.findCostAggregateBuilding(mergerName);
		}
		if (StringUtils.equalsIgnoreCase(CostConstant.AGGREGATE_CARD_TREES, type)) {
			list = costDao.findCostAggregateTrees(mergerName);
		}
		if (StringUtils.equalsIgnoreCase(CostConstant.AGGREGATE_CARD_YOUNG_CROPS, type)) {
			list = costDao.findCostAggregateYoungCrops(mergerName);
		}
		if (StringUtils.equalsIgnoreCase(CostConstant.AGGREGATE_CARD_OTHER, type)) {
			list = costDao.findCostAggregateOther(mergerName);
		}
		return list;
	}

	/**
	 * 按行政区统计(统计图)
	 * 
	 * @param parentCode
	 * @return
	 */
	public List<CostAggregateChatVO> findCostChat(String parentCode) {
		List<CostAggregateChatVO> findCostChat = costDao.findCostChat(parentCode);
		return findCostChat;
	}

}
