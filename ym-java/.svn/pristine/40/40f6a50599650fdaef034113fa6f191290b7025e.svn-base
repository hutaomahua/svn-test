package com.lyht.publicAnnouncementExcel.service;


import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.publicAnnouncementExcel.common.Ureport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * @author ChengLiangge
 * @time 2020/5/12
 * @description:导出公示表excel
 * */
@Service
public class ExportExcelService {

    @Value("${lyht.ureport.path}")
    private String filepath;
    @Autowired
    private Ureport ureport;
    @Autowired
    private AbmOwnerDao abmOwnerDao;


    /**
     * 搬迁安置人口核定公示表
     * @param response
     * @param nm
     * @param startTime
     * @param endTime
     * @param region
     */
    public void exportRelocation(HttpServletResponse response, String nm,
                                 String startTime,String endTime,String region){
        String templateNameNm="file:relocationOwnerNm.ureport.xml";
        String titleName="托巴水电站"+region+"搬迁安置人口核定公示表";
        String fileName="托巴水电站"+region+"搬迁安置人口核定公示表.xlsx";
        commonExport(response,nm,startTime,endTime,titleName,fileName,templateNameNm);
    }


    /**
     * 生产安置人口核定公示表
     * @param response
     * @param nm
     * @param startTime
     * @param endTime
     * @param region
     */
    public void exportProduce(HttpServletResponse response, String nm,
                              String startTime,String endTime,String region){
        String templateNameNm="file:produceOwnerNm.ureport.xml";
        String titleName="托巴水电站"+region+"生产安置人口核定公示表";
        String fileName="托巴水电站"+region+"生产安置人口核定公示表.xlsx";
        commonExport(response,nm,startTime,endTime,titleName,fileName,templateNameNm);
    }


    /**
     * 移民安置人口核定公示表
     * @param response
     * @param nm
     * @param startTime
     * @param endTime
     * @param region
     */
    public void exportResettlement(HttpServletResponse response, String nm,
                                   String startTime,String endTime,String region){
        String templateNameNm="file:rssettlementOwnerNm.ureport.xml";
        String titleName="托巴水电站"+region+"移民安置人口核定公示表";
        String fileName="托巴水电站"+region+"移民安置人口核定公示表.xlsx";
        commonExport(response,nm,startTime,endTime,titleName,fileName,templateNameNm);
    }


    public void commonExport(HttpServletResponse response, String nm,
                             String startTime,String endTime,String titleName,String fileName,String templateName){
        List<String> nmlist =abmOwnerDao.getOwnerNmsForPopulationAnnouncement(nm);
        if(nmlist.size()>0){
            List<Map> remarkAndUnit=abmOwnerDao.getRemarkAndUnit(nm);
            String remark ="";
            String unit="";
            if(remarkAndUnit.size()>0){
                String strR= String.valueOf(remarkAndUnit.get(0).get(remark));
                String strU= String.valueOf(remarkAndUnit.get(0).get(unit));
                if(!strR.equals("null")){
                    remark=strR;
                }
                if(!strU.equals("null")){
                    unit=strU;
                }
            }
            List<Map> list=abmOwnerDao.getAbmOwnerImplByNms(nmlist);
            if (list.size()>0){
                Map<String, Object> params = new HashMap<>();
                params.put("ownNms", nmlist);
                params.put("titleName", titleName);
                params.put("startTime", startTime.equals("null")? "" : startTime);
                params.put("endTime", endTime.equals("null")? "" : endTime);
                params.put("remark", remark.equals("null")? "" : remark);
                params.put("unit", unit.equals("null")? "" : unit);
                Date date = new Date(); //获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String currentDate = sdf.format(date);
                params.put("currentDate", currentDate);
                try {
                    OutputStream outputStream = null;
                    File file = new File(filepath + "ureport.xlsx");
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    outputStream = new FileOutputStream(file);
                    ureport.createReport(filepath + "ureport.xlsx", templateName, fileName, params, outputStream, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
                }
            }else {
                throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
            }
        }else {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }

    }

    /**
     * 实物指标公示表---个人财产
     * @param response
     * @param nm
     * @param code
     * @param start_time
     * @param end_time
     */
    public void exportPhysicalIndex(HttpServletResponse response,String nm,String code,String start_time,String end_time){
        List<String> nmlist =abmOwnerDao.getOwnerNms(nm);
        if(nmlist.size()>0){
            String remark =abmOwnerDao.getRemark(nm);
            List<Map> list=abmOwnerDao.getAbmOwnerImplByNms(nmlist);
            if (list.size()>0){
                String fileName="";
                List<Map> listForFileName=abmOwnerDao.getRegionsAndNameByNms(nmlist);
                String street= String.valueOf(listForFileName.get(0).get("street"));//乡
                if (street.endsWith("乡")||street.endsWith("镇")){
                   street = street.substring(0,street.length()-1);
                }
                String village= String.valueOf(listForFileName.get(0).get("village"));//村
                if (village.endsWith("村")){
                    village = village.substring(0,village.length()-1);
                }
                String group= String.valueOf(listForFileName.get(0).get("zu"));//组
                if (group.endsWith("组")){
                    group = group.substring(0,group.length()-1);
                }
                if(list.size()>1){//公示中多个户主
                    fileName=street+"乡"+village+"村"+group+"组"+"-个人财产公示表.xlsx";
                }else {//公示中只有一个户主
                    String ownerName= String.valueOf(listForFileName.get(0).get("ownerName"));
                    fileName=street+"乡"+village+"村"+group+"组"+" "+ownerName+"-个人财产公示表.xlsx";
                }
                String templateName="file:physicalIndex.ureport.xml";
                Map<String, Object> params = new HashMap<>();
                params.put("ownNms", nmlist);
                params.put("code", code.equals("null")? "" : code);
                params.put("startTime", start_time.equals("null")? "" : start_time);
                params.put("endTime", end_time.equals("null")? "" : end_time);
                params.put("remark", remark.equals("null")? "" : remark);
                Date date = new Date(); //获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String currentDate = sdf.format(date);
                params.put("currentDate", currentDate);
                try {
                    OutputStream outputStream = null;
                    File file = new File(filepath + "ureport.xlsx");
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    outputStream = new FileOutputStream(file);
                    ureport.createReport(filepath + "ureport.xlsx", templateName, fileName, params, outputStream, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
                }
            }else {
                throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
            }
        }else {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }

    }
    
    /**
     * 实物指标公示表---土地
     * @param response
     * @param nm
     * @param code
     * @param start_time
     * @param end_time
     */
    public void exportPhysicalIndexLand(HttpServletResponse response,String nm,String code,String start_time,String end_time){
        List<String> nmlist =abmOwnerDao.getOwnerNmsLand(nm);
        if(nmlist.size()>0){
            String remark =abmOwnerDao.getRemark(nm);
            List<Map> list=abmOwnerDao.getAbmOwnerImplByNms(nmlist);
            if (list.size()>0){
                String fileName="";
                String titleName="";
                List<Map> listForFileName=abmOwnerDao.getRegionsAndNameByNms(nmlist);
                String street= String.valueOf(listForFileName.get(0).get("street"));//乡
                if (street.endsWith("乡")||street.endsWith("镇")){
                   street = street.substring(0,street.length()-1);
                }
                String village= String.valueOf(listForFileName.get(0).get("village"));//村
                if (village.endsWith("村")){
                    village = village.substring(0,village.length()-1);
                }
                String group= String.valueOf(listForFileName.get(0).get("zu"));//组
                if (group.endsWith("组")){
                    group = group.substring(0,group.length()-1);
                }
                if(list.size()>1){//公示中多个户主
                    fileName=street+"乡"+village+"村"+group+"组"+"-土地公示表.xlsx";
                    titleName=street+"乡"+village+"村"+group+"组"+"-土地公示表";
                }else {//公示中只有一个户主
                    String ownerName= String.valueOf(listForFileName.get(0).get("ownerName"));
                    fileName=street+"乡"+village+"村"+group+"组"+" "+ownerName+"-土地公示表.xlsx";
                    titleName=street+"乡"+village+"村"+group+"组"+" "+ownerName+"-土地公示表";
                }
                String templateName="file:physicalIndexLand.ureport.xml";
                Map<String, Object> params = new HashMap<>();
                params.put("ownNms", nmlist);
                params.put("titleName", titleName);
                params.put("code", code.equals("null")? "" : code);
                params.put("startTime", start_time.equals("null")? "" : start_time);
                params.put("endTime", end_time.equals("null")? "" : end_time);
                params.put("remark", remark.equals("null")? "" : remark);
                Date date = new Date(); //获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String currentDate = sdf.format(date);
                params.put("currentDate", currentDate);
                try {
                    OutputStream outputStream = null;
                    File file = new File(filepath + "ureport.xlsx");
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    outputStream = new FileOutputStream(file);
                    ureport.createReport(filepath + "ureport.xlsx", templateName, fileName, params, outputStream, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
                }
            }else {
                throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
            }
        }else {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }

    }


}
