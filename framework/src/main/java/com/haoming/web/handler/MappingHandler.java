package com.haoming.web.handler;

import com.haoming.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 映射器 -> DispatcherServlet会调用该Handler使用Handler将
 * url请求发送到对应的Controller
 *
 * @author 58212
 * @date 2020-01-28 22:44
 */
public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        System.out.println("Any Request");
        //若request url匹配
        String requestUri = ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(requestUri)) {
            System.out.println("false: url not matched");
            return false;
        }
        Object[] parameters = new Object[args.length];
        for(int i = 0; i < args.length; i++){
            parameters[i] = req.getParameter(args[i]);
        }
        //controller class
        Object ctl = BeanFactory.getBean(controller);
        Object response = method.invoke(ctl, parameters);
        res.getWriter().println(response.toString());
        return true;
    }

    MappingHandler(String uri, Method method, Class<?> cls, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = cls;
        this.args = args;
    }
}
