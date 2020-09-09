package com.lyht.business.abm.removal.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmEnterpriseDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.util.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EnterpriseService {
    private Logger log = LoggerFactory.getLogger(EnterpriseService.class);
    
    @Autowired
    AbmEnterpriseDao dao;
    
    public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String region,String scope,String name,String id) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getList(pageable,region,scope,name,id);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getSheBei(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getSheBei(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getWuzi(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getWuzi(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getFw(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getFw(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getFuShu(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getFuShu(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getGjw(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getGjw(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getGd(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getGd(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
    
    public LyhtResultBody<List<Map>> getCaiWu(LyhtPageVO lyhtPageVO, String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = dao.getCaiWu(pageable,nm);
        List<Map> list = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(list, pageVO);

    }
  
}
