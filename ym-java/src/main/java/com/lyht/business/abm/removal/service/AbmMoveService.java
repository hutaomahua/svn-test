package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;

import java.util.Calendar;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.common.constant.AbmConstant;
import com.lyht.business.abm.removal.dao.AbmMoveDao;
import com.lyht.business.abm.removal.dao.AbmMoveProcessDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmMoveProcessEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.vo.AbmMoveDefinitionVO;
import com.lyht.business.abm.removal.vo.AbmMoveFamilyDefinitionVO;
import com.lyht.business.abm.removal.vo.AbmMoveFamilyInfoVO;
import com.lyht.business.abm.removal.vo.AbmMoveFamilyVO;
import com.lyht.business.abm.removal.vo.AbmMoveOwnerVO;
import com.lyht.business.abm.removal.vo.AbmMoveProcessInfoVO;
import com.lyht.business.abm.removal.vo.AbmMoveProcessVO;
import com.lyht.business.process.common.constant.ProcessConstant;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubDictValueService;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.ExcelSheet;
import com.lyht.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class AbmMoveService {

	@Value("${iwind.process.flow.path.move}")
	private String moveFlowPath;

	@Value("${lyht.move.file.path}")
	private String moveFilePath;

	@Autowired
	private AbmMoveProcessDao abmMoveProcessDao;

	@Autowired
	private AbmOwnerDao abmOwnerDao;

	@Autowired
	private AbmMoveDao abmMoveDao;

	@Autowired
	private PubFilesService pubFilesService;

	@Autowired
	private PubDictValueService pubDictValueService;

	@Autowired
	private ProcessOperateService processOperateService;

	@Autowired
	private AbmOwnerService abmOwnerService;

	/**
	 * 发起搬迁安置确认流程
	 * 
	 * @param request
	 * @param file
	 * @param ownerNm
	 * @param remark
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public AbmMoveProcessEntity startProcess(HttpServletRequest request, MultipartFile file, String ownerNm,
			String remark) {
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("户主编码不能为空!");
		}
		AbmMoveProcessVO abmMoveProcessVO = abmMoveProcessDao.findByOwnerNm(ownerNm);
		if (abmMoveProcessVO == null) {
			throw new LyhtRuntimeException("请先“去向界定”后，再发起“界定确认”流程!");
		}
		String processStatus = abmMoveProcessVO.getProcessStatus();
		if (StringUtils.equalsIgnoreCase(processStatus, ProcessConstant.PROCESS_STANDBY)) {
			throw new LyhtRuntimeException("“界定确认”流程正在处理中!");
		}
		String moveState = abmMoveProcessVO.getMoveState();
		if (!StringUtils.equals(moveState, AbmConstant.MOVE_STATE_IS_UPDATE)) {
			throw new LyhtRuntimeException("请先“去向界定”后，再发起“界定确认”流程!");
		}
		Integer iPopulation = abmMoveProcessVO.getIPopulation();// 人口
//		String placeType = abmMoveProcessVO.getPlaceType();// 安置类型
//		String placeName = abmMoveProcessVO.getPlaceName();// 安置名称
//		String define = abmMoveProcessVO.getDefine();// 界定条件
//		if (StringUtils.isAllBlank(placeType, placeName, define) && iPopulation != null && iPopulation >= 1) {
//			throw new LyhtRuntimeException("该户移民人口大于等于1，必须“去向界定”后才能发起“界定确认流程”！");
//		}
		if ((file == null || file.isEmpty()) && iPopulation != null && iPopulation >= 1) {
			throw new LyhtRuntimeException("该户移民人口大于等于1，必须上传已签字的界定确认文件!");
		}

		AbmMoveProcessEntity result = abmMoveProcessDao.getByOwnerNm(ownerNm);
		if (result == null) {
			throw new LyhtRuntimeException("请先“去向界定”后，再发起“界定确认”流程!");
		}
		try {

			AbmOwnerEntity abmOwnerEntity = abmOwnerDao.findByNm(ownerNm);
			if (abmOwnerEntity == null) {
				throw new LyhtRuntimeException("户主不存在!");
			}

			String fileNm = null;
			if (file != null && !file.isEmpty()) {
				PubFilesEntity pubFilesEntity = new PubFilesEntity();
				pubFilesEntity.setTableName(AbmConstant.MOVE_TABLE_NAME);
				pubFilesEntity.setTablePkColumn(AbmConstant.MOVE_PREFIX + ownerNm);

				PubFilesEntity upload = pubFilesService.upload(request, file, pubFilesEntity);
				fileNm = upload.getNm();
			}

			// data数据

			ProcessOperateVO processOperateVO = new ProcessOperateVO();
			processOperateVO.setFlowPath(moveFlowPath);
			String ownerName = abmMoveProcessVO.getOwnerName();
			if (StringUtils.isNotBlank(ownerName)) {
				Map<String, String> data = new HashMap<>();
				data.put("name", ownerName + "-搬迁安置人口界定及去向确认");
				processOperateVO.setData(data);
				log.debug("=====搬迁安置人口界定及去向确认==========" + ownerName);
			}
//			processStartVO.setUser("fc88cd7f-66fb-4541-bb2b-65dc385e5b4e");//未登录测试用
			String processId = null;
			try {
				processId = processOperateService.processStart(processOperateVO, request);
			} catch (Exception e) {
				throw new LyhtRuntimeException("流程引擎请求超时，请检查网络后重试！");
			}
			result.setProcessId(processId);
			result.setSignFileUrl(fileNm);// 附件nm
			result.setRemark(remark);
			result = abmMoveProcessDao.save(result);

			// 修改当前户安置状态为“2”：流程处理中，不可做任何操作
			updateOwnerMoveState(ownerNm, AbmConstant.MOVE_STATE_DOING);
		} catch (LyhtRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new LyhtRuntimeException("流程发起失败！");
		}
		return result;
	}
	
	/**
	 * 跳过搬迁安置 直接将安置状态 moveState 变为0 完成
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public LyhtResultBody<Object> directlyToComplete(String nms) {
		if (StringUtils.isBlank(nms)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		try {
			String[] nmArray = nms.split(",");
			for (String nm : nmArray) {
				abmMoveProcessDao.updateOwner("0", nm);
			}
		} catch (Exception e) {
			log.error("失败", e);
		}
		return new LyhtResultBody<>();
	}

	/**
	 * 查询对应户主，搬迁安置确认流程详情
	 * 
	 * @param ownerNm
	 * @param processId
	 * @return
	 */
	public AbmMoveProcessVO findByOwnerNm(String ownerNm, String processId) {
		if (StringUtils.isNotBlank(ownerNm)) {
			AbmMoveProcessVO findByOwnerNm = abmMoveProcessDao.findByOwnerNm(ownerNm);
			return findByOwnerNm;
		} else if (StringUtils.isNotBlank(processId)) {
			AbmMoveProcessVO findByProcessId = abmMoveProcessDao.findByProcessId(processId);
			return findByProcessId;
		}
		return null;
	}
	
	/**
	 * 查询对应户主（搬迁安置确认流程详情（安置界定后））
	 * @param processId
	 * @return
	 */
	public AbmMoveProcessInfoVO getProcessInfo(String processId) {
		AbmMoveProcessInfoVO abmMoveProcessInfoVO = null;
		AbmMoveProcessVO findByProcessId = abmMoveProcessDao.findByProcessId(processId);
		if (findByProcessId != null) {
			String jsonString = JSON.toJSONString(findByProcessId);
			abmMoveProcessInfoVO = JSON.parseObject(jsonString, AbmMoveProcessInfoVO.class);
			
			String moveJsonData = findByProcessId.getMoveJsonData();
			if (StringUtils.isNotBlank(moveJsonData)) {
				//替换权属人 安置界定后数据
				AbmMoveDefinitionVO abmMoveDefinitionVO = JSON.parseObject(moveJsonData, AbmMoveDefinitionVO.class);
				String newPlaceName = abmMoveDefinitionVO.getPlaceName();
				String newPlaceType = abmMoveDefinitionVO.getPlaceType();
				String newPlaceAddress = abmMoveDefinitionVO.getPlaceAddress();
				String newCounty = abmMoveDefinitionVO.getCounty();
				String newTown = abmMoveDefinitionVO.getTown();
				String newVillage = abmMoveDefinitionVO.getVillage();
				String newToWhere = abmMoveDefinitionVO.getToWhere();
				
				abmMoveProcessInfoVO.setPlaceName(newPlaceName);
				newPlaceType = pubDictValueService.findNameByNm(newPlaceType);
				abmMoveProcessInfoVO.setPlaceType(newPlaceType);
				abmMoveProcessInfoVO.setPlaceAddress(newPlaceAddress);
				abmMoveProcessInfoVO.setCounty(newCounty);
				abmMoveProcessInfoVO.setTown(newTown);
				abmMoveProcessInfoVO.setVillage(newVillage);
				abmMoveProcessInfoVO.setToWhere(newToWhere);
				
				//替换家庭成员 安置界定后数据
				String ownerNm = abmMoveProcessInfoVO.getOwnerNm();
				List<AbmMoveFamilyVO> listMoveFamily = abmMoveDao.listMoveFamily(ownerNm);
				Map<String,AbmMoveFamilyDefinitionVO> replaceMap = new HashMap<>();
				List<AbmMoveFamilyDefinitionVO> newfamilyList = abmMoveDefinitionVO.getFamilyList();
				if (CollectionUtils.isNotEmpty(newfamilyList)) {
					for (AbmMoveFamilyDefinitionVO abmMoveFamilyDefinitionVO : newfamilyList) {
						String nm = abmMoveFamilyDefinitionVO.getNm();
						replaceMap.put(nm, abmMoveFamilyDefinitionVO);
					}
				}
				if (CollectionUtils.isNotEmpty(listMoveFamily)) {
					String jsonString2 = JSON.toJSONString(listMoveFamily);
					List<AbmMoveFamilyInfoVO> familyList = JSON.parseArray(jsonString2, AbmMoveFamilyInfoVO.class);
					for (AbmMoveFamilyInfoVO abmMoveFamilyInfoVO : familyList) {
						String nm = abmMoveFamilyInfoVO.getNm();
						AbmMoveFamilyDefinitionVO abmMoveFamilyDefinitionVO = replaceMap.get(nm);
						if (abmMoveFamilyDefinitionVO != null) {
							String define = abmMoveFamilyDefinitionVO.getDefine();
							String isSatisfy = abmMoveFamilyDefinitionVO.getIsSatisfy();
							abmMoveFamilyInfoVO.setDefine(define);
							if (StringUtils.equals(isSatisfy, "2")) {
								abmMoveFamilyInfoVO.setIsSatisfy("是");
							} else {
								abmMoveFamilyInfoVO.setIsSatisfy("否");
							}
						}
					}
					abmMoveProcessInfoVO.setFamilyList(familyList);
				}
			}
		}
		return abmMoveProcessInfoVO;
	}

	/**
	 * 更新安置状态
	 * 
	 * @param ownerNm
	 * @param state
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateOwnerMoveState(String ownerNm, String state) {
		int updateOwnerMoveState = abmMoveDao.updateOwnerMoveState(ownerNm, state);
		return updateOwnerMoveState;
	}

	/**
	 * 搬迁安置家庭成员信息分页查询
	 * 
	 * @param lyhtPageVO
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<AbmMoveFamilyVO>> pageMoveFamily(LyhtPageVO lyhtPageVO, String ownerNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<AbmMoveFamilyVO> page = abmMoveDao.pageMoveFamily(ownerNm, pageable);
		// 结果集
		List<AbmMoveFamilyVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 去向界定，保存安置数据，改变当前户安置状态
	 * 
	 * @param abmMoveDefinitionVO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean definitionBefore(AbmMoveDefinitionVO abmMoveDefinitionVO) {
		if (abmMoveDefinitionVO == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}

		String ownerNm = abmMoveDefinitionVO.getOwnerNm();// 户主内码

		int countByOwnerNm = abmOwnerService.countByOwnerNm(ownerNm);
		if (countByOwnerNm <= 0) {
			throw new LyhtRuntimeException("户主不存在");
		}
		List<AbmMoveFamilyDefinitionVO> familyList = abmMoveDefinitionVO.getFamilyList();// 家庭成员信息
		if (familyList == null || familyList.isEmpty()) {
			throw new LyhtRuntimeException("界定的家庭成员信息不存在");
		}

		try {
			// 暂存安置数据
			AbmMoveProcessEntity abmMoveProcessEntity = new AbmMoveProcessEntity();
			abmMoveProcessEntity.setOwnerNm(ownerNm);
			abmMoveProcessEntity.setMoveJsonData(JSON.toJSONString(abmMoveDefinitionVO));
			abmMoveProcessEntity = abmMoveProcessDao.save(abmMoveProcessEntity);
			// 修改当前户安置状态为“1”：已修改，可导出界定表和发起流程
			updateOwnerMoveState(ownerNm, AbmConstant.MOVE_STATE_IS_UPDATE);
		} catch (Exception e) {
			log.error("=====AbmMoveService=====Method:definitionBefore=====ownerNm:" + ownerNm, e);
			throw new LyhtRuntimeException("请求超时，请稍后重试！");
		}
		return true;
	}

	/**
	 * 搬迁安置流程回调
	 * 
	 * @param processId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean callback(String processId) {
		// 根据流程id查询当前流程的信息
		AbmMoveProcessVO findByOwnerNm = findByOwnerNm(null, processId);
		String ownerNm = findByOwnerNm.getOwnerNm();
		String moveJsonData = findByOwnerNm.getMoveJsonData();
		String processStatus = findByOwnerNm.getProcessStatus();
		if (StringUtils.isNotBlank(moveJsonData)
				&& StringUtils.equals(processStatus, ProcessConstant.PROCESS_APPROVED)) {
			AbmMoveDefinitionVO parseObject = JSON.parseObject(moveJsonData, AbmMoveDefinitionVO.class);
			definition(parseObject);
			// 修改当前户安置状态为“0”：已界定，可搬迁安置去向界定；
			updateOwnerMoveState(ownerNm, AbmConstant.MOVE_STATE_NORMAL);
		} else if(StringUtils.isNotBlank(moveJsonData)
				&& StringUtils.equals(processStatus, ProcessConstant.PROCESS_REJECTED)) {
			// 拒绝
			updateOwnerMoveState(ownerNm, AbmConstant.MOVE_STATE_FAIL);
		}
		return true;
	}

	/**
	 * 搬迁安置去向及人口界定修改
	 * 
	 * @param abmMoveDefinitionVO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean definition(AbmMoveDefinitionVO abmMoveDefinitionVO) {
		if (abmMoveDefinitionVO == null) {
			log.error("=====AbmMoveService=====Method:definition=====参数错误");
			return false;
		}

		String ownerNm = abmMoveDefinitionVO.getOwnerNm();// 户主内码

		try {
			int countByOwnerNm = abmOwnerService.countByOwnerNm(ownerNm);
			if (countByOwnerNm <= 0) {
				log.error("=====AbmMoveService=====Method:definition=====户主不存在");
				return false;
			}
			List<AbmMoveFamilyDefinitionVO> familyList = abmMoveDefinitionVO.getFamilyList();// 家庭成员信息
			if (familyList == null || familyList.isEmpty()) {
				log.error("=====AbmMoveService=====Method:definition=====界定的家庭成员信息不存在");
				return false;
			}

			String placeType = abmMoveDefinitionVO.getPlaceType();// 安置类型
			String placeName = abmMoveDefinitionVO.getPlaceName();// 安置地点（名称）
			String county = abmMoveDefinitionVO.getCounty();// 县
			String town = abmMoveDefinitionVO.getTown();// 乡镇
			String village = abmMoveDefinitionVO.getVillage();// 村
			String toWhere = abmMoveDefinitionVO.getToWhere();// 去向
			String placeAddress = abmMoveDefinitionVO.getPlaceAddress();// 安置位置（分散安置）

			for (AbmMoveFamilyDefinitionVO abmMoveFamilyDefinitionVO : familyList) {
				String nm = abmMoveFamilyDefinitionVO.getNm();// 家庭成员编码
				String isSatisfy = abmMoveFamilyDefinitionVO.getIsSatisfy();// 是否符合(1：不符合；2：符合)
				String define = abmMoveFamilyDefinitionVO.getDefine();// 界定条件
				if (!StringUtils.equalsAny(isSatisfy, AbmConstant.IS_SATISFY, AbmConstant.IS_NOT_SATISFY)) {
					log.error("=====AbmMoveService=====Method:definition=====是否符合界定条件不能为空(1：不符合；2：符合)");
					return false;
				}
				String masterRelationship = abmMoveFamilyDefinitionVO.getMasterRelationship();
				if (StringUtils.equals(masterRelationship, "户主")) {
					abmMoveDao.updateOwner(ownerNm, define, isSatisfy, county, town, village, placeType, placeName,
							toWhere, placeAddress);
				}
				abmMoveDao.updateFamily(nm, define, isSatisfy, county, town, village, placeType, placeName, toWhere,
						placeAddress);
			}
		} catch (Exception e) {
			log.error("=====AbmMoveService=====Method:definition=====数据库连接超时", e);
			return false;
		}

		return true;
	}

//	
//	public void exportExcel(HttpServletResponse response, String ownerNm, String fileName) {
//		ExcelWriter writer = ExcelUtil.getWriter();
//		// 跳过5行
////		writer.passCurrentRow();
//		writer.passRows(5);
//		// 表头
//		List<List<String>> rowData = new ArrayList<>();
//		List<String> header = new ArrayList<>();
//		header.add("序号");
//		header.add("姓名");
//		header.add("户口类型");
//		header.add("与户主关系");
//
//		header.add("民族");
//		header.add("性别");
//		header.add("年龄");
//
//		header.add("身份证号码");
//		header.add("人口阶段");
//		header.add("是否符合");
//		header.add("界定条件");
//		header.add("安置方式");
//		header.add("安置点名称");
//		header.add("县");
//		header.add("乡");
//		header.add("村");
//		header.add("组");
//		header.add("去向分类");
//		header.add("备注");
//		rowData.add(header);
//
//		// 数据写入
//		AbmMoveOwnerVO findMoveOwnerByNm = abmMoveDao.findMoveOwnerByNm(ownerNm);
//		if (findMoveOwnerByNm == null) {
//			throw new LyhtRuntimeException("户主不存在,请核实后重试!");
//		}
//		String ownerIsSatisfy = findMoveOwnerByNm.getIsSatisfy();
//		if (!StringUtils.equals(ownerIsSatisfy, "2")) {
//			throw new LyhtRuntimeException("该户未搬迁安置核定!");
//		}
//		List<AbmMoveFamilyVO> listMoveFamily = abmMoveDao.listMoveFamily(ownerNm);
//		if (CollectionUtils.isEmpty(listMoveFamily)) {
//			throw new LyhtRuntimeException("该户家庭成员信息不完善,请核实后重试!");
//		}
//
//		for (int i = 0; i < listMoveFamily.size(); i++) {
//			AbmMoveFamilyVO abmMoveFamilyVO = listMoveFamily.get(i);
//			List<String> row = new ArrayList<>();
//			row.add(String.valueOf(i + 1));// 序号
//			String name = abmMoveFamilyVO.getName();
//			row.add(name);
//			String householdsType = abmMoveFamilyVO.getHouseholdsType();
//			row.add(householdsType);
//			String masterRelationshipValue = abmMoveFamilyVO.getMasterRelationshipValue();
//			row.add(masterRelationshipValue);
//			String national = abmMoveFamilyVO.getNational();
//			row.add(national);
//			String gender = abmMoveFamilyVO.getGender();
//			row.add(gender);
//			Integer age = abmMoveFamilyVO.getAge();
//			row.add(age != null ? String.valueOf(age) : null);
//			String idCard = abmMoveFamilyVO.getIdCard();
//			row.add(idCard);
//			String stageValue = abmMoveFamilyVO.getStageValue();
//			row.add(stageValue);
//			String isSatisfy = abmMoveFamilyVO.getIsSatisfy();
//			if (StringUtils.equals(isSatisfy, "2")) {
//				row.add("是");
//			} else {
//				row.add("否");
//			}
//			String define = abmMoveFamilyVO.getDefine();
//			row.add(define);
//			String placeTypeValue = abmMoveFamilyVO.getPlaceTypeValue();
//			row.add(placeTypeValue);
//			String placeName = abmMoveFamilyVO.getPlaceName();
//			row.add(placeName);
//			String county = abmMoveFamilyVO.getCounty();
//			row.add(county);
//			String town = abmMoveFamilyVO.getTown();
//			row.add(town);
//			String village = abmMoveFamilyVO.getVillage();
//			row.add(village);
//			row.add(null);// 组
//			String toWhere = abmMoveFamilyVO.getToWhere();
//			row.add(toWhere);
//			String remark = abmMoveFamilyVO.getRemark();
//			row.add(remark);// 备注
//			rowData.add(row);
//		}
//
//		writer.write(rowData);
//
//		// 合并表头
//		writer.merge(0, 0, 0, 14, "托巴水电站搬迁安置人口界定、安置方式及去向到户确认表", true);
//		writer.merge(0, 1, 0, 0, "姓名", false);
//		writer.merge(0, 1, 1, 1, "与户主关系", false);
//		writer.merge(0, 1, 2, 2, "身份证号码", false);
//		writer.merge(0, 0, 3, 4, "人口界定", false);
//		writer.merge(0, 0, 5, 11, "安置方式及去向", false);
//		// 设置列宽
//		writer.setColumnWidth(0, 15);
//		writer.setColumnWidth(1, 15);
//		writer.setColumnWidth(2, 22);
//		writer.setColumnWidth(3, 10);
//		writer.setColumnWidth(4, 40);
//		writer.setColumnWidth(5, 10);
//		writer.setColumnWidth(6, 20);
//		writer.setColumnWidth(7, 20);
//		writer.setColumnWidth(8, 15);
//		writer.setColumnWidth(9, 15);
//		writer.setColumnWidth(10, 15);
//		writer.setColumnWidth(11, 15);
//
//		try {
//			response.addHeader("Content-Disposition",
//					"attachment;filename="
//							+ URLEncoder.encode((StringUtils.isNotBlank(fileName) ? fileName : "人口界定及安置去向表"), "UTF-8")
//							+ ".xlsx");
//			writer.flush(response.getOutputStream(), true);
//		} catch (Exception e) {
//			throw new LyhtRuntimeException("人口界定及安置去向表导出失败!请稍后重试!");
//		}
//
//	}
	public Integer evaluate(String sfzjh){
			Integer age = 0;
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH)+1;
            int dayNow = cal.get(Calendar.DATE);
            int year = 0;
            int month = 0;
            int day = 0;
            if(sfzjh !=null&&sfzjh.length()==18){
            	  year = Integer.valueOf(sfzjh.substring(6, 10));
                  month = Integer.valueOf(sfzjh.substring(10,12));
                  day = Integer.valueOf(sfzjh.substring(12,14));
            }else if(sfzjh !=null&&sfzjh.length()==15){
          	  year = Integer.valueOf(sfzjh.substring(6, 8))+1900;
                month = Integer.valueOf(sfzjh.substring(9,10));
                day = Integer.valueOf(sfzjh.substring(11,12));
          }else{
        	  return age=0;
          }

            if ((month < monthNow) || (month == monthNow && day<= dayNow) ){
                age = Integer.valueOf(yearNow - year);
            }else {
                age = Integer.valueOf(yearNow - year-1);
            }


        return age;
    }

	/**
	 * 人口公式和安置去向表导出
	 * 
	 * @param response
	 * @param ownerNm
	 * @param fileName
	 */
	public void exportExcel(HttpServletResponse response, String ownerNm, String fileName) {
		AbmMoveOwnerVO findMoveOwnerByNm = abmMoveDao.findMoveOwnerByNm(ownerNm);
		if (findMoveOwnerByNm == null) {
			throw new LyhtRuntimeException("户主不存在,请核实后重试!");
		}

		String moveState = findMoveOwnerByNm.getMoveState();
		if (!StringUtils.equals(moveState, AbmConstant.MOVE_STATE_IS_UPDATE)) {
			throw new LyhtRuntimeException("该户未搬迁安置界定!");
		}
		List<AbmMoveFamilyVO> listMoveFamily = abmMoveDao.listMoveFamily(ownerNm);
		if (CollectionUtils.isEmpty(listMoveFamily)) {
			throw new LyhtRuntimeException("该户家庭成员信息不完善,请核实后重试!");
		}

		AbmMoveProcessVO findByOwnerNm = abmMoveProcessDao.findByOwnerNm(ownerNm);
		AbmMoveDefinitionVO abmMoveDefinitionVO = null;// 界定后的户主信息
		Map<String, AbmMoveFamilyDefinitionVO> replaceMap = new HashMap<>();// 界定后需替换的家庭成员信息
		if (findByOwnerNm != null) {
			String moveJsonData = findByOwnerNm.getMoveJsonData();
			if (StringUtils.isNotBlank(moveJsonData)) {
				abmMoveDefinitionVO = JSON.parseObject(moveJsonData, AbmMoveDefinitionVO.class);
				List<AbmMoveFamilyDefinitionVO> familyList = abmMoveDefinitionVO.getFamilyList();
				if (CollectionUtils.isNotEmpty(familyList)) {
					for (AbmMoveFamilyDefinitionVO abmMoveFamilyDefinitionVO : familyList) {
						String nm = abmMoveFamilyDefinitionVO.getNm();
						replaceMap.put(nm, abmMoveFamilyDefinitionVO);
					}
				}
			}
		}
		
		// 去向界定后的数据
		String newPlaceType = null;
		if (abmMoveDefinitionVO != null) {
			String placeTypeNm = abmMoveDefinitionVO.getPlaceType();
			newPlaceType = pubDictValueService.findNameByNm(placeTypeNm);
		}
		String newPlaceName = null;
		if (abmMoveDefinitionVO != null) {
			newPlaceName = abmMoveDefinitionVO.getPlaceName();
		}
		String newPlaceAddress = null;
		if (abmMoveDefinitionVO != null) {
			newPlaceAddress = abmMoveDefinitionVO.getPlaceAddress();
		}
		String newCounty = null;
		if (abmMoveDefinitionVO != null) {
			newCounty = abmMoveDefinitionVO.getCounty();
		}
		String newTown = null;
		if (abmMoveDefinitionVO != null) {
			newTown = abmMoveDefinitionVO.getTown();
		}
		String newVillage = null;
		if (abmMoveDefinitionVO != null) {
			newVillage = abmMoveDefinitionVO.getVillage();
		}
		String newToWhere = null;
		if (abmMoveDefinitionVO != null) {
			newToWhere = abmMoveDefinitionVO.getToWhere();
		}

		// 数据写入
		List<CellRangeAddress> mergerList = new ArrayList<>();
		List<List<Object>> listData = new ArrayList<>();
		for (int i = 0; i < listMoveFamily.size(); i++) {
			AbmMoveFamilyVO abmMoveFamilyVO = listMoveFamily.get(i);
			String nm = abmMoveFamilyVO.getNm();
			AbmMoveFamilyDefinitionVO abmMoveFamilyDefinitionVO = replaceMap.get(nm);

			List<Object> row = new ArrayList<>();
			row.add(i + 1);// 序号
			String name = abmMoveFamilyVO.getName();
			row.add(name);
			String householdsType = abmMoveFamilyVO.getHouseholdsType();
			row.add(householdsType);
			String masterRelationshipValue = abmMoveFamilyVO.getMasterRelationshipValue();
			row.add(masterRelationshipValue);
			String national = abmMoveFamilyVO.getNational();
			row.add(national);
			String gender = abmMoveFamilyVO.getGender();
			row.add(gender);
			Integer age = abmMoveFamilyVO.getAge();
			String idCard = abmMoveFamilyVO.getIdCard();
		    age = evaluate(idCard);
			row.add(age);
			row.add(idCard);
			String stageValue = abmMoveFamilyVO.getStageValue();
			row.add(stageValue);
			// 是否符合
			String isSatisfy = abmMoveFamilyVO.getIsSatisfy();
			if (abmMoveFamilyDefinitionVO != null) {
				String newIsSatisfy = abmMoveFamilyDefinitionVO.getIsSatisfy();
				if (StringUtils.isNotBlank(newIsSatisfy)) {
					isSatisfy = newIsSatisfy;
				}
			}
			if (StringUtils.equals(isSatisfy, "2")) {
				row.add("是");
			} else {
				row.add("否");
			}
			// 界定条件
			String define = abmMoveFamilyVO.getDefine();
			if (abmMoveFamilyDefinitionVO != null) {
				String newDefine = abmMoveFamilyDefinitionVO.getDefine();
				if (StringUtils.isNotBlank(newDefine)) {
					define = newDefine;
				}
			}
			row.add(define);
			// 安置类型
			String placeTypeValue = abmMoveFamilyVO.getPlaceTypeValue();
			if (StringUtils.isNotBlank(newPlaceType)) {
				placeTypeValue = newPlaceType;
			}
			row.add(placeTypeValue);
			// 安置地点
			String placeName = abmMoveFamilyVO.getPlaceName();
			if (StringUtils.isNotBlank(newPlaceName)) {
				placeName = newPlaceName;
			}
			row.add(placeName);
			// 安置位置
			String placeAddress = abmMoveFamilyVO.getPlaceAddress();
			if (StringUtils.isNotBlank(newPlaceAddress)) {
				placeAddress = newPlaceAddress;
			}
			String county = abmMoveFamilyVO.getCounty();// 县
			if (StringUtils.isNotBlank(newCounty)) {
				county = newCounty;
			}
			String town = abmMoveFamilyVO.getTown();// 乡
			if (StringUtils.isNotBlank(newTown)) {
				town = newTown;
			}
			String village = abmMoveFamilyVO.getVillage();// 村
			if (StringUtils.isNotBlank(newVillage)) {
				village = newVillage;
			}
			if (StringUtils.isBlank(placeAddress)) { //组
				if (StringUtils.isNotBlank(county)) {
					StringBuffer str = new StringBuffer(county);
					if (StringUtils.isNotBlank(town)) {
						str.append(town);
					}
					if (StringUtils.isNotBlank(village)) {
						str.append(village);
					}
					placeAddress = str.toString();
				}
			}
			row.add(placeAddress);
			row.add(null);
			row.add(null);
			row.add(null);
			// 安置去向
			String toWhere = abmMoveFamilyVO.getToWhere();
			if (StringUtils.isNotBlank(newToWhere)) {
				toWhere = newToWhere;
			}
			row.add(toWhere);
			// 备注
			String remark = abmMoveFamilyVO.getRemark();
			row.add(remark);
			listData.add(row);
			
			//需要合并的行和列
			CellRangeAddress cellRangeAddress = new CellRangeAddress(5 + i, 5 + i, 13, 16);
			mergerList.add(cellRangeAddress);
		}
		Map<String, Object> replaceContent = new HashMap<>();
		String mergerName = findMoveOwnerByNm.getMergerName();
		String scopeValue = findMoveOwnerByNm.getScopeValue();
		replaceContent.put("mergerName", mergerName);
		replaceContent.put("scope", scopeValue);
		String householdsTypeValue = findMoveOwnerByNm.getHouseholdsTypeValue();
		if (StringUtils.contains(householdsTypeValue, "集镇")) {
			replaceContent.put("village", "¨");// Wingdings字体
			replaceContent.put("town", "þ");// Wingdings字体
		} else {
			replaceContent.put("village", "þ");// Wingdings字体
			replaceContent.put("town", "¨");// Wingdings字体
		}

		try {
			List<ExcelSheet> excelSheetList = new ArrayList<>();
			ExcelSheet excelSheet = new ExcelSheet();
			excelSheet.setSheetName("搬迁安置去向确认表");// 操作sheet名称
			excelSheet.setBorder(true);// 表格是否设置边框
			excelSheet.setFontSize((short) 10);// 设置字体
			excelSheet.setStartRowIndex(5);// 从第五行开始遍历渲染数据
			excelSheet.setListData(listData);// 表格数据
			excelSheet.setReplaceContent(replaceContent);// 替换内容
			excelSheet.setMergerList(mergerList);//需要合并的行和列
			excelSheetList.add(excelSheet);
			ExcelUtils.exportAndReplaceExcel(moveFilePath, fileName, response, excelSheetList);
		} catch (Exception e) {
			log.error("失败", e);
			throw new LyhtRuntimeException("人口界定及安置去向表导出失败!请稍后重试!");
		}

	}

	/**
	 * 搬迁安置家庭成员信息查询
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmMoveFamilyVO> listMoveFamily(String ownerNm) {
		List<AbmMoveFamilyVO> listMoveFamily = abmMoveDao.listMoveFamily(ownerNm);
		return listMoveFamily;
	}

	/**
	 * 搬迁安置户主信息分页查询
	 * 
	 * @param lyhtPageVO
	 * @param ownerName
	 * @param idCard
	 * @param mergerName
	 * @return
	 */
	public LyhtResultBody<List<AbmMoveOwnerVO>> pageMoveOwner(LyhtPageVO lyhtPageVO, String ownerName, String idCard,
			String mergerName) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<AbmMoveOwnerVO> page = abmMoveDao.pageMoveOwner(ownerName, idCard, mergerName, pageable);
		// 结果集
		List<AbmMoveOwnerVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}
	
}
