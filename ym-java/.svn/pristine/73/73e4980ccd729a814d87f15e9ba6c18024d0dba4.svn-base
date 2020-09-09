package com.lyht.business.letter.service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.letter.dao.LettersDao;
import com.lyht.business.letter.entity.Letters;
import com.lyht.business.letter.vo.LettersVO;
import com.lyht.business.tab.entity.ConfigTableEntity;
import com.lyht.business.tab.entity.ConfigTableFieldEntity;
import com.lyht.util.Randomizer;
import org.springframework.data.domain.Page;

/**
 * @author HuangTianhao
 * @date 2019年10月30日17:08:14
 */

@Service("/LetterService")
public class LettersService {
	@Autowired
	LettersDao lettersDao;
	@Autowired
	PubRegionDao regionDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LyhtResultBody<List<LettersVO>> page(LyhtPageVO lyhtPageVO, LettersVO lettersVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = lettersDao.page( pageable,lettersVO.getWord());
		// 结果集
		String jsonString = JSON.toJSONString(page.getContent());
		List<LettersVO> list = (List<LettersVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 单项查询
	 * 
	 * @param id
	 * @return
	 * 
	 *         public LyhtResultBody<Letters> getLettersById(Integer id) { Letters
	 *         letters = lettersDao.findById(id).get(); return new
	 *         LyhtResultBody<>(letters); }
	 */

	/**
	 * 查询所有分类
	 * 
	 * @return
	 * 
	 *         public List<String> getCategoty() { return lettersDao.getCategoty();
	 *         }
	 */

	/**
	 * 增\改
	 * 
	 * @param letters
	 * @return
	 */
	public LyhtResultBody<Letters> addLetters(Letters letters) {
		// 参数校验
		if (letters == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		Letters result;
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd");
		ParsePosition position = new ParsePosition(0);
		//letters.setVisitsTime(format.parse(letters.getVisitsTimes(),position));
		// 修改
		if (letters.getId() != null) {
			result = letters;
			result = lettersDao.save(letters);
		}
		// 新增
		else {
			// 内码赋值
			String nm = letters.getNm();
			if (StringUtils.isBlank(nm)) {
				letters.setNm(Randomizer.generCode(10));
			}
			Date time = new java.sql.Date(new java.util.Date().getTime());
			// letters.setThroughTime(time);
			// letters.setReceptionTime(time);
			// letters.setVisitsTime(time);
			result = lettersDao.save(letters);
		}

		return new LyhtResultBody<>(result);
	}

	/**
	 * 单项删除
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		lettersDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 分页查询（传参则按姓名模糊查询）
	 * 
	 * @param ptext
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<Letters>> pageByNameLike(String ptext, String category, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Letters> page = lettersDao.pageByNameLike(ptext, category, pageable);
		List<Letters> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	public List<Letters> importList(List<Letters> letters){
		for(Letters letter:letters){
			List<PubRegionEntity> regionEntities = regionDao.getRegion(letter.getRegion());
			if(regionEntities.size()>0){
				letter.setRegion(regionEntities.get(0).getCityCode());
			}else {
				letter.setRegion(null);
			}
			letter.setNm(Randomizer.generCode(10));
		    lettersDao.save(letter);
		}
		return new ArrayList<>(letters);
	}

}
