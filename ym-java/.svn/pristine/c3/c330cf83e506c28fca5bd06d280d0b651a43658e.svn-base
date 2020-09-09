package com.lyht.business.message.service.impl;


import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.enums.imp.MessageTypeEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.message.dao.MessageNoticeDao;
import com.lyht.business.message.entity.MessageNotice;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.business.message.vo.MessageNoticeVO;
import com.lyht.system.dao.SysDeptDao;
import com.lyht.system.dao.SysRoleStaffRefDao;
import com.lyht.system.dao.SysStaffDao;
import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.vo.SysDeptVo;
import com.lyht.util.Randomizer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("/MessageNoticeService")
public class MessageNoticeServiceImpl implements MessageNoticeService {

    private static Logger logger = Logger.getLogger(MessageNoticeServiceImpl.class);

    @Autowired
    private MessageNoticeDao messageNoticeDao;

    @Autowired
    private SysRoleStaffRefDao sysRoleStaffRefDao;

    @Autowired
    private SysStaffDao sysStaffDao;

    @Autowired
    private SysDeptDao sysDeptDao;



    /**
     * 分页查询
     * @param lyhtPageVO
     * @param messageNotice
     * @return
     */
    @Override
    public LyhtResultBody<List<MessageNoticeVO>> page(LyhtPageVO lyhtPageVO, MessageNotice messageNotice) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = messageNoticeDao.page( pageable,messageNotice.getToStaffNm(),messageNotice.getSenderNm());
        // 结果集
        String jsonString = JSON.toJSONString(page.getContent());

        List<MessageNoticeVO> list = (List<MessageNoticeVO>) JSON.parse(jsonString);
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(), page.getTotalElements(), lyhtPageVO.getSorter());

        return new LyhtResultBody<>(list, pageVO);
    }

    /**
     * 推送到指定人
     * @param senderNm 发送人内码
     * @param toStaffNm  接收人内码
     * @param subject  消息主题
     * @param content  消息内容
     * @param messageType  消息类别  : MessageTypeEnums
     * @return
     */
    @Override
    @Transactional
    public LyhtResultBody<MessageNotice> sendMessageNoticeByUser(String senderNm,String toStaffNm, String subject, String content, String messageType) {

        LyhtResultBody lyhtResultBody = new LyhtResultBody<>();

        try{

            if(StringUtils.isBlank(senderNm)||StringUtils.isBlank(toStaffNm)){
                lyhtResultBody.setMsg("发送者或接受者,信息未传");
                lyhtResultBody.setFlag(false);
                return lyhtResultBody;
            }

            if(StringUtils.isBlank(messageType)) messageType = MessageTypeEnums.SHOW.getCode();

            MessageNotice messageNotice = MessageNotice.builder()
                    .nm(Randomizer.generCode(10))
                    .subject(subject).content(content).toStaffNm(toStaffNm).senderNm(senderNm)
                    .readFlag(0).type(messageType)
                    .senderTime(new Date()).createStaff(senderNm).updateStaff(senderNm)
                    .createTime(new Date()).updateTime(new Date())
                    .build();

             messageNoticeDao.save(messageNotice);

        }catch (Exception e){
            logger.error("sendMessageNoticeByUser,senderNm:"+senderNm+",toStaffNm:"+toStaffNm+",subject:"+subject+",content:"+content+",messageType:"+messageType);
            logger.error("sendMessageNoticeByUser--Error:"+e.getMessage());
            lyhtResultBody.setMsg("消息接口异常");
            lyhtResultBody.setFlag(false);
        }

        return lyhtResultBody;
    }

    /**
     * 推送到指定角色下所有人
     * @param senderNm 发送人内码
     * @param roleNm 角色内码
     * @param subject 消息主题
     * @param content 消息内容
     * @param messageType  消息类别  : MessageTypeEnums
     * @return
     */
    @Override
    @Transactional
    public LyhtResultBody<MessageNotice> sendMessageNoticeByRole(String senderNm,String roleNm, String subject, String content, String messageType) {

        LyhtResultBody lyhtResultBody = new LyhtResultBody<>();

        try{
            List<SysRoleStaffRef> roleStaffRefList  = sysRoleStaffRefDao.findByRoleNm(roleNm);

            if(CollectionUtils.isEmpty(roleStaffRefList)){
                                lyhtResultBody.setMsg("该角色下未绑定人员");
                                lyhtResultBody.setFlag(false);
                return lyhtResultBody;
            }

            if(StringUtils.isBlank(messageType)) messageType = MessageTypeEnums.SHOW.getCode();

            for (SysRoleStaffRef sysRoleStaffRef : roleStaffRefList) {
                MessageNotice messageNotice = MessageNotice.builder()
                                    .nm(Randomizer.generCode(10))
                                    .subject(subject).content(content).toStaffNm(sysRoleStaffRef.getStaffNm())
                                    .senderNm(senderNm).readFlag(0).type(messageType)
                                    .senderTime(new Date()).createStaff(senderNm).updateStaff(senderNm)
                                    .createTime(new Date()).updateTime(new Date())
                                    .build();
                messageNoticeDao.save(messageNotice);
            }

        }catch (Exception e){
            logger.error("sendMessageNoticeByRole,senderNm:"+senderNm+",roleNm:"+roleNm+",subject:"+subject+",content:"+content+",messageType:"+messageType);
            logger.error("sendMessageNoticeByRole--Error:"+e.getMessage());
            lyhtResultBody.setMsg("消息接口异常");
            lyhtResultBody.setFlag(false);
        }
        
        return lyhtResultBody;
    }

    /**
     * 推送到指定部门下所有人
     * @param senderNm 发送人内码
     * @param deptNm  部门内码
     * @param subject 消息主题
     * @param content 消息内容
     * @param messageType  消息类别  : MessageTypeEnums
     * @return
     */
    @Override
    @Transactional
    public LyhtResultBody sendMessageNoticeByDepartment(String senderNm,String deptNm, String subject, String content,String messageType) {

        LyhtResultBody lyhtResultBody = new LyhtResultBody<>();

        try{

            List<String> list  = sysStaffDao.queryStaffNmByDeptNm(deptNm);

                if(CollectionUtils.isEmpty(list) ){
                    lyhtResultBody.setMsg("该部门下未绑定人员");
                    lyhtResultBody.setFlag(false);
                    return lyhtResultBody;
                }

                if(StringUtils.isBlank(messageType)) messageType = MessageTypeEnums.SHOW.getCode();

                for (String nm : list) {
                    MessageNotice messageNotice = MessageNotice.builder()
                                                .nm(Randomizer.generCode(10))
                                                .subject(subject).content(content).toStaffNm(nm)
                                                .senderNm(senderNm).readFlag(0).type(messageType)
                                                .senderTime(new Date()).createStaff(senderNm).updateStaff(senderNm)
                                                .createTime(new Date()).updateTime(new Date())
                                                .build();
                   messageNoticeDao.save(messageNotice);
                }
        }catch (Exception e){
            logger.error("sendMessageNoticeByDepartment,senderNm:"+senderNm+",deptNm:"+deptNm+",subject:"+subject+",content:"+content+",messageType:"+messageType);
            logger.error("sendMessageNoticeByDepartment--Error:"+e.getMessage());
            lyhtResultBody.setMsg("消息接口异常");
            lyhtResultBody.setFlag(false);
        }
        return lyhtResultBody;
    }



    /**
     * 修改已读状态
     * @param id  消息id
     * @param readFlag  1:已读  0:未读
     * @return
     */
    @Override
    @Transactional
    public LyhtResultBody updateReadFlag(Integer id, Integer readFlag) {

        if(null==id || null==readFlag)  throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);

        MessageNotice messageNotice =  messageNoticeDao.getOne(id);
                    messageNotice.setReadFlag(readFlag);
            messageNoticeDao.save(messageNotice);
        return new LyhtResultBody();
    }


}
