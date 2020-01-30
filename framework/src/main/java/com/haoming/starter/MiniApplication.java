package com.haoming.starter;

import com.haoming.beans.BeanFactory;
import com.haoming.core.ClassScanner;
import com.haoming.web.handler.HandlerManager;
import com.haoming.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @author 58212
 * @date 2020-01-28 18:21
 */
//框架入口类
public class MiniApplication {

    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello mini-spring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            //启动内嵌tomcat
            tomcatServer.startServer();
            //通过包名, 获取jar包, 获取class, 获取类名
            List<Class<?>> classList = ClassScanner.
                    scanClasses(cls.getPackage().getName());
            System.out.println("Class Scanning...");
            BeanFactory.initBean(classList);
            System.out.println("Bean init Complete");
            HandlerManager.resolveMappingHandler(classList);
            classList.forEach(it -> System.out.println(it.getName()));
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
