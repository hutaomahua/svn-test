package com.lyht.business.change.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.change.dao.ChangeApplicationDao;
import com.lyht.business.change.entity.ChangeApplication;
import com.lyht.business.change.vo.ChangeApplicationVO;
import com.lyht.business.process.service.ProcessService;
import com.lyht.business.process.vo.ProcessStartVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuangAn
 * @date 2019/12/5 14:48
 */
@Service
public class ChangeApplicationService {
	
	@Value("${iwind.process.flow.path.physical}")
	private String physicalFlowPath;
	
	@Autowired
	private ProcessService processService;

    @Autowired
    private ChangeApplicationDao dao;

    public LyhtResultBody<List<ChangeApplicationVO>> page(ChangeApplication changeApplication, LyhtPageVO lyhtPageVO){
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<ChangeApplicationVO> page = dao.page(changeApplication.getProjectName(), changeApplication.getChangeRequestType(),pageable);
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(page.getContent(), pageVO);
    }

    /**
     * 查询单个
     */
    public ChangeApplication findOneById(Integer id) {
        return dao.getOne(id);
    }
    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    public LyhtResultBody<Integer> delete(Integer id) {
        // 参数校验
        if (id == null) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
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
        List<ChangeApplication> deleteFundCost = dao.findAllById(idList);
        dao.deleteInBatch(deleteFundCost);
        return new LyhtResultBody<>(ids);
    }



    /**
     * 添加 修改
     *
     * @param changeApplication
     * @return
     */
    public LyhtResultBody<ChangeApplication> save(ChangeApplication changeApplication) {
        // 参数校验
        if (changeApplication == null) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        // 内码赋值
        String nm = changeApplication.getNm();
        if (StringUtils.isBlank(nm)) {
            changeApplication.setApplyTime(new Date());
            changeApplication.setNm(Randomizer.generCode(10));
        }
        ChangeApplication result = dao.save(changeApplication);
        return new LyhtResultBody<>(result);
    }
    
    /**
     * 发起实物指标变更
     * @param id
     * @return
     */
    public ChangeApplication startChange(Integer id, HttpServletRequest request) {
        // 参数校验
        if (id == null) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        ChangeApplication save = null;
        // step1：获得变更数据
        Optional<ChangeApplication> findById = dao.findById(id);
        if (findById.isPresent()) {
        	ChangeApplication changeApplication = findById.get();
        	Integer changeType = changeApplication.getChangeType();
        	
        	
        	// step2：发起变更流程
        	Map<String, String> taskData = new HashMap<>();
        	taskData.put("changeType", String.valueOf(changeType));
        	
        	ProcessStartVO processStartVO = new ProcessStartVO();
        	processStartVO.setFlowPath(physicalFlowPath);
        	processStartVO.setData(taskData);
        	String processId = processService.processStart(processStartVO, request);
        	
        	// step3：改变数据状态
        	changeApplication.setChangeStatus(1);//变更数据状态；0:未提交，1：已提交
        	changeApplication.setProcessId(processId);//流程ID
        	save = dao.save(changeApplication);
        }
        
        // 内码赋值
        return save;
    }

	public ChangeApplicationVO findByTaskId(String taskId) {
		// 参数校验
        if (StringUtils.isBlank(taskId)) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        ChangeApplicationVO findByTaskId = dao.findByTaskId(taskId);
		return findByTaskId;
	}

}
