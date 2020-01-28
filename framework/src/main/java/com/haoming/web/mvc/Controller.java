package com.haoming.web.mvc;

import java.lang.annotation.*;

/**
 * @author 58212
 * @date 2020-01-28 21:13
 */
//Controller注解注解的类代替类原本的Servlet
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
}
