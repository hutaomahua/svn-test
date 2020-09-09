package com.lyht.business.pub.service;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubProjectInfoDao;
import com.lyht.business.pub.entity.PubProjectInfo;
import com.lyht.business.pub.vo.PubProjectInfoDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人： liuamang 脚本日期:2019年2月21日 22:40:44 说明: 字典分类
 */
@Service("/pubProjectInfoService")
public class PubProjectInfoService {

	@Autowired
	private PubProjectInfoDao dao;

	/**
	 * 查询项目
	 * 
	 * @return
	 */
	public LyhtResultBody<List<PubProjectInfoDetail>> page(LyhtPageVO lyhtPageVO, PubProjectInfo pubdictnamedetail) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PubProjectInfoDetail> page = dao.page(pubdictnamedetail.getRegion(),pubdictnamedetail.getpName(),pageable);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(page.getContent(), pageVO);

	}

	public LyhtResultBody<List<PubProjectInfo>> list(PubProjectInfo pubdictnamedetail) {
		List<PubProjectInfo> findAllByParams = dao.list(pubdictnamedetail.getRegion(), pubdictnamedetail.getpName());
		return new LyhtResultBody<>(findAllByParams);
	}

	public PubProjectInfo findById(Integer id){
		return dao.findById(id).get();
	}

	public void deleteEntity(String ids){
		String[] idList = ids.split(",");
		for (String id : idList){
			dao.deleteById(Integer.parseInt(id));
		}
	}
}
