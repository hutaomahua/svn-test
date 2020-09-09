package com.lyht.business.land.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.land.bean.LandApplyDetail;
import com.lyht.business.land.entity.LandApply;
import com.lyht.business.land.service.LandApplyService;
import com.lyht.util.ExportExcelWrapper;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/10/15 11:30
 */
@Api(value = "/land/apply", tags = "用地报批相关api")
@RestController
@RequestMapping("/land/apply")
public class LandApplyController {
    private static Logger logger = Logger.getLogger(LandApplyController.class);
    @Autowired
    private LandApplyService landApplyService;

    /**
     * 保存
     * @param landApply
     * @return
     */
    @ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
    @PostMapping("/save")
    public LyhtResultBody<LandApply> save(LandApply landApply) {
        if(landApply.getId()==null){
            landApply.setNm(Randomizer.generCode(10));
        }
        landApplyService.saveEntity(landApply);
        return new LyhtResultBody<>();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "按id删除", notes = "删除")
    @GetMapping("/delete")
    public LyhtResultBody<String> delete(String ids) {
        return new LyhtResultBody<>(landApplyService.delete(ids));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
    @GetMapping("/batch")
    public LyhtResultBody<String> batchDel(String ids) {
        return new LyhtResultBody<>(landApplyService.delete(ids));
    }

    /**
     * 分页
     * @param lyhtPageVO
     * @param landApplyDetail
     * @return
     */
    @ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
    @PostMapping("/page")
    public LyhtResultBody<List<LandApplyDetail>> page(LyhtPageVO lyhtPageVO, LandApply landApplyDetail) {
        return landApplyService.page(lyhtPageVO, landApplyDetail);
    }

    /**
     * 模糊查询
     *
     * @param landApply
     * @return
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "模糊查询", notes = "模糊查询")
    @PostMapping("/list")
    public LyhtResultBody<List<LandApply>> list(LandApply landApply) {
        return landApplyService.list(landApply);
    }


    /**
     * 永久用地导出
     * 导出Excel
     * @param landApplyDetail
     * @return
     */
    @ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
    @SuppressWarnings("rawtypes")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, LandApply landApplyDetail) {
        List<Map> list = landApplyService.lists(landApplyDetail);
        String title=null;

        if(landApplyDetail.getApplyType().equals("perpetual")){
            title = "永久用地报批";

        }
        if(landApplyDetail.getApplyType().equals("perforest")){
            title = "永久林地报批";
        }
        String fileName = title;
//		String[] headers = {"序号", "一级分类", "二级分类",  "三级分类", "进展情况及存在问题","计划完成时间", "实际完成时间","责任人","报批进度","行政区","状态","下一步计划建议","备注"};
//		String[] columnNames = { "xuhao","yjfl", "ejfl", "sjfl", "problem","p_end_date", "r_end_date","oper_staff_nm","jindu","diming","flag","next_plan","remark"};
        String[] headers = {"序号", "一级分类", "二级分类",  "三级分类", "进展情况及存在问题","计划完成时间", "实际完成时间","责任人","报批进度"};
        String[] columnNames = { "xuhao","yjfl", "ejfl", "sjfl", "problem","p_end_date", "r_end_date","oper_staff_nm","jindu"};
        try {
            ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
            exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
                    ExportExcelWrapper.EXCEl_FILE_2007);
        } catch (Exception e) {
            logger.error("excel导出失败：", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
    }
    /**
     * 临时用地导出
     * 导出Excel
     *
     * @param landApplyDetail
     * @return
     */
    @ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
    @SuppressWarnings("rawtypes")
    @GetMapping("/temporary/export")
    public void temporaryexportExcel(HttpServletResponse response, LandApply landApplyDetail) {
        List<Map> list = landApplyService.lists(landApplyDetail);
        String title=null;
        if(landApplyDetail.getApplyType().equals("2")){
            title = "临时用地报批";
        }
        if(landApplyDetail.getApplyType().equals("temforest")){
            title = "临时林地报批";
        }
        String fileName = title;
        String[] headers = {"序号", "地块编号","归属地批次名称","一级分类", "二级分类", "进展情况及存在问题",  "计划完成时间", "实际完成时间","责任人","报批进度"};
        String[] columnNames = { "xuhao","dikuai","batch_name","yjfl", "ejfl", "problem", "p_end_date", "r_end_date", "oper_staff_nm","jindu"};
        try {
            ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
            exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
                    ExportExcelWrapper.EXCEl_FILE_2007);
        } catch (Exception e) {
            logger.error("excel导出失败：", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
    }

//    /**
//     * 导入Excel数据
//     *
//     * @param multipartFile
//     */
//    @ApiOperation(value = "按Excel对应模板导入数据", notes = "永久类型Excel导入")
//    @PostMapping("/importPerpetual")
//    public LyhtResultBody<List<LandApply>> importPerpetualExcel(@RequestParam("file") MultipartFile multipartFile, @RequestParam("applyType")String applyType) throws IOException {
//        List<LandApply> result =null;
//        List<LandApply> list = ImportExcelUtil.doImportExcelPerpetual(multipartFile.getInputStream());
//        try {
//            result =landApplyService.importPerpetualExcel(list,applyType);
//        } catch (Exception e) {
//            logger.error("excel导入失败：", e);
//            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
//        }
//        return new LyhtResultBody<>(result);
//    }

//    /**
//     * 导入Excel数据
//     * 临时
//     * @param multipartFile
//     */
//    @ApiOperation(value = "按Excel对应模板导入数据", notes = "临时类型导入Excel导入")
//    @PostMapping("/importTemporary")
//    public LyhtResultBody<List<LandApply>> importTemporaryExcel(@RequestParam("file") MultipartFile multipartFile, @RequestParam("applyType")String applyType) throws IOException {
//        List<LandApply> result =null;
//        List<LandApply> list = ImportExcelUtil.doimportTemporaryExcel(multipartFile.getInputStream());
//        try {
//
//            result =landApplyService.importTemporaryExcel(list,applyType);
//        } catch (Exception e) {
//            logger.error("excel导入失败：", e);
//            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
//        }
//
//        return new LyhtResultBody<>(result);
//
//    }
}
