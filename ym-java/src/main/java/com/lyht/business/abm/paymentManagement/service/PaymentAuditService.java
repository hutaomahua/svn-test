package com.lyht.business.abm.paymentManagement.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.paymentManagement.entity.PaymentConfirmationBatch;
import com.lyht.business.abm.paymentManagement.entity.PaymentDetailEntity;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentAuditService {

	@Value("${iwind.process.flow.path.paymentApply}")
	private String paymentApplyFlowPath;

	@Value("${iwind.process.flow.path.paymentConfirmation}")
	private String paymentConfirmationFlowPath;
	
    @Autowired
    private ProcessOperateService processOperateService; // 注入流程服务实现类
	
    /**
     * 发起兑付申请流程并返回流程实例ID
     * @param paymentDetail 
     * @return
     */
    public String paymentApply(PaymentDetailEntity paymentDetail,ProcessOperateVO processOperateVO, HttpServletRequest request){
    	
    	if(processOperateVO == null || processOperateVO.getUser() == null){    		
    		processOperateVO = new ProcessOperateVO();
    	}
		Map<String, String> taskData = new HashMap<>();
		taskData.put("name", paymentDetail.getPayee()+"-资金兑付申请");
		processOperateVO.setFlowPath(paymentApplyFlowPath);
		processOperateVO.setData(taskData); // 如无条件判断字段，无需此步
		String processId = processOperateService.processStart(processOperateVO, request); // 返回流程实例Id
    	return processId;
    }
    
    /**
     * 发起兑付确认流程并返回流程实例ID
     * @param entity 
     * @param paymentDetail 
     * @return
     */
    public String paymentConfirmation(PaymentConfirmationBatch entity, HttpServletRequest request){
    	ProcessOperateVO processOperateVO = new ProcessOperateVO();
    	
		Map<String, String> taskData = new HashMap<>();
//		taskData.put("name", paymentDetail.getPayee()+"-资金兑付确认申请");
		taskData.put("name", "资金兑付确认申请");
		processOperateVO.setFlowPath(paymentConfirmationFlowPath);
		processOperateVO.setData(taskData); // 如无条件判断字段，无需此步
		String processId = processOperateService.processStart(processOperateVO, request); // 返回流程实例Id
    	return processId;
    }

	public LyhtResultBody<String> checkData() {
		return new LyhtResultBody<String>("此路不通");
	}
	
}
