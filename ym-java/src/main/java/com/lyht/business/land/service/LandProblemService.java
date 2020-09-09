package com.lyht.business.land.service;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.land.bean.LandProblemDetail;
import com.lyht.business.land.dao.LandProblemDao;
import com.lyht.business.land.entity.LandProblem;
import com.lyht.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/10/15 10:23
 */
@Service("/landProblemService")
public class LandProblemService{
    @Autowired
    LandProblemDao dao;

    public LandProblem save (LandProblem landProblem){
        return dao.save(landProblem);
    }
    public void delete(Integer id){
        dao.deleteById(id);
    }
    /**
     * 分页查询
     *
     * @param lyhtPageVO
     * @return
     */
    public LyhtResultBody<List<LandProblemDetail>> page(LyhtPageVO lyhtPageVO,
                                                       LandProblem landProblem) {

        Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent()-1, lyhtPageVO.getPageSize());
        Page<Map> page = dao.findListByLike(landProblem.getLandNm(),landProblem.getpText(),pageable);
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());

        return new LyhtResultBody<>(EntityUtils.toEntityList(page.getContent(), LandProblemDetail.class), pageVO);
    }
}
