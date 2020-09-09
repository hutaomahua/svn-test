package com.lyht.business.abm.plan.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.YearAnnualPlanAdjustDao;
import com.lyht.business.abm.plan.dao.YearAnnualPlanDao;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 年度计划编制日志
 *
 * @author lj
 * 2019/10/22
 */
@Service
public class YearAnnualPlanAdjustService {
    private Logger log = LoggerFactory.getLogger(YearAnnualPlanAdjustService.class);
    @Autowired
    YearAnnualPlanAdjustDao yearAnnualPlanAdjustDao;

    public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String region,String name) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = yearAnnualPlanAdjustDao.getList(region,name,pageable);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
   

}
