package com.lyht.business.message.service;


import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.message.entity.MessageNotice;
import com.lyht.business.message.vo.MessageNoticeVO;

import java.util.List;

/**
 * 消息提醒接口
 */
public interface MessageNoticeService {


    /**
     * 分页查询:默认查全部
     * 查询已接收：toStaffNm  当前接收人nm内码
     * 查询已发送：senderNm   当前登录人nm内码
     * @param lyhtPageVO
     * @param messageNotice  toStaffNm ： 接收人-nm内码   ，senderNm发送人-nm内码
     * @return
     */
    LyhtResultBody<List<MessageNoticeVO>> page(LyhtPageVO lyhtPageVO, MessageNotice messageNotice);



    /**
     * 推送到指定人
     * @param senderNm   发送人内码
     * @param toStaffNm  接收人内码
     * @param subject    消息主题
     * @param content    消息内容
     * @param messageType  消息类别。 SHOW：展示 , JUMP:跳转 ,默认SHOW   枚举MessageTypeEnums已定义
     * @return
     */
    LyhtResultBody<MessageNotice> sendMessageNoticeByUser(String senderNm, String toStaffNm, String subject, String content, String messageType);

    /**
     * 推送到指定角色下所有人
     * @param senderNm  发送人内码
     * @param roleNm    角色内码
     * @param subject   消息主题
     * @param content   消息内容
     * @param messageType 消息类别。 SHOW：展示 , JUMP:跳转 ,默认SHOW   枚举MessageTypeEnums已定义
     * @return
     */
    LyhtResultBody sendMessageNoticeByRole(String senderNm,String roleNm, String subject, String content, String messageType);

    /**
     * 推送到指定部门下所有人
     * @param senderNm  发送人内码
     * @param deptNm    部门内码
     * @param subject   消息主题
     * @param content   消息内容
     * @param messageType 消息类别。 SHOW：展示 , JUMP:跳转 ,默认SHOW   枚举MessageTypeEnums已定义
     * @return
     */
    LyhtResultBody  sendMessageNoticeByDepartment(String senderNm,String deptNm, String subject, String content, String messageType);


    /**
     * 修改已读状态
     * @param id  消息id
     * @param readFlag  1:已读  0:未读
     * @return
     */
    LyhtResultBody  updateReadFlag(Integer id,Integer readFlag);






}
