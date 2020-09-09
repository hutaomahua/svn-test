package com.lyht.business.abm.plan.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.OwnerVerifyDao;
import com.lyht.business.abm.plan.dao.PublicityDetailDao;
import com.lyht.business.abm.plan.entity.OwnerVerifyEntity;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmEnterpriseDao;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.system.pojo.SysStaff;

@Transactional
@Service
public class OwnerVerifyService {
	private Logger log = LoggerFactory.getLogger(OwnerVerifyService.class);
	
	@Autowired
	PublicityDetailDao detailDao;
	 @Autowired
    AbmFamilyDao abmFamilyDao;
	@Autowired
	OwnerVerifyDao dao;
	@Autowired
	AbmFamilyDao abmfamilydao;
	@Autowired
	AbmEnterpriseDao enterprisedao;
	@Autowired
	OwnerVerifyDao ownerVerifyDao;
	@Autowired
	private ProcessOperateService processService;

	@Value("${iwind.process.flow.path.family}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath;

	public OwnerVerifyEntity findByTaskId(String taskId) {
		// 参数校验
		if (StringUtils.isBlank(taskId)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		OwnerVerifyEntity findByTaskId = dao.findByTaskId(taskId);
		return findByTaskId;
	}

	public LyhtResultBody<OwnerVerifyEntity> save(OwnerVerifyEntity entity, String flag, HttpServletRequest request) {
		OwnerVerifyEntity result = null;

		if (flag.equals("1")) {// 权属人
			List<Map> code = ownerVerifyDao.getCode();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String time = "";
			if (code.size() > 0) {
				String s = code.get(0).get("code").toString();
				Integer i = Integer.valueOf(s.substring(s.length() - 4));
				i = i + 1;
				String str = i.toString();
				if (str.length() == 1) {
					time = "FH" + sdf1.format(date) + "000" + str;
				}
				if (str.length() == 2) {
					time = "FH" + sdf1.format(date) + "00" + str;
				}
				if (str.length() == 3) {
					time = "FH" + sdf1.format(date) + "0" + str;
				}
				if (str.length() == 4) {
					time = "FH" + sdf1.format(date) + str;
				}
				entity.setCode(time);
			} else {
				time = "FH" + sdf1.format(date) + "0001";
				entity.setCode(time);
			}
			HashMap<String, String> taskData = new HashMap<>();
			taskData.put("name", entity.getHomeName() + "权属人复核申请");
			ProcessOperateVO processOperateVO = new ProcessOperateVO();
			processOperateVO.setFlowPath(physicalFlowPath);
			processOperateVO.setData(taskData);
			String processId = processService.processStart(processOperateVO, request);
			Object obj1 = request.getSession().getAttribute(Constants.SESSION_STAFF);// 获取session中的当前登录的账户信息
			SysStaff sysStaff = (SysStaff) obj1;

			abmfamilydao.updateAbmFamily(entity.getNm(), "1", "0", processId, sysStaff.getNm());// 待审核
			entity.setProcessId(processId);
			result = dao.save(entity);
		} else {
			// 企业
			enterprisedao.updateEnterprise(entity.getNm(), "1");// 待审核
			result = dao.save(entity);
		}

		return new LyhtResultBody<>(result);
	}

	/**
	 * 修改实物指标公示状态
	 * 
	 * @param gsState
	 * @param nm
	 */
	public void editGsState(String gsState, String nm) {
		dao.editGsState(gsState, nm);
	}

	/**
	 * 修改搬迁人口公示状态
	 * 
	 * @param gsState
	 * @param nm
	 */
	public void editBqGsState(String gsState, String nm) {
		dao.editBqGsState(gsState, nm);
	}

	public void editFhState(String fhState, String processId) {
		dao.editFhState(fhState, processId);
	}

	public void editFhxzYj(String zfOpinion, String processId) {
		dao.editFhxzYj(zfOpinion, processId);
	}

	public void editFhzfYj(String ymjOpinion, String processId) {
		dao.editFhzfYj(ymjOpinion, processId);
	}

	public void updateAbmFamily(String nm, String fhState, String processId) {
		abmfamilydao.updateAbmFamily(nm, fhState, processId);
	}

	public void editfgState(String fgstate, String processId) {
		dao.editfgState(fgstate,processId);
		
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map findEchoDisplay(String nm, String region) {
		 Map map = new HashMap<>();
	     List<Map> ownerAllList = abmFamilyDao.getOwnerAllList(region);
	     List<Map> nm2 = detailDao.getNm(nm);
	    map.put("no", ownerAllList);
	    map.put("yes", nm2);
		
		return map;
	}

}
