package com.lyht.business.info.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.lyht.util.tree.RegionTreeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.info.dao.InfoMoveAggregateDao;
import com.lyht.business.info.vo.InfoMoveAggregateCardVO;
import com.lyht.business.info.vo.InfoMoveAggregateParamVO;
import com.lyht.business.info.vo.InfoMoveAggregateTreeVO;
import com.lyht.util.CommonUtil;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
public class InfoMoveAggregateService {

	@Autowired
	private InfoMoveAggregateDao infoMoveAggregateDao;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void findRegionTree2() throws IOException {
		File xlsFile = new File("D:\\work\\test.xlsx");
		Workbook workbook = WorkbookFactory.create(xlsFile);
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			int rowNumbers = sheet.getLastRowNum() + 1;
			Row temp = sheet.getRow(0);
			if (temp == null) {
				continue;
			}
			int cells = temp.getPhysicalNumberOfCells();
			for (int row = 0; row < rowNumbers; row++) {
				Row r = sheet.getRow(row);
				String uuid;
				if(null != r.getCell(4)){
					uuid = r.getCell(4).toString();
				}else {
					uuid = "gnsfggavh" + row + "asdbva";
				}
				infoMoveAggregateDao.insertInfo(r.getCell(3).toString(),uuid,r.getCell(5).toString(),
						r.getCell(6).toString(),r.getCell(7).toString(),r.getCell(8).toString(),
						r.getCell(9).toString(),r.getCell(10).toString(),r.getCell(11).toString()
						,r.getCell(12).toString(),r.getCell(13).toString(),r.getCell(14).toString(),r.getCell(15).toString(),r.getCell(16).toString()
						,r.getCell(17).toString(),r.getCell(18).toString(),r.getCell(19).toString(),r.getCell(20).toString(),r.getCell(21).toString()
						,r.getCell(22).toString(),r.getCell(23).toString(),r.getCell(24).toString(),r.getCell(25).toString());
				/*for (int col = 0; col < cells; col++) {
					System.out.print(r.getCell(col) + " ");
				}
				System.out.println();*/
			}
		}
	}

	/**
	 * 获取搬迁安置汇总树--按行政区汇总
	 * 
	 * @return
	 */
	public List<InfoMoveAggregateTreeVO> findRegionTree() {
		/*List<InfoMoveAggregateVO> findMoveAggregate = infoMoveAggregateDao.findMoveAggregate();
		String jsonString = JSON.toJSONString(findMoveAggregate);
		List<InfoMoveAggregateTreeVO> parseArray = JSON.parseArray(jsonString, InfoMoveAggregateTreeVO.class);
		List<InfoMoveAggregateTreeVO> getAggregateTree = getRegionTree(parseArray);
		return getAggregateTree;*/
		Query dataQuery = entityManager.createNativeQuery("SELECT name,cityCode,parentCode,households,population," +
				"reservoirTotalHouseholds,reservoirTotalPopulation," +
				"pivotTotalHouseholds + pivotTotalHouseholds2 AS pivotTotalHouseholds," +
				"pivotTotalPopulation + pivotTotalPopulation2 AS pivotTotalPopulation," +
				"newTownHouseholds,newTownPopulation,householdsProgramme,populationProgramme," +
				"reservoirTotalHouseholdsProgramme,reservoirTotalPopulationProgramme," +
				"pivotTotalHouseholdsProgramme + pivotTotalHouseholdsProgramme2 AS pivotTotalHouseholdsProgramme," +
				"pivotTotalPopulationProgramme + pivotTotalPopulationProgramme2 AS pivotTotalPopulationProgramme," +
				"newTownHouseholdsProgramme,newTownPopulationProgramme " +
				"FROM t_info_statistical_relocation_population");
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<InfoMoveAggregateTreeVO> findMoveAggregate = dataQuery.getResultList();
		String jsonString = JSON.toJSONString(findMoveAggregate);
		List<InfoMoveAggregateTreeVO> parseArray = JSON.parseArray(jsonString, InfoMoveAggregateTreeVO.class);
		return new RegionTreeUtils().getRegionTree(parseArray);
	}

	/**
	 * 获取行政区汇总树
	 * 
	 * @param list
	 * @return
	 */
	private List<InfoMoveAggregateTreeVO> getRegionTree(List<InfoMoveAggregateTreeVO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		// step1：遍历找出所有根节点，并递归子节点
		List<InfoMoveAggregateTreeVO> rootList = new ArrayList<>();
		int startSerialNumber = 1;
		for (InfoMoveAggregateTreeVO infoMoveAggregateTreeVO : list) {
			String level = infoMoveAggregateTreeVO.getLevel();
			if (StringUtils.equals(level, "3")) {
				// 生成序号
				infoMoveAggregateTreeVO.setSerialNumber(String.valueOf(startSerialNumber));
				rootList.add(infoMoveAggregateTreeVO);
				startSerialNumber++;
			}
		}
		if (!rootList.isEmpty()) {
			list.removeAll(rootList);
			for (InfoMoveAggregateTreeVO infoMoveAggregateTreeVO : rootList) {
				getRegionChildren(list, infoMoveAggregateTreeVO);
			}
			// 清空空数据
			clearMoveEmptyData(rootList);
			return rootList;
		}
		return null;
	}

	/**
	 * 递归行政区子节点
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	private InfoMoveAggregateTreeVO getRegionChildren(List<InfoMoveAggregateTreeVO> list,
			InfoMoveAggregateTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}

		// root属性
		String cityCode = root.getCityCode();// 父ID
		String parentSerialNumber = root.getSerialNumber();// 序号
		BigDecimal households = root.getHouseholds();// 总户数
		BigDecimal population = root.getPopulation();// 总人数
		BigDecimal pivotTotalHouseholds = root.getPivotTotalHouseholds();// 水库淹没影响区--合计(户数)
		BigDecimal pivotTotalPopulation = root.getPivotTotalPopulation();// 水库淹没影响区--合计(人口)
		BigDecimal reservoirTotalHouseholds = root.getReservoirTotalHouseholds();// 枢纽工程建设区--合计(户数)
		BigDecimal reservoirTotalPopulation = root.getReservoirTotalPopulation();// 枢纽工程建设区--合计(人口)
		BigDecimal newTownHouseholds = root.getNewTownHouseholds();// 集镇新址占地区(户数)
		BigDecimal newTownPopulation = root.getNewTownPopulation();// 集镇新址占地区(人数)
		List<String> parentCodes = root.getParentCodes();// 所有上级ID

		// step1：遍历找出子节点
		List<InfoMoveAggregateTreeVO> children = new ArrayList<>();
		for (InfoMoveAggregateTreeVO infoMoveAggregateTreeVO : list) {
			String parentCode = infoMoveAggregateTreeVO.getParentCode();
			if (StringUtils.equalsIgnoreCase(cityCode, parentCode)) {
				children.add(infoMoveAggregateTreeVO);
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (InfoMoveAggregateTreeVO infoMoveAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				infoMoveAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 获取所有的上级ID
				List<String> childrenParentCodes = new ArrayList<>();
				if (parentCodes == null || parentCodes.isEmpty()) {
					childrenParentCodes.add(cityCode);
				} else {
					childrenParentCodes.addAll(parentCodes);
					childrenParentCodes.add(cityCode);
				}
				infoMoveAggregateTreeVO.setParentCodes(childrenParentCodes);
				// 递归
				InfoMoveAggregateTreeVO aggregateTree = getRegionChildren(list, infoMoveAggregateTreeVO);
				// 统计父节点的总值
				BigDecimal households2 = aggregateTree.getHouseholds();// 总户数
				households = households.add(households2);
				BigDecimal population2 = aggregateTree.getPopulation();// 总人数
				population = population.add(population2);
				BigDecimal pivotTotalHouseholds2 = aggregateTree.getPivotTotalHouseholds();// 水库淹没影响区--合计(户数)
				pivotTotalHouseholds = pivotTotalHouseholds.add(pivotTotalHouseholds2);
				BigDecimal pivotTotalPopulation2 = aggregateTree.getPivotTotalPopulation();// 水库淹没影响区--合计(人口)
				pivotTotalPopulation = pivotTotalPopulation.add(pivotTotalPopulation2);
				BigDecimal reservoirTotalHouseholds2 = aggregateTree.getReservoirTotalHouseholds();// 枢纽工程建设区--合计(户数)
				reservoirTotalHouseholds = reservoirTotalHouseholds.add(reservoirTotalHouseholds2);
				BigDecimal reservoirTotalPopulation2 = aggregateTree.getReservoirTotalPopulation();// 枢纽工程建设区--合计(人口)
				reservoirTotalPopulation = reservoirTotalPopulation.add(reservoirTotalPopulation2);
				BigDecimal newTownHouseholds2 = aggregateTree.getNewTownHouseholds();// 集镇新址占地区(户数)
				newTownHouseholds = newTownHouseholds.add(newTownHouseholds2);
				BigDecimal newTownPopulation2 = aggregateTree.getNewTownPopulation();// 集镇新址占地区(人数)
				newTownPopulation = newTownPopulation.add(newTownPopulation2);
			}
			root.setHouseholds(households);
			root.setPopulation(population);
			root.setPivotTotalHouseholds(pivotTotalHouseholds);
			root.setPivotTotalPopulation(pivotTotalPopulation);
			root.setReservoirTotalHouseholds(reservoirTotalHouseholds);
			root.setReservoirTotalPopulation(reservoirTotalPopulation);
			root.setNewTownHouseholds(newTownHouseholds);
			root.setNewTownPopulation(newTownPopulation);
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 递归清空空数据
	 * 
	 * @param dataList
	 */
	public void clearMoveEmptyData(List<InfoMoveAggregateTreeVO> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			List<InfoMoveAggregateTreeVO> removeList = new ArrayList<>();
			for (InfoMoveAggregateTreeVO infoMoveAggregateTreeVO : dataList) {
				if (infoMoveAggregateTreeVO != null) {
					BigDecimal households = infoMoveAggregateTreeVO.getHouseholds();
					BigDecimal population = infoMoveAggregateTreeVO.getPopulation();
					if (CommonUtil.isZeroOrNull(households) && CommonUtil.isZeroOrNull(population)) {
						// 被清楚的空数据
						removeList.add(infoMoveAggregateTreeVO);
					} else {
						// 递归
						List<InfoMoveAggregateTreeVO> children = infoMoveAggregateTreeVO.getChildren();
						clearMoveEmptyData(children);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(removeList)) {
				dataList.removeAll(removeList);
			}
		}
	}

	/**
	 * 搬迁安置--汇总卡片
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<InfoMoveAggregateCardVO> findMoveAggregateCard(InfoMoveAggregateParamVO infoMoveAggregateParamVO) {
		if (infoMoveAggregateParamVO == null) {
			return null;
		}
		String mergerName = infoMoveAggregateParamVO.getMergerName();
		List<String> scopes = infoMoveAggregateParamVO.getScopes();
		boolean isAll = true;
		if (CollectionUtils.isNotEmpty(scopes)) {
			isAll = false;
		}

		List<InfoMoveAggregateCardVO> findMoveAggregateCard = infoMoveAggregateDao.findMoveAggregateCard(mergerName,
				scopes, isAll);
		return findMoveAggregateCard;
	}

}
