package com.lyht.business.rests.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.rests.bean.LetterManagerDetail;
import com.lyht.business.rests.pojo.LetterManagerEntity;
import com.lyht.business.rests.service.LetterManageService;
import com.lyht.util.ExportExcelWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 信访管理
 * 2019/8/12
 *
 * @author jms
 */
@RestController
@Api(value = "/letter/manage", tags = "信访管理api")
@RequestMapping("/letter/manage")
public class LetterManageController {
    private static Logger logger = Logger.getLogger(LetterManageController.class);

    @Autowired
    private LetterManageService letterManageService;

    /**
     * 保存
     *
     * @return
     */
    @ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
    @PostMapping(value = "/save")
    public LyhtResultBody<LetterManagerEntity> save(LetterManagerEntity letterManagerEntity) {
  return letterManageService.save(letterManagerEntity);
    }

    /**
     *  分页查询
     *  @return
     */
    @ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
    @PostMapping(value = "/page")
    public LyhtResultBody<List<LetterManagerDetail>> page(LyhtPageVO lyhtPageVO, LetterManagerDetail letterManagerDetail) {
  return letterManageService.page(lyhtPageVO,letterManagerDetail);
    }

    @ApiOperation(value = "按id删除", notes = "删除")
    @GetMapping("/delete")
    public LyhtResultBody<Integer> delete(Integer id) {
  return letterManageService.delete(id);
    }
    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
    @GetMapping("/batch")
    public LyhtResultBody<String> batchDel(String ids) {
  return letterManageService.batchDel(ids);
    }
    /**
     * 永久用地导出
     * 导出Excel
     *
     * @param letterManagerDetail
     * @return
     */
    @ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
    @SuppressWarnings("rawtypes")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, LetterManagerDetail letterManagerDetail) {
        List<Map> list = letterManageService.list(letterManagerDetail);
        System.out.println("list:"+list);
        String title="信访管理";
        String fileName = title;
        String[] headers = { "信访事项", "来访人",  "来访人性别", "来访人职位", "来访人号码", "来访人单位或地址","所属行政区","建议内容","接待人/登记人","来访时间"};
        String[] columnNames = { "matter", "name", "sex", "job", "phone", "diming","content","jindu","reception_name","create_time"};
        try {
            ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
            exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
                    ExportExcelWrapper.EXCEl_FILE_2007);
        } catch (Exception e) {
            logger.error("excel导出失败：", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
    }
}
