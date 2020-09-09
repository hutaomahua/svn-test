package com.lyht.base.filter;

import com.lyht.Constants;
import com.lyht.util.CommonUtil;
import com.lyht.util.IpUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * session过滤器
 *
 * @author liuamang
 */
@Slf4j
@WebFilter(filterName = "sessionFilter", urlPatterns = { "/*" })
public class SessionFilter implements Filter {
	// 标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
//	private String ERROR_LOGIN = "{code:401,flag:false,msg:'登录已过期，请重新登录'}";
	// 不需要登录就可以访问的路径(比如:注册登录等)
	String[] includeUrls = new String[] { "/lyht/login/login", "/lyht/letters/importExcel" };
	// 不需要登录就可以访问的路径(如静态文件)
	String[] includeFiles = new String[] { "css", "js", "png", "gif", "icon", "jpg", "jpeg", "woff", "map", "ttf",
			"jsp", "mp4" };

//	private LyhtSessionContext sessionContext = LyhtSessionContext.getInstance();

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);
		String uri = request.getRequestURI();
		String ip = IpUtil.getIpAddr(request);// 获取登陆人ip
		String sign = request.getHeader("sign");// 本机服务请求认证
//		String wxToken = request.getHeader("WX-Token");// 微信token

		// 是否需要过滤
		boolean needFilter = isNeedFilter(uri, ip, sign);

		if (!needFilter) { // 不需要过滤直接传给下一个过滤器
			filterChain.doFilter(servletRequest, servletResponse);
		} else { // 需要过滤器
			// pc端登录校验
			if (session != null && session.getAttribute(Constants.SESSION_ACCT) != null) {
				filterChain.doFilter(request, response);
			} else {
				// 小程序登录校验
//				if (StringUtils.isNotBlank(wxToken)) {
//					HttpSession wxSession = sessionContext.getSession(wxToken);
//					if (wxSession != null && wxSession.getAttribute(Constants.SESSION_ACCT) != null) {
//						filterChain.doFilter(request, response);
//					}
//				}
				if("/lyht/abm/split/household/storage/household".equals(uri)||"/lyht/abm/merge/owner/save".equals(uri)){
					filterChain.doFilter(request, response);
				}else {
					log.info("-------------过滤器拦截请求(" + uri + ")------ip:" + ip + "----");
					response.sendError(HttpStatus.SC_UNAUTHORIZED);
					return;
				}
			}
		}
	}

	/**
	 * @Author: xxxxx
	 * @Description: 是否需要过滤
	 * @Date: 2018-03-12 13:20:54
	 * @param uri
	 * @param ip
	 * @param sign
	 * @throws UnknownHostException
	 */
	public boolean isNeedFilter(String uri, String ip, String sign) {
		if (StringUtils.equalsIgnoreCase(sign, "server_request")) {
			return false;
		}
		String fileNameNow = CommonUtil.trim(uri.substring(uri.lastIndexOf(".") + 1));
		if (StringUtils.equalsAnyIgnoreCase(fileNameNow, includeFiles)) {
			return false;
		}
		if (StringUtils.equalsAnyIgnoreCase(uri, includeUrls)) {
			return false;
		}

		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}