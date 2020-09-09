package com.lyht.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: response
 * @Author: xzp
 * @Date: 2019/10/24 5:42 下午
 **/
public class JsonUtil {

    /**
     * @Description: 返回前端JSON格式数据
     * @Author: xzp
     * @Date: 2019/10/9 4:53 下午
     **/
    public static void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding( "UTF-8" );
        response.setContentType( "application/json;charset=utf-8" );
        try {
            writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.print( json );
        } catch (IOException e) {
        }finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
    public static void returnJson(ServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding( "UTF-8" );
        response.setContentType( "application/json;charset=utf-8" );
        try {
            writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.print( json );
        } catch (IOException e) {
        }finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

}