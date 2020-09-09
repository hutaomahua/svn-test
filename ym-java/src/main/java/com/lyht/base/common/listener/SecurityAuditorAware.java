package com.lyht.base.common.listener;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lyht.util.SystemUtil;

/**
 * 审计
 * @author hxl
 *
 */
@Component
public class SecurityAuditorAware implements AuditorAware<String> {

	//获取登录人内码
	@Override
	public Optional<String> getCurrentAuditor() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String loginStaffNm = SystemUtil.getLoginStaffNm(request);
		if (StringUtils.isNotBlank(loginStaffNm)) {
			return Optional.of(loginStaffNm);
		} else {
			return Optional.ofNullable(null);
		}
	}

}
