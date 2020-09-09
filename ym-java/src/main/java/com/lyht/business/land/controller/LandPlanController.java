package com.lyht.business.land.controller;


import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.land.bean.LandApplyDetail;
import com.lyht.business.land.entity.LandPlan;
import com.lyht.business.land.service.LandPlanService;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(value = "/land/plan", tags = "用地报批报批进度相关api")
@RestController
@RequestMapping("/land/plan")
public class LandPlanController {
    private static Logger logger = Logger.getLogger(LandPlanController.class);
    @Autowired
    private LandPlanService landPlanService;

    @Autowired
    private PubFilesService pubFilesService;


    /**
     * 保存
     * @param
     * @return
     */

    @ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
    @PostMapping("/save")
    public LyhtResultBody<LandPlan> save(LandPlan landPlan,HttpServletRequest request,String tableName, @RequestParam(required = false, value = "files") MultipartFile[] files) {

        LandPlan result = null;
        try {
            // 内码赋值
            String nm = landPlan.getNm();
            if (StringUtils.isBlank(nm)) {
                landPlan.setNm(Randomizer.generCode(10));
            }
            // 保存
            landPlan.setCreateTime(new Date());
            LandPlan save = landPlanService.saveEntity(landPlan);
            if(save!=null){
                PubFilesEntity pubFilesEntity=new PubFilesEntity();
                pubFilesEntity.setTableName(tableName);
                pubFilesEntity.setTablePkColumn(save.getNm());
                LyhtResultBody<List<PubFilesEntity>> uploads = pubFilesService.uploads(request, files, pubFilesEntity);
            }
        } catch (Exception e) {
            logger.error("=====LandPlanService=====Method=save=====Params:" + landPlan + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }

        return new LyhtResultBody<>(result);
    }
    /**
     * 查询
     * @param
     * @return
     */

    @ApiOperation(value = "查询 通过nm查询，", notes = "分页查询")
    @PostMapping("/page")
    public LyhtResultBody<List<LandApplyDetail>> list(LyhtPageVO lyhtPageVO, LandApplyDetail landApplyDetail) {
        return landPlanService.list(lyhtPageVO,landApplyDetail);
    }

    /**
     * 查询
     * @param
     * @return
     */

    @ApiOperation(value = "  通过nm查询查看是有过次步骤", notes = "条件查询")
    @PostMapping("/list")
    public LyhtResultBody<Integer> byLandNm(LandApplyDetail landApplyDetail) {
        return landPlanService.byLandNm(landApplyDetail);
    }
}
