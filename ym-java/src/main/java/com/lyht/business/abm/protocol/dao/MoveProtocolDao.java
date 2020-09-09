package com.lyht.business.abm.protocol.dao;

import com.lyht.business.abm.protocol.entity.MoveProtocol;
import com.lyht.business.abm.protocol.vo.MoveProtocolVO;
import com.lyht.business.abm.removal.entity.MoveApproveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HuangAn
 * @date 2019/12/9 17:12
 */
@Repository
public interface MoveProtocolDao extends JpaRepository<MoveProtocol, Integer> {

    @Query(value =" select  " +
            "tapl.id,tapl.nm,tapl.protocol_no as protocolNo,tapl.protocol_name as protocolName,tapl.protocol_money as protocolMoney,tapl.cost_nm as costNm,tapl.is_move_reset as isMoveReset,tapl.move_reset_time as moveResetTime,tapl.is_money_escrow as isMoneyEscrow ,tapl.money_escrow_time as moneyEscrowTime,tapl.remark, " +
            "tama.nm as approveNm,tama.owner_nm as ownerNm,tama.to_where as toWhere,tama.place_type as placeType ,tama.place_addr as placeAddr,tama.place_name as placeName, " +
            "tif.name as ownerName,tif.id_card as idCard " +
            "from t_abm_move_approve tama  " +
            "LEFT JOIN t_info_family tif on tama.owner_nm = tif.id  " +
            "LEFT JOIN t_abm_move_protocol tapl on tapl.approve_nm = tama.nm  " +
            "where tif.master_relationship = (select nm from pub_dict_value where listnm_sys_dict_cate = 'dict_owner_relation' and name = '户主')  " +
            "and tama.place_name is not null and tama.place_addr is not null " +
            "and IF (:ownerName is not null,tmi.owner_nm like CONCAT('%',:ownerName,'%'), 1=1)  order by  tmi.id desc ",
            countQuery=" select count(*) from ( select    " +
                    "  tapl.id,tapl.nm,tapl.protocol_no as protocolNo,tapl.protocol_name as protocolName,tapl.protocol_money as protocolMoney,tapl.cost_nm as costNm,tapl.is_move_reset as isMoveReset,tapl.move_reset_time as moveResetTime,tapl.is_money_escrow as isMoneyEscrow ,tapl.money_escrow_time as moneyEscrowTime,tapl.remark,   " +
                    "  tama.nm as approveNm,tama.owner_nm as ownerNm,tama.to_where as toWhere,tama.place_type as placeType ,tama.place_addr as placeAddr,tama.place_name as placeName,   " +
                    "  tif.name as ownerName,tif.id_card as idCard   " +
                    "  from t_abm_move_approve tama    " +
                    "  LEFT JOIN t_info_family tif on tama.owner_nm = tif.id    " +
                    "  LEFT JOIN t_abm_move_protocol tapl on tapl.approve_nm = tama.nm    " +
                    "  where tif.master_relationship = (select nm from pub_dict_value where listnm_sys_dict_cate = 'dict_owner_relation' and name = '户主')    " +
                    "  and tama.place_name is not null and tama.place_addr is not null   " +
                    "  and IF (:ownerName is not null,tmi.owner_nm like CONCAT('%',:ownerName,'%'), 1=1)  order by  tmi.id desc " +
                    ") c "
            ,nativeQuery = true)
    Page<MoveProtocolVO> findListByLike(Pageable pageable);
}
