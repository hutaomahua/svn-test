package com.lyht.business.pub.entity;

import javax.persistence.*;

/**
 * @author HuangAn
 * @date 2019/9/20 13:58
 */
@Entity
@Table(name = "pub_dict_name")
public class PubPictureInfo  implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private int id;
    private String nm;
    private String name;
    private String url;
    private String imgType;
    private  String tabNm;
    private String sort;
    private String uploadNm;
    private  String uploadTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "nm" , nullable = false )
    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    @Column(name = "name" , nullable = false )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "url" , nullable = false )
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Column(name = "imgType" , nullable = false )
    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
    @Column(name = "tab_nm" , nullable = false )
    public String getTabNm() {
        return tabNm;
    }

    public void setTabNm(String tabNm) {
        this.tabNm = tabNm;
    }
    @Column(name = "sort" , nullable = false )
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    @Column(name = "upload_nm" , nullable = false )
    public String getUploadNm() {
        return uploadNm;
    }

    public void setUploadNm(String uploadNm) {
        this.uploadNm = uploadNm;
    }
    @Column(name = "upload_time" , nullable = false )
    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
