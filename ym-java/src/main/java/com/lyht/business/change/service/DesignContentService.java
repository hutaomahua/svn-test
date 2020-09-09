package com.lyht.business.change.service;

import com.lyht.business.change.dao.DesignContentDao;
import com.lyht.business.change.entity.ChangeApplication;
import com.lyht.business.change.entity.DesignContent;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HuangAn
 * @date 2019/12/5 14:48
 */
@Service
public class DesignContentService {
    @Autowired
    private DesignContentDao dao;

    public List<DesignContent> findByChangeRequestType(Integer changeType){

        return dao.findByChangeRequestTypeOrderBySortAsc(changeType);
    }

    public void delete (Integer id){
        dao.deleteById(id);
    }

    public void save(DesignContent designContent){
        String nm = designContent.getNm();
        if (StringUtils.isBlank(nm)) {
            designContent.setNm(Randomizer.generCode(10));
        }
        dao.save(designContent);
    }
}
