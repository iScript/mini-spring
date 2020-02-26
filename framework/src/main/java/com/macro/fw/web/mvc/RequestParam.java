package com.macro.fw.web.mvc;

import java.lang.annotation.*;

// 添加元注解
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)  // 这个注解用于参数上
public @interface RequestParam {
    String value();
}
