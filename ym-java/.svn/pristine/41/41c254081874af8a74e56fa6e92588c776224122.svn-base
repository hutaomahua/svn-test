package com.lyht.business.process.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lyht.business.abm.household.service.AbmSplitHouseholdService;
import com.lyht.business.abm.landAllocation.dao.LandAuditDao;
import com.lyht.business.abm.landAllocation.service.LandAuditService;
import com.lyht.business.abm.paymentManagement.service.PaymentApplyService;
import com.lyht.business.abm.paymentManagement.service.PaymentConfirmationService;
import com.lyht.business.abm.removal.service.AbmMoveService;
import com.lyht.business.abm.review.service.PersonaWealthService;
import com.lyht.business.process.common.constant.ProcessConstant;
import com.lyht.business.process.dao.ProcessDao;
import com.lyht.business.process.dto.response.ProcessMessage;
import com.lyht.business.process.dto.response.model.BackStepData;
import com.lyht.business.process.dto.response.model.InfoData;
import com.lyht.business.process.vo.ProcessOperateVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CallBackService {

	@Autowired
	private LandAuditService landAuditService;
	@Autowired
	private LandAuditDao landAuditEntityDao;
	@Autowired
	private ProcessDao processDao;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PaymentApplyService paymentService;
	@Autowired
	private PaymentConfirmationService paymentConfirmationService;
	@Autowired
	private ProcessInquireService processInquireService;
	private Logger log = LoggerFactory.getLogger(ProcessInquireService.class);
	@Value("${iwind.process.getStepInfo.url}")
	private String getStepInfoUrl;// 查询某步骤信息
	@Value("${iwind.process.project.id}")
	private String projectId;// 项目ID

	@Autowired
	private PersonaWealthService service;// 个人财产 实物指标复核

	@Autowired
	private AbmSplitHouseholdService abmSplitHouseholdService;// 分户

	@Autowired
	private AbmMoveService abmMoveService;// 搬迁安置

	/**
	 * 回调业务代码
	 * 
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unused")
	public void callBack(String taskId, String stepId, String token, InfoData processInfo, HttpServletRequest request) {
		try {
			// 获取流程实体和步骤实体
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + token);
			headers.set("_LoginProjectId", projectId);
			headers.set("_TenantId", projectId);// 租户ID 5-28更新
			Map<String, Object> urlParams = new HashMap<>();
			urlParams.put("taskId", taskId);
			urlParams.put("stepId", stepId);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> getForEntity = restTemplate.exchange(getStepInfoUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<BackStepData> body = JSON.parseObject(obj,
					new TypeReference<ProcessMessage<BackStepData>>() {
					});
			BackStepData stepData = body.getData();
			String name = processInfo.getName();
			String state = processInfo.getState();

			// 设计变更
			if (StringUtils.equalsAny(name, "土地省市分解至县", "土地省市分解至乡", "土地省市分解至村", "土地省市分解至组", "土地省市分解至户")) {
				List<Map<String, Object>> incomplete = landAuditEntityDao.findIncompleteByTaskId(taskId);
				if (incomplete == null || incomplete.size() == 0) {
					return;
				}
				for (Map<String, Object> map : incomplete) {
					String status = map.get("status") != null ? map.get("status").toString() : null;
					Integer id = map.get("id") != null ? Integer.valueOf(map.get("id").toString()) : 0;
					if ("Approved".equals(status)) { // 流程通过了
						landAuditService.landAudit(id, "64FABB5F0D", null);
					} else if ("Rejected".equals(status)) { // 流程被拒绝了
						landAuditService.landAudit(id, "36845517AF", null);
					}
				}
			}
			// 移民补偿不住资金支付分批
			if (StringUtils.equals(name, "移民补偿补助资金支付审批")) {
				paymentService.audit(taskId, state);
			}
			// 移民补偿不住资金支付分批
			else if (StringUtils.equals(name, "移民补偿补助资金支付确认审批")) {
				paymentConfirmationService.paymentFinish(taskId, state);
			}

			// 分户申请流程
			else if (StringUtils.equals(name, "分户申请") || name.contains("分户申请")) {
				abmSplitHouseholdService.applyProcessCallback(taskId, request);
			}
			
			// 分户流程
			else if (StringUtils.equals(name, "分户提交") || StringUtils.equals(name, "分户")) {
				abmSplitHouseholdService.splitCallback(taskId, request);
			}

			// 搬迁安置界定流程
			else if (StringUtils.equals(name, "搬迁安置人口界定及去向确认")) {
				abmMoveService.callback(taskId);
			}
			
		} catch (Exception e) {
			log.error("=====CallBackService=====Method：callBack=====", e);
		}

	}
}
