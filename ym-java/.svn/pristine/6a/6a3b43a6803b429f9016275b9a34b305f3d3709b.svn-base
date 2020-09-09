package com.lyht.business.land.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.land.bean.LandApplyDetail;
import com.lyht.business.land.dao.LandApplyDao;
import com.lyht.business.land.entity.LandApply;
import com.lyht.business.pub.dao.PubDictValueDao;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/10/15 10:19
 */
@Service("/landApplyService")
public class LandApplyService {
    private static Logger logger = Logger.getLogger(LandApplyService.class);
    @Autowired
    LandApplyDao dao;
    @Autowired
    PubDictValueDao dictValueDao;

    /**
     * 批量删除，id用","隔开
     * @param ids
     * @return
     */
    public String delete(String ids){
        try {

            String[] stringId = ids.split(",");
            for(String id:stringId){
                dao.deleteById(Integer.parseInt(id));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ids;
    }
    /**
     * 分页查询
     *
     * @param lyhtPageVO
     * @return
     */
    public LyhtResultBody<List<LandApplyDetail>> page(LyhtPageVO lyhtPageVO,
                                                LandApply landApply) {
        Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent()-1, lyhtPageVO.getPageSize());
        Page<Map> page = dao.findListByLike(landApply.getRegion(), landApply.getLandName(),landApply.getApplyType(),pageable);
        String jsonString = JSON.toJSONString(page.getContent());
        List<LandApplyDetail> resultList = JSON.parseArray(jsonString, LandApplyDetail.class);
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(resultList, pageVO);
    }
    /**
     * 查询全部数据
     * @param landApply
     * @return
     */
    public LyhtResultBody<List<LandApply>> list(LandApply landApply) {
        return new LyhtResultBody<>(dao.findByApplyTypeLike(landApply.getApplyType()));
    }
    /**
     * 查询全部数据
     * @param landApply
     * @return
     */
    public List<Map> lists(LandApply landApply) {
        return dao.findByApplyType(landApply.getApplyType());
    }
    public LandApply saveEntity(LandApply landApply){
        return dao.save(landApply);
    }

}
