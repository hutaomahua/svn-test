package com.lyht.system.service;


import com.lyht.system.dao.SysRoleMenuRefDao;
import com.lyht.system.pojo.SysRoleMenuRef;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月18日 15:56:01
  * 说明:  系统角色
  */
@Service("/sysRoleMenuRefService")
public class SysRoleMenuRefService{
	
	@Autowired  private SysRoleMenuRefDao dao;
    @Autowired private SysMenuService sysMenuService;

    /**
     * 角色菜单授权
     * @param roleNm
     * @param menuNms
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Transactional(readOnly = false)
    public int setRoleMenuRef(String roleNm , String menuNms){
        int result = 0;
        removeRoleSysMenuRef(roleNm);//删除角色菜单授权
        if(menuNms==null){ return result;}
        String[] menuNmArr = menuNms.split(",");
     //   List<SysMenuDetail> mapList =  sysMenuService.findListTop();

        List<String> stringList = dao.findSuperNm(menuNmArr);
        for(String s : menuNmArr){
            stringList.add(s);
        }
        for(String menuNm : stringList){
            boolean pd = true;
//            for(SysMenuDetail mp :mapList){
//                if(mp.getNm().equals(menuNm) ){
//                    pd = false;
//                }
//            }
            if(pd){
                if(CommonUtil.getLength(menuNm) > 0 ){
                    SysRoleMenuRef sysRolePartitionRef = new SysRoleMenuRef();
                    sysRolePartitionRef.setCode(Randomizer.generCode(10));
                    sysRolePartitionRef.setMenuNm(menuNm);
                    sysRolePartitionRef.setRoleNm(roleNm);
                    sysRolePartitionRef.setFlag(0);
                    dao.save(sysRolePartitionRef);
                }
            }
        }
        return result;
    }

    /** 查询菜单授权
     * @param roleNm
     * @return
     */
    public List<SysRoleMenuRef> findRoleMenuRefByRoleNm(String roleNm){
       // String sql = "select * from sys_role_menu_ref where role_nm = ? ";
        return  dao.findByRoleNm(roleNm);// EntityUtils.toEntityList(dao.findAllByParams(sql,new Object[]{roleNm}),SysRoleMenuRef.class) ;
    }
    /** 删除角色授权人员关联
     * @param roleNm
     * @return
     */
    @Transactional(readOnly = false)
    public int removeRoleSysMenuRef(String roleNm){
//        String sql = "delete from sys_role_menu_ref where role_nm = ? ";
//        return dao.exectueSQL(sql, new Object[]{roleNm});
        return dao.deleteByRoleNm(roleNm);
    }
}
 