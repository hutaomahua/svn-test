package com.lyht.system.pojo;

import javax.persistence.*;

/**
 *作者： liuamang
 *脚本日期:2019年2月19日 9:46:58
 *说明:  单位部门
*/
@Entity
@Table(name = "sys_dept")
public class SysDept implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**
    *主键
    */
    private Integer id;
    /**
    *内码
    */
    private String nm;
    /**
    *短编码
    */
    private String scode;
    /**
    *全编码
    */
    private String fcode;
    /**
    * 上级ID
    */
    private Integer superId;
   
    /**
    *单位部门名称
    */
    private String name;
    /**
     *组织机构类型
     */
    private String deptType;
    /**
     *备注
     */
     private String remark;
    /**
    *状态
    */
    private Integer flag;
    
    /** default constructor */
    public SysDept() {
    
    }
    
    //属性get/set定义       		

    /**
      *主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    public  Integer getId() {
        return this.id;
    }
    public void setId( Integer id) {
        this.id = id;
    }        

    /**
      *内码
    */
    @Column(name = "nm" , nullable = false )
    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }        

    @Column(name = "scode"  )
    public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}
	
	@Column(name = "fcode"  )
	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
    @Column(name = "dept_type"  )
    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    @Column(name = "super_id"  )
	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}
	
	@Column(name = "remark" )
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
      *单位部门名称
    */
    @Column(name = "name" , nullable = false )
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    /**
      *状态
    */
    @Column(name = "flag"  )
    public  Integer getFlag() {
        return this.flag;
    }
    public void setFlag( Integer flag) {
        this.flag = flag;
    }        
    
    
}