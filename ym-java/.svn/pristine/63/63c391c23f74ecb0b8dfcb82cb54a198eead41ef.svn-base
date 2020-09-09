package com.lyht.base.filter;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.config.ResponseWrapper;
import com.lyht.base.common.config.WhiteListEnums;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.util.AesUtil;
import com.lyht.util.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * @Description: 返回值输出过滤器，这里用来加密返回值
 * @Author: xzp
 * @Date: 2020/8/11 11:15
 **/
public class ResponseFilter implements Filter {
 
     public static List<String> list = new ArrayList<String>(){{ for (WhiteListEnums e : WhiteListEnums.values()){ add(e.getKey()); } }};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);//转换成代理类
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        filterChain.doFilter(request, wrapperResponse);
        byte[] content = wrapperResponse.getContent();//获取返回值
        HttpServletRequest request2 = (HttpServletRequest) request;
        String requestURI = request2.getRequestURI();
        System.err.println("请求路径:" + requestURI + "---加密白名单:" + list);
        if (content.length > 0 && !list.contains(requestURI)) {
            String str = new String(content, "UTF-8");
            System.err.println("加密前数据:" + str);
            //String param = AesEncryptUtil.encrypt(str, AesEncryptUtil.KEY, AesEncryptUtil.IV).trim();
            String param = AesUtil.aesEncrypt(str, AesUtil.AES_KEY).trim();
            JsonUtil.returnJson(response, JSON.toJSONString(new LyhtResultBody(param)));
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
