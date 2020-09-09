package com.lyht.business.change.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * @author HuangAn
 * @date 2019/12/5 14:53
 */
public interface DesignContentVO {
      Integer getId();

      String getNm();

      String getContentItems();

      String getChangeType();
      String getChangeRequestType();
      String getSort();
}
