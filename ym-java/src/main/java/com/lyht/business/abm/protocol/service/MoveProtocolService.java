package com.lyht.business.abm.protocol.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.protocol.dao.MoveProtocolDao;
import com.lyht.business.abm.protocol.entity.MoveProtocol;
import com.lyht.business.abm.protocol.vo.MoveProtocolVO;
import com.lyht.business.abm.removal.dao.MoveApproveDao;
import com.lyht.business.change.dao.DesignContentDao;
import com.lyht.business.change.entity.DesignContent;
import com.lyht.business.land.bean.LandApplyDetail;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/12/9 17:13
 */
@Service
public class MoveProtocolService {
    @Autowired
    MoveProtocolDao dao;

    @Autowired
    MoveApproveDao moveApproveDao;

    public LyhtResultBody<List<MoveProtocolVO>> page(LyhtPageVO lyhtPageVO,MoveProtocolVO moveProtocolVO){
        Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent()-1, lyhtPageVO.getPageSize());
        Page<MoveProtocolVO> page = dao.findListByLike(pageable);
        String jsonString = JSON.toJSONString(page.getContent());
        List<MoveProtocolVO> resultList = JSON.parseArray(jsonString, MoveProtocolVO.class);
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(resultList, pageVO);
    }

    public List<MoveProtocol> findByChangeRequestType(Integer changeType){

        return null; // dao.findByChangeRequestTypeOrderBySortAsc(changeType);
    }

    public void delete (Integer id){
        dao.deleteById(id);
    }

    public void batchDel (String ids){
        String[] idList = ids.split(",");
        for (String id:idList){
            dao.deleteById(Integer.parseInt(id));
        }
    }
    public MoveProtocol save(MoveProtocol moveProtocol){
        String nm = moveProtocol.getNm();
        if (StringUtils.isBlank(nm)) {
            moveProtocol.setNm(Randomizer.generCode(10));
        }
        return dao.save(moveProtocol);
    }
}
