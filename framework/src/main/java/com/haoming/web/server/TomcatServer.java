package com.haoming.web.server;

import com.haoming.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author 58212
 * @date 2020-01-28 19:46
 */
public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();

        //Servlet容器Context
        Context context = new StandardContext();
        //配置该Context容器可以处理的uri
        context.setPath("");
        //注册监听器, 监听指定接口
        context.addLifecycleListener(new Tomcat.FixContextListener());
        //刚刚自定义的servlet
        DispatcherServlet servlet = new DispatcherServlet();
        Tomcat.addServlet(context, "dispatcherServlet", servlet).
                setAsyncSupported(true);
        //配置该servlet可以处理的uri
        context.addServletMappingDecoded("/", "dispatcherServlet");
        //将context放入host容器中
        tomcat.getHost().addChild(context);
        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        //设定线程为非守护线程
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
