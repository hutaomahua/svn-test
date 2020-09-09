package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 *作者： liuamang
 *脚本日期:2019年2月19日 9:50:15
 *说明:  人员信息
*/
@Entity
@Table(name = "sys_staff")
@ApiModel(description = "人员表")
public class SysStaff implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**
    *主键ID
    */
    private Integer id;
    /**
    *唯一编号
    */
    private String nm;
    /**
    *人员类型
    */
    @ApiModelProperty(value = "人员类型")
    private Integer staffType;
    /**
    *人员编码
    */
    @ApiModelProperty(value = "人员编码")
    private String staffCode;
    /**
    *姓名
    */
    @ApiModelProperty(value = "人员姓名")
    private String staffName;
    /**
    *性别
    */
    @ApiModelProperty(value = "性别")
    private String staffSex;
    /**
    *民族
    */
    @ApiModelProperty(value = "民族")
    private String staffEthnic;
    /**
    *生日
    */
    @ApiModelProperty(value = "生日")
    private String staffBirthday;
    /**
    *联系电话
    */
    @ApiModelProperty(value = "联系电话")
    private String staffLink;
    /**
    *籍贯
    */
    @ApiModelProperty(value = "籍贯")
    private String staffOrigin;
    /**
     *文化程度
     */
    @ApiModelProperty(value = "文化程度")
    private String staffEducation;
    /**
     *职务
     */
    @ApiModelProperty(value = "职务")
    private String staffPosition;
    /**
    *所属单位
    */
    @ApiModelProperty(value = "所属单位")
    private String deptCode;
    /**
    *排序号
    */
    @ApiModelProperty(value = "排序号")
    private Integer sortNum;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
    *状态
    */
    private Integer state;

    private String uuid;
    
    /** default constructor */
    public SysStaff() {
    
    }
    /** full constructor */
    /*
    public SysStaff(
                  Integer id ,
                  String nm ,
                  Integer staffType ,
                  String staffCode ,
                  String staffName ,
                  String staffSex ,
                  String staffEthnic ,
                  String staffBirthday ,
                  String staffLink ,
                  String staffOrigin ,
                  String deptCode ,
                  Integer sortNum ,
                  String remark ,
                  Integer state 
                  ) {
        super();
        this.id = id;
        this.nm = nm;
        this.staffType = staffType;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.staffSex = staffSex;
        this.staffEthnic = staffEthnic;
        this.staffBirthday = staffBirthday;
        this.staffLink = staffLink;
        this.staffOrigin = staffOrigin;
        this.deptCode = deptCode;
        this.sortNum = sortNum;
        this.remark = remark;
        this.state = state;
    }*/
 
    //属性get/set定义       		

    /**
      *主键ID
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
      *唯一编号
    */
    @Column(name = "nm"  )
    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }        

    /**
      *人员类型
    */
    @Column(name = "staff_type" , nullable = false )
    public  Integer getStaffType() {
        return this.staffType;
    }
    public void setStaffType( Integer staffType) {
        this.staffType = staffType;
    }        

    /**
      *人员编码
    */
    @Column(name = "staff_code"  )
    public  String getStaffCode() {
        return this.staffCode;
    }
    public void setStaffCode( String staffCode) {
        this.staffCode = staffCode;
    }        

    /**
      *姓名
    */
    @Column(name = "staff_name" , nullable = false )
    public  String getStaffName() {
        return this.staffName;
    }
    public void setStaffName( String staffName) {
        this.staffName = staffName;
    }        

    /**
      *性别
    */
    @Column(name = "staff_sex" , nullable = false )
    public  String getStaffSex() {
        return this.staffSex;
    }
    public void setStaffSex( String staffSex) {
        this.staffSex = staffSex;
    }        

    /**
      *民族
    */
    @Column(name = "staff_ethnic" , nullable = false )
    public  String getStaffEthnic() {
        return this.staffEthnic;
    }
    public void setStaffEthnic( String staffEthnic) {
        this.staffEthnic = staffEthnic;
    }        

    /**
      *生日
    */
    @Column(name = "staff_birthday" , nullable = false )
    public  String getStaffBirthday() {
        return this.staffBirthday;
    }
    public void setStaffBirthday( String staffBirthday) {
        this.staffBirthday = staffBirthday;
    }        

    /**
      *联系电话
    */
    @Column(name = "staff_link" , nullable = false )
    public  String getStaffLink() {
        return this.staffLink;
    }
    public void setStaffLink( String staffLink) {
        this.staffLink = staffLink;
    }        

    /**
      *籍贯
    */
    @Column(name = "staff_origin"  )
    public  String getStaffOrigin() {
        return this.staffOrigin;
    }
    public void setStaffOrigin( String staffOrigin) {
        this.staffOrigin = staffOrigin;
    }

    /**
     *文化程度
     */
    @Column(name = "staff_education")
    public String getStaffEducation() {
        return staffEducation;
    }

    public void setStaffEducation(String staffEducation) {
        this.staffEducation = staffEducation;
    }
    /**
     *职务
     */
    @Column(name = "staff_position")
    public String getStaffPosition() {
        return staffPosition;
    }

    public void setStaffPosition(String staffPosition) {
        this.staffPosition = staffPosition;
    }

    /**
      *所属单位
    */
    @Column(name = "dept_code"  )
    public  String getDeptCode() {
        return this.deptCode;
    }
    public void setDeptCode( String deptCode) {
        this.deptCode = deptCode;
    }        

    /**
      *排序号
    */
    @Column(name = "sort_num"  )
    public  Integer getSortNum() {
        return this.sortNum;
    }
    public void setSortNum( Integer sortNum) {
        this.sortNum = sortNum;
    }        

    /**
      *备注
    */
    @Column(name = "remark"  )
    public  String getRemark() {
        return this.remark;
    }
    public void setRemark( String remark) {
        this.remark = remark;
    }        

    /**
      *状态
    */
    @Column(name = "state"  )
    public  Integer getState() {
        return this.state;
    }
    public void setState( Integer state) {
        this.state = state;
    }

    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}