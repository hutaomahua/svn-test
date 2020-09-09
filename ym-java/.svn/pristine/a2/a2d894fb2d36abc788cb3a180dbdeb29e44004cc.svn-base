package com.lyht.business.abm.settle.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.settle.entity.SettleInfo;
import com.lyht.business.abm.settle.entity.SettleStatus;

import io.swagger.annotations.ApiOperation;

/**
* @author HuangTianhao
* @date 2019年11月28日 
*/
public interface SettleStatusDao  extends JpaRepository<SettleStatus, Integer> {
	//关联删除
	@Modifying
	@Transactional
    @Query(value = "DELETE FROM t_abm_construction_settle_status "
    		+ "WHERE 1=1 and "
    		+ "info_nm = :nm ",nativeQuery = true)
    void delete(@Param("nm") String nm);
    //条件分页
	 @Query(value =  "select * from t_abm_construction_settle_status status "
		  		+ "where 1=1 and "
		  		+ "IF (:builtStatus is not null,status.built_status = :builtStatus,1=1) and "
		  		+ "IF (:timeStart is not null,status.time_start = :timeStart,1=1) and "
		  	//	+ "status.diff =:diff and "
		  		+ "IF (:name is not null,status.name LIKE concat('%',:name,'%'),1=1) "
		  		+ "order by  status.name asc",nativeQuery = true)
	Page<Map> getList(Pageable pageable, @Param("name") String name, @Param("timeStart") Date timeStart, 
			@Param("builtStatus") String builtStatus);
	 
}
