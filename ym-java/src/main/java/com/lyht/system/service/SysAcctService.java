package com.lyht.system.service;


import com.lyht.system.dao.SysAcctDao;
import com.lyht.system.pojo.SysAcct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月19日 10:06:00
  * 说明:  账户信息
  */
@Service("/sysAcctService")
public class SysAcctService {
	
	@Autowired  private SysAcctDao dao;
	
	public SysAcct findByName(String name){
		return dao.findByName(name);
	}
	public SysAcct findByStaffNm(String staffNm){
		return dao.findByStaffNm(staffNm);
	}
	public void save(SysAcct sysAcct){
		dao.save(sysAcct);
	}
	public void deleteEntityById(Integer id){
		dao.deleteById(id);
	}

//	public SysAcct loginfind(String name) throws ParseException {
//		List<Object> objValue= new ArrayList<Object>();
//		SysAcct sysAcct =new SysAcct();
//		StringBuffer sql = new StringBuffer("select ID as \"id\",STAFF_NM as \"staffNm\",NM as \"nm\",NAME as \"name\",PWD as \"pwd\",FLAG as \"flag\",YXQ as \"yxq\",SYSFLAG as \"sysflag\" from sys_acct  " +
//				" where 1=1 ");
//		sql.append(" and (NAME = ? or STAFF_NM = (select nm from sys_staff where staff_link = ?))");
//		objValue.add(name);
//		objValue.add(name);
//		Map mapList= dao.findAllByParams(sql.toString(),objValue.toArray()).get(0);
//		if(mapList==null){
//			return null;
//		}
//		sysAcct.setId(Integer.parseInt(mapList.get("id")+""));
//		sysAcct.setNm(mapList.get("nm")+"");
//		sysAcct.setName(mapList.get("name")+"");
//		sysAcct.setStaffNm(mapList.get("staffNm")+"");
//		sysAcct.setSysflag(Integer.parseInt(mapList.get("sysflag")+""));
//		sysAcct.setFlag(Integer.parseInt(mapList.get("flag")+""));
//		String yxq = mapList.get("yxq")+"";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		java.sql.Date date=new java.sql.Date(sdf.parse(yxq).getTime());
//		sysAcct.setYxq(date);
//		sysAcct.setPwd(mapList.get("pwd")+"");
//		return sysAcct;
//	}
}
 