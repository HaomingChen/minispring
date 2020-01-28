package com.haoming;

import com.haoming.starter.MiniApplication;

/**
 * @author 58212
 * @date 2020-01-28 18:04
 */
//应用入口类
public class Application {
    //实际生产代码
    //SpringApplication.run(HelloWorldMainApplication.class, args);
    public static void main(String[] args) {
        System.out.println("Hello World");
        MiniApplication.run(Application.class, args);
    }
}
