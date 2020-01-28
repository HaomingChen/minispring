package com.haoming.web.mvc;

import java.lang.annotation.*;

/**
 * @author 58212
 * @date 2020-01-28 21:20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value();
}
