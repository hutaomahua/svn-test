package com.lyht.business.process.service;

import com.lyht.business.process.dto.response.ProcessResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ProcessAuthorizationService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${iwind.process.appid}")
	private String appId;// 应用ID
	@Value("${iwind.process.secret}")
	private String secret;// 密钥
	@Value("${iwind.process.gettoken.url}")
	private String getTokenUrl;/// 获取token的接口地址
	@Value("${iwind.process.project.id}")
	private String projectId;// 项目ID
	@Value("${iwind.process.department.id}")
	private String departmentId;// 部门ID

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public String getToken() {
		Map<String, Object> urlParam = new HashMap<String, Object>();
		urlParam.put("appId", appId);
		urlParam.put("secret", secret);
		String token = null;
		try {
			ProcessResponse syncDataResponse = restTemplate.getForObject(getTokenUrl, ProcessResponse.class, urlParam);
			if (syncDataResponse != null) {
				token = syncDataResponse.getData();
			}
		} catch (Exception e) {
			log.error("=====ProcessAuthorizationService=====getToken=====获取token失败", e);
		}
		return token;
	}

}
