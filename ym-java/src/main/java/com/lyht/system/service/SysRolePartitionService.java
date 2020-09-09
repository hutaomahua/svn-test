package com.lyht.system.service;


import com.alibaba.fastjson.JSON;
import com.lyht.system.bean.RolePartitionRefDetail;
import com.lyht.system.dao.SysRolePartitionRefDao;
import com.lyht.system.dao.SysRoleStaffRefDao;
import com.lyht.system.pojo.SysRolePartitionRef;
import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("/sysRolePartitionService")
public class SysRolePartitionService{
	
	@Autowired  private SysRolePartitionRefDao dao;

    @Autowired  private SysRoleStaffRefDao sysRoleStaffRefDao;
    /** 角色区域关联设置 保存
     * @param roleNm
     * @param partitionNms
     * @return
     */
    @Transactional
    public int setRolePartitionRef(String roleNm , String partitionNms){
        String sql = "";

        int result = 0;
        removeRolePartitionRefByRole(roleNm);//删除角色人员授权
        if(partitionNms==null){return result;}
        String [] partitionNmArr  = partitionNms.split(",");
        for(String partitionNm : partitionNmArr){
            if(CommonUtil.getLength(partitionNm) > 0 ){
                SysRolePartitionRef sysRolePartitionRef = new SysRolePartitionRef();
                sysRolePartitionRef.setCode(Randomizer.generCode(10));
                sysRolePartitionRef.setPartitionNm(partitionNm);
                sysRolePartitionRef.setRoleNm(roleNm);
                sysRolePartitionRef.setFlag(0);
                dao.save(sysRolePartitionRef);
            }
        }
        return result;
    }

    /** 删除角色授权人员关联
     * @param staffNm
     * @return
     */
    @Transactional
    public int removeRolePartitionRef(String staffNm){
//        String sql = "delete from sys_role_partition_ref where partition_nm = ? ";
//        return dao.exectueSQL(sql, new Object[]{staffNm});
        return dao.deleteByPartitionNm(staffNm);
    }

    /** 删除角色授权人员关联
     * @param roleNm
     * @return
     */
    @Transactional
    public int removeRolePartitionRefByRole(String roleNm){
//        String sql = "delete from sys_role_partition_ref where role_nm = ? ";
//        return dao.exectueSQL(sql, new Object[]{roleNm});
        return dao.deleteByRoleNm(roleNm);
    }

    /** 查询人员授权
     * @param roleNm
     * @return
     */
    public List<RolePartitionRefDetail> findRolePartitionRefByRoleNm(String roleNm){
//        String sql = "select id as \"id\",code as \"code\",role_nm as \"role_nm\",partition_nm as \"partition_nm\",flag as \"flag\" from sys_role_partition_ref where role_nm = ? ";
//        return dao.findAllByParams(sql,new Object[]{roleNm});
        return dao.findRolePartitionRefByRoleNm(roleNm);
    }

    /** 查询人员区域授权
     * @param staffnm
     * @return
     */
    public List<SysRolePartitionRef> findRolePartitionRefByRequest(String staffnm){
        //SQL 语句
        List<Object> objValue= new ArrayList<Object>();
        List<SysRoleStaffRef> sysRoleStaffRefs =  sysRoleStaffRefDao.findByStaffNm(staffnm);
        StringBuffer  stringBuffer = new StringBuffer();
        for (SysRoleStaffRef sysRoleStaffRef :sysRoleStaffRefs){
            stringBuffer.append("'"+sysRoleStaffRef.getRoleNm()+"',");
        }
        stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
        List<Map> mapList = dao.findRolePartitionRefByRequest(stringBuffer.toString());
        String jsonString = JSON.toJSONString(mapList);
        List<SysRolePartitionRef> resultList = JSON.parseArray(jsonString, SysRolePartitionRef.class);
        return resultList;
    }

}
 