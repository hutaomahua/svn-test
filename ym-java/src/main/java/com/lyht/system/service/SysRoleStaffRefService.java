package com.lyht.system.service;


import com.lyht.system.bean.RoleStaffRefDetail;
import com.lyht.system.dao.SysRoleStaffRefDao;
import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.system.vo.StaffRoleRefVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.EntityUtils;
import com.lyht.util.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("/sysRoleStaffRefService")
public class SysRoleStaffRefService{
	
	@Autowired  private SysRoleStaffRefDao dao;

	/**
	 * 根据人员查询角色信息
	 * @param staffNm
	 * @return
	 */
	public List<StaffRoleRefVO> getRoleRef(String staffNm){
		return dao.getRoleRef(staffNm);
	}
	


    /** 角色人员关联设置 保存
     * @param roleNm
     * @param staffNms
     * @return
     */
    @Transactional
    public int setRoleStaffRef(String roleNm , String staffNms){
        String sql = "";

        int result = 0;
        removeRoleStaffRefByRole(roleNm);//删除角色人员授权
        if(staffNms==null){return result;}
        String [] staffNmArr  = staffNms.split(",");
        for(String staffNm : staffNmArr){
            if(CommonUtil.getLength(staffNm) > 0 ){
                SysRoleStaffRef sysRolePartitionRef = new SysRoleStaffRef();
                sysRolePartitionRef.setCode(Randomizer.generCode(10));
                sysRolePartitionRef.setStaffNm(staffNm);
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
    public int removeRoleStaffRef(String staffNm){
        //String sql = "delete from sys_role_staff_ref where staff_nm = ? ";
        return dao.deleteByStaffNm(staffNm);
    }

    /** 删除角色授权人员关联
     * @param roleNm
     * @return
     */
    @Transactional
    public int removeRoleStaffRefByRole(String roleNm){
       // String sql = "delete from sys_role_staff_ref where role_nm = ? ";
        return dao.deleteByRoleNm(roleNm);
    }

    /** 查询人员授权
     * @param roleNm
     * @return
     */
    public List<RoleStaffRefDetail> findRoleStaffRefByRoleNm(String roleNm){
//        String sql = "select id as \"id\",code as \"code\",role_nm as \"role_nm\",staff_nm as \"staff_nm\",flag as \"flag\" from sys_role_staff_ref where role_nm = ? ";
//        return dao.findAllByParams(sql,new Object[]{roleNm});
        return EntityUtils.toEntityList(dao.findRoleStaffRefByRoleNm(roleNm),RoleStaffRefDetail.class);
    }

}
 