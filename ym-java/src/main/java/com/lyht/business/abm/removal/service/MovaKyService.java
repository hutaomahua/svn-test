package com.lyht.business.abm.removal.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.MoveDao;
import com.lyht.business.abm.removal.dao.MoveKyDao;

@Service
public class MovaKyService {
	  private Logger log = LoggerFactory.getLogger(MovaKyService.class);
	    @Autowired
	    MoveKyDao moveKyDao;
	    
	    
	    public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String region,int count) {


	        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
	        Page<Map> page = moveKyDao.getList(pageable,region,count);
//	    	String jsonString = JSON.toJSONString();
			List<Map> list = page.getContent();
			LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
					page.getTotalElements(), lyhtPageVO.getSorter());
			return new LyhtResultBody<>(list, pageVO);
	    }

}
