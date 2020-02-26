package com.macro.fw.web.mvc;

import java.lang.annotation.*;

// 添加元注解
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //只能注解方法
public @interface RequestMapping {
    String value();
}
