package com.lyht.business.land.dao;

import com.lyht.business.land.entity.LandProblem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/10/15 10:17
 */
@Repository
public interface LandProblemDao extends JpaRepository<LandProblem,Integer> {
    /**
     * 分页查询
     * @param landNm
     * @param pageable
     * @return
     */
    @Query(value = "select tt.*, ss.staff_name AS createStaff FROM t_land_problem AS tt  LEFT JOIN sys_staff AS ss ON tt.create_staff_nm = ss.nm where 1=1 and " +
            " IF (:landNm is not null, tt.land_nm =:landNm , 1=1) and" +
            " IF (:ptext is not null, tt.p_text LIKE CONCAT('%',:ptext,'%'), 1=1) order by tt.id asc",
            countQuery = "SELECT count(1) FROM (SELECT tt.*, ss.staff_name FROM t_land_problem AS tt  LEFT JOIN sys_staff AS ss ON tt.create_staff_nm = ss.nm where 1=1 and " +
                    " IF (:landNm is not null, tt.land_nm =:landNm , 1=1) and" +
                    " IF (:ptext is not null, tt.p_text LIKE CONCAT('%',:ptext,'%'), 1=1)) c",
            nativeQuery = true)
    Page<Map> findListByLike(@Param("landNm") String landNm, @Param("ptext")String ptext, Pageable pageable);
}
