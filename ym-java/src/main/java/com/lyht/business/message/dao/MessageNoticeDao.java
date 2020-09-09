package com.lyht.business.message.dao;

import com.lyht.business.message.entity.MessageNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface MessageNoticeDao extends JpaRepository<MessageNotice, Integer> {

    @Query(value = " SELECT  t.id,t.nm,subject,content," +
                 " IFNULL((SELECT name FROM t_info_owner_impl WHERE nm =t.to_staff_nm),(SELECT staff_name FROM sys_staff WHERE nm = t.to_staff_nm)) AS 'toStaffName', "+
                 " IFNULL((SELECT name FROM t_info_owner_impl WHERE nm =t.sender_nm),(SELECT staff_name FROM sys_staff WHERE nm = t.sender_nm)) AS 'senderName', "+
                 " DATE_FORMAT(sender_time,'%Y-%m-%d') AS senderTime,t.readFlag,t.type " +
                 " FROM t_message_notice t "+
                 " where 1 = 1  " +
                 " AND if(:toStaffNm is not null &&  :toStaffNm != '', t.to_staff_nm = :toStaffNm ,1=1) " +
                 " AND if(:senderNm is not null &&  :senderNm != '', t.sender_nm = :senderNm ,1=1) " +
                 " order by t.readFlag, t.id desc  ",
            countQuery = "   SELECT count(t.id) FROM t_message_notice t    "
                    + " where 1 = 1  " +
                    "AND if(:toStaffNm is not null &&  :toStaffNm != '', t.to_staff_nm = :toStaffNm ,1=1) " +
                    "AND if(:senderNm is not null && :senderNm != '',    t.sender_nm = :senderNm ,1=1) " +
                    " ",nativeQuery=true)
    Page<Map> page(Pageable pageable, @Param("toStaffNm")String toStaffNm, @Param("senderNm")String senderNm);




}
