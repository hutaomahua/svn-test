package com.lyht.business.rests.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.rests.bean.LetterManagerDetail;
import com.lyht.business.rests.dao.LetterManageDao;
import com.lyht.business.rests.pojo.LetterManagerEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LetterManageService {
    private static Logger logger = Logger.getLogger(LetterManageService.class);
    @Autowired
    LetterManageDao letterManageDao;

    public LyhtResultBody<LetterManagerEntity> save(LetterManagerEntity letterManagerEntity) {
        LetterManagerEntity result = null;
        try {
            String nm = letterManagerEntity.getNm();
            if (StringUtils.isBlank(nm)) {
                letterManagerEntity.setNm(Randomizer.generCode(10));
            }
            result = letterManageDao.save(letterManagerEntity);

        } catch (Exception e) {
            logger.error("=====LetterManageService=====Method=save=====Params:" + letterManagerEntity + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(result);
    }

    public LyhtResultBody<List<LetterManagerDetail>> page(LyhtPageVO lyhtPageVO, LetterManagerDetail letterManagerDetail) {
        // 分页,排序
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = letterManageDao.findPageAndSortByParams(letterManagerDetail.getParameter(), pageable);
        // 结果集
        String jsonString = JSON.toJSONString(page.getContent());
        List<LetterManagerDetail> resultList = JSON.parseArray(jsonString, LetterManagerDetail.class);
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(resultList, pageVO);

    }

    public LyhtResultBody<Integer> delete(Integer id) {
        try {
            letterManageDao.deleteById(id);
        } catch (Exception e) {
            logger.error("=====LetterManageService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<String> batchDel(String ids) {
        List<Integer> idList = null;
        try {
            idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
            List<LetterManagerEntity> deleteOwners = letterManageDao.findAllById(idList);
            letterManageDao.deleteInBatch(deleteOwners);
        } catch (Exception e) {
            logger.error("=====LetterManageService=====Method=batchDel=====Params:" + ids + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(ids);
    }
    public List<Map> list(LetterManagerDetail letterManagerDetail) {
        // 结果集
        List<Map> resultList = letterManageDao.list(letterManagerDetail.getParameter());
          return resultList;
    }
}
