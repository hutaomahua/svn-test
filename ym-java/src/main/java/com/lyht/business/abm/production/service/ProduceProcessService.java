package com.lyht.business.abm.production.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.production.dao.ProduceProcessDao;
import com.lyht.business.abm.production.dao.ProductionDao;
import com.lyht.business.abm.production.entity.ProduceProcess;
import com.lyht.business.abm.production.vo.ProduceProcessVO;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.system.pojo.SysStaff;

/**
 * 生产安置人口界定及去向确认
 * 
 * @author wzw
 *
 */
@Service
public class ProduceProcessService {
	private static Logger log = LoggerFactory.getLogger(ProduceProcessService.class);

	@Autowired
	private MessageNoticeService messageNoticeService ;
	
	@Autowired
	private ProduceProcessDao dao;

	@Autowired
	private AbmOwnerDao abmOwnerDao;

	@Autowired
	private ProductionDao productionDao;

	@Autowired
	private ProductionService productionService;

	@Value("${iwind.process.flow.path.affirm}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath;// 申请流程

	@Autowired
	private ProcessOperateService processOperateService; // 注入流程服务实现类

	@Autowired
	private PubFilesService pubFilesService;
	
	/**
	 * 保存数据
	 */
	@Transactional
	public ProduceProcess save(String ownerNm, String jsonData) {
		ProduceProcess process = new ProduceProcess();
		process.setJsonData(jsonData);
		process.setOwnerNm(ownerNm);
		// 修改 生产安置状态 = 0 即
		productionDao.updateOwner("0", ownerNm);
		process = dao.save(process);
		return process;
	}

	/**
	 * 发起生产安置确认流程
	 * 
	 * @param request
	 * @param file
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackOn = Exception.class)
	public ProduceProcess startProcess(HttpServletRequest request, MultipartFile file, String ownerNm, String remark) {
		if (file == null || file.isEmpty()) {
			throw new LyhtRuntimeException("上传签字表文件不能为空!");
		}
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("户主编码不能为空!");
		}

		ProduceProcess save = null;
		try {

			AbmOwnerEntity abmOwnerEntity = abmOwnerDao.findByNm(ownerNm);
			if (abmOwnerEntity == null) {
				throw new LyhtRuntimeException("户主不存在!");
			}
			// productionDao.updateUser("3", ownerNm);// 修改人 改变状态
			PubFilesEntity pubFilesEntity = new PubFilesEntity();// 附件
			pubFilesEntity.setTableName("t_abm_produce_process");// 关联表
			pubFilesEntity.setTablePkColumn("produce_" + ownerNm);// 关联列
			PubFilesEntity upload = pubFilesService.upload(request, file, pubFilesEntity);// 上传
			String fileUrl = upload.getFileUrl();// 获取文件路径
			HashMap<String, String> taskData = new HashMap<>();
			taskData.put("name", abmOwnerEntity.getName() + "生产安置人口界定及去向确认");// 自定义流程名称
			ProcessOperateVO processOperateVO = new ProcessOperateVO();// 流程发起参数对象
			processOperateVO.setFlowPath(physicalFlowPath);// 流程地址
			processOperateVO.setData(taskData);//
			String processId = processOperateService.processStart(processOperateVO, request);// 发起流程返回流程id
			ProduceProcess poduceProcessEntity = dao.queryByOwnerNm(ownerNm);// 建立业务对象 存储业务流程数据
			if(poduceProcessEntity==null) {
				poduceProcessEntity = new ProduceProcess();
			}
			poduceProcessEntity.setProcessId(processId);// 流程id
			poduceProcessEntity.setSignFileUrl(fileUrl);// 文件地址
			poduceProcessEntity.setRemark(remark);
			save = dao.save(poduceProcessEntity);// 存储
			productionDao.updateOwner("3", ownerNm);
		} catch (Exception e) {
			log.error("流程发起失败：",e);
			throw new LyhtRuntimeException("流程发起失败！");
		}
		return save;
	}

	/**
	 * 查询对应户主，生产安置确认流程详情
	 * 
	 * @param ownerNm
	 * @return
	 */
	public ProduceProcessVO findByOwnerNm(String ownerNm) {
		ProduceProcessVO produceProcessVO = dao.findByOwnerNm(ownerNm);
		return produceProcessVO;
	}

	/**
	 * 回调 type = 1 失败 type = 0 成功
	 */
	@Transactional
	public LyhtResultBody<String> callBack(String taskId, Integer type,String senderNm) {
		String ownerNm = productionDao.getOwnerNmByTaskId(taskId);
		ProduceProcess process = dao.findByProcessId(taskId);
		//根据复核流程Id查询到复核信息
		//角色nm
		String roleNm= "BCA5AA572F";
		String jsonString = JSON.toJSONString(process);
		if (type == 0) {
			productionDao.updateOwner("2", ownerNm);
			String jsonData = process.getJsonData();
			Map<String, Object> map = (Map<String, Object>) JSON.parse(jsonData);
			List<Map<String, Object>> sTableColumns = (List<Map<String, Object>>) map.get("popuData");
			String place = map.get("place") != null ? map.get("place").toString() : null;
			productionService.popuDefinition(place, ownerNm, sTableColumns);
			messageNoticeService.sendMessageNoticeByUser(senderNm, ownerNm, "生产安置界定确认审批通过", jsonString, "SHOW");
			messageNoticeService.sendMessageNoticeByRole(senderNm, roleNm, "生产安置界定确认审批通过", jsonString, "SHOW");
		} else {
			productionDao.updateOwner("1", ownerNm);
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("name", "生产安置界定确认审批");
			data.put("info", process);
			messageNoticeService.sendMessageNoticeByRole(senderNm, roleNm, "生产安置界定确认审批拒绝", JSON.toJSONString(data), "JUMP");
		}
		
		return new LyhtResultBody<String>();
	}

}
