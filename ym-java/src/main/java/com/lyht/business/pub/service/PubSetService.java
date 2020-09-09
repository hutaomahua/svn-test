package com.lyht.business.pub.service;


import com.lyht.business.pub.dao.PubSetDao;
import com.lyht.business.pub.entity.PubSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月21日 22:41:09
  * 说明:  系统设置
  */
@Service("/pubSetService")
public class PubSetService{
	
	@Autowired  private PubSetDao dao;
	
	public void deleteEntity(String ids){
		String[] idList = ids.split(",");
		for (String id : idList){
			dao.deleteById(Integer.parseInt(id));
		}
	}
}
 