package com.lyht.business.land.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.land.bean.LandApplyDetail;
import com.lyht.business.land.bean.LandProblemDetail;
import com.lyht.business.land.dao.LandPlanDao;
import com.lyht.business.land.entity.LandPlan;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/10/15 10:22
 */
@Service("/landPlanService")
public class LandPlanService{
    private static Logger logger = Logger.getLogger(LandPlanService.class);
    @Autowired
    LandPlanDao dao;

    /**
     * 新增 修改
     * @param landPlan
     * @return
     */
    public LandPlan saveEntity(LandPlan landPlan) {
        return dao.save(landPlan);
    }

    public LyhtResultBody<List<LandApplyDetail>> list(LyhtPageVO lyhtPageVO, LandApplyDetail landApplyDetail) {
        // 分页,排序
        List<Map> allByParams = dao.list(landApplyDetail.getNm(), landApplyDetail.getApplyType(),landApplyDetail.getProcessStep());
        // 结果集
        String jsonString = JSON.toJSONString(allByParams);
        List<LandApplyDetail> resultList = JSON.parseArray(jsonString, LandApplyDetail.class);
        return new LyhtResultBody<>(resultList);
    }

    public LyhtResultBody<Integer> byLandNm(LandApplyDetail landApplyDetail) {
        List<Map> allByParams = dao.byLandNm(landApplyDetail.getLandName(), landApplyDetail.getProcessStep());
        // 结果集
        String jsonString = JSON.toJSONString(allByParams);
        List<LandProblemDetail> resultList = JSON.parseArray(jsonString, LandProblemDetail.class);
        return new LyhtResultBody<>(resultList.size());

    }
}
