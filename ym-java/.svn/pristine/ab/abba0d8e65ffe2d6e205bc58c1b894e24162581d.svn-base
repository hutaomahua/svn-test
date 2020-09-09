package com.lyht.system.pojo;

import java.io.Serializable;

import javax.persistence.*;

import com.alibaba.fastjson.JSON;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * 创建人： Yanxh 
 * 脚本日期:2020年1月7日
 * 说明:  系统日志
 */
@Entity
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "sys_log")
public class SysLog implements  Serializable{

    private static final long serialVersionUID = 1L;
    /**
    *主键
    */
	@ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    private Integer id;
    /**
    *内码
    */
	@ApiModelProperty(value = "内码")
    @Column(name = "nm" )
    private String nm;
    /**
    *操作时间
    */
	@ApiModelProperty(value = "操作时间")
    @Column(name = "logtime"  )
    private String logtime;
    /**
    *操作员
    */
	@ApiModelProperty(value = "操作员")
    @Column(name = "name"  )
    private String name;
    /**
    *操作员内码
    */
	@ApiModelProperty(value = "操作员内码")
    @Column(name = "opernm"  )
    private String opernm;
    /**
    *模块唯一标识
    */
	@ApiModelProperty(value = "模块唯一标识")
    @Column(name = "menuflag"  )
    private String menuflag;
    /**
    *操作类型
    */
	@ApiModelProperty(value = "操作类型")
    @Column(name = "dictnm_opttype"  )
    private String dictnmOpttype;
    /**
    *旧数据
    */
	@ApiModelProperty(value = "旧数据")
    @Column(name = "olddata"  )
    private String olddata;
    /**
    *新数据
    */
	@ApiModelProperty(value = "新数据")
    @Column(name = "newdata"  )
    private String newdata;
    /**
    *备注（预留字段）
    */
	@ApiModelProperty(value = "备注（预留字段）")
    @Column(name = "memo"  )
    private String memo;
    /**
    *操作状态（预留字段）
    */
	@ApiModelProperty(value = "操作状态（预留字段）")
    @Column(name = "flag"  )
    private Integer flag;
    /**
    *系统标识（预留字段）
    */
	@ApiModelProperty(value = "系统标识（预留字段）")
    @Column(name = "sysflag"  )
    private Integer sysflag;
    
    /** default constructor */
    public SysLog() {
    
    }
    /** full constructor */
    
    public SysLog(
                  Integer id ,
                  String nm ,
                  String logtime ,
                  String name ,
                  String opernm ,
                  String menuflag ,
                  String dictnmOpttype ,
                  String olddata ,
                  String newdata ,
                  String memo ,
                  Integer flag ,
                  Integer sysflag 
                  ) {
        super();
        this.id = id;
        this.nm = nm;
        this.logtime = logtime;
        this.name = name;
        this.opernm = opernm;
        this.menuflag = menuflag;
        this.dictnmOpttype = dictnmOpttype;
        this.olddata = olddata;
        this.newdata = newdata;
        this.memo = memo;
        this.flag = flag;
        this.sysflag = sysflag;
    }
 
    //属性get/set定义       		

    /**
      *主键
    */
    public  Integer getId() {
        return this.id;
    }
    public void setId( Integer id) {
        this.id = id;
    }        

    /**
      *内码
    */
    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }        

    /**
      *操作时间
    */
    public  String getLogtime() {
        return this.logtime;
    }
    public void setLogtime( String logtime) {
        this.logtime = logtime;
    }        

    /**
      *操作员
    */
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    /**
      *操作员内码
    */
    public  String getOpernm() {
        return this.opernm;
    }
    public void setOpernm( String opernm) {
        this.opernm = opernm;
    }        

    /**
      *模块唯一标识
    */
    public  String getMenuflag() {
        return this.menuflag;
    }
    public void setMenuflag( String menuflag) {
        this.menuflag = menuflag;
    }        

    /**
      *操作类型
    */
    public  String getDictnmOpttype() {
        return this.dictnmOpttype;
    }
    public void setDictnmOpttype( String dictnmOpttype) {
        this.dictnmOpttype = dictnmOpttype;
    }        

    /**
      *旧数据
    */
    public  String getOlddata() {
        return this.olddata;
    }
    public void setOlddata( String olddata) {
        this.olddata = olddata;
    }        

    /**
      *新数据
    */
    public  String getNewdata() {
        return this.newdata;
    }
    public void setNewdata( String newdata) {
        this.newdata = newdata;
    }        

    /**
      *备注
    */
    public  String getMemo() {
        return this.memo;
    }
    public void setMemo( String memo) {
        this.memo = memo;
    }        

    /**
      *操作状态
    */
    public  Integer getFlag() {
        return this.flag;
    }
    public void setFlag( Integer flag) {
        this.flag = flag;
    }        

    /**
      *系统标识
    */
    public  Integer getSysflag() {
        return this.sysflag;
    }
    public void setSysflag( Integer sysflag) {
        this.sysflag = sysflag;
    }        
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    
}