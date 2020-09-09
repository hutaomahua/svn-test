package com.lyht.business.pub.entity;

import javax.persistence.*;

/**
 *作者： liuamang
 *脚本日期:2019年2月19日 23:22:57
 *说明:  字典分类
*/
@Entity
@Table(name = "pub_dict_name")
public class PubDictName implements java.io.Serializable{

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
    *编码
    */
    private String code;
    /**
    *名称
    */
    private String name;
    /**
    *备注
    */
    private String memo;
    /**
    *状态
    */
    private Integer flag;
    
    /** default constructor */
    public PubDictName() {
    
    }
    /** full constructor */
    /*
    public PubDictName(
                  Integer id ,
                  String nm ,
                  String code ,
                  String name ,
                  String memo ,
                  Integer flag 
                  ) {
        super();
        this.id = id;
        this.nm = nm;
        this.code = code;
        this.name = name;
        this.memo = memo;
        this.flag = flag;
    }*/
 
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

    /**
      *编码
    */
    @Column(name = "code" , nullable = false )
    public  String getCode() {
        return this.code;
    }
    public void setCode( String code) {
        this.code = code;
    }        

    /**
      *名称
    */
    @Column(name = "name" , nullable = false )
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    /**
      *备注
    */
    @Column(name = "memo"  )
    public  String getMemo() {
        return this.memo;
    }
    public void setMemo( String memo) {
        this.memo = memo;
    }        

    /**
      *状态
    */
    @Column(name = "flag" , nullable = false )
    public  Integer getFlag() {
        return this.flag;
    }
    public void setFlag( Integer flag) {
        this.flag = flag;
    }        
    
    
}