package com.lyht.business.abm.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.wechat.dao.AbmWechatOwnerDao;
import com.lyht.business.abm.wechat.vo.AbmWechatFamilyVO;
import com.lyht.business.abm.wechat.vo.AbmWechatInfoVO;
import com.lyht.business.abm.wechat.vo.AbmWechatOwnerDetailVO;
import com.lyht.business.abm.wechat.vo.AbmWechatOwnerVO;

@Service
public class AbmWechatOwnerService {

	@Autowired
	private AbmWechatOwnerDao abmWechatOwnerDao;

	@Autowired
	private AbmWechatFamilyService abmWechatFamilyService;

	@Autowired
	private AbmWechatInfoService abmWechatInfoService;

	/**
	 * 户主列表查询
	 * 
	 * @param mergerName
	 * @param scope
	 * @param value
	 * @return
	 */
	public List<AbmWechatOwnerVO> list(String mergerName, String scope, String value) {
		List<AbmWechatOwnerVO> list = abmWechatOwnerDao.list(mergerName, scope, value);
		return list;
	}

	/**
	 * 通过户主nm查询权属人
	 * 
	 * @param nm
	 * @return
	 */
	public AbmWechatOwnerVO getOwner(String nm) {
		AbmWechatOwnerVO abmWechatOwnerVO = abmWechatOwnerDao.getOwner(nm);
		return abmWechatOwnerVO;
	}

	/**
	 * 通过户主nm查询权属人详情
	 * 
	 * @param ownerNm
	 * @return
	 */
	public AbmWechatOwnerDetailVO getOwnerDetail(String ownerNm) {
		AbmWechatOwnerDetailVO abmWechatOwnerDetailVO = new AbmWechatOwnerDetailVO();
		AbmWechatOwnerVO owner = getOwner(ownerNm);
		abmWechatOwnerDetailVO.setOwner(owner);

		List<AbmWechatFamilyVO> family = abmWechatFamilyService.list(ownerNm);
		abmWechatOwnerDetailVO.setFamily(family);

		List<AbmWechatInfoVO> info = abmWechatInfoService.list(ownerNm);
		abmWechatOwnerDetailVO.setInfo(info);

		return abmWechatOwnerDetailVO;
	}

	public LyhtResultBody<List<AbmWechatOwnerVO>> page(LyhtPageVO lyhtPageVO, String mergerName, String scope,
			String value) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<AbmWechatOwnerVO> page = abmWechatOwnerDao.page(mergerName, scope, value, pageable);
		// 结果集
		List<AbmWechatOwnerVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

}
